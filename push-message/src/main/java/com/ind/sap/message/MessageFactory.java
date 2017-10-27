package com.ind.sap.message;

import com.ind.sap.message.concrete.CimMessage;
import com.ind.sap.message.concrete.DbMessage;
import com.ind.sap.message.concrete.MqttMessage;
import com.ind.sap.message.concrete.OpcMessage;

public class MessageFactory {
	private static BasicMessage message;
	private static Object object = new Object();

	private MessageFactory() {
	}

	public static BasicMessage getInstance(MessageEnum messageType) {
		if (null == message) {
			synchronized (object) {
				if (null == message) {
					switch (messageType) {
					case DB:
						message = new DbMessage();
						break;
					case MQTT:
						message = new MqttMessage();
						break;
					case OPC:
						message = new OpcMessage();
						break;
					case CIM:
						message = new CimMessage();
						break;
					default:
						break;
					}
				}
			}
		}
		return message;
	}
}
