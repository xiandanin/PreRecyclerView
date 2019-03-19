package com.dyhdyh.view.prerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * @author dengyuhan
 * created 2019/3/19 15:13
 */
public interface PreInnerAdapter<VH extends RecyclerView.ViewHolder> {

    void onViewAttachedToWindow(@NonNull VH holder, int innerPosition);

    void onViewDetachedFromWindow(@NonNull VH holder, int innerPosition);

    void onViewRecycled(@NonNull VH holder, int innerPosition);

    boolean onFailedToRecycleView(@NonNull VH holder, int innerPosition);
}
