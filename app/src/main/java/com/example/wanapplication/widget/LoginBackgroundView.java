package com.example.wanapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.wanapplication.R;

public class LoginBackgroundView extends View {
    private Paint mPaint = new Paint();
    private Paint bluePaint = new Paint();
    private Path mPath = new Path();
    private int heightSize;
    private int color;

    public LoginBackgroundView(Context context) {
        super(context);
        init();
    }

    public LoginBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    @SuppressLint("ResourceAsColor")
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginBackgroundView);
        color = typedArray.getColor(R.styleable.LoginBackgroundView_backgroundColor, R.color.mine_background_blue);
    }

    public LoginBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoginBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        bluePaint.setColor(color);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), heightSize, bluePaint);
        mPath.moveTo(0, heightSize);//起点
        mPath.quadTo(getWidth() /2, heightSize-200, getWidth(), heightSize);
        canvas.drawPath(mPath, mPaint);
        //canvas.drawArc(new RectF(0, 0, 100, 100),0,90,true,mPaint);

    }
}
