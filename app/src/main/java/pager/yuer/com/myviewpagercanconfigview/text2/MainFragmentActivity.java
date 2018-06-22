package pager.yuer.com.myviewpagercanconfigview.text2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import pager.yuer.com.myviewpagercanconfigview.R;
import pager.yuer.com.myviewpagercanconfigview.TestFragment;
import pager.yuer.com.myviewpagercanconfigview.adapter.MyFragmentPagerAdapter;
import pager.yuer.com.myviewpagercanconfigview.fragment.FourFragment;


public class MainFragmentActivity  extends FragmentActivity  {
	/**
	 * 控件声明
	 */
	RadioButton rb_setting;
	RadioButton rb_jiaoban;
	RadioButton rb_zhuxiao;
	RadioButton rb_quit;

	RadioGroup radioGroup;

	/**
	 * 四个Fragment（页面）
	 */
	Fragment oneFragment;
	Fragment twoFragment;
	Fragment threeFragment;
	Fragment fourFragment;
	private TextView title;
	//FragmentTransaction transaction;

	private ViewPager mViewPager;
	private List<Fragment> fragmentList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragmentmain);
		rb_setting = (RadioButton) findViewById(R.id.setting);
		rb_jiaoban = (RadioButton) findViewById(R.id.jiaoban);
		rb_zhuxiao = (RadioButton) findViewById(R.id.zhuxiao);
		rb_quit = (RadioButton) findViewById(R.id.quit);
		title = (TextView) findViewById(R.id.title);

		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		//默认选中第一个RadioButton
		((RadioButton)radioGroup.findViewById(R.id.setting)).setChecked(true);
		//	transaction = getSupportFragmentManager().beginTransaction();
//		Fragment fragment = new OneFragment();
		Fragment onefragment = new OneFragment();
		Fragment twofragment = new OneFragment();
//		Fragment twofragment = new TwoFragment();
//		Fragment threefragment = new ThreeFragment();

		TestFragment mCounselChildFragment = new TestFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("index", 0);//当前fg标识
		mCounselChildFragment.setArguments(bundle);


		Fragment fourfragment = new FourFragment();
		fragmentList.add(onefragment);
		fragmentList.add(twofragment);
		fragmentList.add(mCounselChildFragment);
		fragmentList.add(fourfragment);
//		transaction.replace(R.id.content, fragment);
		//	transaction.commit();
		title.setText(((RadioButton)findViewById(R.id.setting)).getText().toString());
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.setting:
						mViewPager.setCurrentItem(0, false);
						break;
					case R.id.jiaoban:
						mViewPager.setCurrentItem(1, false);
						break;
					case R.id.zhuxiao:
						mViewPager.setCurrentItem(2, false);
						break;
					case R.id.quit:
						mViewPager.setCurrentItem(3, false);
						break;

					default:
						break;
				}
			}
		});
		InitAdapter();
	}

	private void InitAdapter() {
		mViewPager = (ViewPager) findViewById(R.id.myviewpager);
		mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				switch (arg0) {
					case 0:
						radioGroup.check(R.id.setting);
						title.setText(((RadioButton)findViewById(R.id.setting)).getText().toString());
						break;
					case 1:
						radioGroup.check(R.id.jiaoban);
						title.setText(((RadioButton)findViewById(R.id.jiaoban)).getText().toString());
						break;
					case 2:
						radioGroup.check(R.id.zhuxiao);
						title.setText(((RadioButton)findViewById(R.id.zhuxiao)).getText().toString());
						break;
					case 3:
						radioGroup.check(R.id.quit);
						title.setText(((RadioButton)findViewById(R.id.quit)).getText().toString());
						break;

				}
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}


	// 在这里你可以传 list<Fragment> 也可以传递 list<Object>数据    public class MyAdapter extends FragmentStatePagerAdapter {      List<Entity> list_ee;             public MyAdapter(FragmentManager fm, List<Entity> ee) {        super(fm);        this.list_ee = ee ;      }         @Override     public int getCount() {        return list_ee.size();      }         // 初始化每个页卡选项      @Override     public Object instantiateItem(ViewGroup arg0, int position) {                 ArrayFragment ff = (ArrayFragment)super.instantiateItem(arg0, position);        ff.setThings(list_ee.get(position),position);        return ff;      }             @Override     public void destroyItem(ViewGroup container, int position, Object object) {        System.out.println( "position Destory" + position);        super.destroyItem(container, position, object);      }                @Override     public Fragment getItem(int arg0) {        // TODO Auto-generated method stub        return new ArrayFragment();      }           }
}
