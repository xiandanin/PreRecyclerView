package com.dyhdyh.widget.swiperefresh.loadmore;

import android.view.View;

/**
 * author  dengyuhan
 * created 2017/7/6 15:06
 */
public interface LoadMoreFooter {

    View getView();

    void setState(State status);

    State getState();

    enum State {
        /**隐藏*/
        GONE,

        /**加载到最底了*/
        THE_END,

        /**加载中..*/
        LOADING,

        /**异常*/
        ERROR
    }
}
