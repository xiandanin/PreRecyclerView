package com.dyhdyh.view.prerecyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SimpleLoadMoreView extends RelativeLayout implements LoadMoreFooter {
    private ProgressBar mProgressBar;
    private TextView mLabelTextView;
    private TextView mSymbolView;
    private int mState;

    public SimpleLoadMoreView(Context context) {
        this(context, null);
    }

    public SimpleLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, getLayoutId(), this);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loadmore);
        mLabelTextView = (TextView) findViewById(R.id.tv_loadmore_label);
        mSymbolView = (TextView) findViewById(R.id.tv_loadmore_symbol);
        final Drawable drawable = mSymbolView.getBackground();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mLabelTextView.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.darker_gray, null));
            mSymbolView.setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.darker_gray, null));
        }
        setState(LoadMoreFooter.STATE_NORMAL);
        buildViewStyle(mProgressBar, mLabelTextView, mSymbolView);
    }

    protected int getLayoutId() {
        return R.layout.view_pre_simple_footer;
    }

    protected void buildViewStyle(ProgressBar progress, TextView label, TextView symbol) {

    }

    @Override
    public void setState(int state) {
        mState = state;
        if (LoadMoreFooter.STATE_NORMAL == state) {
            super.setVisibility(View.GONE);
        } else {
            super.setVisibility(View.VISIBLE);
            if (LoadMoreFooter.STATE_LOADING == state) {
                mProgressBar.setVisibility(View.VISIBLE);
                mSymbolView.setVisibility(View.GONE);
                mLabelTextView.setText(R.string.pre_label_footer_loading);
            } else if (LoadMoreFooter.STATE_THE_END == state) {
                mProgressBar.setVisibility(View.GONE);
                mSymbolView.setVisibility(View.GONE);
                mLabelTextView.setText(R.string.pre_label_footer_end);
            } else if (LoadMoreFooter.STATE_ERROR == state) {
                mProgressBar.setVisibility(View.GONE);
                mSymbolView.setVisibility(View.VISIBLE);
                mLabelTextView.setText(R.string.pre_label_footer_error);
            }
        }
    }

    @Override
    public int getState() {
        return mState;
    }

    @Override
    public View getView() {
        return this;
    }
}

