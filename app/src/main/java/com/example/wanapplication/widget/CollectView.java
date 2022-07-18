package com.example.wanapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wanapplication.R;

import per.goweii.reveallayout.RevealLayout;

public class CollectView extends RevealLayout {

    private int mUncheckedColor;

    public CollectView(Context context) {
        this(context, null);
    }

    public CollectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initAttr(AttributeSet attrs) {
        super.initAttr(attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CollectView);
        mUncheckedColor = typedArray.getColor(R.styleable.CollectView_cv_uncheckedColor, 0);
        typedArray.recycle();
        setCheckWithExpand(true);
        setUncheckWithExpand(false);
        setAnimDuration(500);
        setAllowRevert(true);
    }


    @Override
    protected View createUncheckedView() {
        View view = super.createUncheckedView();

        return view;
    }

}
