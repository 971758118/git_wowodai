package com.example.common;

//////////////////////////此类的功能是使用共享参数 记住用户名和密码

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginService {

	public Context context;

	public LoginService(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public boolean saveLoginMsg(String name, String password) {
		boolean flag = false;
		SharedPreferences preferences = context.getSharedPreferences("Login",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("userName", name);
		editor.putString("password", password);
		flag = editor.commit();
		return flag;

	}

	public boolean saveSharePreference(String fileName, Map<String, Object> map) {
		boolean flag = false;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();
			if (object instanceof Boolean) {
				Boolean b = (Boolean) object;
				editor.putBoolean(key, b);
			} else if (object instanceof Float) {
				Float f = (Float) object;
				editor.putFloat(key, f);

			} else if (object instanceof Integer) {
				Integer i = (Integer) object;
				editor.putInt(key, i);

			} else if (object instanceof Long) {
				Long l = (Long) object;
				editor.putLong(key, l);

			} else if (object instanceof String) {
				String s = (String) object;
				editor.putString(key, s);

			}

		}
		flag = editor.commit();
		return flag;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public Map<String, ?> getSharePreference(String fileName) {
		Map<String, ?> map = null;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		map = preferences.getAll();
		return map;

	}

}
