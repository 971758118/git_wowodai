package com.example.zhanghu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.MyProgressBar;
import com.example.common.TypeChange;
import com.example.wowodai.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SyjlActivity extends Activity implements OnClickListener {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private TypeChange typeChange;
	private ImageDownLoader imageDownLoader;
	private Context context;
	private MyHeadView myHeadView;
	private LinearLayout layout_head_back;
	private ListView listView;
	private List<HashMap<String, Object>> dataList;
	private MyListViewAdapter adapter;

	private String userId;
	private Handler initDataHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				adapter = new MyListViewAdapter(dataList);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						// HashMap<String, Object> m = (HashMap<String, Object>)
						// arg0
						// .getItemAtPosition(arg2);
						// String Project_ID = null;
						// Intent intent = new Intent(context,
						// XiangMuDetailActivity.class);
						// if (m.get("Project_ID") != null) {
						// Project_ID = m.get("Project_ID").toString();
						// }
						// intent.putExtra("Project_ID", Project_ID);
						// startActivity(intent);

					}
				});
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhanghu_syjl);
		httpUtils = new HttpUtils();
		context = this;
		typeChange = new TypeChange();
		myApplication = (MyApplication) getApplication();
		imageDownLoader = new ImageDownLoader(context);
		dataList = new ArrayList<HashMap<String, Object>>();
		userId = myApplication.getUserId();
		init();
		getData();
	}

	private void init() {
		listView = (ListView) findViewById(R.id.listview1);
		myHeadView = (MyHeadView) findViewById(R.id.myHeadView);
		layout_head_back = myHeadView.getBackLayout();
		layout_head_back.setOnClickListener(this);
		myHeadView.setTitle("收益记录");

	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getUserProfit&User_ID="
						+ userId;
				dataList = httpUtils.getListResultFromUrl(url);
				if (dataList != null) {
					Message message = Message.obtain();
					message.what = 1;
					initDataHandler.sendMessage(message);
				}

			}
		}).start();
	}

	private class MyListViewAdapter extends BaseAdapter {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		private MyListViewAdapter(List<HashMap<String, Object>> l) {
			list = l;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.zhanghu_syjl_listview_item, null);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_time);
				holder.tv_value = (TextView) convertView
						.findViewById(R.id.tv_value);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tv_title.setText(typeChange.object2String(list.get(position)
					.get("Project_Name")));
			holder.tv_time.setText(typeChange.object2String(list.get(position)
					.get("Time_Move")));
			holder.tv_value.setText(typeChange.object2String(list.get(position)
					.get("Money_Move")));
			return convertView;
		}

	}

	private class ViewHolder {

		TextView tv_title, tv_time, tv_value;

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
