package com.zihao.websocketlogin.activity;

import com.ind.sap.service.push_login.R;
import com.zihao.websocketlogin.app.MyApp;
import com.zihao.websocketlogin.service.MyService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
	private EditText textUserName;
	private EditText textPsd;
	private EditText textRoleId;

	String userName, psd;
	int roleId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		init();
	}

	private void init() {
		textUserName = (EditText) getViewById(R.id.editTextUserName);
		textPsd = (EditText) getViewById(R.id.editTextPas);
		textRoleId = (EditText) getViewById(R.id.editTextRoleId);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnOK:
			userName = textUserName.getText().toString().trim();
			psd = textPsd.getText().toString().trim();

			if (checkUser(userName, psd)) {
				roleId = Integer.parseInt(textRoleId.getText().toString().trim());

				Intent intent = new Intent(LoginActivity.this, MyService.class);
				intent.putExtra("userName", userName);
				intent.putExtra("psd", psd);
				intent.putExtra("roleId", roleId);
				startService(intent);

				((MyApp) getApplication()).setLogined(true);
			} else {
				Toast.makeText(LoginActivity.this, "用户、密码输入为空！", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.btnCancle:

			break;
		}

		this.finish();
	}

	private boolean checkUser(String textUserName, String textPsd) {
		if (userName.equals("") || textPsd.equals("")) {
			return false;
		}
		return true;
	}

}
