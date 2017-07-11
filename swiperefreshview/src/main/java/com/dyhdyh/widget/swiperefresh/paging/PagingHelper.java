package com.dyhdyh.widget.swiperefresh.paging;

/**
 * author  dengyuhan
 * created 2017/7/10 18:29
 */
public class PagingHelper implements PagingWrapper {

    private int mPage;
    private int mPageCount;

    private int mStartPage = DEFAULT_START_PAGE;

    public PagingHelper() {

    }

    public PagingHelper(int pageCount) {
        this(DEFAULT_START_PAGE, pageCount);
    }

    public PagingHelper(int startPage, int pageCount) {
        this.mStartPage = startPage;
        this.mPageCount = pageCount;
    }

    @Override
    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
    }

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void resetPage() {
        mPage = mStartPage;
    }

    @Override
    public void pageDown() {
        mPage++;
    }

    public int getPageCount() {
        return mPageCount;
    }
}
