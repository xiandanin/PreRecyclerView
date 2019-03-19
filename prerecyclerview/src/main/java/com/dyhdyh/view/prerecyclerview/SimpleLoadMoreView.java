package com.dyhdyh.view.prerecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SimpleLoadMoreView extends RelativeLayout implements LoadMoreFooter{
    private ProgressBar mProgressBar;
    private TextView mLabelTextView;
    private TextView mSymbolView;

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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && drawable instanceof GradientDrawable) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorAccent, typedValue, true);
            int[] attribute = new int[]{android.support.v7.appcompat.R.attr.colorAccent};
            TypedArray array = context.obtainStyledAttributes(typedValue.resourceId, attribute);
            //((GradientDrawable) drawable).setStroke(getResources().getDimensionPixelSize(R.dimen.stroke_width_error_icon), array.getColor(0, Color.DKGRAY));
            array.recycle();
        }
        buildViewStyle(mProgressBar, mLabelTextView, mSymbolView);
    }

    protected int getLayoutId() {
        return R.layout.view_pre_simple_footer;
    }

    protected void buildViewStyle(ProgressBar progress, TextView label, TextView symbol) {

    }

    @Override
    public void setState(int state) {
        if (LoadMoreFooter.STATE_NORMAL == state) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (LoadMoreFooter.STATE_LOADING == state) {
                mProgressBar.setVisibility(View.VISIBLE);
                mSymbolView.setVisibility(View.GONE);
                mLabelTextView.setText(R.string.pre_label_footer_loading);
            } else if (LoadMoreFooter.STATE_THE_END == state) {
                mProgressBar.setVisibility(View.GONE);
                mSymbolView.setVisibility(View.GONE);
                mLabelTextView.setText(R.string.pre_label_footer_error);
            } else if (LoadMoreFooter.STATE_ERROR == state) {
                mProgressBar.setVisibility(View.GONE);
                mSymbolView.setVisibility(View.VISIBLE);
                mLabelTextView.setText(R.string.pre_label_footer_end);
            }
        }
    }

    @Override
    public View getView() {
        return this;
    }
}

