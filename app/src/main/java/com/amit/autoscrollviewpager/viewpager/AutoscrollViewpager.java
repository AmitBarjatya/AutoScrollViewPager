package com.amit.autoscrollviewpager.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * A viewpager implementation which auto scrolls it's contents
 * Also loops through it's contents
 * <p>
 * Created by Amit Barjatya on 10/19/17.
 */

public class AutoscrollViewpager extends ViewPager {

    private static final String TAG = "AutoscrollViewpager";
    private static final long SCROLL_INTERVAL_MILLIS = 4 * 1000;

    private static final int SCROLL=0;
    float downX = 0;

    //user has to drag 20 pixels before we consider it as a scroll event
    private static final float SCROLL_THRESHOLD = 50f;

    private Handler autoScrollHandler;

    public AutoscrollViewpager(Context context) {
        super(context);
        initialize();
    }

    public AutoscrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize(){
        autoScrollHandler = new AutoscrollHandler(this);
    }

    /**
     * Make this viewpager scroll to the right after every SCROLL_INTERVAL_MILLIS milliseconds
     */
    public void startAutoScroll() {
        sendScrollMessage();
    }

    /**
     * Stops the autoscrolling of this viewpager
     */
    public void stopAutoScroll() {
        if (autoScrollHandler != null) {
            autoScrollHandler.removeCallbacksAndMessages(SCROLL);
        }
    }

    /**
     * Send a scroll message to handler
     * Also make sure that there's only one scroll message present in the message queue
     */
    private void sendScrollMessage(){
        autoScrollHandler.removeMessages(SCROLL);
        Message msg = autoScrollHandler.obtainMessage();
        autoScrollHandler.sendMessageDelayed(msg,SCROLL_INTERVAL_MILLIS);
    }

    /**
     * Scroll this viewpager by one page to the right
     */
    private void scrollOnce(){
        PagerAdapter mAdapter = getAdapter();
        if (mAdapter == null || mAdapter.getCount() <= 1){
            return;
        }

        int items = mAdapter.getCount();
        int currentItemIndex = getCurrentItem();
        int nextItemIndex = currentItemIndex+1;
        if (nextItemIndex == items ){
            setCurrentItem(0,true);
        }else {
            setCurrentItem(nextItemIndex,true);
        }
    }

    /**
     * Intercept touch events to provide scroll on last page
     * Stop auto scroll on touch
     * else start auto scroll
     *
     * if we are scrolling right on first page
     *      goto the last page
     * if we are scrolling left on last page
     *      goto the first page
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_DOWN){
            stopAutoScroll();
        }else if (action == MotionEvent.ACTION_UP){
            startAutoScroll();
        }

        PagerAdapter mAdapter = getAdapter();
        int currentItemIndex = getCurrentItem();
        int items = mAdapter == null ? 0:mAdapter.getCount();
        float touchX = ev.getX();
        if (action == MotionEvent.ACTION_DOWN){
            downX = ev.getX();
        }

        if ((currentItemIndex == 0 && touchX > downX + SCROLL_THRESHOLD)
                || (currentItemIndex == items- 1 && touchX + SCROLL_THRESHOLD < downX)){
            if (items>1){
                setCurrentItem(items-currentItemIndex-1,true);
            }
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.dispatchTouchEvent(ev);
    }


    /**
     * Handler for sending scroll messages every SCROLL_INTERVAL milliseconds
     */
    private static class AutoscrollHandler extends Handler {
        WeakReference<AutoscrollViewpager> wrViewPager;

        AutoscrollHandler(AutoscrollViewpager viewPager) {
            this.wrViewPager = new WeakReference<AutoscrollViewpager>(viewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case SCROLL:
                    AutoscrollViewpager autoscrollViewpager = wrViewPager.get();
                    if (autoscrollViewpager!=null){
                        autoscrollViewpager.scrollOnce();
                        autoscrollViewpager.sendScrollMessage();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
