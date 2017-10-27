package com.ind.sap.service.job;

import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.zihao.utils.mqtt.Constant;
import org.zihao.utils.mqtt.MqttMgr;

import com.google.gson.Gson;
import com.ind.sap.message.MessageEnum;
import com.ind.sap.message.MessageFactory;
import com.ind.sap.message.entity.AlarmBean;
import com.ind.sap.message.entity.AndonMessage;
import com.ind.sap.message.entity.ProductionBean;
import com.ind.sap.service.conn.UserConnMgr;
import com.ind.sap.service.entity.UserBean;

public class PushJob implements Job {
	private final static MessageEnum MESSAGE_TYPE = MessageEnum.MQTT;
	private final static Logger logger = Logger.getLogger(PushJob.class);

	public PushJob() {
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		sendByWebSocket();

		sendByMqtt();
	}

	/***
	 * 使用MQTT直接发送给用户(测试用)
	 */
	private void sendByMqtt() {
		// 封装Json数据
		ProductionBean prod = new ProductionBean();
		prod.setName("FIN_");
		prod.setDesc("final line");
		int random = new Double(Math.random() * 1000).intValue(); // 1000以内随机整数
		prod.setCount(random);

		AlarmBean alarm = new AlarmBean();
		alarm.setName("FIN_");
		alarm.setDesc("final line alarm");
		alarm.setAlarm(true);

		AndonMessage andonMessage = new AndonMessage();
		andonMessage.setProductionBean(prod);
		andonMessage.setAlarm(alarm);

		String json = new Gson().toJson(andonMessage);

		MqttMgr mqttMgr = new MqttMgr(Constant.MQTT_BROKER_URL);
		mqttMgr.publish(Constant.MQTT_TOPIC, json);

		logger.info("---debug:订阅主题：" + Constant.MQTT_TOPIC + ",发送消息:" + json + "\n");
	}

	/**
	 * 使用WebSocket协议发送给用户
	 */
	private void sendByWebSocket() {
		if (!sendMessage()) {
			return;
		}

		Set<UserBean> onlineUser = UserConnMgr.getOnlineUser();
		if (onlineUser.size() == 0) {
			return;
		}

		for (UserBean user : onlineUser) {
			String message = getMessage(user.getRoleId());
			if (null != message && message != "") {
				logger.info("---debug:发送信息... user=" + user.getUserName() + ",message=" + message);
				UserConnMgr.sendMsgToUser(user.getDeviceId(), message);
			}
		}

		logger.info("---debug:使用WebSocket发送数据...\n");
	}

	private boolean sendMessage() {
		return MessageFactory.getInstance(MESSAGE_TYPE).isSendMessage();
	}

	private String getMessage(int roleId) {
		String message = MessageFactory.getInstance(MESSAGE_TYPE).getMessage(roleId);
		return message;
	}
}
