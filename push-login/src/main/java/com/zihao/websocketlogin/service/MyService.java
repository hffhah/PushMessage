package com.zihao.websocketlogin.service;

import com.google.gson.Gson;
import com.zihao.websocket.broadcast.ReciveMessageBroadCast;
import com.zihao.websocket.global.Constant;
import com.zihao.websocketlogin.entity.UserBean;
import com.zihao.websocketlogin.utils.Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;

public class MyService extends Service {
	public final static WebSocketConnection mConnection = new WebSocketConnection();
	private final String TAG = MyService.class.getSimpleName();
	private String userName, psd;
	private int roleId;
	private Context mContext;
	private ReciveMessageBroadCast messageBroadcast;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "service onCreate");

		messageBroadcast = new ReciveMessageBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.BROADCAST_FLAG);
		registerReceiver(messageBroadcast, filter);

		wsStart();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		this.userName = intent.getStringExtra("userName");
		this.psd = intent.getStringExtra("psd");
		this.roleId = intent.getIntExtra("roleId", -1);
		mContext = getBaseContext();
	}

	private void wsStart() {
		try {
			mConnection.connect(Constant.WS_URI, new WebSocketConnectionHandler() {
				@Override
				public void onOpen() {
					super.onOpen();
					Log.i(TAG, "conn onOpen.");

					String jsonString = setJsonString();

					Log.i(TAG, jsonString);
					mConnection.sendTextMessage(jsonString);
				}

				@Override
				public void onClose(int code, String reason) {
					String logMsg = "conn onClose.code=" + code + ",reason=" + reason;
					Log.i(TAG, logMsg);
					super.onClose(code, reason);
					
					sendBroadCast(logMsg);
				}

				@Override
				public void onTextMessage(String payload) {
					super.onTextMessage(payload);
					Log.i(TAG, "conn receive data=" + payload);

					if (!payload.isEmpty()) {

						sendBroadCast(payload);
					}
				}
			});
		} catch (WebSocketException e) {
			e.printStackTrace();
		}

	}

	private void sendBroadCast(String message) {
		Intent intent = new Intent();
		intent.setAction(Constant.BROADCAST_FLAG);
		intent.putExtra(Constant.BROADCAST_KEY, message);
		sendBroadcast(intent);
	}

	protected String setJsonString() {
		Gson gson = new Gson();
		UserBean user = new UserBean();
		user.setUserName(userName);
		user.setPsd(psd);
		user.setRoleId(roleId);
		user.setDeviceId(Utils.getDeviceId(mContext));
		return gson.toJson(user);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		unregisterReceiver(messageBroadcast);
		
		if (mConnection != null && mConnection.isConnected()) {
			mConnection.disconnect();
		}

		Log.i(TAG, "service onDestroy");
	}

}
