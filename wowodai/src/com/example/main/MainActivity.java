package com.example.main;

import java.util.Timer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.ExitHelper;
import com.example.common.ExitTimerTask;
import com.example.common.HttpUtils;
import com.example.common.MyApplication;
import com.example.wowodai.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Fragment fshouye, fxiangmu, fbaozhang, fzhanghu;
	private LinearLayout layout_shouye, layout_xiangmu, layout_baozhang,
			layout_zhanghu;
	private ImageView img_shouye, img_xiangmu, img_baozhang, img_zhanghu;
	private TextView tv_shouye, tv_xiangmu, tv_baozhang, tv_zhanghu, tv_title;
	private FragmentManager fragmentManager;
	private int currentFragmentNO = 0;// 当前显示的Fragment的标号 0-4
	private boolean loginSuccess = false;// 登录用户是否登录成功，是否为从登录页面跳过来的
	private MyApplication myApplication;
	private String USERID;
	private String IPCONFIG;
	private HttpUtils httpUtils;
	private Context context;
	private Timer tExit = new Timer();// 实现按两下返回退出
	private ExitTimerTask exitTimerTask = new ExitTimerTask();// 实现按两下返回退出

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		httpUtils = new HttpUtils();
		context = MainActivity.this;
		myApplication = (MyApplication) getApplication();

		USERID = myApplication.getUserId();

		IPCONFIG = myApplication.IPCONFIG;
		if (getIntent() != null) {
			loginSuccess = getIntent().getBooleanExtra("loginSuccess", false);
			if (loginSuccess) {
				currentFragmentNO = 3;// 如果是登录后跳过来的据就显示第三个fragment
			}
		}
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 设置显示哪一个Fragment
		setTabSelection(currentFragmentNO);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void initViews() {
		layout_shouye = (LinearLayout) findViewById(R.id.layout_shouye);
		layout_xiangmu = (LinearLayout) findViewById(R.id.layout_xiangmu);
		layout_baozhang = (LinearLayout) findViewById(R.id.layout_baozhang);
		layout_zhanghu = (LinearLayout) findViewById(R.id.layout_zhanghu);
		img_shouye = (ImageView) findViewById(R.id.img_shouye);
		img_xiangmu = (ImageView) findViewById(R.id.img_xiangmu);
		img_baozhang = (ImageView) findViewById(R.id.img_baozhang);
		img_zhanghu = (ImageView) findViewById(R.id.img_zhanghu);
		tv_shouye = (TextView) findViewById(R.id.tv_shouye);
		tv_xiangmu = (TextView) findViewById(R.id.tv_xiangmu);
		tv_zhanghu = (TextView) findViewById(R.id.tv_zhanghu);
		tv_baozhang = (TextView) findViewById(R.id.tv_baozhang);
		tv_title = (TextView) findViewById(R.id.tv_title);
		layout_shouye.setOnClickListener(this);
		layout_xiangmu.setOnClickListener(this);
		layout_baozhang.setOnClickListener(this);
		layout_zhanghu.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 100 && arg1 == 101) {
			currentFragmentNO = 3;
			setTabSelection(currentFragmentNO);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.layout_shouye:
			// 当点击了消息tab时，选中第0个tab
			currentFragmentNO = 0;
			setTabSelection(currentFragmentNO);
			break;
		case R.id.layout_xiangmu:
			// 当点击了联系人tab时，选中第1个tab
			currentFragmentNO = 1;
			setTabSelection(currentFragmentNO);
			break;
		case R.id.layout_baozhang:
			// 当点击了动态tab时，选中第2个tab
			currentFragmentNO = 2;
			setTabSelection(currentFragmentNO);
			break;
		case R.id.layout_zhanghu:
			// 当点击了设置tab时，选中第3个tab
			if (!myApplication.isLogin()) {

				Intent intent = new Intent(context, LoginActivity.class);
				startActivityForResult(intent, 100);

			} else {
				currentFragmentNO = 3;
				setTabSelection(currentFragmentNO);
			}

			break;

		}

	}

	private void setTabSelection(int index) {

		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了首页时，改变控件的图片和文字颜色
			img_shouye.setImageResource(R.drawable.shouye_selected);
			tv_shouye.setTextColor(Color.rgb(230, 45, 45));
			tv_title.setText("握握贷");
			tv_title.setTextColor(Color.WHITE);
			if (fshouye == null) {
				// 如果fguanwang为空，则创建一个并添加到界面上
				fshouye = new Fragment_shouye();
				transaction.add(R.id.container, fshouye);
			} else {
				// 如果fguanwang不为空，则直接将它显示出来
				transaction.show(fshouye);
			}
			break;
		case 1:
			// 当点击了项目时，改变控件的图片和文字颜色
			img_xiangmu.setImageResource(R.drawable.xiangmu_selected);
			tv_xiangmu.setTextColor(Color.rgb(230, 45, 45));
			tv_title.setText("项目");
			tv_title.setTextColor(Color.WHITE);
			if (fxiangmu == null) {
				// 如果fxiangmu为空，则创建一个并添加到界面上
				fxiangmu = new Fragment_xiangmu();
				transaction.add(R.id.container, fxiangmu);
			} else {
				// 如果fxiangmu不为空，则直接将它显示出来
				transaction.show(fxiangmu);
			}
			break;
		case 2:

			// 当点击了保障时，改变控件的图片和文字颜色
			img_baozhang.setImageResource(R.drawable.baozhang_selected);
			tv_baozhang.setTextColor(Color.rgb(230, 45, 45));
			tv_title.setText("保障");
			tv_title.setTextColor(Color.WHITE);
			if (fbaozhang == null) {
				// 如果fbaozhang为空，则创建一个并添加到界面上
				fbaozhang = new Fragment_baozhang();
				transaction.add(R.id.container, fbaozhang);
			} else {
				// 如果fbaozhang不为空，则直接将它显示出来
				transaction.show(fbaozhang);
			}
			break;

		case 3:

			// 当点击了账户时，改变控件的图片和文字颜色
			img_zhanghu.setImageResource(R.drawable.zhanghu_selected);
			tv_zhanghu.setTextColor(Color.rgb(230, 45, 45));
			tv_title.setText("账户");
			tv_title.setTextColor(Color.WHITE);
			if (fzhanghu == null) {
				// 如果zhanghuMainFragment为空，则创建一个并添加到界面上
				fzhanghu = new Fragment_zhanghu();
				transaction.add(R.id.container, fzhanghu);
			} else {
				// 如果zhanghuMainFragment不为空，则直接将它显示出来
				transaction.show(fzhanghu);
			}

			break;

		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {

		img_shouye.setImageResource(R.drawable.shouye_unselected);
		tv_shouye.setTextColor(Color.rgb(146, 146, 146));
		img_xiangmu.setImageResource(R.drawable.xiangmu_unselected);
		tv_xiangmu.setTextColor(Color.rgb(146, 146, 146));
		img_baozhang.setImageResource(R.drawable.baozhang_unselected);
		tv_baozhang.setTextColor(Color.rgb(146, 146, 146));
		img_zhanghu.setImageResource(R.drawable.zhanghu_unselected);
		tv_zhanghu.setTextColor(Color.rgb(146, 146, 146));

	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {

		if (fshouye != null) {
			transaction.hide(fshouye);
		}
		if (fxiangmu != null) {
			transaction.hide(fxiangmu);
		}
		if (fbaozhang != null) {
			transaction.hide(fbaozhang);
		}

		if (fzhanghu != null) {
			transaction.hide(fzhanghu);
		}
	}

	/**
	 * 此部分为了实现按两下返回退出
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!ExitHelper.getIsExit()) {
				ExitHelper.setIsExit(true);
				Toast.makeText(getApplicationContext(), "再按一次退出程序", 0).show();
				if (tExit != null) {
					if (exitTimerTask != null) {
						// 将原任务从队列中移除(必须的，否则报错)
						exitTimerTask.cancel();
					}
					// 新建一个任务
					exitTimerTask = new ExitTimerTask();
					tExit.schedule(exitTimerTask, 1 * 1500);// 1.5s内连按返回键两下则退出程序
				}
			} else {
				ExitHelper.setIsExit(false);
				finish();
				System.exit(0);
			}
		}
		return true;
	}
}
