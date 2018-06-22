package pager.yuer.com.myviewpagercanconfigview.view.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by king on 2018/6/10.
 */

public class ChildViewPager   extends ViewPager {

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    private float mLastMotionX;
    private boolean flag = false;

    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        final float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                flag = true;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (flag) {
                    if (x - mLastMotionX > 5 && getCurrentItem() == 0) {
                        flag = false;
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    if (x - mLastMotionX < -5
                            && getCurrentItem() == getAdapter().getCount() - 1) {
                        flag = false;
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }
}
