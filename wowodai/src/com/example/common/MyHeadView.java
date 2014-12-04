package com.example.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wowodai.R;

public class MyHeadView extends RelativeLayout {
	private View headView;
	private ImageView img_head_back, img_head_ok;
	private TextView tv_head_title, tv_head_ok;
	private LinearLayout layout_head_back;

	public MyHeadView(Context context) {
		this(context, null);
		init();
	}

	public MyHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		headView = LayoutInflater.from(MyApplication.getContext()).inflate(
				R.layout.head, this);
		img_head_back = (ImageView) headView.findViewById(R.id.img_head_back);
		img_head_ok = (ImageView) headView.findViewById(R.id.img_head_ok);
		tv_head_title = (TextView) headView.findViewById(R.id.tv_head_title);
		tv_head_ok = (TextView) headView.findViewById(R.id.tv_head_ok);
		layout_head_back = (LinearLayout) headView
				.findViewById(R.id.layout_head_back);
	}

	public void setTitle(String s) {
		tv_head_title.setText(s);
	}

	public void setImgHeadBackResource(int imgID) {
		img_head_back.setImageResource(imgID);
	}

	public ImageView getImg_head_ok() {

		return img_head_ok;
	}

	public TextView getTv_head_ok() {
		return tv_head_ok;
	}

	public LinearLayout getBackLayout() {
		return layout_head_back;
	}
}
