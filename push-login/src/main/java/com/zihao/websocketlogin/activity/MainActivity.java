package com.zihao.websocketlogin.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.protocol.HTTP;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.zihao.utils.mqtt.Constant;
import org.zihao.utils.mqtt.MqttMgr;
import org.zihao.utils.mqtt.PushCallback;

import com.ind.sap.service.push_login.R;
import com.zihao.websocketlogin.app.MyApp;
import com.zihao.websocketlogin.service.MyService;
import com.zihao.websocketlogin.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	private Button btnLoginIn;
	private Intent intent;
	private String loginIn = "Login in";
	private String stopService = "Stop service";
	private TextView txt1;
	private TextView textMqttMsg;
	private String servicePackage = "com.zihao.websocketlogin.service.MyService";
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Bundle b = msg.getData();

				String message = b.get("msg").toString();
				try {
					// 乱码问题
					String content = new String(message.getBytes(HTTP.DEFAULT_PROTOCOL_CHARSET), HTTP.UTF_8);
					MainActivity.this.setMqttMessage(content);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	};

	enum enum1 {
		LoginIn, LogOff;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnLoginIn = (Button) getViewById(R.id.btnLoginIn);
		txt1 = (TextView) getViewById(R.id.txt1);
		textMqttMsg = (TextView) getViewById(R.id.txtMqttMsg);

		this.btnLoginIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String btnText = MainActivity.this.btnLoginIn.getText().toString();
				if (btnText == stopService) {
					if (Utils.isServiceRunning(MainActivity.this, servicePackage)) {
						System.out.println("---service now running");
						intent = new Intent(MainActivity.this, MyService.class);
						stopService(intent);
					}
					MainActivity.this.btnLoginIn.setText(loginIn);

				} else {
					intent = new Intent(MainActivity.this, LoginActivity.class);
					startActivity(intent);

					((MyApp) getApplication()).setMainActivity(MainActivity.this);
				}
			}
		});
		subscribeMqttMsg();
	}

	private void subscribeMqttMsg() {
		MqttMgr mqttMgr = new MqttMgr(Constant.MQTT_BROKER_URL);

		mqttMgr.subscribe(Constant.MQTT_TOPIC, new PushCallback() {
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				super.messageArrived(topic, message);
				// 无法直接修改UI
				Message msg = new Message();
				msg.what = 1;

				Bundle b = new Bundle();
				b.putString("msg", new String(message.getPayload()));
				msg.setData(b);

				MainActivity.this.handler.sendMessage(msg);

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("on Resume");

		boolean logined = ((MyApp) getApplication()).isLogined();
		if (logined) {
			this.btnLoginIn.setText(stopService);
		} else {
			this.btnLoginIn.setText(loginIn);
		}

	}

	public void setMessage(String message) {
		this.txt1.setText(message);
	}

	public void setMqttMessage(String message) {
		this.textMqttMsg.setText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
