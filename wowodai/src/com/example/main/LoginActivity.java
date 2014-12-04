package com.example.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.GetCodeInfo;
import com.example.common.HttpUtils;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.MyMD5Util;
import com.example.common.TypeChange;
import com.example.wowodai.R;

public class LoginActivity extends Activity implements OnClickListener {

	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private Context context;
	private GetCodeInfo getCodeInfo;
	private InputMethodManager inputMethodManager;// 得到软键盘
	private MyMD5Util myMD5Util;
	private MyHeadView myHeadView;
	private LinearLayout layout_head_back;
	private TextView tv_title, tv_head_ok;
	private EditText et_name, et_pswd;
	private Button btn_ok;
	private String userName, userPswd;
	private Map<String, Object> mapResult;// 提交用户名和密码后返回的数据
	private boolean isQuitSuccess = false;// 登录用户是否退出成功，是否为点击退出按钮跳转过来的
	private Handler loginSuccessHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (typeChange.object2String(mapResult.get("code")).equals(
						"200")) {
					HashMap<String, Object> resultMap = new HashMap<String, Object>();
					resultMap = (HashMap<String, Object>) mapResult
							.get("result");
					myApplication.setUserId(typeChange.object2String(resultMap
							.get("User_ID")));
					myApplication.setLogin(true);
					if (isQuitSuccess) {
						Intent intent = new Intent(context, MainActivity.class);
						intent.putExtra("loginSuccess", true);
						startActivity(intent);
						finish();
					} else {
						setResult(101);// 此页面不是点击退出按钮跳转过来的
						finish();
					}

				} else {
					Toast.makeText(
							context,
							getCodeInfo.getCodeInfo(typeChange
									.object2String(mapResult.get("code"))),
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mian_login);
		context = this;
		httpUtils = new HttpUtils();
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		myMD5Util = new MyMD5Util();
		getCodeInfo = new GetCodeInfo();
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getIntent() != null) {
			isQuitSuccess = getIntent().getBooleanExtra("isQuitSuccess", false);

		}
		init();
	}

	private void init() {
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		tv_head_ok = myHeadView.getTv_head_ok();
		tv_head_ok.setVisibility(View.VISIBLE);
		tv_head_ok.setText("注册");
		tv_head_ok.setOnClickListener(this);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("登录");

		btn_ok = (Button) findViewById(R.id.btn_ok);
		et_name = (EditText) findViewById(R.id.et_name);
		et_pswd = (EditText) findViewById(R.id.et_pswd);

		btn_ok.setOnClickListener(this);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		// 点击空白,隐藏输入法
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuitSuccess) {
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				finish();
			}

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
		switch (v.getId()) {
		case R.id.btn_ok:
			if (et_name.getText() == null || et_pswd.getText() == null) {
				Toast.makeText(context, "请输入有效信息", Toast.LENGTH_SHORT).show();
				break;
			}
			userName = et_name.getText().toString();
			userPswd = et_pswd.getText().toString();

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					mapResult = new HashMap<String, Object>();
					String u = "http://manage.55178.com/mobile/api.php?module=usermod&act=UserLogin&User_Name="
							+ userName
							+ "&User_Password="
							+ myMD5Util.getMD5Str(userPswd);
					mapResult = httpUtils.getMapFromUrl(u);
					if (mapResult != null) {
						Message message = Message.obtain();
						message.what = 1;
						loginSuccessHandler.sendMessage(message);
					}
				}
			}).start();

			break;
		case R.id.layout_head_back:
			if (isQuitSuccess) {
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				finish();
			}

			break;
		case R.id.tv_head_ok:
			Intent intent = new Intent(context, RegisterActivity2.class);
			startActivity(intent);

			break;

		}

	}

}
