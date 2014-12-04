package com.example.main;

import com.example.wowodai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Init extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initlayout);
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(1500);
				} catch (InterruptedException e) {
				}
				Intent intent = new Intent(Init.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}.start();
	}
}
