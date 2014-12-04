package com.example.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.HttpUtils;
import com.example.common.ImageDownLoader;
import com.example.common.ImageDownLoader.onImageLoaderListener;
import com.example.common.MyApplication;
import com.example.common.MyImageCircled;
import com.example.common.MyProgressBar;
import com.example.common.TypeChange;
import com.example.wowodai.R;
import com.example.xiangmu.XiangMuDetailActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class Fragment_shouye extends Fragment implements OnClickListener {

	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private Context context;
	private ImageDownLoader imageDownLoader;
	private TypeChange typeChange;
	private View mainView;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private ImageView img_tuijian;
	private TextView tv_tuijan, tv_nhsy, tv_rzje, tv_rzqx;
	private MyProgressBar myProgressBar;
	private LinearLayout line_tuijian;
	private Button btn_ok;
	private HashMap<String, Object> recomProjectMap;// 推荐项目数据
	private MyImageCircled myImageCircled;
	private List<Bitmap> imgBitmapList;// 存放图片轮播中的图片
	private boolean isImgLoaded;// 轮播图片是否已经加载完
	private boolean isPlaying;// 轮播是否正在进行
	private Handler imageCircledhaHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				isImgLoaded = true;

				myImageCircled.setImageBitmapList(imgBitmapList);
				// myImageCircled.startPlay();
				isPlaying = true;

			}
		};
	};
	private Handler recomProjecthaHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (recomProjectMap.get("Cover_pic_middle") != null) {
					imageDownLoader.downloadImage(context,
							recomProjectMap.get("Cover_pic_middle").toString(),
							new onImageLoaderListener() {

								@Override
								public void onImageLoader(Bitmap bitmap,
										String url) {
									// TODO Auto-generated method stub
									img_tuijian.setImageBitmap(bitmap);

								}
							});
				}
				tv_tuijan.setText(typeChange.object2String(recomProjectMap
						.get("Project_Name")));
				tv_nhsy.setText(typeChange.object2String(recomProjectMap
						.get("Interest_Base")));
				tv_rzje.setText(typeChange.object2String(recomProjectMap
						.get("Project_Money")));
				tv_rzqx.setText(typeChange.object2String(recomProjectMap
						.get("DeadLine")));
				myProgressBar.setProgress(typeChange
						.object2Integer(recomProjectMap.get("plan")));
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity();
		httpUtils = new HttpUtils();
		myApplication = (MyApplication) getActivity().getApplication();
		typeChange = new TypeChange();
		imageDownLoader = new ImageDownLoader(context);

		imgBitmapList = new ArrayList<Bitmap>();
		recomProjectMap = new HashMap<String, Object>();
		getImageCircledData();
		getRecomProjectData();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (isImgLoaded && !isPlaying) {
			// myImageCircled.startPlay();
			isPlaying = true;
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (isImgLoaded) {
			// myImageCircled.stopPlay();
			isPlaying = false;
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.main_shuoye, null);
		init();

		return mainView;
	}

	private void init() {
		mPullToRefreshScrollView = (PullToRefreshScrollView) mainView
				.findViewById(R.id.pull_refresh_scrollview);
		myImageCircled = (MyImageCircled) mainView
				.findViewById(R.id.myImageCircled);
		myProgressBar = (MyProgressBar) mainView
				.findViewById(R.id.myProgressBar);
		img_tuijian = (ImageView) mainView.findViewById(R.id.img_tuijian);
		tv_tuijan = (TextView) mainView.findViewById(R.id.tv_tuijian);
		tv_nhsy = (TextView) mainView.findViewById(R.id.tv_nhsy);
		tv_rzje = (TextView) mainView.findViewById(R.id.tv_rzje);
		tv_rzqx = (TextView) mainView.findViewById(R.id.tv_rzqx);
		btn_ok = (Button) mainView.findViewById(R.id.btn_ok);
		line_tuijian = (LinearLayout) mainView.findViewById(R.id.line_tuijian);

		btn_ok.setOnClickListener(this);
		line_tuijian.setOnClickListener(this);
		mPullToRefreshScrollView.getLoadingLayoutProxy().setPullLabel("刷新");
		mPullToRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
				"正在刷新...");
		mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
				"释放以刷新");

		mPullToRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						// TODO Auto-generated method stub
						new RefreshDataTask().execute();
					}
				});
	}

	private void getImageCircledData() {
		imgBitmapList = new ArrayList<Bitmap>();
		new Thread(new Runnable() {
			List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
			Bitmap imgBitmap;
			byte[] imgBytes;
			Message msg = Message.obtain();

			@Override
			public void run() {
				// TODO Auto-generated method stub
				resultList = httpUtils
						.getListResultFromUrl("http://manage.55178.com/mobile/api.php?module=projectmod&act=getBannerImage");
				if (resultList != null) {
					for (int i = 0; i < resultList.size(); i++) {
						if (resultList.get(i).get("Img_url") != null) {
							imgBytes = httpUtils.getPhoto(resultList.get(i)
									.get("Img_url").toString());
							if (imgBytes != null) {
								imgBitmap = typeChange.Bytes2Bimap(imgBytes);
								imgBitmapList.add(imgBitmap);
								msg.what = 1;
							}

						}

					}
					imageCircledhaHandler.sendMessage(msg);
				}

			}
		}).start();
	}

	private void getRecomProjectData() {
		new Thread(new Runnable() {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();

			@Override
			public void run() {
				// TODO Auto-generated method stub
				resultMap = httpUtils
						.getMapResultFromUrl("http://manage.55178.com/mobile/api.php?module=projectmod&act=getRecomProject");
				if (resultMap != null) {
					recomProjectMap = resultMap;

					Message msg = Message.obtain();
					msg.what = 1;

					recomProjecthaHandler.sendMessage(msg);
				}

			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_ok:
			Toast.makeText(context, "请到官网投资", Toast.LENGTH_SHORT).show();
			break;
		case R.id.line_tuijian:
			String Project_ID = null;
			Intent intent = new Intent(context, XiangMuDetailActivity.class);
			if (recomProjectMap.get("Project_ID") != null) {
				Project_ID = recomProjectMap.get("Project_ID").toString();
			}
			intent.putExtra("Project_ID", Project_ID);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	private class RefreshDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.

			getImageCircledData();
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here

			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshScrollView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

}
