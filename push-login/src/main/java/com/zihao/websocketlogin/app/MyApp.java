package com.zihao.websocketlogin.app;

import com.zihao.websocketlogin.activity.MainActivity;

import android.app.Application;

public class MyApp extends Application {

	private boolean logined;
	private MainActivity mainActivity;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public boolean isLogined() {
		return logined;
	}

	public void setLogined(boolean logined) {
		this.logined = logined;
	}

	public MainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

}
