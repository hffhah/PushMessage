package com.ind.sap.service.conn;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ind.sap.service.entity.UserBean;

public class UserConnMgr {
	private static final Map<UserBean, UserConn> USER_CONN_POOL;
	private final static Logger logger;

	static {
		USER_CONN_POOL = new ConcurrentHashMap<UserBean, UserConn>();
		logger = Logger.getLogger(UserConnMgr.class);
	}

	public synchronized static Set<UserBean> getOnlineUser() {
		return USER_CONN_POOL.keySet();
	}

	public synchronized static void addUserConn(UserBean userInfo, UserConn userConn) {
		if (null == userInfo) {
			return;
		}

		for (UserBean user : USER_CONN_POOL.keySet()) {
			if (userInfo.equals(user)) {
				return;
			}
		}

		USER_CONN_POOL.put(userInfo, userConn);

		logger.info("user:" + userInfo.getUserName() + " login in... \nuser count:" + getOnlineUser().size() + "\n");
	}

	public synchronized static void removeUserConn(UserBean userInfo) {
		if (null == userInfo) {
			return;
		}

		for (UserBean user : USER_CONN_POOL.keySet()) {
			if (userInfo.equals(user)) {
				USER_CONN_POOL.remove(userInfo);
				break;
			}
		}

		logger.info("user:" + userInfo.getUserName() + " log off... \nuser count:" + getOnlineUser().size() + "\n");
	}

	public synchronized static void updateUserConn(UserBean oldUserInfo, UserBean newUserInfo) {
		if (null == oldUserInfo) {
			return;
		}
		for (UserBean user : USER_CONN_POOL.keySet()) {
			if (oldUserInfo.equals(user)) {
				UserConn userConn = USER_CONN_POOL.get(oldUserInfo);
				USER_CONN_POOL.remove(oldUserInfo);
				USER_CONN_POOL.put(newUserInfo, userConn);
				break;
			}
		}

		logger.info("user:" + newUserInfo.getUserName() + " update user info... \nuser count:" + getOnlineUser().size()
				+ "\n");
	}

	public static void sendMsgToUser(String deviceId, String message) {
		for (UserBean user : USER_CONN_POOL.keySet()) {
			if (user.getDeviceId().equals(deviceId)) {
				UserConn userConn = USER_CONN_POOL.get(user);
				if (null != userConn) {
					try {
						userConn.getBasicRemote().sendText(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void sendMsgToAllUser(String message) {
		for (UserBean user : USER_CONN_POOL.keySet()) {
			UserConn userConn = USER_CONN_POOL.get(user);
			if (null != userConn) {
				try {
					userConn.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}