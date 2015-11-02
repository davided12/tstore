package com.app.tstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	private Activity activity = this;
	private EditText et_Username;
	private EditText et_Password;
	private Button bt_SignIn;
	private Button btnLinkToMap;

	static String url = "http://192.168.10.157:8080/TechStoreWA/jsp/mobile/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialization
		et_Username = (EditText) findViewById(R.id.et_Username);
		et_Password = (EditText) findViewById(R.id.et_Password);
		bt_SignIn = (Button) findViewById(R.id.bt_SignIn);
		btnLinkToMap = (Button) findViewById(R.id.btnLinkToMap);

		bt_SignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(activity, Order.class);
				intent.putExtra("USERNAME", et_Username.getText().toString());
				intent.putExtra("PASSWORD", et_Password.getText().toString());
				startActivity(intent);
			}
		});
		btnLinkToMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(activity, Map.class);
				startActivity(intent);
			}
		});
	}
}