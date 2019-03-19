package com.dyhdyh.view.prerecyclerview;

import android.view.View;

public interface LoadMoreFooter {

    /**
     * 正常
     */
    int STATE_NORMAL = 0;
    /**
     * 加载中..
     */
    int STATE_LOADING = 1;
    /**
     * 异常
     */
    int STATE_ERROR = 2;
    /**
     * 加载到最底了
     */
    int STATE_THE_END = 3;

    void setState(int state);

    View getView();
}
