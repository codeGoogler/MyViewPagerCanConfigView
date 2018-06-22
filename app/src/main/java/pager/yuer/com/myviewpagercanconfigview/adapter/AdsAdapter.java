package pager.yuer.com.myviewpagercanconfigview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;

import java.lang.reflect.Field;
import java.util.List;

import pager.yuer.com.myviewpagercanconfigview.R;


/**
 * 轮播图数据适配器
 *
 */
public class AdsAdapter extends PagerAdapter {
    private Context mContext;
    private DisplayImageOptions options;// 设置ImageLoder参数
    private long downIime;// 轮播图按下时间
    private long upTime;// 轮播图按下松开时间
    private LunboTask mTask;// 轮播图控制器
    private final int BANNER_PLAY_TIME = 5000;// 自动轮播间隔时长
    private final int BANNER_ANIM_TIME = 500;// 轮播切换动画的时长
    private ViewPager mViewPager;//
   private List<String>url;
    private com.nostra13.universalimageloader.core.ImageLoader universalImageLoader;

    public AdsAdapter(Context context, List<String> url, ViewPager mViewPager) {
        this.mContext = context;
        this.url = url;
        this.mViewPager = mViewPager;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisc(true).considerExifParams(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
        universalImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance(); //必须初始化
        universalImageLoader.init(config);

        if (mTask == null) {
            mTask = new LunboTask();
            mTask.startLunbo();
        }
        setScroller();

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (url.size() != 0) {
            position %= url.size();
            if (position < 0) {
                position = url.size() + position;
            }
        }
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String picUrl = null;
        if (url.size() != 0) {
            picUrl = url.get(position);
        }
        universalImageLoader.displayImage(picUrl, imageView, options);
        Log.i("lzf",picUrl);
        final int index = position;
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downIime = System.currentTimeMillis();
                        mTask.stopLunbo();
                        break;
                    case MotionEvent.ACTION_UP:
                        upTime = System.currentTimeMillis();
                        mTask.startLunbo();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mTask.startLunbo();
                        break;
                }
                if (downIime != 0 && upTime != 0 && (upTime - downIime) <= 200 && (upTime - downIime) >= 0) {
                    jumpInstruction(index);
                }
                return true;
            }
        });

        container.addView(imageView);
        return imageView;
    }

    /**
     * 轮播图片点击事件-> 跳转指令
     *
     * @param index 当前对象下标
     */
    private void jumpInstruction(final int index) {
        Toast.makeText(mContext,"轮播图"+index,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void start() {
        if (mTask != null) {
            mTask.startLunbo();
        }
    }

    public void end() {
        if (mTask != null) {
            mTask.stopLunbo();
            L.i("position=stopLunbo()");
        }
    }

    /**
     * 轮播控制中心
     */
    public  class LunboTask extends Handler implements Runnable {
        public void startLunbo() {
            stopLunbo();// 开始轮播之前清除一下原来的消息
            postDelayed(this, BANNER_PLAY_TIME);
        }

        public void stopLunbo() {
            removeCallbacksAndMessages(null);// 清除所有消息和回调
        }

        @Override
        public void run() {
            setViewPagerItem();
            postDelayed(this, BANNER_PLAY_TIME);
        }
    }

    public void setViewPagerItem() {

        if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
            mViewPager.setCurrentItem(0, true);
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        }
    }

    public void setScroller() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            MyScroller mScroller = new MyScroller(mViewPager.getContext(),
                    new AccelerateInterpolator());
            mField.set(mViewPager, mScroller);
        } catch (Exception ee) {
            L.d("Exception: " + ee.getMessage());
        }
    }

    class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            super.startScroll(startX, startY, dx, dy, BANNER_ANIM_TIME);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, BANNER_ANIM_TIME);
        }
    }
}
