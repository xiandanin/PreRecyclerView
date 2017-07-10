package com.dyhdyh.view.swiperefresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.dyhdyh.view.swiperefresh.R;
import com.dyhdyh.view.swiperefresh.header.HeaderWrapper;
import com.dyhdyh.view.swiperefresh.loadmore.LoadMoreFooter;
import com.dyhdyh.view.swiperefresh.loadmore.LoadMoreWrapper;
import com.dyhdyh.view.swiperefresh.loadmore.OnLoadMoreListener;
import com.dyhdyh.view.swiperefresh.loadmore.RecyclerLoadMoreHelper;
import com.dyhdyh.view.swiperefresh.paging.PagingHelper;
import com.dyhdyh.view.swiperefresh.paging.PagingWrapper;

/**
 * author  dengyuhan
 * created 2017/7/10 15:45
 */
public class SwipeRefreshRecyclerView extends SwipeRefreshView<RecyclerView> implements HeaderWrapper, LoadMoreWrapper, PagingWrapper, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private RecyclerLoadMoreHelper mLoadMoreHelper;
    private PagingHelper mPagingHelper;
    private OnRefreshListener2 mListener;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RecyclerView inflateChildView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        mLoadMoreHelper = new RecyclerLoadMoreHelper(recyclerView);
        return recyclerView;
    }

    @Override
    protected void applyAttributeSet(Context context, AttributeSet attrs) {
        super.applyAttributeSet(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshRecyclerView);
        int startPage = a.getColor(R.styleable.SwipeRefreshRecyclerView_startPage, PagingWrapper.DEFAULT_START_PAGE);
        int pageCount = a.getColor(R.styleable.SwipeRefreshRecyclerView_pageCount, PagingWrapper.DEFAULT_PAGE_COUNT);
        mPagingHelper = new PagingHelper(startPage, pageCount);
        a.recycle();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mLoadMoreHelper.setLayoutManager(layout);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mLoadMoreHelper.setAdapter(adapter);
    }

    @Override
    public void setRefreshComplete() {
        super.setRefreshComplete();
        mLoadMoreHelper.setLoadMore(false);
    }

    @Override
    public void addHeaderView(View header) {
        mLoadMoreHelper.addHeaderView(header);
    }

    @Override
    public void addFooterView(View footer) {
        mLoadMoreHelper.addFooterView(footer);
    }

    @Override
    public void removeHeaderView(View header) {
        mLoadMoreHelper.removeHeaderView(header);
    }

    @Override
    public void removeFooterView(View footer) {
        mLoadMoreHelper.removeFooterView(footer);
    }

    @Override
    public boolean isHeader(int position) {
        return mLoadMoreHelper.isHeader(position);
    }

    @Override
    public boolean isFooter(int position) {
        return mLoadMoreHelper.isFooter(position);
    }

    @Override
    public void setLoadMoreFooter(LoadMoreFooter loadMoreFooter) {
        mLoadMoreHelper.setLoadMoreFooter(loadMoreFooter);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreHelper.setOnLoadMoreListener(listener);
    }

    public void setOnRefreshListener(OnRefreshListener2 listener) {
        this.mListener = listener;
        super.setOnRefreshListener(this);
        mLoadMoreHelper.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        if (this.mListener != null) {
            this.mListener.onRefresh(true);
        }
    }

    @Override
    public void onLoadMore() {
        if (this.mListener != null) {
            this.mListener.onRefresh(false);
        }
    }

    @Override
    public void setPageCount(int pageCount) {
        mPagingHelper.setPageCount(pageCount);
    }

    @Override
    public int getPage() {
        return mPagingHelper.getPage();
    }

    @Override
    public void resetPage() {
        mPagingHelper.resetPage();
    }

    @Override
    public void addPage() {
        mPagingHelper.addPage();
    }
}
