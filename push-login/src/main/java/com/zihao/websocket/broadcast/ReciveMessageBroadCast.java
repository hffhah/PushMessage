package com.zihao.websocket.broadcast;

import com.zihao.websocket.global.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.zihao.websocketlogin.activity.MainActivity;
import com.zihao.websocketlogin.app.MyApp;

public class ReciveMessageBroadCast extends BroadcastReceiver {
	private Context context;
	private Intent intent;
	private MainActivity mainAty;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				mainAty = ((MyApp) context.getApplicationContext()).getMainActivity();
				String message = intent.getStringExtra(Constant.BROADCAST_KEY);
				mainAty.setMessage(message);
			}
		}
	};

	public ReciveMessageBroadCast() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("--------on receive");
		this.context = context;
		this.intent = intent;

		Message message = new Message();
		message.what = 1;
		this.handler.sendMessage(message);
	}

}
