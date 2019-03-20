package com.dyhdyh.view.prerecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author dengyuhan
 * created 2019/3/19 14:20
 */
public class PreRecyclerView extends RecyclerView implements OnItemCountChangedListener {
    private final int DEFAULT_AUTOLOAD_LASTCOUNT = 1;

    private PreWrapperAdapter mWrapperAdapter;
    private View mHeaderView;
    private LoadMoreFooter mFooterView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemCountChangedListener mOnItemCountChangedListener;

    //是否开启加载更多
    private boolean mLoadMoreEnabled = true;

    //加载更多是否完成
    private boolean mLoadMoreCompleted = true;

    //是否已经到底
    private boolean mLoadMoreEnd;

    //显示到倒数第mAutoLoadByLastCount条才开始自动加载
    private int mAutoLoadByLastCount = DEFAULT_AUTOLOAD_LASTCOUNT;

    private PreViewCallback mPreViewCallback = new PreViewCallback() {
        @Override
        public View getHeaderView() {
            return mHeaderView;
        }

        @Override
        public View getFooterView() {
            return mFooterView == null ? null : mFooterView.getView();
        }

        @Override
        public int getHeaderCount() {
            return mHeaderView == null ? 0 : 1;
        }

        @Override
        public int getFooterCount() {
            return mLoadMoreEnabled && mFooterView != null ? 1 : 0;
        }

        @Override
        public boolean isLoadMoreEnabled() {
            return mLoadMoreEnabled;
        }
    };

    public PreRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PreRecyclerView);
        mLoadMoreEnabled = a.getBoolean(R.styleable.PreRecyclerView_loadMoreEnabled, true);
        mAutoLoadByLastCount = a.getInt(R.styleable.PreRecyclerView_autoLoadByLastCount, DEFAULT_AUTOLOAD_LASTCOUNT);
        String footerClassString = a.getString(R.styleable.PreRecyclerView_loadMoreFooter);
        a.recycle();

        try {
            if (!TextUtils.isEmpty(footerClassString)) {
                final Class<?> footerClass = Class.forName(footerClassString);
                if (LoadMoreFooter.class.isAssignableFrom(footerClass)) {
                    Constructor con = footerClass.getConstructor(Context.class);
                    mFooterView = (LoadMoreFooter) con.newInstance(context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mFooterView == null) {
            mFooterView = new SimpleLoadMoreView(context, attrs, defStyle);
        }
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layout);
            gridManager.setSpanSizeLookup(new PreSpanSizeLookup(gridManager) {

                @Override
                public boolean isHeaderOrFooter(int position) {
                    return mWrapperAdapter != null && (mWrapperAdapter.isHeader(position) || mWrapperAdapter.isFooter(position));
                }
            });
        }
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter == null) {
            super.setAdapter(null);
        } else {
            mWrapperAdapter = new PreWrapperAdapter(adapter, mPreViewCallback);
            final PreInnerDataObserver observer = new PreInnerDataObserver(mWrapperAdapter);
            observer.setOnItemCountChangedListener(this);
            adapter.registerAdapterDataObserver(observer);
            super.setAdapter(mWrapperAdapter);
        }
    }

    @Nullable
    public Adapter getInnerAdapter() {
        return mWrapperAdapter == null ? null : mWrapperAdapter.getInnerAdapter();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //如果加载更多是开启的/处于空闲状态/数据没有到底/也有监听 就检查是否可以加载更多
        if (mLoadMoreEnabled && mLoadMoreCompleted && !mLoadMoreEnd && mOnLoadMoreListener != null) {
            LayoutManager lm = super.getLayoutManager();
            if (lm != null && state == RecyclerView.SCROLL_STATE_IDLE) {
                int lastVisibleItemPosition = findLastVisibleItemPosition(lm);
                int itemCount = lm.getItemCount();

                //如果最后显示的位置符合 加载更多也处于空闲 就回调加载更多
                final int footerCount = mPreViewCallback.getFooterCount();
                if (itemCount > 0 && lastVisibleItemPosition >= itemCount - footerCount - mAutoLoadByLastCount) {
                    this.mLoadMoreCompleted = false;
                    this.setLoadMoreState(LoadMoreFooter.STATE_LOADING);
                    this.mOnLoadMoreListener.onLoadMore();
                }
            }
        }
    }


    @Override
    public void onItemCountChanged(int itemCount) {
        if (mOnItemCountChangedListener != null) {
            mOnItemCountChangedListener.onItemCountChanged(itemCount);
        }
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        if (mWrapperAdapter != null) {
            mWrapperAdapter.refreshHeaderType();
            mWrapperAdapter.notifyDataSetChanged();
        }
    }

    public void removeHeaderView() {
        this.mHeaderView = null;
        if (mWrapperAdapter != null) {
            mWrapperAdapter.notifyDataSetChanged();
        }
    }

    public void setLoadMoreFooter(LoadMoreFooter footerView) {
        this.mFooterView = footerView;
        if (mWrapperAdapter != null) {
            mWrapperAdapter.refreshFooterType();
            mWrapperAdapter.notifyDataSetChanged();
        }
    }

    public void setAutoLoadByLastCount(int autoLoadByLastCount) {
        this.mAutoLoadByLastCount = autoLoadByLastCount;
    }

    public void setLoadMoreCompleted() {
        this.mLoadMoreCompleted = true;
    }

    public boolean isLoadMoreCompleted() {
        return mLoadMoreCompleted;
    }

    public void setLoadMoreState(@IntRange(from = LoadMoreFooter.STATE_NORMAL, to = LoadMoreFooter.STATE_THE_END) int state) {
        if (mFooterView != null) {
            mFooterView.setState(state);
        }
        if (LoadMoreFooter.STATE_THE_END == state) {
            mLoadMoreEnd = true;
        }
    }


    public void reset() {
        setLoadMoreState(LoadMoreFooter.STATE_NORMAL);
        mLoadMoreCompleted = true;
        mLoadMoreEnd = false;
    }

    public int getLoadMoreState() {
        if (mFooterView != null) {
            return mFooterView.getState();
        }
        return LoadMoreFooter.STATE_NORMAL;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    public void setOnItemCountChangedListener(OnItemCountChangedListener listener) {
        this.mOnItemCountChangedListener = listener;
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
