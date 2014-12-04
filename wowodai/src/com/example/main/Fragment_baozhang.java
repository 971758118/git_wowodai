package com.example.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baozhang.Baozhang_detailActivity;
import com.example.common.HttpUtils;
import com.example.common.MyApplication;
import com.example.wowodai.R;

public class Fragment_baozhang extends Fragment implements OnClickListener {

	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private Context context;
	private View mainView;
	private RelativeLayout layout_aqywms, layout_xsfktx, layout_zcflbz,
			layout_tzhtfb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HttpUtils();
		myApplication = (MyApplication) getActivity().getApplication();
		context = getActivity();
	}

	private void init() {
		layout_aqywms = (RelativeLayout) mainView
				.findViewById(R.id.layout_aqywms);
		layout_xsfktx = (RelativeLayout) mainView
				.findViewById(R.id.layout_xsfktx);
		layout_zcflbz = (RelativeLayout) mainView
				.findViewById(R.id.layout_zcflbz);
		layout_tzhtfb = (RelativeLayout) mainView
				.findViewById(R.id.layout_tzhtfb);
		layout_aqywms.setOnClickListener(this);
		layout_xsfktx.setOnClickListener(this);
		layout_zcflbz.setOnClickListener(this);
		layout_tzhtfb.setOnClickListener(this);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.main_baozhang, null);
		init();
		return mainView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String webViewUrl = "";
		String titleString = "";
		Intent intent;

		switch (v.getId()) {
		case R.id.layout_aqywms:
			webViewUrl = "http://manage.55178.com/mobile/page/moshi.html";
			titleString = "安全业务模式";
			intent = new Intent(context, Baozhang_detailActivity.class);
			intent.putExtra("webViewUrl", webViewUrl);
			intent.putExtra("titleString", titleString);
			startActivity(intent);

			break;
		case R.id.layout_xsfktx:
			webViewUrl = "http://manage.55178.com/mobile/page/xinshen.html";
			titleString = "信审风控体系";
			intent = new Intent(context, Baozhang_detailActivity.class);
			intent.putExtra("webViewUrl", webViewUrl);
			intent.putExtra("titleString", titleString);
			startActivity(intent);
			break;
		case R.id.layout_zcflbz:
			webViewUrl = "http://manage.55178.com/mobile/page/zhengce.html";
			titleString = "政策法律保障";
			intent = new Intent(context, Baozhang_detailActivity.class);
			intent.putExtra("webViewUrl", webViewUrl);
			intent.putExtra("titleString", titleString);
			startActivity(intent);
			break;
		case R.id.layout_tzhtfb:
			webViewUrl = "http://manage.55178.com/mobile/page/hetong.html";
			titleString = "投资合同范本";
			intent = new Intent(context, Baozhang_detailActivity.class);
			intent.putExtra("webViewUrl", webViewUrl);
			intent.putExtra("titleString", titleString);
			startActivity(intent);
			break;

		}

	}

}
