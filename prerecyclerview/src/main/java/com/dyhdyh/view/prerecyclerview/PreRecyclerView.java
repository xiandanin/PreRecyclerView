package com.dyhdyh.view.prerecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import java.lang.reflect.Method;

/**
 * @author dengyuhan
 * created 2019/3/19 14:20
 */
public class PreRecyclerView extends RecyclerView {
    private PreWrapperAdapter mWrapperAdapter;
    private LoadMoreFooter mFooter;

    //是否开启加载更多
    private boolean mLoadMoreEnabled;

    //加载更多是否完成
    private boolean mLoadMoreCompleted = true;

    //显示到倒数第mAutoLoadByLastCount条才开始自动加载
    private int mAutoLoadByLastCount = 1;

    private OnLoadMoreListener mOnLoadMoreListener;

    public PreRecyclerView(@NonNull Context context) {
        super(context);
    }

    public PreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //context.obtainStyledAttributes(attrs,R.styleable.)
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layout);
            final GridLayoutManager.SpanSizeLookup oldSizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mWrapperAdapter != null && mWrapperAdapter.isInner(position)) {
                        return 1;
                    } else {
                        if (oldSizeLookup == null) {
                            return gridManager.getSpanCount();
                        } else {
                            return oldSizeLookup.getSpanSize(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //判断是否需要加载更多
        if (mLoadMoreEnabled && mOnLoadMoreListener != null) {
            LayoutManager lm = super.getLayoutManager();
            if (lm != null && state == RecyclerView.SCROLL_STATE_IDLE) {
                int lastVisibleItemPosition = findLastVisibleItemPosition(lm);
                int itemCount = lm.getItemCount();

                //如果最后显示的位置符合 加载更多也处于空闲 就回调加载更多
                if (itemCount > 0 && lastVisibleItemPosition >= itemCount - mAutoLoadByLastCount && mLoadMoreCompleted) {
                    mLoadMoreCompleted = false;
                    if (mFooter != null) {
                        mFooter.setState(LoadMoreFooter.STATE_LOADING);
                    }
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }


    /**
     * 查找最后一个显示的位置
     *
     * @param lm
     * @return
     */
    protected int findLastVisibleItemPosition(RecyclerView.LayoutManager lm) {
        if (lm instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) lm).findLastVisibleItemPosition();
        } else if (lm instanceof StaggeredGridLayoutManager) {
            final int[] lastPositions = ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(null);
            return lastPositions[lastPositions.length - 1];
        } else if (lm.getClass().getName().equals("com.google.android.flexbox.FlexboxLayoutManager")) {
            try {
                final Method flexboxMethod = lm.getClass().getMethod("findLastVisibleItemPosition");
                return (int) flexboxMethod.invoke(lm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        } else {
            return 0;
        }
    }

}
