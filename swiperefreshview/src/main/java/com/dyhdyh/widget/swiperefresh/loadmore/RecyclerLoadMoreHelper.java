package com.dyhdyh.widget.swiperefresh.loadmore;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dyhdyh.widget.swiperefresh.header.RecyclerHeaderHelper;
import com.dyhdyh.widget.swiperefresh.listener.OnLoadMoreListener;
import com.dyhdyh.widget.swiperefresh.listener.OnRecyclerScrollChangeListener;
import com.dyhdyh.widget.swiperefresh.view.LoadMoreView;


/**
 * 加载更多Helper
 * author  dengyuhan
 * created 2017/7/6 14:06
 */
public class RecyclerLoadMoreHelper extends RecyclerHeaderHelper implements LoadMoreWrapper{
    private final String TAG = "RecyclerLoadMoreHelper";
    private LoadMoreFooter mLoadMoreFooter;
    private boolean mLoadMoreEnabled = true;
    private boolean mLoadMore;

    private OnLoadMoreListener mOnLoadMoreListener;

    public RecyclerLoadMoreHelper(RecyclerView recyclerView) {
        super(recyclerView);
        setupHelper(recyclerView);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        if (this.mLoadMoreFooter == null) {
            setLoadMoreFooter(new LoadMoreView(getRecyclerView().getContext()));
        } else {
            setupLoadMoreFooter();
        }
    }

    protected void setupHelper(RecyclerView recyclerView) {
        RecyclerOnScrollListener scrollListener = new RecyclerOnScrollListener();
        scrollListener.setScrollChangeListener(new OnRecyclerScrollChangeListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int state) {
                //滑到底部
                if (mLoadMoreEnabled && state == OnRecyclerScrollChangeListener.STATE_ON_BOTTOM) {
                    dispatchLoadMoreState();
                }
            }
        });
        recyclerView.addOnScrollListener(scrollListener);
    }


    protected void dispatchLoadMoreState() {
        LoadMoreFooter.State footerState = mLoadMoreFooter.getState();
        if (mLoadMore) {
            Log.d(TAG, "上一个请求还未执行完成");
        } else if (footerState == LoadMoreFooter.State.GONE) {
            Log.d(TAG, "隐藏");
        } else if (footerState == LoadMoreFooter.State.THE_END) {
            Log.d(TAG, "已经到底了");
        } else {
            Log.d(TAG, "正在加载");
            if (mOnLoadMoreListener != null) {
                mLoadMore = true;
                mLoadMoreFooter.setState(LoadMoreFooter.State.LOADING);
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }


    public void setLoadMore(boolean loadMore) {
        this.mLoadMore = loadMore;
    }

    @Override
    public void setLoadMoreFooter(LoadMoreFooter loadMoreFooter) {
        this.mLoadMoreFooter = loadMoreFooter;
        setupLoadMoreFooter();
    }

    private void setupLoadMoreFooter() {
        if (getWrapperAdapter() != null) {
            if (mLoadMoreEnabled) {
                mLoadMoreFooter.setState(LoadMoreFooter.State.LOADING);
                addFooterView(mLoadMoreFooter.getView());
            } else {
                removeFooterView(mLoadMoreFooter.getView());
            }
        }
    }

    @Override
    public void setLoadMoreState(LoadMoreFooter.State state) {
        this.mLoadMoreFooter.setState(state);
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.mLoadMoreEnabled = loadMoreEnabled;
        if (!mLoadMoreEnabled && mLoadMoreFooter != null) {
            removeFooterView(mLoadMoreFooter.getView());
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public LoadMoreFooter getLoadMoreFooter() {
        return mLoadMoreFooter;
    }

    public boolean isLoadMore() {
        return mLoadMore;
    }
}
