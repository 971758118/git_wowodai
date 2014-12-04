package com.example.main;

import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baozhang.Baozhang_detailActivity;
import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.ImageDownLoader.onImageLoaderListener;
import com.example.common.MyApplication;
import com.example.common.TypeChange;
import com.example.wowodai.R;
import com.example.zhanghu.SyjlActivity;
import com.example.zhanghu.TzxmjlActivity;
import com.example.zhanghu.WddjqActivity;
import com.example.zhanghu.ZczjActivity;
import com.example.zhanghu.ZhszActivity;
import com.example.zhanghu.ZhyejActivity;

public class Fragment_zhanghu extends Fragment implements OnClickListener {

	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private ImageDownLoader imageDownLoader;
	private Context context;
	private View mainView;
	private TextView tv_shouyi_head, tv_shouyi_end, tv_zczj, tv_ljsy, tv_zhye,
			tv_wddjq, tv_tzxmjl, tv_syjl;
	private LinearLayout layout_zczj, layout_ljsy;
	private RelativeLayout layout_zhye, layout_wddjq, layout_tzxmjl,
			layout_syjl, layout_zhsz, layout_gywwd;
	private Button btn_ok;
	private ImageView img_shouyi;
	private HashMap<String, Object> dataMap;// 填充此页面数据的数据源
	private String userId;
	private Handler initDataHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				initData();

			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HttpUtils();
		context = getActivity();
		typeChange = new TypeChange();
		myApplication = (MyApplication) getActivity().getApplication();
		imageDownLoader = new ImageDownLoader(context);
		dataMap = new HashMap<String, Object>();
		userId = myApplication.getUserId();

	}

	private void init() {
		tv_shouyi_head = (TextView) mainView.findViewById(R.id.tv_shouyi_head);
		tv_shouyi_end = (TextView) mainView.findViewById(R.id.tv_shouyi_end);
		tv_zczj = (TextView) mainView.findViewById(R.id.tv_zczj);
		tv_ljsy = (TextView) mainView.findViewById(R.id.tv_ljsy);
		tv_zhye = (TextView) mainView.findViewById(R.id.tv_zhye);
		tv_wddjq = (TextView) mainView.findViewById(R.id.tv_wddjq);
		tv_tzxmjl = (TextView) mainView.findViewById(R.id.tv_tzxmjl);
		tv_syjl = (TextView) mainView.findViewById(R.id.tv_syjl);
		img_shouyi = (ImageView) mainView.findViewById(R.id.img_shouyi);
		btn_ok = (Button) mainView.findViewById(R.id.btn_ok);
		layout_zhye = (RelativeLayout) mainView.findViewById(R.id.layout_zhye);
		layout_wddjq = (RelativeLayout) mainView
				.findViewById(R.id.layout_wddjq);
		layout_tzxmjl = (RelativeLayout) mainView
				.findViewById(R.id.layout_tzxmjl);
		layout_syjl = (RelativeLayout) mainView.findViewById(R.id.layout_syjl);
		layout_zhsz = (RelativeLayout) mainView.findViewById(R.id.layout_zhsz);
		layout_gywwd = (RelativeLayout) mainView
				.findViewById(R.id.layout_gywwd);
		layout_zczj = (LinearLayout) mainView.findViewById(R.id.layout_zczj);
		layout_ljsy = (LinearLayout) mainView.findViewById(R.id.layout_ljsy);
		layout_zhye.setOnClickListener(this);
		layout_wddjq.setOnClickListener(this);
		layout_tzxmjl.setOnClickListener(this);
		layout_syjl.setOnClickListener(this);
		layout_zhsz.setOnClickListener(this);
		layout_gywwd.setOnClickListener(this);
		layout_zczj.setOnClickListener(this);
		layout_ljsy.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	private void initData() {

		tv_shouyi_head.setText(typeChange.object2String(dataMap
				.get("mounth_income_before")) + ".");
		tv_shouyi_end.setText(typeChange.object2String(dataMap
				.get("mounth_income_after")));
		tv_zczj.setText(typeChange.object2String(dataMap.get("Money_All")));
		tv_ljsy.setText(typeChange.object2String(dataMap.get("Money_Profit")));
		tv_zhye.setText(typeChange.object2String(dataMap.get("Money_Available")));
		tv_wddjq.setText(typeChange.object2String(dataMap.get("Chit")));
		tv_tzxmjl.setText(typeChange.object2String(dataMap.get("")));
		tv_syjl.setText(typeChange.object2String(dataMap.get("")));

		imageDownLoader.downloadImage(context,
				typeChange.object2String(dataMap.get("charts")),
				new onImageLoaderListener() {

					@Override
					public void onImageLoader(Bitmap bitmap, String url) {
						// TODO Auto-generated method stub
						img_shouyi.setImageBitmap(bitmap);

					}
				});

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.main_zhanghu, null);
		init();
		getData();
		return mainView;
	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getUserInfo&User_ID="
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {

		case R.id.layout_zczj:
			intent = new Intent(context, ZczjActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_ljsy:
			// intent = new Intent(context, );
			// startActivity(intent);
			break;
		case R.id.layout_zhye:
			intent = new Intent(context, ZhyejActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_wddjq:
			intent = new Intent(context, WddjqActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_tzxmjl:
			intent = new Intent(context, TzxmjlActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_syjl:
			intent = new Intent(context, SyjlActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_zhsz:
			intent = new Intent(context, ZhszActivity.class);
			startActivity(intent);
			break;
		case R.id.layout_gywwd:
			String webViewUrl = "http://manage.55178.com/mobile/page/about.html";
			String titleString = "关于握握贷";
			intent = new Intent(context, Baozhang_detailActivity.class);
			intent.putExtra("webViewUrl", webViewUrl);
			intent.putExtra("titleString", titleString);
			startActivity(intent);
			break;
		case R.id.btn_ok:
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("确定退出?")
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent intent;
									myApplication.setLogin(false);
									intent = new Intent(context,
											LoginActivity.class);
									intent.putExtra("isQuitSuccess", true);// 标识退出成功
									startActivity(intent);
									getActivity().finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			builder.create().show();

			break;

		}

	}

}
