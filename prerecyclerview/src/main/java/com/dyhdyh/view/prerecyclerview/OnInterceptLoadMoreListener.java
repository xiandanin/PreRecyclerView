package com.dyhdyh.view.prerecyclerview;

/**
 * @author dengyuhan
 * created 2019/3/19 15:59
 */
public interface OnInterceptLoadMoreListener {

    /**
     *
     * @return true=拦截 不继续执行 false=不拦截 执行加载更多
     */
    boolean onInterceptLoadMore();
}
