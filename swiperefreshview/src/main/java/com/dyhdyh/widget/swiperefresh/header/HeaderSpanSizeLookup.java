package com.dyhdyh.widget.swiperefresh.header;

import android.support.v7.widget.GridLayoutManager;

/**
 * author  dengyuhan
 * created 2017/7/4 17:22
 */
public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private HeaderWrapper mAdapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(HeaderWrapper adapter, int spanSize) {
        this.mAdapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = mAdapter.isHeader(position) || mAdapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}