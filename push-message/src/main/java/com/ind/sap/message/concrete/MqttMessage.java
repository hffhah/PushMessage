package com.ind.sap.message.concrete;

import com.ind.sap.message.BasicMessage;

public class MqttMessage extends BasicMessage {
	public enum MqttMessageType {
		WEBSOCKET_TO_CLIENT, DIRECT_TO_CLIENT;
	}

	@Override
	public String getMessage(int roleId) {
		map.put(1, "websocket message:1111");
		map.put(2, "websocket message:2222");
		map.put(3, "websocket message:3333");
		map.put(4, "websocket message:4444");
		map.put(5, "websocket message:5555");
		map.put(6, "websocket message:6666");
		map.put(7, "websocket message:7777");
		map.put(8, "websocket message:8888");
		map.put(9, "websocket message:9999");
		map.put(10, "websocket message:101010");
		return map.get(roleId);
	}
	
	
	
	
	
}
