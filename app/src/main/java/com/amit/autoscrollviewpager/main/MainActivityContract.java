package com.amit.autoscrollviewpager.main;

import com.amit.autoscrollviewpager.models.ImageElement;

/**
 * Main Activity Contract for MVP architecture
 * <p>
 * MainActivityView should implement .View interface
 * MainActivityPresenter should implement .Presenter interface
 * <p>
 * Created by Amit Barjatya on 10/19/17.
 */

public interface MainActivityContract {

    interface View {
        void addElementToViewpager(ImageElement ie, int addAtIndex);

        void removeElementFromViewPager(int removeFromIndex);

        void removeAllElementFromViewPager();

        void showEmptyViewAndHideViewPager();

        void hideEmptyViewAndShowViewPager();

        void startViewpagerAutoScroll();

        void stopViewpagerAutoScroll();

        int getCurrentItemCountInViewpager();

        void gotoIndexInViewpager(int index);
    }

    interface Presenter {
        void onResume();

        void onPause();

        void onAddElementFabClicked();

        void onResetElementFabClicked();

        void onRemoveElementFabClicked();
    }

}
