package com.example.zhanghu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.ImageDownLoader.onImageLoaderListener;
import com.example.common.MyApplication;
import com.example.common.MyProgressBar;
import com.example.common.TypeChange;
import com.example.wowodai.R;

public class Tzxmjl_Fragment_content extends Fragment {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private ImageDownLoader imageDownLoader;
	private View mainView;
	private Context context;
	private ListView listView;
	private TypeChange typeChange;
	private MyListViewAdapter adapter;

	private List<HashMap<String, Object>> dataList;
	private String Project_status;
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
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HttpUtils();
		myApplication = (MyApplication) getActivity().getApplication();
		Project_status = getArguments().getString("Project_status");
		dataList = new ArrayList<HashMap<String, Object>>();
		context = getActivity();
		typeChange = new TypeChange();
		imageDownLoader = new ImageDownLoader(context);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.xiangmu_content_fragment, null);
		listView = (ListView) mainView.findViewById(R.id.listView1);
		getData();
		return mainView;
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
						R.layout.xiangmu_content_fragment_listview_item, null);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_nhsy = (TextView) convertView
						.findViewById(R.id.tv_nhsy);
				holder.tv_rzje = (TextView) convertView
						.findViewById(R.id.tv_rzje);
				holder.tv_rzqx = (TextView) convertView
						.findViewById(R.id.tv_rzqx);
				holder.tv_character = (TextView) convertView
						.findViewById(R.id.tv_character);
				holder.tv_content = (TextView) convertView
						.findViewById(R.id.tv_content);
				holder.img_logo = (ImageView) convertView
						.findViewById(R.id.img_logo);
				holder.img_character = (ImageView) convertView
						.findViewById(R.id.img_character);
				holder.myProgressBar = (MyProgressBar) convertView
						.findViewById(R.id.myProgressBar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			initListViewData(holder, list, position);
			return convertView;
		}

	}

	private class ViewHolder {
		ImageView img_logo, img_character;
		TextView tv_title, tv_nhsy, tv_rzje, tv_rzqx, tv_character, tv_content;
		MyProgressBar myProgressBar;
	}

	private void initListViewData(ViewHolder holder,
			List<HashMap<String, Object>> list, int position) {
		holder.tv_content.setVisibility(View.GONE);
		holder.myProgressBar.setVisibility(View.GONE);
		if (list != null) {
			switch (Integer.parseInt(typeChange.object2String(list
					.get(position).get("Project_status")))) {
			// case 2:
			// holder.tv_content.setVisibility(View.VISIBLE);
			// holder.tv_content.setText(typeChange.object2String(list.get(
			// position).get("Time_Start_Sale")));
			// holder.img_character
			// .setImageResource(R.drawable.xiangmu_listview_item_xin);
			// holder.tv_character.setText("开始时间");
			// break;
			case 3:
				// holder.myProgressBar.setVisibility(View.VISIBLE);
				// holder.myProgressBar.setProgress(typeChange.object2Integer(list
				// .get(position).get("plan")));
				holder.img_character
						.setImageResource(R.drawable.xiangmu_listview_item_tou);
				holder.tv_character.setText("融资进度");
				break;
			case 4:
				holder.img_character
						.setImageResource(R.drawable.xiangmu_listview_item_huan);
				holder.tv_character.setText("企业还款中");
				break;
			case 5:
				holder.img_character
						.setImageResource(R.drawable.xiangmu_listview_item_wan);
				holder.tv_character.setText("还款完成");
				break;

			}

			holder.tv_title.setText(typeChange.object2String(list.get(position)
					.get("Project_Name")));
			holder.tv_nhsy.setText(typeChange.object2String(list.get(position)
					.get("Interest_Base")));
			holder.tv_rzje.setText(typeChange.object2String(list.get(position)
					.get("Project_Money")));
			holder.tv_rzqx.setText(typeChange.object2String(list.get(position)
					.get("DeadLine")));

			// final ImageView img_logo = holder.img_logo;
			// imageDownLoader.downloadImage(
			// context,
			// typeChange.object2String(list.get(position).get(
			// "Cover_pic_small")), new onImageLoaderListener() {
			//
			// @Override
			// public void onImageLoader(Bitmap bitmap, String url) {
			// // TODO Auto-generated method stub
			// img_logo.setTag(url);
			// if (img_logo.getTag().equals(url)) {
			// img_logo.setImageBitmap(bitmap);
			// }
			// }
			// });

		}

	}

	private void getData() {

		new Thread(new Runnable() {
			List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

			@Override
			public void run() {
				// TODO Auto-generated method stub
				resultList = httpUtils
						.getListResultFromUrl("http://manage.55178.com/mobile/api.php?module=usermod&act=getUserProjectList&User_ID="
								+ Project_status);
				if (resultList != null && resultList.size() > 0) {
					dataList = resultList;
					Message message = Message.obtain();
					message.what = 1;
					initDataHandler.sendMessage(message);
				}
			}
		}).start();
	}
}
