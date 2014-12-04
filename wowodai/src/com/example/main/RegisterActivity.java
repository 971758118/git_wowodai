package com.example.main;

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
import com.example.wowodai.R;

public class RegisterActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private Context context;
	private InputMethodManager inputMethodManager;// 得到软键盘
	private String identifyingCode;// 从服务器获取到验证码
	private String user_identifyingCode;// 用户输入的验证码
	private String phoneNO, userName, userPswd;// 输入的手机号,用户名
	private TimeCount time;// 倒计时
	private MyMD5Util myMD5Util;
	private GetCodeInfo getCodeInfo;
	private int currentNO = 1;// 当前是第几步
	private LinearLayout layout_success, layout_head_back;
	private EditText et_one, et_two;
	private Button btn_invite, btn_ok, btn_head_ok;
	private CheckBox ckb_ok;
	private TextView tv_agreement, tv_head_title, tv_step1, tv_step2, tv_step3,
			tv_sendSuccess, tv_head_ok;

	private String resultStr = null;// 修改密码时服务器返回的信息
	private String userId;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 1) {

				if (resultStr.equals("获取数据成功")) {
					switch (currentNO) {
					case 1:
						currentNO = 2;// 输入手机号正确,开始显示第二个页面
						initSecondView();
						break;
					case 2:
						currentNO = 3;
						initThirdView();// 输入的验证码正确,开始显示第三个页面
						break;
					case 3:
						currentNO = 4;
						initFourthView();
						break;
					default:
						break;
					}

				} else {
					Toast.makeText(context, resultStr, Toast.LENGTH_SHORT)
							.show();
				}

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_register);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		myMD5Util = new MyMD5Util();
		getCodeInfo = new GetCodeInfo();
		userId = myApplication.getUserId();
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initFristView();
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

	private void initFristView() {

		layout_head_back = (LinearLayout) findViewById(R.id.layout_head_back);
		layout_success = (LinearLayout) findViewById(R.id.layout_success);

		et_one = (EditText) findViewById(R.id.et_one);
		et_two = (EditText) findViewById(R.id.et_two);

		tv_agreement = (TextView) findViewById(R.id.tv_agreement);
		tv_head_title = (TextView) findViewById(R.id.tv_head_title);
		tv_step1 = (TextView) findViewById(R.id.tv_step1);
		tv_step2 = (TextView) findViewById(R.id.tv_step2);
		tv_step3 = (TextView) findViewById(R.id.tv_step3);
		tv_sendSuccess = (TextView) findViewById(R.id.tv_sendSuccess);
		tv_head_ok = (TextView) findViewById(R.id.tv_head_ok);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_invite = (Button) findViewById(R.id.btn_invite);
		btn_head_ok = (Button) findViewById(R.id.btn_head_ok);
		ckb_ok = (CheckBox) findViewById(R.id.ckb_ok);

		tv_head_title.setText("修改手机号码");
		tv_step1.setTextColor(Color.RED);
		tv_step2.setTextColor(Color.GRAY);
		tv_step3.setTextColor(Color.GRAY);
		tv_agreement.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		btn_invite.setOnClickListener(this);
		btn_head_ok.setOnClickListener(this);
		ckb_ok.setOnClickListener(this);
		layout_head_back.setOnClickListener(this);
		tv_head_ok.setOnClickListener(this);

		tv_sendSuccess.setVisibility(View.GONE);
		btn_ok.setText("提交密码");
	}

	// 显示"2 输入验证码"这个页面
	private void initSecondView() {
		et_two.setVisibility(View.GONE);
		ckb_ok.setVisibility(View.GONE);
		tv_agreement.setVisibility(View.GONE);
		et_one.setText("");
		et_one.setHint("请输入您短信中的验证码");
		tv_step1.setTextColor(Color.GRAY);
		tv_step2.setTextColor(Color.RED);
		tv_step3.setTextColor(Color.GRAY);
		tv_head_title.setText("输入验证码");

		if (time == null) {
			time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
		} else {
			time.cancel();
			time = new TimeCount(60000, 1000);// 重新构造CountDownTimer对象
		}

		String tv_sendSuccessText = "验证码短信已发送到手机" + phoneNO.substring(0, 3)
				+ "****"
				+ phoneNO.substring((phoneNO.length() - 4), phoneNO.length());
		tv_sendSuccess.setVisibility(View.VISIBLE);
		tv_sendSuccess.setText(tv_sendSuccessText);

		btn_ok.setText("提交验证码");
		btn_head_ok.setVisibility(View.VISIBLE);
		time.start();
		btn_head_ok.setOnClickListener(this);
	}

	// 显示"3 设置"这个页面
	private void initThirdView() {
		tv_sendSuccess.setVisibility(View.GONE);
		et_two.setVisibility(View.VISIBLE);
		et_one.setText("");
		et_two.setText("");
		et_one.setHint("设置密码:");
		et_two.setHint("确认密码:");
		btn_ok.setText("提交密码");
		et_one.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		et_two.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		if (time != null) {
			time.cancel();
		}

		btn_head_ok.setVisibility(View.GONE);
		tv_step1.setTextColor(Color.GRAY);
		tv_step2.setTextColor(Color.GRAY);
		tv_step3.setTextColor(Color.RED);

		tv_head_title.setText("设置密码");

	}

	private void initFourthView() {
		tv_head_ok.setVisibility(View.VISIBLE);
		tv_head_ok.setText("放弃");
		tv_head_title.setText("注册成功");
		layout_success.setVisibility(View.VISIBLE);
	}

	private void getIdentifyingCode() {
		final String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getMobileCode"
				+ "&Mobile=" + phoneNO;
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

	private void submitIdentifyingCode() {

		final String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=ModifyMobile&User_ID="
				+ userId
				+ "&Newmobile="
				+ phoneNO
				+ "&Codes="
				+ user_identifyingCode;
		new Thread(new Runnable() {

			@Override
			public void run() {
				resultStr = getCodeInfo.getCodeInfo(typeChange
						.object2String(httpUtils.getCodeFromUrl(url)));
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
				+ phoneNO
				+ "&Password="
				+ userPswd
				+ "&Codes=" + user_identifyingCode;
		new Thread(new Runnable() {

			@Override
			public void run() {
				resultStr = getCodeInfo.getCodeInfo(typeChange
						.object2String(httpUtils.getCodeFromUrl(url)));
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
			btn_head_ok.setText("重新获取");
			btn_head_ok.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_head_ok.setClickable(false);
			btn_head_ok.setVisibility(View.VISIBLE);
			btn_head_ok.setText("(" + millisUntilFinished / 1000 + ")重新获取");
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
			switch (currentNO) {
			case 1:
				if (!ckb_ok.isChecked()) {
					Toast.makeText(context, "请同意用户协议", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				Log.i("", "---et_one.getText()"
						+ et_one.getText().toString().length());

				if (et_one.getText().toString().length() < 1) {
					Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT)
							.show();
					break;
				} else if (et_two.getText().length() < 5) {
					Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT)
							.show();
					break;
				} else {
					phoneNO = et_two.getText().toString();

					userName = et_one.getText().toString();

					getIdentifyingCode();
					break;
				}

			case 2:
				user_identifyingCode = et_one.getText().toString().trim();
				Log.i("", "---user_identifyingCode"+user_identifyingCode);
				submitIdentifyingCode();
				break;
			case 3:
				if (et_one.getText() == null) {
					Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
					break;
				} else if (et_two.getText() == null) {
					Toast.makeText(context, "请再次输入密码", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				String fristPswd = et_one.getText().toString();
				String secondPswd = et_two.getText().toString();
				if (!fristPswd.equals(secondPswd)) {
					Toast.makeText(context, "两次输入的密码不一致", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				userPswd = fristPswd;
				register();
				break;

			}
			break;

		case R.id.btn_invite:
			Toast.makeText(context, "邀请", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_head_ok:
			currentNO = 1;
			getIdentifyingCode();

			break;
		case R.id.tv_head_ok:
			finish();
			break;
		}

	}

}
