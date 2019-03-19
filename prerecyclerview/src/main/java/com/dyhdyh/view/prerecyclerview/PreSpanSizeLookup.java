package com.dyhdyh.view.prerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

/**
 * @author dengyuhan
 * created 2019/3/19 18:34
 */
public abstract class PreSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private GridLayoutManager.SpanSizeLookup mInnerSizeLookup;
    private int mSpanCount;

    public PreSpanSizeLookup(@NonNull GridLayoutManager layoutManager) {
        this.mInnerSizeLookup = layoutManager.getSpanSizeLookup();
        this.mSpanCount = layoutManager.getSpanCount();
    }

    @Override
    public int getSpanSize(int position) {
        //如果是header或者footer
        if (isHeaderOrFooter(position)) {
            return mSpanCount;
        } else {
            return mInnerSizeLookup == null ? 1 : mInnerSizeLookup.getSpanSize(position);
        }
    }

    public abstract boolean isHeaderOrFooter(int position);
}
