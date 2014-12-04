package com.example.zhanghu;

import java.util.HashMap;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.TypeChange;
import com.example.suoping.UnlockGesturePasswordActivity;
import com.example.wowodai.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ZhszActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private ImageDownLoader imageDownLoader;
	private Context context;
	private MyHeadView myHeadView;
	private LinearLayout layout_head_back;
	private RelativeLayout layout_xgdlmm, layout_xgsjhm, layout_xgssmm;
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
		setContentView(R.layout.zhanghu_zhsz);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		imageDownLoader = new ImageDownLoader(context);
		dataMap = new HashMap<String, Object>();
		userId = myApplication.getUserId();
		init();
		getData();
	}

	private void init() {
		layout_xgdlmm = (RelativeLayout) findViewById(R.id.layout_xgdlmm);
		layout_xgsjhm = (RelativeLayout) findViewById(R.id.layout_xgsjhm);
		layout_xgssmm = (RelativeLayout) findViewById(R.id.layout_xgssmm);
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("账户设置");

		layout_xgdlmm.setOnClickListener(this);
		layout_xgsjhm.setOnClickListener(this);
		layout_xgssmm.setOnClickListener(this);

	}

	private void initData() {

	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getMoneyInfo&User_ID="
						+ userId;
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
		Intent intent = null;

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_head_back:
			finish();
			break;
		case R.id.layout_xgdlmm:
			intent = new Intent(context, Zhsz_xgdlmmActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_xgsjhm:
			intent = new Intent(context, Zhsz_xgsjhmActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_xgssmm:
			// intent = new Intent(context, Zhsz_xgssmmActivity.class);
			intent = new Intent(context, UnlockGesturePasswordActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

}
