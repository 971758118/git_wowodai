package com.example.main;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baozhang.Baozhang_detailActivity;
import com.example.common.GetCodeInfo;
import com.example.common.HttpUtils;
import com.example.common.MyApplication;
import com.example.common.MyMD5Util;
import com.example.common.TypeChange;
import com.example.main.RegisterActivity.TimeCount;
import com.example.wowodai.R;

public class RegisterActivity2 extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private Context context;
	private InputMethodManager inputMethodManager;// 得到软键盘
	private String identifyingCode;// 服务器返回的验证码
	private HashMap<String, Object> resultMap = new HashMap<String, Object>();// 用户注册成功,服务器返回的数据
	private String userPhone, userName, userPswd, userIdentifyingCode;// 用户输入的信息
	private TimeCount time;// 倒计时
	private MyMD5Util myMD5Util;
	private GetCodeInfo getCodeInfo;
	private boolean isRegister = false;// 用户是否已经获得验证码
	private LinearLayout layout_head_back;
	private EditText et_name, et_phone, et_pswd, et_code;
	private Button btn_ok, btn_identifyingCode;
	private CheckBox ckb_ok;
	private TextView tv_agreement, tv_head_title;

	private String resultStr = null;// 修改密码时服务器返回的信息
	private String userId;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {
				if (isRegister) {
					if (resultStr.equals("获取数据成功")) {
						myApplication.setUserId(typeChange
								.object2String(resultMap.get("User_ID")));
						myApplication.setUserName(typeChange
								.object2String(resultMap.get("User_Name")));

						Intent intent = new Intent(context, MainActivity.class);
						intent.putExtra("loginSuccess", true);
						startActivity(intent);
						finish();

					} else {
						Toast.makeText(context, resultStr, Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					if (resultStr.equals("获取数据成功")) {
						isRegister = true;
						Toast.makeText(context, "验证码已发送到手机上,注意查收",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(context, resultStr, Toast.LENGTH_SHORT)
								.show();
					}
				}

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_register2);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		myMD5Util = new MyMD5Util();
		getCodeInfo = new GetCodeInfo();
		userId = myApplication.getUserId();
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initView();
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

	private void initView() {

		layout_head_back = (LinearLayout) findViewById(R.id.layout_head_back);

		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_pswd = (EditText) findViewById(R.id.et_pswd);
		et_code = (EditText) findViewById(R.id.et_code);

		tv_agreement = (TextView) findViewById(R.id.tv_agreement);
		tv_head_title = (TextView) findViewById(R.id.tv_head_title);
		tv_head_title.setText("注册");

		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_identifyingCode = (Button) findViewById(R.id.btn_identifyingCode);

		ckb_ok = (CheckBox) findViewById(R.id.ckb_ok);

		tv_agreement.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		btn_identifyingCode.setOnClickListener(this);

		ckb_ok.setOnClickListener(this);
		layout_head_back.setOnClickListener(this);

	}

	private void getIdentifyingCode() {
		final String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getMobileCode"
				+ "&Mobile=" + userPhone;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				resultStr = getCodeInfo.getCodeInfo(typeChange
						.object2String(httpUtils.getCodeFromUrl(url)));
				identifyingCode = typeChange.object2String(httpUtils
						.getMapResultFromUrl(url).get("Codes"));
				Message message = Message.obtain();
				if (resultStr != null) {
					message.what = 1;
					handler.sendMessage(message);
				}

			}
		}).start();

	}

	private void register() {
		final String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=UserRegister&User_Name="
				+ userName
				+ "&Mobile="
				+ userPhone
				+ "&Password="
				+ myMD5Util.getMD5Str(userPswd)
				+ "&Codes="
				+ userIdentifyingCode;
		new Thread(new Runnable() {

			@Override
			public void run() {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map = (HashMap<String, Object>) httpUtils.getMapFromUrl(url);
				if (map != null && map.containsKey("code")) {
					resultStr = getCodeInfo.getCodeInfo(map.get("code")
							.toString());
					if (!map.get("result").equals(false)) {
						resultMap = (HashMap<String, Object>) map.get("result");
					}

				}

				Message message = Message.obtain();
				if (resultStr != null) {
					message.what = 1;
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	public class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btn_identifyingCode.setText("重新获取");
			btn_identifyingCode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_identifyingCode.setClickable(false);
			btn_identifyingCode.setVisibility(View.VISIBLE);
			btn_identifyingCode.setText("(" + millisUntilFinished / 1000
					+ ")重新获取");
		}
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

		if (getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}

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
			if (!isRegister) {
				Toast.makeText(context, "请先获取验证码", Toast.LENGTH_SHORT).show();
			} else if (et_name.getText().toString().length() < 1) {
				Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
				break;
			} else if (et_pswd.getText().length() < 1) {
				Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
			} else if (et_code.getText().length() < 1) {
				Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
			} else {

				userIdentifyingCode = et_code.getText().toString();
				userName = et_name.getText().toString();
				userPswd = et_pswd.getText().toString();

				register();
				break;
			}

		case R.id.btn_identifyingCode:
			if (et_phone.getText().length() < 5) {
				Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				break;
			}
			userPhone = et_phone.getText().toString();
			if (time == null) {
				time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
			} else {
				time.cancel();
				time = new TimeCount(60000, 1000);// 重新构造CountDownTimer对象
			}
			time.start();
			getIdentifyingCode();

			break;

		}

	}

}
