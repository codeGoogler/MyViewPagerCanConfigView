package pager.yuer.com.myviewpagercanconfigview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

import pager.yuer.com.myviewpagercanconfigview.adapter.AdsAdapter;
import pager.yuer.com.myviewpagercanconfigview.text2.MainFragmentActivity;


public class TestFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View mView;//fragment视图
    private View mHeadView;//头部轮播视图
    private PullUpRefreshListView mListView;

    int index;//当前fragment
    private int mCurrentPager = 1;//当前页数
    private TextView mLoadMore;//加载更多
    private ProgressBar mProgressBar;//上拉加载的进度条
    private Context mContext;
    private LinearLayout tab_news;//轮播导航点
    private AdsAdapter mPagerAdapter;//轮播适配器
    private ViewPager mViewPager;
    private List<String> lvData = new ArrayList<>();
    private List<String> mAdsList = new ArrayList<>();
    public  static final String URL1="http://d.hiphotos.baidu.com/image/pic/item/d52a2834349b033b6068dc3d10ce36d3d439bd70.jpg";
    public  static final String URL2="http://a.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6007ac92c50ec50352ac75cb7d5.jpg";
    public  static final String URL3="http://g.hiphotos.baidu.com/image/pic/item/5ab5c9ea15ce36d3eb01a0d73ff33a87e850b1cb.jpg";
    public  static final String URL4="http://b.hiphotos.baidu.com/image/pic/item/e1fe9925bc315c60b5364b0888b1cb13485477b1.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_test, null);
        Bundle bundle = getArguments();
        index = bundle.getInt("index");//当前fg表示
        mContext = getActivity();

        initData();
        initView();
        //解决使用懒加载时跳跃点击无法加载的问题,因为viewpager刚进来时只会初始化前2个fg
        // 突然点击第三个时 有两个问题 第一没初始化好，第二 不会触发setUserVisibleHint()所以在初始化完时处于可见且没有数据则加载
        if (getUserVisibleHint() && lvData.size() == 0) {
            onVisible();
        }
        return mView;
    }

    private void initData() {
        mAdsList.clear();
        mAdsList.add(URL1);
        mAdsList.add(URL2);
        mAdsList.add(URL3);
        mAdsList.add(URL4);
    }

    private void initView() {
        //listview
        mListView = (PullUpRefreshListView) mView.findViewById(R.id.mlistview);
        mListView.setOnItemClickListener(this);
        //添加轮播功能
        if (index==0){//只给第一页加轮播功能  index为外层viewpager的页数  可根据index来做每页不同的布局
            addAds();
        }
        //添加上拉加载功能
        pullUpLoad();
        mListView.setAdapter(new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, lvData));
    }

    //动态添加导航
    // mAdsList.size()表示有几张图就加几个导航圆点
    private void initTab() {
        int dp = 5;// 添加小圆点
        for (int i = 0; i < mAdsList.size(); i++) {
            View views = new View(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp, dp);
            if (i != 0) {
                params.leftMargin = dp;
            }
            views.setLayoutParams(params);
            views.setBackgroundResource(R.drawable.v_lunbo_point_selector);
            tab_news.addView(views);
            tab_news.getChildAt(i).setEnabled(false);
        }
        tab_news.getChildAt(0).setEnabled(true);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(),MainFragmentActivity.class));
    }

    //添加图片轮播功能
    public void addAds() {
        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.listviewheaderview, null);
        tab_news = (LinearLayout) mHeadView.findViewById(R.id.tab_news);
        mViewPager = (ViewPager) mHeadView.findViewById(R.id.viewPager_header);
        //添加轮播圆点

            initTab();

        // 创建适配器
        mPagerAdapter = new AdsAdapter(mContext, mAdsList, mViewPager);
        mViewPager.setAdapter(mPagerAdapter);
        int currentIndex = (Integer.MAX_VALUE / 2) % mAdsList.size() == 0 ? (Integer.MAX_VALUE / 2) : (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % mAdsList.size();
        mViewPager.setCurrentItem(currentIndex);// 设置当前轮播图下标
        // 绑定动作监听器
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                int len = 0;
                if (mAdsList.size() != 0) {
                    len = mAdsList.size();
                }
                //设置轮播导航圆点被选中状态
                for (int i = 0; i < len; i++) {
                    tab_news.getChildAt(i).setEnabled(i == position % len);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mListView.addHeaderView(mHeadView);
    }


    //上拉加载
    public void pullUpLoad() {
        //listview上拉加载
        View view = getActivity().getLayoutInflater().inflate(R.layout.listfooter_more, null);
        mLoadMore = (TextView) view.findViewById(R.id.load_more);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里一般是发起网络请求数据 而且根据请求结果做进度条的显示隐藏，isRequestFinish是否完成请求等一系列逻辑
            //       if (isRequestFinish) {//上一个请求完成才执行这个，不然一直往上拉，会连续发多个请求
//                    mCurrentPager++;
//                    //向列表接口发起请求
//                    pullUpLoadMore(BaseURLs.URL_FOOTBALL_INFOLIST, mCurrentPager + "", infotype + "");
//                }
                mCurrentPager++;
                Toast.makeText(mContext, "请求第"+mCurrentPager+"页的数据", Toast.LENGTH_SHORT).show();
            }
        });
        mProgressBar = (ProgressBar) view.findViewById(R.id.pull_to_refresh_progress);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadMore.setText("加载更多");
        mListView.initBottomView(view);
        mListView.setMyPullUpListViewCallBack(new PullUpRefreshListView.MyPullUpListViewCallBack() {
            @Override
            public void scrollBottomState() {
                //这里一般是发起网络请求数据 而且根据请求结果做进度条的显示隐藏，isRequestFinish是否完成请求等一系列逻辑
//                if (isRequestFinish) {//上一个请求完成才执行这个，不然一直往上拉，会连续发多个请求
//                    mCurrentPager++;
//                    //向列表接口发起请求
//                    pullUpLoadMore(BaseURLs.URL_FOOTBALL_INFOLIST, mCurrentPager + "", infotype + "");
//                }
                mCurrentPager++;
                Toast.makeText(mContext, "请求第"+mCurrentPager+"页的数据", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //fg切换时回调该方法
    //用来取消viewpager的预加载机制  减少初始化开销
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && lvData.size() == 0) {
            onVisible();

        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
//            //一般是发起网络请求数据  这里采用模拟数据
//            loadNewsData(BaseURLs.URL_FOOTBALL_INFOLIST, mCurrentPager + "", infotype + "");
        for (int i=0;i<30;i++){
            lvData.add("第"+i+"条数据");
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.i("position=onDestroyView" + index);
        //解决因testfragment层滑动引起的轮播小圆点混乱
        if (index==0) {
            mPagerAdapter.end();
        }

    }

}