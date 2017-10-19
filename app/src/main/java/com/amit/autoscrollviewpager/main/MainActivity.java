package com.amit.autoscrollviewpager.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amit.autoscrollviewpager.R;
import com.amit.autoscrollviewpager.models.ImageElement;
import com.amit.autoscrollviewpager.viewpager.AutoscrollViewpager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Main View for the app which is controlled by the MainActivityContract.Presenter
 * Contains a custom view pager which autoscrolls when it has more than 2 element
 * Contains 3 Actions which tells the presenter to add a new element to viewpager,
 * remove an element from viewpager and reset this view to its initial state (zero elements)
 */
public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @BindView(R.id.activity_main_viewpager)
    AutoscrollViewpager mViewpager;

    @BindView(R.id.activity_main_empty_view)
    RelativeLayout mEmptyView;

    /**
     * Pass the click event on Add Element Fab to presenter
     */
    @OnClick(R.id.fabAddElement)
    public void onAddElementFabClicked(){
        mPresenter.onAddElementFabClicked();
    }

    /**
     * Pass the click event on Reset Element Fab to presenter
     */
    @OnClick(R.id.fabResetElement)
    public void onResetElementFabClicked(){
        mPresenter.onResetElementFabClicked();
    }

    /**
     * Pass the click event on Remove Element Fab to presenter
     */
    @OnClick(R.id.fabRemoveElement)
    public void onRemoveElementFabClicked(){
        mPresenter.onResetElementFabClicked();
    }

    /**
     * Presenter which controls the state and views contained in this Activity
     */
     MainActivityContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainActivityPresenter(this);
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

    @Override
    public void addElementToViewpager(ImageElement ie, int addAtIndex) {
        //Stub
        Toast.makeText(this, "Implement this", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeElementFromViewPager(ImageElement ie, int removeFromIndex) {
        //Stub
        Toast.makeText(this, "Implement this", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeAllElementFromViewPager() {
        //Stub
        Toast.makeText(this, "Implement this", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyViewAndHideViewPager() {
        mEmptyView.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyViewAndShowViewPager() {
        mEmptyView.setVisibility(View.GONE);
        mViewpager.setVisibility(View.VISIBLE);
    }

    @Override
    public void startViewpagerAutoScroll() {
        //Stub
        Toast.makeText(this, "Implement this", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopViewpagerAutoScroll() {
        //Stub
        Toast.makeText(this, "Implement this", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCurrentItemCountInViewpager() {
        return mViewpager.getChildCount();
    }
}
