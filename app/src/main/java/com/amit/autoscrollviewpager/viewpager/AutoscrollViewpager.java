package com.amit.autoscrollviewpager.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

/**
 *
 * A viewpager implementation which auto scrolls it's contents
 * Also loops through it's contents
 *
 * Created by Amit Barjatya on 10/19/17.
 */

public class AutoscrollViewpager extends ViewPager {

    private static final String TAG = "AutoscrollViewpager";
    private static final long SCROLL_INTERVAL_MILLIS = 4*1000;

    private Handler autoScrollHandler;

    public AutoscrollViewpager(Context context) {
        super(context);
    }

    public AutoscrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Make this viewpager scroll to the right after every SCROLL_INTERVAL_MILLIS milliseconds
     *
     * if the current visible position is the last element then set next item as 0th item
     * else set next item as current visible index + 1
     */
    public void startAutoScroll(){
        if (autoScrollHandler == null) {
            autoScrollHandler = new Handler();
            Log.d("TAG", "start auto scroll");
            autoScrollHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int currVisiblePosition = getCurrentItem();
                    int numItemsInAdapter = getAdapter().getCount();
                    int nextVisiblePosition =
                            currVisiblePosition == numItemsInAdapter - 1 ? 0 : currVisiblePosition + 1;
                    setCurrentItem(nextVisiblePosition, true);
                    autoScrollHandler.postDelayed(this, SCROLL_INTERVAL_MILLIS);
                }
            }, SCROLL_INTERVAL_MILLIS);
        }else{
            Log.d(TAG,"already scrolling");
        }
    }

    /**
     * Stops the autoscrolling of this viewpager
     */
    public void stopAutoScroll(){
        if (autoScrollHandler!=null){
            autoScrollHandler.removeCallbacksAndMessages(null);
            autoScrollHandler = null;
        }
    }

}
