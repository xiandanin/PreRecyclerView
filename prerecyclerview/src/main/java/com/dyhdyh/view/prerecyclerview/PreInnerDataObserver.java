package com.dyhdyh.view.prerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * 给InnerAdapter注册 通过WrapperAdapter更新Inner
 * InnerAdapter调用notify 会进入到这里
 *
 * @author dengyuhan
 * created 2019/3/19 15:48
 */
public class PreInnerDataObserver extends RecyclerView.AdapterDataObserver {
    private PreWrapperAdapter mWrapperAdapter;
    private int mLastItemCount = -1;
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
        callItemCountChanged();
    }


    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        //转换为实际位置 再刷新
        mWrapperAdapter.notifyItemRangeInserted(mWrapperAdapter.getWrapperPosition(positionStart), itemCount);
        callItemCountChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mWrapperAdapter.notifyItemRangeChanged(mWrapperAdapter.getWrapperPosition(positionStart), itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        mWrapperAdapter.notifyItemRangeChanged(mWrapperAdapter.getWrapperPosition(positionStart), itemCount, payload);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mWrapperAdapter.notifyItemRangeRemoved(mWrapperAdapter.getWrapperPosition(positionStart), itemCount);
        callItemCountChanged();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        mWrapperAdapter.notifyItemMoved(mWrapperAdapter.getWrapperPosition(fromPosition), mWrapperAdapter.getWrapperPosition(toPosition));
    }


    protected void callItemCountChanged() {
        if (mOnItemCountChangedListener != null) {
            //如果数量发生变化 就回调
            final int innerItemCount = mWrapperAdapter.getInnerAdapter().getItemCount();
            if (innerItemCount != mLastItemCount) {
                this.mLastItemCount = innerItemCount;
                mOnItemCountChangedListener.onItemCountChanged(innerItemCount);
            }
        }
    }
}
