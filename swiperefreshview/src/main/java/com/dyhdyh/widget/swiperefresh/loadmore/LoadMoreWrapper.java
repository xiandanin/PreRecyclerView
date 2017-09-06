package com.dyhdyh.widget.swiperefresh.loadmore;

/**
 * author  dengyuhan
 * created 2017/7/10 17:17
 */
public interface LoadMoreWrapper {

    void setLoadMoreEnabled(boolean loadMoreEnabled);

    void setLoadMoreFooter(LoadMoreFooter loadMoreFooter);

    void setLoadMoreState(LoadMoreFooter.State state);
}
