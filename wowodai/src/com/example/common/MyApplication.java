package com.example.common;

import android.app.Application;
import android.content.Context;

import com.example.suoping.LockPatternUtils;

public class MyApplication extends Application {

	private static MyApplication mInstance = null;
	private static Context context;
	private LockPatternUtils mLockPatternUtils;
	private boolean isLogin = false;// 记录用户是否已经登录
	private String userId;
	private String userName;
	private String userPassWord;
	public String IPCONFIG = "http://";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		context = getApplicationContext();
		mLockPatternUtils = new LockPatternUtils(this);
	}

	public static Context getContext() {
		return context;
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
