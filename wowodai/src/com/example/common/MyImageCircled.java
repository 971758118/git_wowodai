package com.example.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.example.wowodai.R;

/**
 * ViewPager实现的图片轮播, 既支持自动轮播页面也支持手势滑动切换页面,可以动态设置图片的张数
 * 
 * @author DuJiaXu
 * 
 */
@SuppressLint("HandlerLeak")
public class MyImageCircled extends FrameLayout {
	private final static boolean isAutoPlay = true;
	private List<Bitmap> imageBitmapList;
	private List<ImageView> imageViewsList;
	private List<ImageView> dotViewsList;
	private LinearLayout mLinearLayout;
	private ViewPager mViewPager;
	private int currentItem = 0;
	private ScheduledExecutorService scheduledExecutorService;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {

				mViewPager.setCurrentItem(currentItem);
			}

		}
	};

	public MyImageCircled(Context context) {
		this(context, null);
	}

	public MyImageCircled(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyImageCircled(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initUI(context);

		if (isAutoPlay) {

			// startPlay();
		}
	}

	private void initUI(Context context) {

		LayoutInflater.from(context).inflate(R.layout.myimagecircled_main,
				this, true);
		mLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
	}

	public void setImageBitmapList(List<Bitmap> imageBitmapList) {

		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<ImageView>();
		mLinearLayout.removeAllViews();
		if (imageBitmapList != null) {

			this.imageBitmapList = imageBitmapList;
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(5, 0, 0, 0);
			for (int i = 0; i < imageBitmapList.size(); i++) {
				ImageView imageView = new ImageView(getContext());
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setImageBitmap(imageBitmapList.get(i));
				imageViewsList.add(imageView);
				ImageView viewDot = new ImageView(getContext());
				if (i == 0) {
					viewDot.setBackgroundResource(R.drawable.myimagecircled_dot_white);
				} else {
					viewDot.setBackgroundResource(R.drawable.myimagecircled_dot_light);
				}
				viewDot.setLayoutParams(lp);
				dotViewsList.add(viewDot);
				mLinearLayout.addView(viewDot);
			}
			mViewPager.setFocusable(true);
			mViewPager.setAdapter(new MyPagerAdapter(imageViewsList));
			mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		}
		if (mViewPager != null && imageViewsList.size() > 0) {
			mViewPager.setCurrentItem(0);
			currentItem = 0;
		}
		if (scheduledExecutorService != null) {
			stopPlay();
			startPlay();
		} else {
			startPlay();
		}

	}

	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < dotViewsList.size(); i++) {
			if (i == selectItems) {
				dotViewsList.get(i).setBackgroundResource(
						R.drawable.myimagecircled_dot_white);
			} else {
				dotViewsList.get(i).setBackgroundResource(
						R.drawable.myimagecircled_dot_light);
			}
		}
	}

	private class MyPagerAdapter extends PagerAdapter {
		List<ImageView> imgViewsList = new ArrayList<ImageView>();

		private MyPagerAdapter(List<ImageView> l) {
			imgViewsList = l;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(imgViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(imgViewsList.get(position));
			return imgViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
		}
	}

	private class MyPageChangeListener implements OnPageChangeListener {
		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:
				isAutoPlay = false;
				break;
			case 2:
				isAutoPlay = true;
				break;
			case 0:
				if (mViewPager.getCurrentItem() == mViewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					mViewPager.setCurrentItem(0);
				} else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
					mViewPager.setCurrentItem(mViewPager.getAdapter()
							.getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub
			setImageBackground(pos % imageBitmapList.size());
			currentItem = pos % imageBitmapList.size();
			// currentItem = pos;
			// for(int i=0;i < dotViewsList.size();i++){
			// if(i == pos){
			// ((ImageView)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_black);//R.drawable.main_dot_light
			// }else {
			// ((ImageView)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_white);
			// }
			// }
		}
	}

	private class SlideShowTask implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (mViewPager) {

				currentItem = (currentItem + 1) % imageViewsList.size();
				Message msg = Message.obtain();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}
	}

	@SuppressWarnings("unused")
	private void destoryBitmaps() {
		for (int i = 0; i < imageViewsList.size(); i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				drawable.setCallback(null);
			}
		}
	}
}
