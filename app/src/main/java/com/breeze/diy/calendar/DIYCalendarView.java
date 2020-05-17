package com.breeze.diy.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DIYCalendarView extends View {

    private static final String TAG = "DIYCalendarView";
    private boolean mIsInit = false;

    //创建二维数组
//    private ArrayList<Point>[][] mArrayLists = new ArrayList<>()[7][6];
    //设置需要画多少个数字
    private int size = 42;

    //画笔
    private Paint mPaint;

    //颜色
    private int mNormalPaintColor = 0xff808080;
    private float mFirstY;
    private float mFirstX;
    private float mTextWidth;

    public DIYCalendarView(Context context) {
        super(context);
    }

    public DIYCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DIYCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        drawShow(canvas);
//        invalidate();
    }

    private void drawShow(Canvas canvas) {
        Log.d(TAG, "drawShow: ");
        //onDraw在不断绘制的过程中会多次调用，所以需要判断一下是不是第一次调用
        if (!mIsInit) {

            //计算出点的位置
            initDot();
            //初始化画笔
            initPaint();
        }

        //绘制数字
        drawNumber(canvas);
    }

    private void drawNumber(Canvas canvas) {
        Log.d(TAG, "drawNumber: ");
        float dx = mFirstX;
        float dy = mFirstY;
        int x = 1;
        for (int i = 0; i < size; i++) {

            if (i >= 1) {
                dx = mFirstX + mFirstX * 2 * (x - 1);
            }
            if (i % 7 == 0) {
                //这个时候需要换行  mFirstY 需要增加   mFirstX从最左边重新开始相加
                dx = mFirstX;
                dy = dy + mFirstX * 2;
                x = 1;
            }

            String text = (i + 1) + "";

            //获取文字的宽高
            getTextWidth(text, mPaint);
            canvas.drawText(text, dx - mTextWidth / 2, dy, mPaint);
            x++;
        }
    }

    //获取文字的宽高
    private void getTextWidth(String text, Paint paint) {
        //文字的宽度
        mTextWidth = paint.measureText(text);
        //高度
//        mTextHeight = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
    }

    private void initDot() {
        //计算每个数字的中心点
        float mWidth = getWidth();
        float mHeight = getHeight();
        float offsetX = mWidth / 7;
        float offsetY = (mHeight - mWidth) / 4;

        //第一个点的中心点为
        mFirstX = offsetX / 2;
        mFirstY = offsetY + mFirstX;
    }

    //初始化画笔
    private void initPaint() {
        Log.d(TAG, "initPaint: ");
        mPaint = new Paint();
        mPaint.setColor(mNormalPaintColor);
//        mPaint.setStrokeWidth(30);
        mPaint.setTextSize(65);
        mPaint.setAntiAlias(true);
    }
}
