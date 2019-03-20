package com.dyhdyh.view.prerecyclerview.example;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dyhdyh.view.prerecyclerview.LoadMoreFooter;

/**
 * @author dengyuhan
 * created 2019/3/20 10:45
 */
public class CustomFooterView extends RelativeLayout implements LoadMoreFooter {
    private int mState;

    private TextView tv_footer_label;

    public CustomFooterView(Context context) {
        this(context, null);
    }

    public CustomFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_custom_footer, this);
        tv_footer_label = findViewById(R.id.tv_footer_label);
    }

    @Override
    public void setState(int state) {
        mState = state;
        if (LoadMoreFooter.STATE_LOADING == mState) {
            tv_footer_label.setText(R.string.pre_label_footer_loading);
        }else if (LoadMoreFooter.STATE_ERROR == mState) {
            tv_footer_label.setText(R.string.pre_label_footer_error);
        }else if (LoadMoreFooter.STATE_THE_END == mState) {
            tv_footer_label.setText(R.string.pre_label_footer_end);
        }else{
            tv_footer_label.setText("闲置");
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
