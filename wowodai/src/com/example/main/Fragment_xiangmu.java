package com.example.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.common.HttpUtils;
import com.example.common.MyApplication;
import com.example.wowodai.R;
import com.example.xiangmu.Fragment_content;

public class Fragment_xiangmu extends Fragment {
	private HttpUtils httpUtils;
	private MyApplication myApplication;
	private Context context;
	private View mainView;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new HttpUtils();
		myApplication = (MyApplication) getActivity().getApplication();
		context = getActivity();
		fragmentManager = getFragmentManager();
	}

	private void init() {
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
		spinner = (Spinner) mainView.findViewById(R.id.spinner1);
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
				fragmengContent = new Fragment_content();
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.main_xiangmu, null);
		init();
		return mainView;
	}
}
