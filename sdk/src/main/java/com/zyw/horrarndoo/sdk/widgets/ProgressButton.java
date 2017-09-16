package com.zyw.horrarndoo.sdk.widgets;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

import com.zyw.horrarndoo.sdk.R;


/**
 * Created by Horrarndoo on 2017/4/5.
 * <p>
 * 带有进度变化的button
 */
public class ProgressButton extends Button {
    public static final int MAX_PROGRESS = 1000;
    private boolean isAdding = false;
    private int progress = 0;
    private int progressStrokeWidth = 6;

    private Paint mPaint;
    private RectF dst;

    private Context context;

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        dst = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(progressStrokeWidth);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        mPaint.setColor(context.getResources().getColor(R.color.dark_grey));
        canvas.drawColor(Color.TRANSPARENT);

        dst.left = progressStrokeWidth / 2; // 左上角x
        dst.top = progressStrokeWidth / 2; // 左上角y
        dst.right = width - progressStrokeWidth / 2; // 左下角x
        dst.bottom = height - progressStrokeWidth / 2; // 右下角y

        canvas.drawArc(dst, -90, 360, false, mPaint);

        if (isAdding) {
            mPaint.setColor(context.getResources().getColor(R.color.light_yellow));
            canvas.drawArc(dst, -90, ((float) progress / MAX_PROGRESS) * 360, false, mPaint);
        }
    }

    /**
     * 非ＵＩ线程调用
     */
    public void setProgressNotInUiThread(int progress, boolean isAdding) {
        this.isAdding = isAdding;
        this.progress = progress;
        this.postInvalidate();
    }
}