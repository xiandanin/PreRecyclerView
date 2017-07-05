package com.dyhdyh.view.swiperefresh.recyclerview;

import android.support.v7.widget.GridLayoutManager;

/**
 * author  dengyuhan
 * created 2017/7/4 17:22
 */
public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private WrapperAdapter mAdapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(WrapperAdapter adapter, int spanSize) {
        this.mAdapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = mAdapter.isHeader(position) || mAdapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}