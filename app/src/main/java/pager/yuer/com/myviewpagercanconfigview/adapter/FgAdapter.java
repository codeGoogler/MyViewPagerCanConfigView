package pager.yuer.com.myviewpagercanconfigview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;



import java.util.List;

import pager.yuer.com.myviewpagercanconfigview.TestFragment;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
public class FgAdapter extends FragmentStatePagerAdapter {
    private List<TestFragment> mList;
    private FragmentManager mFragmentManager;
    private List<String> mTitles;

    public FgAdapter(FragmentManager fm, List<TestFragment> list, List<String> mTitles) {
        super(fm);
        this.mFragmentManager = fm;
        this.mList = list;
        this.mTitles=mTitles;

    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
