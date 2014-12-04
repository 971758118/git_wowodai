package com.example.zhanghu;

import com.example.baozhang.Baozhang_detailActivity;
import com.example.common.GetCodeInfo;
import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.MyMD5Util;
import com.example.common.TypeChange;
import com.example.wowodai.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Zhsz_xgssmmActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private Context context;
	private MyHeadView myHeadView;
	private LinearLayout layout_head_back;
	private MyMD5Util myMD5Util;
	private GetCodeInfo getCodeInfo;
	private EditText et_confirm, et_new, et_original;
	private CheckBox ckb_ok;
	private Button btn_ok;
	private TextView tv_agreement;
	private String resultStr = null;// 修改密码时服务器返回的信息
	private String str_confirm, str_new, str_original, userId;
	private Handler getCodeInfoHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
				Toast.makeText(context, resultStr, Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhsz_xgssmm);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		myMD5Util = new MyMD5Util();
		getCodeInfo = new GetCodeInfo();
		userId = myApplication.getUserId();
		//init();
	}

	private void init() {
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("修改登录密码");

		et_confirm = (EditText) findViewById(R.id.et_confirm);
		et_new = (EditText) findViewById(R.id.et_new);
		et_original = (EditText) findViewById(R.id.et_original);
		tv_agreement = (TextView) findViewById(R.id.tv_agreement);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		ckb_ok = (CheckBox) findViewById(R.id.ckb_ok);

		tv_agreement.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	private void updataPassworld(final String url) {
		Log.i("", "---url:" + url);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				resultStr = getCodeInfo.getCodeInfo(typeChange
						.object2String(httpUtils.getCodeFromUrl(url)));
				Log.i("", "---resultStr:" + resultStr);
				Message message = Message.obtain();
				if (resultStr != null) {
					message.what = 1;
					getCodeInfoHandler.sendMessage(message);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.layout_head_back:
			finish();
			break;
		case R.id.tv_agreement:
			String webViewUrl = "http://manage.55178.com/mobile/page/shiyongxieyi.html";
			String titleString = "握握贷用户协议";
			Intent intent = new Intent(context, Baozhang_detailActivity.class);
			intent.putExtra("webViewUrl", webViewUrl);
			intent.putExtra("titleString", titleString);
			startActivity(intent);

			break;
		case R.id.btn_ok:
			if (!ckb_ok.isChecked()) {
				Toast.makeText(context, "请同意用户协议", Toast.LENGTH_SHORT).show();
				break;
			}
			str_confirm = typeChange.editable2String(et_confirm.getText());
			str_new = typeChange.editable2String(et_new.getText());
			str_original = typeChange.editable2String(et_original.getText());
			String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=ModifyPassword&User_ID="
					+ userId
					+ "&Repassword="
					+ str_confirm
					+ "&Newpassword="
					+ str_new + "&Password=" + str_original;

			updataPassworld(url);

			break;

		}

	}

}
