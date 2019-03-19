package com.dyhdyh.view.prerecyclerview;

import android.view.View;

/**
 * @author dengyuhan
 * created 2019/3/19 14:27
 */
public interface PreViewCallback {

    View getHeaderView();

    View getFooterView();

    int getHeaderCount();

    int getFooterCount();

    boolean isLoadMoreEnabled();
}
