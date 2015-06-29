package com.markesilva.sunshine.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mark on 6/28/2015.
 */
public class WindDirView extends View {
    private int mHeight = -1;
    private int mWidth = -1;
    private float mWindDir = -1;

    public WindDirView(Context context) {
        super(context);
    }
    public WindDirView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public WindDirView(Context context, AttributeSet attrs, int DefaultStyle) {
        super(context, attrs, DefaultStyle);
    }

    public void setWindDir(float dir) {
        mWindDir = dir;
    }
    @Override
    protected void onMeasure(int wMeasureSpec, int hMeasureSpec) {
        int hSpecMode = MeasureSpec.getMode(hMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(hMeasureSpec);
        mHeight = hSpecSize;

        if (hSpecMode == MeasureSpec.EXACTLY) {
            mHeight = hSpecSize;
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            mHeight = hSpecSize;
        }

        int wSpecMode = MeasureSpec.getMode(wMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(wMeasureSpec);
        mWidth= wSpecSize;

        if (wSpecMode == MeasureSpec.EXACTLY) {
            mWidth = wSpecSize;
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            mWidth = wSpecSize;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // if canvas is null we have nothing to do
        if (canvas == null) { return; }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);

        // Create arrow path
        Path leftTriangle = new Path();
        leftTriangle.moveTo(mWidth * .5f, mHeight * .5f);
        leftTriangle.lineTo(mWidth * .5f, mHeight * .02f);
        leftTriangle.lineTo(mWidth * .4f, mHeight * .75f);
        leftTriangle.lineTo(mWidth * .5f, mHeight * .5f);
        leftTriangle.close();
        Path rightTriangle = new Path();
        rightTriangle.moveTo(mWidth * .5f, mHeight * .5f);
        rightTriangle.lineTo(mWidth * .5f, mHeight * .02f);
        rightTriangle.lineTo(mWidth * .6f, mHeight * .75f);
        rightTriangle.lineTo(mWidth * .5f, mHeight * .5f);
        rightTriangle.close();
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        // we draw the arrow first so we can rotate it
        canvas.save();
        canvas.rotate(mWindDir, canvas.getWidth() / 2, canvas.getHeight() / 2);
        paint.setColor(Color.RED);
        canvas.drawPath(leftTriangle, paint);
        paint.setColor(Color.MAGENTA);
        canvas.drawPath(rightTriangle, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(leftTriangle, paint);
        canvas.drawPath(rightTriangle, paint);
        canvas.restore();

        // Now we can add the circle and text
        paint.setStrokeWidth(7);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(canvas.getWidth() * .5f, canvas.getHeight() * .5f, canvas.getHeight() * .48f, paint);

        // Now the text
        paint.setTextSize(canvas.getHeight() * .1f);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText("N", canvas.getWidth() * .5f, canvas.getHeight() * .1f, paint);
        canvas.save();
        canvas.rotate(90, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawText("E", canvas.getWidth() * .5f, canvas.getHeight() * .1f, paint);
        canvas.restore();
        canvas.save();
        canvas.rotate(180, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawText("S", canvas.getWidth() * .5f, canvas.getHeight() * .1f, paint);
        canvas.restore();
        canvas.save();
        canvas.rotate(270, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawText("W", canvas.getWidth()*.5f, canvas.getHeight()*.1f, paint);
        canvas.restore();
    }
}
