package com.example.zhanghu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.TypeChange;
import com.example.wowodai.R;
import com.example.xiangmu.Fragment_content;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class TzxmjlActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private ImageDownLoader imageDownLoader;
	private Context context;
	private MyHeadView myHeadView;
	private Spinner spinner;
	private SimpleAdapter adapter2;
	private List<String> spinnerList;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private Fragment fragmengContent;
	private List<Map<String, Object>> spinnerList2;
	private int[] spinnerImgId = { R.drawable.xiangmu_spinner_selected_quanbu,
			R.drawable.xiangmu_spinner_selected_new,
			R.drawable.xiangmu_spinner_selected_touzizhong,
			R.drawable.xiangmu_spinner_selected_huankuanzhong,
			R.drawable.xiangmu_spinner_selected_end };
	private String[] spinnerContent = { "全部", "新项目", "投资中", "还款中", "结束" };
	private LinearLayout layout_head_back;
	private HashMap<String, Object> dataMap;// 数据源
	private String userId;
	private Handler initDataHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				initData();

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhanghu_tzxmjl);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		fragmentManager = getFragmentManager();
		imageDownLoader = new ImageDownLoader(context);
		dataMap = new HashMap<String, Object>();
		userId = myApplication.getUserId();
		init();
		getData();
	}

	private void init() {
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("投资项目记录");

		spinnerList = new ArrayList<String>();
		spinnerList2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> spinnerMap;
		for (int i = 0; i < spinnerContent.length; i++) {
			spinnerList.add("item" + i);
			spinnerMap = new HashMap<String, Object>();
			spinnerMap.put("content", spinnerContent[i]);
			spinnerMap.put("img", spinnerImgId[i]);
			spinnerList2.add(spinnerMap);
		}
		spinner = (Spinner) findViewById(R.id.spinner1);
		adapter2 = new SimpleAdapter(context, spinnerList2,
				R.layout.xiangmu_spinner_item,
				new String[] { "content", "img" }, new int[] { R.id.tv_spinner,
						R.id.img_spinner });
		spinner.setAdapter(adapter2);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				fragmentTransaction = fragmentManager.beginTransaction();
				Bundle args = new Bundle();
				args.putString("Project_status", String.valueOf(arg2 + 1));
				fragmengContent = new Tzxmjl_Fragment_content();
				fragmengContent.setArguments(args);
				fragmentTransaction
						.replace(R.id.frame_content, fragmengContent);

				fragmentTransaction.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	private void initData() {

	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getMoneyInfo&User_ID="
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_head_back:
			finish();
			break;

		default:
			break;
		}

	}

}
