package org.zihao.utils.mqtt;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 
 * @author zihaozhu
 *
 */
public class MqttMgr {
	private String brokenRUL;
	private String clientId;

	// 客户端对象
	private MqttClient client; 
	// 连接对象
	private MqttConnectOptions options; 

	private String userName;
	private String psd;

	public MqttMgr(String rul) {
		this.brokenRUL = rul;
		this.clientId = MqttClient.generateClientId();

		try {
			this.client = new MqttClient(rul, this.clientId, new MemoryPersistence());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public MqttMgr(String url, String userName, String psd) {
		this.brokenRUL = url;
		this.clientId = MqttClient.generateClientId();
		this.userName = userName;
		this.psd = psd;

		initOptions();
	}

	private void initOptions() {
		try {
			client = new MqttClient(this.brokenRUL, this.clientId, new MemoryPersistence());

			options = new MqttConnectOptions();
			// false 服务端保留连接信息
			options.setCleanSession(true); 
			options.setUserName(userName);
			options.setPassword(psd.toCharArray());
			options.setConnectionTimeout(10);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void subscribe(String topic) {
		try {
			client.setCallback(new PushCallback());

			if (options == null) {
				client.connect();
				System.out.println("options == null");
			} else {
				client.connect(options);
				System.out.println("options != null");
			}

			client.subscribe(topic);

		} catch (Exception e) {
			e.printStackTrace();
			disconnect();
		}
	}

	/**
	 * 订阅主题
	 * 
	 * @param topic
	 *            主题
	 * @param pushCallback
	 *            接收到消息的回调函数
	 */
	public void subscribe(String topic, PushCallback pushCallback) {
		try {
			client.setCallback(pushCallback);

			if (options == null) {
				client.connect();
				System.out.println("options == null");
			} else {
				client.connect(options);
				System.out.println("options != null");
			}

			client.subscribe(topic);

		} catch (Exception e) {
			e.printStackTrace();
			disconnect();
		}
	}

	public void publish(String topic, String message) {
		try {
			client.connect(options);

			MqttTopic mqttTopic = client.getTopic(topic);
			MqttMessage mqttMessage = new MqttMessage();
			try {
				String s = new String(message.getBytes(), "UTF-8");
				mqttMessage.setPayload(s.getBytes());
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				mqttMessage.setPayload(message.getBytes());
			}
			
			mqttTopic.publish(mqttMessage);

		} catch (MqttException e) {
			e.printStackTrace();
			disconnect();
		}
	}

	public void disconnect() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
