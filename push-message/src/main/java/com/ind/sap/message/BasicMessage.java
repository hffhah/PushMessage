package com.ind.sap.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息基础类。实现类统一把数据填充到map中，供websocket使用。
 *
 */
public abstract class BasicMessage {
	protected Map<Integer, String> map = new HashMap<Integer, String>();
	protected boolean sendMessage;

	public BasicMessage() {
	}

	/**
	 * 获取Message
	 * @param roleId 角色
	 * @return json字符串
	 */
	public abstract String getMessage(int roleId);

	public Map<Integer, String> getMap() {
		return map;
	}

	public void setMap(Map<Integer, String> map) {
		this.map = map;
	}

	public boolean isSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(boolean sendMessage) {
		this.sendMessage = sendMessage;
	}
}
