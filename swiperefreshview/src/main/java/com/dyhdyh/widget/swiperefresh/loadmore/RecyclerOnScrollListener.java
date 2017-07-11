package com.dyhdyh.widget.swiperefresh.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dyhdyh.widget.swiperefresh.listener.OnRecyclerScrollChangeListener;

/**
 * 继承自RecyclerView.OnRecyclerScrollStateListener，可以监听到是否滑动到页面最低部
 */
public class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 当前RecyclerView类型
     */
    protected RecyclerView.LayoutManager layoutManager;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    /**
     * 触发在上下滑动监听器的容差距离
     */
    private static final int HIDE_THRESHOLD = 20;

    /**
     * 滑动的距离
     */
    private int mDistance = 0;

    /**
     * 是否需要监听控制
     */
    private boolean mIsScrollDown = true;

    /**
     * Y轴移动的实际距离（最顶部为0）
     */
    private int mScrolledYDistance = 0;

    /**
     * X轴移动的实际距离（最左侧为0）
     */
    private int mScrolledXDistance = 0;

    private OnRecyclerScrollChangeListener mScrollChangeListener;

    public void setScrollChangeListener(OnRecyclerScrollChangeListener listener) {
        this.mScrollChangeListener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int firstVisibleItemPosition = 0;
        layoutManager = recyclerView.getLayoutManager();

        /*if (null != mSwipeRefreshLayout) {
            int topRowVerticalPosition =
                    (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
            mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
        }*/

        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (lastPositions == null) {
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            }
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            lastVisibleItemPosition = findMax(lastPositions);
            staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(lastPositions);
            firstVisibleItemPosition = findMax(lastPositions);
        }

        // 根据类型来计算出第一个可见的item的位置，由此判断是否触发到底部的监听器
        // 计算并判断当前是向上滑动还是向下滑动
        calculateScrollUpOrDown(recyclerView, firstVisibleItemPosition, dy);
        // 移动距离超过一定的范围，我们监听就没有啥实际的意义了
        mScrolledXDistance += dx;
        mScrolledYDistance += dy;
        mScrolledXDistance = (mScrolledXDistance < 0) ? 0 : mScrolledXDistance;
        mScrolledYDistance = (mScrolledYDistance < 0) ? 0 : mScrolledYDistance;
        if (mIsScrollDown && (dy == 0)) {
            mScrolledYDistance = 0;
        }

        if (mScrollChangeListener != null) {
            mScrollChangeListener.onScrollStateChanged(recyclerView, OnRecyclerScrollChangeListener.STATE_SCROLL);
            mScrollChangeListener.onScrolled(recyclerView, mScrolledXDistance, mScrolledYDistance);
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        //Log.d("RecyclerLoadMoreHelper>", visibleItemCount + "------->" + currentScrollState + "------->" + lastVisibleItemPosition + "----->" + totalItemCount + "----->" + ((lastVisibleItemPosition) >= totalItemCount - 1));
        //(lastVisibleItemPosition) >= totalItemCount - 1 - 1)  除开加载更多的View
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1 - 1)) {
            if (mScrollChangeListener != null) {
                mScrollChangeListener.onScrollStateChanged(recyclerView, OnRecyclerScrollChangeListener.STATE_ON_BOTTOM);
            }
        }
    }


    /**
     * 计算当前是向上滑动还是向下滑动
     */
    private void calculateScrollUpOrDown(RecyclerView recyclerView, int firstVisibleItemPosition, int dy) {
        if (firstVisibleItemPosition == 0) {
            if (!mIsScrollDown) {
                if (mScrollChangeListener != null) {
                    mScrollChangeListener.onScrollStateChanged(recyclerView, OnRecyclerScrollChangeListener.STATE_SCROLL_DOWN);
                }
                mIsScrollDown = true;
            }
        } else {
            if (mDistance > HIDE_THRESHOLD && mIsScrollDown) {
                if (mScrollChangeListener != null) {
                    mScrollChangeListener.onScrollStateChanged(recyclerView, OnRecyclerScrollChangeListener.STATE_SCROLL_UP);
                }
                mIsScrollDown = false;
                mDistance = 0;
            } else if (mDistance < -HIDE_THRESHOLD && !mIsScrollDown) {
                if (mScrollChangeListener != null) {
                    mScrollChangeListener.onScrollStateChanged(recyclerView, OnRecyclerScrollChangeListener.STATE_SCROLL_DOWN);
                }
                mIsScrollDown = true;
                mDistance = 0;
            }
        }
        if ((mIsScrollDown && dy > 0) || (!mIsScrollDown && dy < 0)) {
            mDistance += dy;
        }
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }


}
