package com.zihao.websocketlogin.activity;

import android.app.Activity;
import android.view.View;

public class BaseActivity extends Activity {

	@SuppressWarnings("unchecked")
	public <T extends View> T getViewById(int id) {
		return (T) findViewById(id);
	}
}
