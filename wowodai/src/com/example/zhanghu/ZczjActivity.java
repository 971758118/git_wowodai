package com.example.zhanghu;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.MyHeadView;
import com.example.common.MyApplication;
import com.example.common.TypeChange;
import com.example.wowodai.R;

public class ZczjActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private ImageDownLoader imageDownLoader;
	private Context context;
	private TextView tv_kyye, tv_djzj, tv_dsbj, tv_dssy, tv_kytzq;
	private MyHeadView myHeadView;
	private LinearLayout layout_head_back;
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
		setContentView(R.layout.zhanghu_zczj);
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
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("资产总计");
		tv_kyye = (TextView) findViewById(R.id.tv_kyye);
		tv_djzj = (TextView) findViewById(R.id.tv_djzj);
		tv_dsbj = (TextView) findViewById(R.id.tv_dsbj);
		tv_dssy = (TextView) findViewById(R.id.tv_dssy);
		tv_kytzq = (TextView) findViewById(R.id.tv_kytzq);
	}

	private void initData() {
		tv_kyye.setText(typeChange.object2String(dataMap.get("Money_Available")));
		tv_djzj.setText(typeChange.object2String(dataMap.get("Money_Frozen")));
		tv_dsbj.setText(typeChange.object2String(dataMap
				.get("Money_Investment")));
		tv_dssy.setText(typeChange.object2String(dataMap
				.get("Collect_ interest")));
		tv_kytzq.setText(typeChange.object2String(dataMap.get("chit")));
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
		switch (v.getId()) {
		case R.id.layout_head_back:
			finish();
			break;

		default:
			break;
		}

	}

}
