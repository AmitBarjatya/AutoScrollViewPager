package com.amit.autoscrollviewpager.main;

import com.amit.autoscrollviewpager.models.DataRepository;
import com.amit.autoscrollviewpager.models.DataRepositoryImpl;
import com.amit.autoscrollviewpager.models.ImageElement;

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
     * <p>
     * if current Index is -1 that means no element has been added to the view
     * show the empty view and hide viewpager in the view
     * else
     * hide empty view and show the content
     * <p>
     * if viewpager has more than 1 element also ask viewpager to autoscroll
     */
    @Override
    public void onResume() {
        if (currIndex == -1) {
            mView.showEmptyViewAndHideViewPager();
        } else {
            mView.hideEmptyViewAndShowViewPager();
        }
        int itemsShowingCurrently = mView.getCurrentItemCountInViewpager();
        if (itemsShowingCurrently > 1) {
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

    /**
     * Adds a new element to the viewpager
     * Maintains a counter which fetches the next element in data repository
     * In case when all the elements in the datarepository are exhausted: does nothing
     * In case this was the first element: hide the empty view and show viewpager
     * Also translates the view pager to newly added index
     */
    @Override
    public void onAddElementFabClicked() {
        currIndex++;
        try {
            ImageElement element = mRepository.getElement(currIndex);
            int index = mView.getCurrentItemCountInViewpager();
            mView.addElementToViewpager(element,index);
            if (index == 0){
                mView.hideEmptyViewAndShowViewPager();
            }
            mView.gotoIndexInViewpager(index);
        }catch (ArrayIndexOutOfBoundsException e){
            //no more elements
            //do nothing
        }
    }

    /**
     * Reset the state of this application
     * Clears out the child views in viewpager
     * Clears out the state of this presenter
     */
    @Override
    public void onResetElementFabClicked() {
        mView.removeAllElementFromViewPager();
        mView.showEmptyViewAndHideViewPager();
        currIndex = -1;
    }

    /**
     * Remove an element from last of the items in viewpager
     * In case when there's no element: currently this function does not do anything
     * (A feedback message can be show to the user via toast or snackbar)
     * In case when there's just 1 element: hide viewpager and show empty view
     */
    @Override
    public void onRemoveElementFabClicked() {
        int itemsInViewpager = mView.getCurrentItemCountInViewpager();
        if (itemsInViewpager != 0){
            mView.removeElementFromViewPager(itemsInViewpager - 1);
            if (itemsInViewpager == 1){
                mView.showEmptyViewAndHideViewPager();
            }
        }
    }
}
