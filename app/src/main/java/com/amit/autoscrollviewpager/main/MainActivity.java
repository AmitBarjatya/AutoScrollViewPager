package com.amit.autoscrollviewpager.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.amit.autoscrollviewpager.R;
import com.amit.autoscrollviewpager.models.ImageElement;
import com.amit.autoscrollviewpager.viewpager.AutoscrollViewpager;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main View for the app which is controlled by the MainActivityContract.Presenter
 * Contains a custom view pager which autoscrolls when it has more than 2 element
 * Contains 3 Actions which tells the presenter to add a new element to viewpager,
 * remove an element from viewpager and reset this view to its initial state (zero elements)
 */
public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.activity_main_viewpager)
    AutoscrollViewpager mViewpager;

    @BindView(R.id.activity_main_empty_view)
    RelativeLayout mEmptyView;
    /**
     * Presenter which controls the state and views contained in this Activity
     */
    MainActivityContract.Presenter mPresenter;

    /**
     * View pager adapter that controls the dataset for the contained viewpager
     */
    ViewPagerAdapter mAdapter;
    ArrayList<Fragment> mFragments = new ArrayList<>();
    ArrayList<String> mFragmentTitles = new ArrayList<>();

    /**
     * These dummy lists are used for an circular view pager behaviour
     */
    ArrayList<Fragment> mDummyFragments = new ArrayList<>();
    ArrayList<String> mDummyFragmentTitles = new ArrayList<>();

    /**
     * Pass the click event on Add Element Fab to presenter
     */
    @OnClick(R.id.fabAddElement)
    public void onAddElementFabClicked() {
        mPresenter.onAddElementFabClicked();
    }

    /**
     * Pass the click event on Reset Element Fab to presenter
     */
    @OnClick(R.id.fabResetElement)
    public void onResetElementFabClicked() {
        mPresenter.onResetElementFabClicked();
    }

    /**
     * Pass the click event on Remove Element Fab to presenter
     */
    @OnClick(R.id.fabRemoveElement)
    public void onRemoveElementFabClicked() {
        mPresenter.onRemoveElementFabClicked();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainActivityPresenter(this);
        initViewpager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    /**
     * Initialize viewpager with a new PagerAdapter
     */
    private void initViewpager() {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mDummyFragments, mDummyFragmentTitles);
        mViewpager.setAdapter(mAdapter);
    }

    /**
     * Adds a new fragment to the viewpager adapter
     *
     * @param ie         the image element to get the url and index information from
     * @param addAtIndex the index at which the new element is to be added
     */
    @Override
    public void addElementToViewpager(ImageElement ie, int addAtIndex) {
        Fragment fragment = new ImageElementFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", ie.getImgUrl());
        bundle.putInt("index", ie.getIndex());
        fragment.setArguments(bundle);

        mFragments.add(addAtIndex, fragment);
        mFragmentTitles.add(addAtIndex, String.format(Locale.US, "Index %s", ie.getIndex()));

        populateDummyLists();

        mAdapter.notifyDataSetChanged();
    }

    /**
     * Removes a fragment
     *
     * @param removeFromIndex the index from which the fragment is to be removed
     */
    @Override
    public void removeElementFromViewPager(int removeFromIndex) {
        mFragments.remove(removeFromIndex);
        mFragmentTitles.remove(removeFromIndex);

        populateDummyLists();

        mAdapter.notifyDataSetChanged();
    }

    /**
     * Populates the dummy lists for looping behaviour
     * Dummy lists have their first element(index 0) a copy of the last element of the original list
     * and their last element(index size() -1) a copy of the first element of the original list
     */
    private void populateDummyLists() {
        mDummyFragments.clear();
        mDummyFragmentTitles.clear();

        mDummyFragments.addAll(mFragments);
        mDummyFragmentTitles.addAll(mFragmentTitles);

        //only add dummy elements when the size is greater than 1
        if (mFragmentTitles.size() > 1) {
            Fragment first = new ImageElementFragment();
            first.setArguments(mFragments.get(mFragments.size() - 1).getArguments());

            Fragment last = new ImageElementFragment();
            last.setArguments(mFragments.get(0).getArguments());

            mDummyFragments.add(0, first);
            mDummyFragmentTitles.add(0, mFragmentTitles.get(mFragmentTitles.size() - 1));

            mDummyFragments.add(last);
            mDummyFragmentTitles.add(mFragmentTitles.get(0));
        }
    }

    /**
     * Removes all the elements from viewpager
     */
    @Override
    public void removeAllElementFromViewPager() {
        mFragments.clear();
        mFragmentTitles.clear();
        mDummyFragments.clear();
        mDummyFragmentTitles.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * show empty view and hide viewpager
     */
    @Override
    public void showEmptyViewAndHideViewPager() {
        mEmptyView.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.GONE);
    }

    /**
     * hide empty view and show viewpager
     */
    @Override
    public void hideEmptyViewAndShowViewPager() {
        mEmptyView.setVisibility(View.GONE);
        mViewpager.setVisibility(View.VISIBLE);
    }

    /**
     * notify the viewpager to start autoscrolling
     */
    @Override
    public void startAutoScrollAndLoop() {
        mViewpager.startAutoScroll();
        mViewpager.addOnPageChangeListener(mViewpager.getPageChangeListener());
    }

    /**
     * notify the viewpager to stop autoscrolling
     */
    @Override
    public void stopAutoScrollAndLoop() {
        mViewpager.stopAutoScroll();
        mViewpager.removeOnPageChangeListener(mViewpager.getPageChangeListener());
    }

    @Override
    public int getRealItemCountInViewpager() {
        return mFragments.size();
    }

    @Override
    public void gotoIndexInViewpager(int index) {
        mViewpager.setCurrentItem(index, true);
    }
}
