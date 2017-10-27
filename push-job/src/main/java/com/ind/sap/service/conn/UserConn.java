package com.ind.sap.service.conn;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ind.sap.service.entity.CodeBean;
import com.ind.sap.service.entity.UserBean;
import com.ind.sap.service.global.CodeImpl;

/**
 * 基于JSR356规范的Websocket; Tomcat从7.0.47开始支持JSR356; jdk>7
 * 
 * @author zihaozhu
 *
 */
@ServerEndpoint(value = "/websocket/{user}")
public class UserConn {
	private Session session; // client session
	private UserBean userBean; // user info
	private Basic basicRemote; // conn info
	private final Logger logger = Logger.getLogger(UserConn.class);

	public Basic getBasicRemote() {
		return basicRemote;
	}

	public void setBasicRemote(Basic basicRemote) {
		this.basicRemote = basicRemote;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "user") String user) {
		this.session = session;
		basicRemote = this.session.getBasicRemote();

		logger.info("*** WebSocket opened from sessionId:" + session.getId() + ",uri:" + session.getRequestURI()
				+ ",user=" + user);
	}

	@OnError
	public void onError(Throwable t) {
		logger.info("*** WebSocket error from sessionId:" + this.session.getId() + "error: " + t.getMessage());
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		logger.info(
				"*** WebSocket closed from sessionId:" + this.session.getId() + ",reason:" + reason.getReasonPhrase());
		UserConnMgr.removeUserConn(userBean);
	}

	@OnMessage
	public void onMessage(String message) {
		logger.info("---debug:getMessage sessionId:" + this.session.getId() + ",message:" + message);

		Gson gson = new Gson();
		userBean = gson.fromJson(message, UserBean.class);

		String userName = userBean.getUserName();
		String psd = userBean.getPsd();
		String deviceId = userBean.getDeviceId();

		// verify paras
		verifyParas(userName, psd, deviceId);

		// TODO
		// get user roleId and privilege from db
		
		//add user to userlist
		UserConnMgr.addUserConn(userBean, this);
		
		returnCode(CodeImpl.CODE_RIGHT);
	}

	private void verifyParas(String userName, String psd, String deviceId) {
		if (userName == null || userName.isEmpty() || psd == null || psd.isEmpty() || deviceId == null
				|| deviceId.isEmpty()) {
			returnCode(CodeImpl.CODE_ERROR_PARAMETER);
		}
	}

	private boolean returnCode(int c) {
		boolean flag = false;
		Gson gson = new Gson();
		CodeBean code = new CodeBean();
		code.setCode(c);

		String json = gson.toJson(code);

		try {
			basicRemote.sendText(json);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
