package com.amit.autoscrollviewpager.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * A viewpager implementation which auto scrolls it's contents
 * Also loops through it's contents
 * <p>
 * Created by Amit Barjatya on 10/19/17.
 */

public class AutoscrollViewpager extends ViewPager {

    private static final String TAG = "AutoscrollViewpager";
    private static final long SCROLL_INTERVAL_MILLIS = 4 * 1000;

    private Handler autoScrollHandler;
    private OnPageChangeListener pageChangeListener = new PageChangeListenerForLooping(this);

    public AutoscrollViewpager(Context context) {
        super(context);
    }

    public AutoscrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Make this viewpager scroll to the right after every SCROLL_INTERVAL_MILLIS milliseconds
     * <p>
     * if the current visible position is the last element then set next item as 0th item
     * else set next item as current visible index + 1
     */
    public void startAutoScroll() {
        if (autoScrollHandler == null) {
            autoScrollHandler = new Handler();
            setCurrentItem(1);
            Log.d(TAG, "start auto scroll");
            autoScrollHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int currVisiblePosition = getCurrentItem();
                    int nextVisiblePosition = currVisiblePosition + 1;
                    setCurrentItem(nextVisiblePosition, true);
                    autoScrollHandler.postDelayed(this, SCROLL_INTERVAL_MILLIS);
                }
            }, SCROLL_INTERVAL_MILLIS);
        } else {
            Log.d(TAG, "already scrolling");
        }
    }

    /**
     * Stops the autoscrolling of this viewpager
     */
    public void stopAutoScroll() {
        if (autoScrollHandler != null) {
            autoScrollHandler.removeCallbacksAndMessages(null);
            autoScrollHandler = null;
        }
    }

    public OnPageChangeListener getPageChangeListener() {
        return pageChangeListener;
    }

    /**
     * An implementation of OnPageChangeListener which helps in looping the dataset
     * i.e. a leftswipe on last element will get you to first element
     * or a rightswipe on first element will get you to last element
     */
    private class PageChangeListenerForLooping implements ViewPager.OnPageChangeListener {
        int mCurrentPosition = 1;
        ViewPager vp;

        PageChangeListenerForLooping(ViewPager vp) {
            this.vp = vp;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                int totalItemsInViewpagerAdapter = vp.getAdapter().getCount();
                if (mCurrentPosition == 0) {
                    vp.setCurrentItem(totalItemsInViewpagerAdapter - 2, false);
                }
                if (mCurrentPosition == totalItemsInViewpagerAdapter - 1) {
                    vp.setCurrentItem(1, false);
                }
            }
        }
    }

}
