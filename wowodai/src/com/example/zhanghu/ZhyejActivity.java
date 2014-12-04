package com.example.zhanghu;

import java.util.HashMap;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.TypeChange;
import com.example.wowodai.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZhyejActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private ImageDownLoader imageDownLoader;
	private MyHeadView myHeadView;
	private LinearLayout layout_head_back;
	private Context context;
	private TextView tv_zhye;
	private RelativeLayout layout_zhye, layout_cz, layout_tx;
	private HashMap<String, Object> dataMap;// 数据源
	private String userId;
	private Handler initDataHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				initData();

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhanghu_zhye);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		userId = myApplication.getUserId();
		imageDownLoader = new ImageDownLoader(context);
		init();
		getData();
	}

	private void init() {
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("账户余额");
		tv_zhye = (TextView) findViewById(R.id.tv_zhye);
		layout_zhye = (RelativeLayout) findViewById(R.id.layout_zhye);
		layout_cz = (RelativeLayout) findViewById(R.id.layout_cz);
		layout_tx = (RelativeLayout) findViewById(R.id.layout_tx);

		layout_zhye.setOnClickListener(this);
		layout_cz.setOnClickListener(this);
		layout_tx.setOnClickListener(this);

	}

	private void initData() {
		tv_zhye.setText(typeChange.object2String(dataMap.get("Money_Available")));

	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://www.baidu.com" + userId;
				dataMap = httpUtils.getMapResultFromUrl(url);
				if (dataMap != null) {
					Message message = Message.obtain();
					message.what = 1;
					initDataHandler.sendMessage(message);
				}

			}
		}).start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_head_back:
			finish();
			break;
		case R.id.layout_zhye:

			break;
		case R.id.layout_cz:

			break;
		case R.id.layout_tx:

			break;

		}

	}

}
