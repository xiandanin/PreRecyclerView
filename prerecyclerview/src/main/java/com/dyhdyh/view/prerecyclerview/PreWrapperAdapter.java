package com.dyhdyh.view.prerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author dengyuhan
 * created 2019/3/19 14:24
 */
public class PreWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PreViewCallback mPreView;

    private final RecyclerView.Adapter mInnerAdapter;

    private int VIEW_TYPE_HEADER = Integer.MAX_VALUE / 2; //1073741823
    private int VIEW_TYPE_FOOTER = VIEW_TYPE_HEADER - 1; //1073741822

    public PreWrapperAdapter(@NonNull RecyclerView.Adapter adapter, @NonNull PreViewCallback preView) {
        this.mInnerAdapter = adapter;
        this.mPreView = preView;
    }

    /**
     * 当HeaderView更换的时候 需要刷新VIEW_TYPE_HEADER 否则不会重新调用onCreateViewHolder
     */
    public void refreshHeaderType() {
        VIEW_TYPE_HEADER++;
    }


    /**
     * 当FooterView更换的时候 需要刷新VIEW_TYPE_FOOTER 否则不会重新调用onCreateViewHolder
     */
    public void refreshFooterType() {
        VIEW_TYPE_FOOTER--;
    }

    @NonNull
    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return VIEW_TYPE_HEADER;
        } else if (isFooter(position)) {
            return VIEW_TYPE_FOOTER;
        } else {
            return mInnerAdapter.getItemViewType(getInnerPosition(position));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new RecyclerView.ViewHolder(mPreView.getHeaderView()) {
            };
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return new RecyclerView.ViewHolder(mPreView.getFooterView()) {
            };
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (isInner(position)) {
            mInnerAdapter.onBindViewHolder(holder, getInnerPosition(position), payloads);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isInner(position)) {
            mInnerAdapter.onBindViewHolder(holder, getInnerPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + mPreView.getHeaderCount() + mPreView.getFooterCount();
    }


    protected boolean isHeader(int position) {
        final int headerCount = mPreView.getHeaderCount();
        if (headerCount > 0) {
            //如果有header position小于header数量的都是header
            return position < headerCount;
        }
        return false;
    }


    protected boolean isFooter(int position) {
        final int footerCount = mPreView.getFooterCount();
        if (footerCount > 0) {
            //如果有footer position大于footer数量的都是footer
            return position >= this.getItemCount() - footerCount;
        } else {
            return false;
        }
    }

    protected boolean isInner(int position) {
        //return position >= mPreView.getHeaderCount() && position < this.getItemCount() - mPreView.getFooterCount();
        return !isHeader(position) && !isFooter(position);
    }


    /**
     * 通过实际位置或者内部位置
     * @param position
     * @return
     */
    protected int getInnerPosition(int position) {
        return position - mPreView.getHeaderCount();
    }

    /**
     * 通过内部位置获取实际位置
     * @param innerPosition
     * @return
     */
    protected int getWrapperPosition(int innerPosition) {
        return innerPosition + mPreView.getHeaderCount();
    }


    /**
     * ---------------------------------------------------------
     */


    @Override
    public long getItemId(int position) {
        if (isInner(position)) {
            return mInnerAdapter.getItemId(getInnerPosition(position));
        } else {
            return super.getItemId(position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull final RecyclerView.ViewHolder holder) {
        final int layoutPosition = holder.getLayoutPosition();
        if (isInner(layoutPosition)) {
            //如果有实现接口 就回调带内部position的方法
            if (mInnerAdapter instanceof PreInnerAdapter) {
                ((PreInnerAdapter) mInnerAdapter).onViewAttachedToWindow(holder, getInnerPosition(layoutPosition));
            } else {
                mInnerAdapter.onViewAttachedToWindow(holder);
            }
        } else {
            //兼容瀑布流
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            super.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull final RecyclerView.ViewHolder holder) {
        final int layoutPosition = holder.getLayoutPosition();
        if (isInner(layoutPosition)) {
            //如果有实现接口 就回调带内部position的方法
            if (mInnerAdapter instanceof PreInnerAdapter) {
                ((PreInnerAdapter) mInnerAdapter).onViewDetachedFromWindow(holder, getInnerPosition(layoutPosition));
            } else {
                mInnerAdapter.onViewDetachedFromWindow(holder);
            }
        } else {
            super.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        final int layoutPosition = holder.getLayoutPosition();
        if (isInner(layoutPosition)) {
            //如果有实现接口 就回调带内部position的方法
            if (mInnerAdapter instanceof PreInnerAdapter) {
                ((PreInnerAdapter) mInnerAdapter).onViewRecycled(holder, getInnerPosition(layoutPosition));
            } else {
                mInnerAdapter.onViewRecycled(holder);
            }
        } else {
            super.onViewRecycled(holder);
        }
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        final int layoutPosition = holder.getLayoutPosition();
        if (isInner(layoutPosition)) {
            //如果有实现接口 就回调带内部position的方法
            if (mInnerAdapter instanceof PreInnerAdapter) {
                return ((PreInnerAdapter) mInnerAdapter).onFailedToRecycleView(holder, getInnerPosition(layoutPosition));
            } else {
                return mInnerAdapter.onFailedToRecycleView(holder);
            }
        } else {
            return super.onFailedToRecycleView(holder);
        }
    }

}
