package com.example.zhanghu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.ImageDownLoader.onImageLoaderListener;
import com.example.common.MyApplication;
import com.example.common.MyHeadView;
import com.example.common.TypeChange;
import com.example.wowodai.R;
import com.example.wowodai.R.color;

public class WddjqActivity extends Activity implements OnClickListener {
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
		setContentView(R.layout.zhanghu_wddjq);
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
		myHeadView.setTitle("我的代金券");

	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://manage.55178.com/mobile/api.php?module=usermod&act=getInvestmentInfo&User_ID="
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
						R.layout.zhanghu_wddjq_listview_item, null);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_content = (TextView) convertView
						.findViewById(R.id.tv_content);
				holder.tv_status = (TextView) convertView
						.findViewById(R.id.tv_status);
				holder.tv_validity = (TextView) convertView
						.findViewById(R.id.tv_validity);
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_time);
				holder.img_logo = (ImageView) convertView
						.findViewById(R.id.img_logo);
				holder.img_character = (ImageView) convertView
						.findViewById(R.id.img_character);
				holder.layout_status = (RelativeLayout) convertView
						.findViewById(R.id.layout_status);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			switch (typeChange.object2Integer(list.get(position)
					.get("Statekey"))) {
			case 1:
				setColorHigh(holder);
				break;
			case 2:
				setColorLow(holder);
				break;
			case 3:
				setColorLow(holder);
				break;
			case 4:
				setColorLow(holder);
				break;

			}

			holder.tv_title.setText(typeChange.object2String(list.get(position)
					.get("Notevalue")));
			holder.tv_time.setText(typeChange.object2String(list.get(position)
					.get("Over_Time")));
			holder.tv_content.setText("");
			holder.tv_status.setText(typeChange.object2String(list
					.get(position).get("Statevalue")));
			holder.img_character.setImageResource(R.drawable.close);

			final ImageView img_logo = holder.img_logo;
			imageDownLoader.downloadImage(context, typeChange
					.object2String(list.get(position).get("Imageurl")),
					new onImageLoaderListener() {

						@Override
						public void onImageLoader(Bitmap bitmap, String url) {
							// TODO Auto-generated method stub
							img_logo.setTag(url);
							if (img_logo.getTag().equals(url)) {
								img_logo.setImageBitmap(bitmap);
							}
						}
					});
			return convertView;
		}

	}

	private class ViewHolder {
		RelativeLayout layout_status;
		TextView tv_title, tv_content, tv_status, tv_validity, tv_time;
		ImageView img_logo, img_character;

	}

	private void setColorLow(ViewHolder holder) {
		holder.tv_title.setTextColor(color.wowodai_text_grey);
		holder.tv_content.setTextColor(color.wowodai_text_grey);
		holder.tv_status.setTextColor(color.wowodai_text_grey);
		holder.tv_validity.setTextColor(color.wowodai_text_grey);
		holder.tv_time.setTextColor(color.wowodai_text_grey);
		holder.layout_status.setBackgroundColor(color.wowodai_text_white);

	}

	private void setColorHigh(ViewHolder holder) {
		holder.tv_title.setTextColor(color.wowodai_text_black);
		holder.tv_content.setTextColor(color.wowodai_text_gray);
		holder.tv_status.setTextColor(color.wowodai_text_white);
		holder.tv_validity.setTextColor(color.wowodai_text_white);
		holder.tv_time.setTextColor(color.wowodai_text_white);
		holder.layout_status.setBackgroundColor(color.wowodai_text_red);

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
