package com.ind.sap.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ind.sap.message.MessageEnum;
import com.ind.sap.message.MessageFactory;
import com.ind.sap.service.job.PushJobMgr;

public class StopJobServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("stop");
		MessageFactory.getInstance(MessageEnum.MQTT).setSendMessage(false);
		PushJobMgr.stopJob();
	}
}
