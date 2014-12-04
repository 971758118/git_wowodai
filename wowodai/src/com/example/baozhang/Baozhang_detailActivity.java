package com.example.baozhang;

import com.example.common.HttpUtils;
import com.example.common.MyApplication;
import com.example.common.TypeChange;
import com.example.wowodai.R;
import com.example.xiangmu.XiangMuDetailActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Baozhang_detailActivity extends Activity implements
		OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private Context context;
	private TypeChange typeChange;
	private WebView webView;
	private String webViewUrl = "";// webView 加载的url
	private String titleString = "";
	private LinearLayout layout_back;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baozhang_detail);
		context = Baozhang_detailActivity.this;
		httpUtils = new HttpUtils();
		myApplication = (MyApplication) getApplication();
		typeChange = new TypeChange();
		webViewUrl = getIntent().getStringExtra("webViewUrl");
		titleString = getIntent().getStringExtra("titleString");
		if (webViewUrl != "" && webViewUrl != null) {
			init();
		}

	}

	private void init() {
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(webViewUrl);
		layout_back = (LinearLayout) findViewById(R.id.layout_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titleString);
		layout_back.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_back:
			finish();
			break;

		}

	}

}
