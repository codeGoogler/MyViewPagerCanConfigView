package pager.yuer.com.myviewpagercanconfigview;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;



import java.util.ArrayList;
import java.util.List;

import pager.yuer.com.myviewpagercanconfigview.adapter.FgAdapter;


public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> tabData=new ArrayList<>();
    private  int fragmentCount=4;
    private List<TestFragment> mViewPagerData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupViewPager();

    }

    private void initView() {
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mTabLayout = (TabLayout)findViewById(R.id.tabs);
    }
    private void setupViewPager() {
        initData();
        //此处长度
        for (int i = 0; i <fragmentCount; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabData.get(i)));
        }
        FgAdapter adapter = new FgAdapter(getSupportFragmentManager(),mViewPagerData,tabData);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
    //根据头数据的长度  来构建viewpager的数据源
    private void initData() {
        for (int i = 0; i < fragmentCount; i++) {
            TestFragment mCounselChildFragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);//当前fg标识
            mCounselChildFragment.setArguments(bundle);
            mViewPagerData.add(mCounselChildFragment);
            tabData.add("第"+i+"页");
        }
    }

}
