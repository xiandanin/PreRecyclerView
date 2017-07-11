package com.dyhdyh.widget.swiperefresh.listener;

import android.support.v7.widget.RecyclerView;

/**
 * author  dengyuhan
 * created 2017/7/6 14:12
 */
public class OnRecyclerScrollChangeListener {
    /**
     * 向下滑
     */
    public static final int STATE_SCROLL_UP = 1;

    /**
     * 向上滑
     */
    public static final int STATE_SCROLL_DOWN = 2;

    /**
     * 到底部
     */
    public static final int STATE_ON_BOTTOM = 3;


    /**
     * 正在滑动
     */
    public static final int STATE_SCROLL = 4;


    /**
     * 正在滑动
     *
     * @param recyclerView
     * @param distanceX
     * @param distanceY
     */
    public void onScrolled(RecyclerView recyclerView, int distanceX, int distanceY) {

    }

    public void onScrollStateChanged(RecyclerView recyclerView, int state) {

    }

}