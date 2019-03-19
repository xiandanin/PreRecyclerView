package com.dyhdyh.view.prerecyclerview;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author dengyuhan
 * created 2019/3/19 14:27
 */
public interface PreView {

    void setHeaderView(@NonNull View headerView);

    void setFooterView(@NonNull View footerView);

    View getHeaderView();

    View getFooterView();

    int getHeaderCount();

    int getFooterCount();

    void setLoadMoreEnabled(boolean enabled);

    boolean isLoadMoreEnabled();
}
