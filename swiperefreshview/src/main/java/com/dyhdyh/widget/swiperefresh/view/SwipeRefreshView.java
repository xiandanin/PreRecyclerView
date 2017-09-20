package com.dyhdyh.widget.swiperefresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import com.dyhdyh.widget.swiperefresh.R;


/**
 * author  dengyuhan
 * created 2017/7/4 13:51
 */
public abstract class SwipeRefreshView<V extends View> extends SwipeRefreshLayout {
    private V mView;

    public SwipeRefreshView(Context context) {
        this(context, null);
    }

    public SwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
        mView = inflateChildView(context, attrs);
        addView(mView);
        applyAttributeSet(context, attrs);
    }

    protected void applyAttributeSet(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshView);
        setEnabled(a.getBoolean(R.styleable.SwipeRefreshView_refreshEnabled, true));
        int colorScheme = a.getColor(R.styleable.SwipeRefreshView_schemeColor, getDefaultColorScheme(context,attrs));
        if (colorScheme != 0) {
            setColorSchemeColors(colorScheme);
        }
        a.recycle();
    }

    private int getDefaultColorScheme(Context context, AttributeSet attr) {
        //刷新进度圈的默认颜色
        int[] colorAccentAttributes;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAccentAttributes = new int[]{android.R.attr.colorAccent};
        } else {
            colorAccentAttributes = new int[]{android.support.v7.appcompat.R.attr.colorAccent};
        }
        TypedArray androidTypedArray = context.obtainStyledAttributes(attr, colorAccentAttributes);
        int defaultColorScheme = androidTypedArray.getColor(0, 0);
        androidTypedArray.recycle();
        return defaultColorScheme;
    }

    protected abstract V inflateChildView(Context context, AttributeSet attrs);


    public void setRefreshComplete() {
        setRefreshing(false);
    }

    public V getView() {
        return mView;
    }
}
