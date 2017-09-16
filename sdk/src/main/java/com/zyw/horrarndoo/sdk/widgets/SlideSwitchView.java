package com.zyw.horrarndoo.sdk.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zyw.horrarndoo.sdk.R;


/**
 * Created by Horrarndoo on 2017/6/1.
 * <p>
 * 自定义滑动开关
 * <p>
 * Android 的界面绘制流程
 * 测量			 摆放		绘制
 * measure	->	layout	->	draw
 * | 		  |			 |
 * onMeasure -> onLayout -> onDraw 重写这些方法, 实现自定义控件
 * <p>
 * onResume()之后执行
 * <p>
 * View
 * onMeasure() (在这个方法里指定自己的宽高) -> onDraw() (绘制自己的内容)
 * <p>
 * ViewGroup
 * onMeasure() (指定自己的宽高, 所有子View的宽高)-> onLayout() (摆放所有子View) -> onDraw() (绘制内容)
 */
public class SlideSwitchView extends View {
    private Bitmap slideButtonBitmap; // 滑块图片
    private Paint mPaint; // 画笔
    private float currentX; //当前滑动的x坐标
    private int mBaseLineY; //  text基准线
    private String mTextContent; //text内容

    public SlideSwitchView(Context context) {
        this(context, null);
    }

    public SlideSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideSwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideSwitchView);
        setSlideButtonResource(ta.getResourceId(R.styleable.SlideSwitchView_slide_button, -1));
        setText(ta.getString(R.styleable.SlideSwitchView_android_text));
        setTextSize(ta.getDimension(R.styleable.SlideSwitchView_android_textSize, 30));
        setTextColor(ta.getColor(R.styleable.SlideSwitchView_android_textColor, Color.BLACK));
        ta.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setAntiAlias(true);
    }

    /**
     * 初始化text居中基准线
     */
    private void initTextBaseLine() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        mBaseLineY = (int) (getMeasuredHeight() / 2 - top / 2 - bottom / 2);//基线中间点的y轴计算公式
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            int newWidth = (int) (slideButtonBitmap.getWidth() * 2 + getTextWidth());
            if (width >= newWidth)
                width = newWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            if (height < slideButtonBitmap.getHeight()) {
                // 获得图片的宽高
                int widthSlide = slideButtonBitmap.getWidth();
                int heightSlide = slideButtonBitmap.getHeight();
                float scaleHeight = height * 1.0f / slideButtonBitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.postScale(scaleHeight, scaleHeight);
                slideButtonBitmap = Bitmap.createBitmap(slideButtonBitmap, 0, 0, widthSlide,
                        heightSlide, matrix, true);
                invalidate();
            }
        }

        if (slideButtonBitmap.getWidth() > (width - getTextWidth()) / 2) {
            // 获得图片的宽高
            int widthSlide = slideButtonBitmap.getWidth();
            int heightSlide = slideButtonBitmap.getHeight();
            float scaleWidth = (width - getTextWidth()) / 2 / slideButtonBitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            slideButtonBitmap = Bitmap.createBitmap(slideButtonBitmap, 0, 0, widthSlide,
                    heightSlide, matrix, true);
            invalidate();
        }

        setMeasuredDimension(width, slideButtonBitmap.getHeight());
        initTextBaseLine();
    }

    // Canvas 画布, 画板. 在上边绘制的内容都会显示到界面上.
    @Override
    protected void onDraw(Canvas canvas) {
        // 1. 绘制text
        canvas.drawText(mTextContent, slideButtonBitmap.getWidth(), mBaseLineY, mPaint);

        // 2. 绘制滑块
        if (isTouchMode) {
            // 根据当前用户触摸到的位置画滑块
            // 让滑块向左移动自身一半大小的位置
            float newLeft = currentX - slideButtonBitmap.getWidth() / 2.0f;

            int maxLeft = getMeasuredWidth() - slideButtonBitmap.getWidth();

            // 限定滑块范围
            if (newLeft < 0) {
                newLeft = 0; // 左边范围
            } else if (newLeft > maxLeft) {
                newLeft = maxLeft; // 右边范围
            }

            canvas.drawBitmap(slideButtonBitmap, newLeft, 0, mPaint);
        } else {
            //还原button位置
            canvas.drawBitmap(slideButtonBitmap, 0, 0, mPaint);
        }

    }

    boolean isTouchMode = false;
    private OnSwitchStateUpdateListener onSwitchStateUpdateListener;

    // 重写触摸事件, 响应用户的触摸.
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchMode = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchMode = false;
                currentX = event.getX();

                float center = getMeasuredWidth() / 2.0f;

                // 根据当前按下的位置, 和控件中心的位置进行比较.
                boolean isStateChanged = currentX > center;

                // 如果开关状态变化了, 通知界面
                if (isStateChanged && onSwitchStateUpdateListener != null) {
                    onSwitchStateUpdateListener.onStateUpdate();
                }
                break;

            default:
                break;
        }

        // 重绘界面
        invalidate(); // 会引发onDraw()被调用, 里边的变量会重新生效.界面会更新

        return true; // 消费了用户的触摸事件, 才可以收到其他的事件.
    }

    /**
     * 设置滑块图片资源
     *
     * @param slideButton 滑块图片资源
     */
    public void setSlideButtonResource(int slideButton) {
        slideButtonBitmap = BitmapFactory.decodeResource(getResources(), slideButton);
    }

    /**
     * 设置text字号大小
     *
     * @param textSize text字号大小
     */
    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
        mPaint.setStrokeWidth(textSize / 15.f);
    }

    /**
     * 设置text内容
     *
     * @param text text内容
     */
    public void setText(String text) {
        mTextContent = text;
    }

    /**
     * 设置text颜色
     *
     * @param color text颜色资源
     */
    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 获取text文字宽度
     *
     * @return text文字宽度
     */
    private float getTextWidth() {
        return mPaint.measureText(mTextContent);
    }

    /**
     * 获取text文字高度
     *
     * @return text文字高度
     */
    private float getTextHeight() {
        return mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;
    }

    public interface OnSwitchStateUpdateListener {
        // 状态回调
        void onStateUpdate();
    }

    public void setOnSwitchStateUpdateListener(
            OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
    }
}