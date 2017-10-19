package com.amit.autoscrollviewpager.main;

import com.amit.autoscrollviewpager.models.DataRepository;
import com.amit.autoscrollviewpager.models.DataRepositoryImpl;

/**
 * Created by Amit Barjatya on 10/19/17.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    DataRepository mRepository;
    int currIndex;

    MainActivityContract.View mView;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.mRepository = new DataRepositoryImpl();
        this.currIndex = -1;
        this.mView = view;
    }

    /**
     * Notify this presenter that the view is inflated
     * and is now in focus
     *
     * if current Index is -1 that means no element has been added to the view
     *      show the empty view and hide viewpager in the view
     * else
     *      hide empty view and show the content
     *
     * if viewpager has more than 1 element also ask viewpager to autoscroll
     */
    @Override
    public void onResume() {
        if (currIndex == -1){
            mView.showEmptyViewAndHideViewPager();
        }else{
            mView.hideEmptyViewAndShowViewPager();
        }
        int itemsShowingCurrently = mView.getCurrentItemCountInViewpager();
        if (itemsShowingCurrently>1){
            mView.startViewpagerAutoScroll();
        }
    }

    /**
     * Notify this presenter that the view is about to go out of focus
     *
     * ask viewpager to stop auto scroll
     */
    @Override
    public void onPause() {
        mView.stopViewpagerAutoScroll();
    }

    @Override
    public void onAddElementFabClicked() {

    }

    @Override
    public void onResetElementFabClicked() {

    }

    @Override
    public void onRemoveElementFabClicked() {

    }
}
