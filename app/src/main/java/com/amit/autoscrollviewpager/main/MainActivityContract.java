package com.amit.autoscrollviewpager.main;

import com.amit.autoscrollviewpager.models.ImageElement;

/**
 * Main Activity Contract for MVP architecture
 *
 * MainActivityView should implement .View interface
 * MainActivityPresenter should implement .Presenter interface
 *
 * Created by Amit Barjatya on 10/19/17.
 */

public interface MainActivityContract {

    interface View{
        void addElementToViewpager(ImageElement ie, int addAtIndex);
        void removeElementFromViewPager(ImageElement ie, int removeFromIndex);
        void removeAllElementFromViewPager();
        void showEmptyViewAndHideViewPager();
        void hideEmptyViewAndShowViewPager();
        void startAutoScroll();
        void stopAutoScroll();
    }

    interface Presenter{
        void onResume();
        void onPause();
        void onAddElementFabClicked();
        void onResetElementFabClicked();
        void onRemoveElementFabClicked();
    }

}
