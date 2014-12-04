package com.example.common;

import java.util.HashMap;
import java.util.Map;

public class GetCodeInfo {
	private Map<String, String> codeInfoMap;

	/**
	 * 获得服务器返回代码对应的信息
	 * 
	 * @param code
	 * @return
	 */
	public String getCodeInfo(String code) {

		String result = null;
		codeInfoMap = new HashMap<String, String>();
		codeInfoMap.put("101", "接口文件不存在");
		codeInfoMap.put("102", "接口不存在");
		codeInfoMap.put("103", "接口内方法不存在");
		codeInfoMap.put("200", "获取数据成功");
		codeInfoMap.put("201", "获取数据失败");
		codeInfoMap.put("202", "项目不存在");
		codeInfoMap.put("203", "用户不存在");
		codeInfoMap.put("204", "参数不能为空");
		codeInfoMap.put("205", "服务器异常错误（参数不正确）");
		codeInfoMap.put("206", "用户名或密码错误");
		codeInfoMap.put("207", "手机号格式不正确");
		codeInfoMap.put("208", "两次密码输入不一致");
		codeInfoMap.put("209", "密码格式不正确");
		codeInfoMap.put("210", "原密码不正确");
		codeInfoMap.put("211", "用户名格式不正确");
		codeInfoMap.put("212", "用户名已存在");
		codeInfoMap.put("213", "手机号已存在");
		codeInfoMap.put("214", "获取手机验证码失败");
		codeInfoMap.put("215", "验证码不正确");
		codeInfoMap.put("216", "请勿在验证码有效时间内重复获取验证码");
		codeInfoMap.put("217", "原验证码过期，请使用最新获得的验证码");
		codeInfoMap.put("218", "1天内只能获取10次验证码");
		codeInfoMap.put("219", "密码或确认密码不能为空");
		codeInfoMap.put("220", "手机号不能为空");

		if (codeInfoMap.containsKey(code)) {
			result = codeInfoMap.get(code);
		}

		return result;

	}

}
