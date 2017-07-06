package com.dyhdyh.view.swiperefresh.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * author  dengyuhan
 * created 2017/7/4 14:17
 */
public class RecyclerHeaderHelper implements WrapperAdapter {

    private RecyclerView mRecyclerView;
    private HeaderRecyclerViewAdapter mWrapperAdapter;

    public RecyclerHeaderHelper(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mWrapperAdapter = new HeaderRecyclerViewAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            setupLayoutManager(layoutManager);
        }
        this.mRecyclerView.setAdapter(mWrapperAdapter);
    }


    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(setupLayoutManager(layout));
    }

    @Override
    public void addHeaderView(View header) {
        addHeaderView(header, true);
    }

    /**
     * 添加HeaderView
     *
     * @param header   头部View
     * @param animated 是否需要动画
     */
    public void addHeaderView(View header, boolean animated) {
        if (header == null) {
            throw new NullPointerException("header is null");
        }
        checkAdapterNotNull();
        mWrapperAdapter.getHeaderViews().add(header);
        notifyChanged(animated, new Runnable() {
            @Override
            public void run() {
                mWrapperAdapter.notifyItemInserted(mWrapperAdapter.getHeaderViewsCount() - 1);
            }
        });
    }

    @Override
    public void addFooterView(View footer) {
        addFooterView(footer, true);
    }

    /**
     * 添加FooterView
     *
     * @param footer   尾部View
     * @param animated 是否需要动画
     */
    public void addFooterView(View footer, boolean animated) {
        if (footer == null) {
            throw new NullPointerException("footer is null");
        }
        checkAdapterNotNull();
        mWrapperAdapter.getFooterViews().add(footer);
        notifyChanged(animated, new Runnable() {
            @Override
            public void run() {
                mWrapperAdapter.notifyItemInserted(mWrapperAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void removeHeaderView(View header) {
        removeHeaderView(header, true);
    }

    /**
     * 移除HeaderView
     *
     * @param header
     */
    public void removeHeaderView(View header, boolean animated) {
        checkAdapterNotNull();
        final List<View> headerViews = mWrapperAdapter.getHeaderViews();
        if (headerViews.size() <= 0) {
            return;
        }
        final int index = headerViews.indexOf(header);
        if (index < 0) {
            return;
        }
        mWrapperAdapter.getHeaderViews().remove(index);
        notifyChanged(animated, new Runnable() {
            @Override
            public void run() {
                final int position = index;
                mWrapperAdapter.notifyItemRemoved(position);
                int itemCount = mWrapperAdapter.getItemCount();
                mWrapperAdapter.notifyItemRangeChanged(position, itemCount - position);
            }
        });
    }

    @Override
    public void removeFooterView(View footer) {
        removeFooterView(footer, true);
    }

    /**
     * 移除FooterView
     *
     * @param footer
     */
    public void removeFooterView(View footer, boolean animated) {
        checkAdapterNotNull();
        final List<View> footerViews = mWrapperAdapter.getFooterViews();
        if (footerViews.size() <= 0) {
            return;
        }
        final int index = footerViews.indexOf(footer);
        if (index < 0) {
            return;
        }
        mWrapperAdapter.getFooterViews().remove(index);
        notifyChanged(animated, new Runnable() {
            @Override
            public void run() {
                final int position = mWrapperAdapter.getHeaderViewsCount() + getInnerItemCount() + index;
                mWrapperAdapter.notifyItemRemoved(position);
                int itemCount = mWrapperAdapter.getItemCount();
                if (position != itemCount) {
                    //如果移除的是最后一个，忽略
                    mWrapperAdapter.notifyItemRangeChanged(position, itemCount - position);
                }
            }
        });
    }


    @Override
    public boolean isHeader(int position) {
        return mWrapperAdapter.isHeader(position);
    }

    @Override
    public boolean isFooter(int position) {
        return mWrapperAdapter.isFooter(position);
    }


    public HeaderRecyclerViewAdapter getWrapperAdapter() {
        return mWrapperAdapter;
    }


    public RecyclerView.Adapter getInnerAdapter() {
        return mWrapperAdapter.getInnerAdapter();
    }


    public int getInnerItemCount() {
        return mWrapperAdapter.getInnerItemCount();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private RecyclerView.LayoutManager setupLayoutManager(RecyclerView.LayoutManager layout) {
        if (mWrapperAdapter == null) {
            return layout;
        }
        if (layout instanceof GridLayoutManager) {
            ((GridLayoutManager) layout).setSpanSizeLookup(new HeaderSpanSizeLookup(this, ((GridLayoutManager) layout).getSpanCount()));
        }
        return layout;
    }

    private void checkAdapterNotNull() {
        if (mWrapperAdapter == null) {
            throw new NullPointerException("adapter is null");
        }
    }

    private void notifyChanged(boolean animated, Runnable runnable) {
        if (animated) {
            runnable.run();
        } else {
            mWrapperAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 替代RecyclerView.ViewHolder的getLayoutPosition()方法
     *
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof HeaderRecyclerViewAdapter) {
            int headerViewCounter = ((HeaderRecyclerViewAdapter) adapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }
        return holder.getLayoutPosition();
    }

    /**
     * 替代RecyclerView.ViewHolder的getAdapterPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof HeaderRecyclerViewAdapter) {
            int headerViewCounter = ((HeaderRecyclerViewAdapter) adapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }
        return holder.getAdapterPosition();
    }
}
