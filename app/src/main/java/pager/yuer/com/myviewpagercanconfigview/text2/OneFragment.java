package pager.yuer.com.myviewpagercanconfigview.text2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import pager.yuer.com.myviewpagercanconfigview.R;

public class OneFragment extends Fragment implements OnClickListener {
	Button employeeInfo;
	Button updatePass;
	Button printSetting;

	ViewPager mViewPager;

	Fragment home1;
	Fragment home2;
	Fragment home3;

	List<Fragment> fragmentLists;

	
	
	FragmentTransaction transaction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settingfragment, null, false);
		InitView(view);
		fragmentLists = new ArrayList<Fragment>();
		home1 = new OneFragment_1();
		home2 = new OneFragment_2();
		home3 = new OneFragment_3();
		
		fragmentLists.add(home1);
		fragmentLists.add(home2);
		fragmentLists.add(home3);
		
		employeeInfo.setOnClickListener(this);
		updatePass.setOnClickListener(this);
		printSetting.setOnClickListener(this);
		InitAdapter(view);
		

		return view;
	}

	private void InitAdapter(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(new MyFragStatePagerAdapter(
				getChildFragmentManager()));

	}

	private void InitView(View parentView) {
		employeeInfo = (Button) parentView.findViewById(R.id.employeeInfo);
		updatePass = (Button) parentView.findViewById(R.id.updatePass);
		printSetting = (Button) parentView.findViewById(R.id.printSetting);
		//imageviewOvertab = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
	}

	class MyFragStatePagerAdapter extends FragmentStatePagerAdapter {

		public MyFragStatePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentLists.get(position);
		}

		@Override
		public int getCount() {
			return fragmentLists.size();
		}

		
	}

	
	
	
	 
//����UI
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.employeeInfo:
			changeView(0);
			
			break;
		case R.id.updatePass:
			changeView(1);
		
			
			break;
		case R.id.printSetting:
			changeView(2);
		
			break;

		}
		
	}
	private void changeView(int desTab) {
		mViewPager.setCurrentItem(desTab, true);
	}

	}

	
	
