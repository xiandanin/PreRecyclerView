package com.dyhdyh.view.swiperefresh.recyclerview;

import android.view.View;

/**
 * author  dengyuhan
 * created 2017/7/5 11:30
 */
public interface WrapperAdapter {

    void addHeaderView(View header);

    void addFooterView(View footer);

    void removeHeaderView(View header);

    void removeFooterView(View footer);

    boolean isHeader(int position);

    boolean isFooter(int position);
}
