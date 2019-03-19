package com.dyhdyh.view.prerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * @author dengyuhan
 * created 2019/3/19 15:48
 */
public class PreInnerDataObserver extends RecyclerView.AdapterDataObserver {
    private PreWrapperAdapter mWrapperAdapter;
    private OnItemCountChangedListener mOnItemCountChangedListener;

    public PreInnerDataObserver(@NonNull PreWrapperAdapter wrapperAdapter) {
        this.mWrapperAdapter = wrapperAdapter;
    }

    public void setOnItemCountChangedListener(OnItemCountChangedListener listener) {
        this.mOnItemCountChangedListener = listener;
    }

    @Override
    public void onChanged() {
        mWrapperAdapter.notifyDataSetChanged();
        if (mOnItemCountChangedListener != null) {
            mOnItemCountChangedListener.onItemCountChanged(mWrapperAdapter.getItemCount());
        }
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mWrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
        if (mOnItemCountChangedListener != null) {
            mOnItemCountChangedListener.onItemCountChanged(mWrapperAdapter.getItemCount());
        }
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mWrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        if (mOnItemCountChangedListener != null) {
            mOnItemCountChangedListener.onItemCountChanged(mWrapperAdapter.getItemCount());
        }
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        mWrapperAdapter.notifyItemMoved(fromPosition, toPosition);
    }
}
