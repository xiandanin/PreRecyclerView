package com.dyhdyh.view.swiperefresh.paging;

/**
 * author  dengyuhan
 * created 2017/7/10 18:35
 */
public interface PagingWrapper {
    int DEFAULT_START_PAGE = 0;
    int DEFAULT_PAGE_COUNT = 10;

    void setPageCount(int pageCount);

    int getPage();

    void resetPage();

    void addPage();
}
