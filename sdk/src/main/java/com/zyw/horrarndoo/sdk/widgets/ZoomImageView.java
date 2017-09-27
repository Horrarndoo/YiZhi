package com.zyw.horrarndoo.sdk.widgets;

/**
 * Created by Horrarndoo on 2017/9/27.
 * <p>
 */

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 缩放ImageView
 *
 * @author xiejinxiong
 */
public class ZoomImageView extends ImageView {

    /**
     * ImageView高度
     */
    private int imgHeight;
    /**
     * ImageView宽度
     */
    private int imgWidth;
    /**
     * 图片高度
     */
    private int intrinsicHeight;
    /**
     * 图片宽度
     */
    private int intrinsicWidth;
    /**
     * 最大缩放级别
     */
    private float mMaxScale = 2.0f;
    /**
     * 最小缩放级别
     */
    private float mMinScale = 0.2f;
    /**
     * 用于记录拖拉图片移动的坐标位置
     */
    private Matrix matrix = new Matrix();
    /**
     * 用于记录图片要进行拖拉时候的坐标位置
     */
    private Matrix currentMatrix = new Matrix();
    /**
     * 记录第一次点击的时间
     */
    private long firstTouchTime = 0;
    /**
     * 时间点击的间隔
     */
    private int intervalTime = 250;
    /**
     * 第一次点完坐标
     */
    private PointF firstPointF;

    public ZoomImageView(Context context) {
        super(context);
        initUI();
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {

        this.setScaleType(ScaleType.FIT_CENTER);
        this.setOnTouchListener(new TouchListener());

        getImageViewWidthHeight();
        getIntrinsicWidthHeight();
    }

    /**
     * 获得图片内在宽高
     */
    private void getIntrinsicWidthHeight() {
        Drawable drawable = this.getDrawable();

        // 初始化bitmap的宽高
        intrinsicHeight = drawable.getIntrinsicHeight();
        intrinsicWidth = drawable.getIntrinsicWidth();
    }

    private final class TouchListener implements OnTouchListener {

        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         */
        private int mode = 0;// 初始状态
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;
        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 两个手指的开始距离
         */
        private float startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;

        public boolean onTouch(View v, MotionEvent event) {

            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {// 单点监听和多点触碰监听
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    matrix.set(currentMatrix);
                    makeImageViewFit();
                    break;
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        // System.out.println("ACTION_MOVE_____MODE_DRAG");
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        // 在没有移动之前的位置上进行移动z
                        matrix.set(currentMatrix);
                        float[] values = new float[9];
                        matrix.getValues(values);
                        dx = checkDxBound(values, dx);
                        dy = checkDyBound(values, dy);
                        matrix.postTranslate(dx, dy);

                    }
                    // 放大缩小图片
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);

                            float[] values = new float[9];
                            matrix.getValues(values);

                            scale = checkFitScale(scale, values);

                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

                        }
                    }
                    break;
                // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    setDoubleTouchEvent(event);

                case MotionEvent.ACTION_POINTER_UP:
                    // System.out.println("ACTION_POINTER_UP");
                    mode = 0;
                    // matrix.set(currentMatrix);
                    float[] values = new float[9];
                    matrix.getValues(values);
                    makeImgCenter(values);
                    break;
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    // System.out.println("ACTION_POINTER_DOWN");
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        // 记录当前ImageView的缩放倍数
                        currentMatrix.set(getImageMatrix());
                    }
                    break;
            }
            setImageMatrix(matrix);
            return true;
        }

        /**
         * 计算两个手指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }

        /**
         * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
         *
         * @param values
         * @param dy
         * @return
         */
        private float checkDyBound(float[] values, float dy) {

            float height = imgHeight;
            if (intrinsicHeight * values[Matrix.MSCALE_Y] < height)
                return 0;
            if (values[Matrix.MTRANS_Y] + dy > 0)
                dy = -values[Matrix.MTRANS_Y];
            else if (values[Matrix.MTRANS_Y] + dy < -(intrinsicHeight
                    * values[Matrix.MSCALE_Y] - height))
                dy = -(intrinsicHeight * values[Matrix.MSCALE_Y] - height)
                        - values[Matrix.MTRANS_Y];
            return dy;
        }

        /**
         * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
         *
         * @param values
         * @param dx
         * @return
         */
        private float checkDxBound(float[] values, float dx) {

            float width = imgWidth;
            if (intrinsicWidth * values[Matrix.MSCALE_X] < width)
                return 0;
            if (values[Matrix.MTRANS_X] + dx > 0)
                dx = -values[Matrix.MTRANS_X];
            else if (values[Matrix.MTRANS_X] + dx < -(intrinsicWidth
                    * values[Matrix.MSCALE_X] - width))
                dx = -(intrinsicWidth * values[Matrix.MSCALE_X] - width)
                        - values[Matrix.MTRANS_X];
            return dx;
        }

        /**
         * MSCALE用于处理缩放变换
         *
         *
         * MSKEW用于处理错切变换
         *
         *
         * MTRANS用于处理平移变换
         */

        /**
         * 检验scale，使图像缩放后不会超出最大倍数
         *
         * @param scale
         * @param values
         * @return
         */
        private float checkFitScale(float scale, float[] values) {
            if (scale * values[Matrix.MSCALE_X] > mMaxScale)
                scale = mMaxScale / values[Matrix.MSCALE_X];
            if (scale * values[Matrix.MSCALE_X] < mMinScale)
                scale = mMinScale / values[Matrix.MSCALE_X];
            return scale;
        }

        /**
         * 促使图片居中
         *
         * @param values (包含着图片变化信息)
         */
        private void makeImgCenter(float[] values) {

            // 缩放后图片的宽高
            float zoomY = intrinsicHeight * values[Matrix.MSCALE_Y];
            float zoomX = intrinsicWidth * values[Matrix.MSCALE_X];
            // 图片左上角Y坐标
            float leftY = values[Matrix.MTRANS_Y];
            // 图片左上角X坐标
            float leftX = values[Matrix.MTRANS_X];
            // 图片右下角Y坐标
            float rightY = leftY + zoomY;
            // 图片右下角X坐标
            float rightX = leftX + zoomX;

            // 使图片垂直居中
            if (zoomY < imgHeight) {
                float marY = (imgHeight - zoomY) / 2.0f;
                matrix.postTranslate(0, marY - leftY);
            }

            // 使图片水平居中
            if (zoomX < imgWidth) {

                float marX = (imgWidth - zoomX) / 2.0f;
                matrix.postTranslate(marX - leftX, 0);

            }

            // 使图片缩放后上下不留白（即当缩放后图片的大小大于imageView的大小，但是上面或下面留出一点空白的话，将图片移动占满空白处）
            if (zoomY >= imgHeight) {
                if (leftY > 0) {// 判断图片上面留白
                    matrix.postTranslate(0, -leftY);
                }
                if (rightY < imgHeight) {// 判断图片下面留白
                    matrix.postTranslate(0, imgHeight - rightY);
                }
            }

            // 使图片缩放后左右不留白
            if (zoomX >= imgWidth) {
                if (leftX > 0) {// 判断图片左边留白
                    matrix.postTranslate(-leftX, 0);
                }
                if (rightX < imgWidth) {// 判断图片右边不留白
                    matrix.postTranslate(imgWidth - rightX, 0);
                }
            }
        }

    }

    /**
     * 获取ImageView的宽高
     */
    private void getImageViewWidthHeight() {
        ViewTreeObserver vto2 = getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imgWidth = getWidth();
                imgHeight = getHeight();

            }
        });
    }

    /**
     * 使得ImageView一开始便显示最适合的宽高比例，便是刚好容下的样子
     */
    private void makeImageViewFit() {
        if (getScaleType() != ScaleType.MATRIX) {
            setScaleType(ScaleType.MATRIX);

            matrix.postScale(1.0f, 1.0f, imgWidth / 2, imgHeight / 2);
        }
    }

    /**
     * 双击事件触发
     *
     * @param event
     */
    private void setDoubleTouchEvent(MotionEvent event) {

        float values[] = new float[9];
        matrix.getValues(values);
        // 存储当前时间
        long currentTime = System.currentTimeMillis();
        // 判断两次点击间距时间是否符合
        if (currentTime - firstTouchTime >= intervalTime) {
            firstTouchTime = currentTime;
            firstPointF = new PointF(event.getX(), event.getY());
        } else {
            // 判断两次点击之间的距离是否小于30f
            if (Math.abs(event.getX() - firstPointF.x) < 30f
                    && Math.abs(event.getY() - firstPointF.y) < 30f) {
                // 判断当前缩放比例与最大最小的比例
                if (values[Matrix.MSCALE_X] < mMaxScale) {
                    matrix.postScale(mMaxScale / values[Matrix.MSCALE_X],
                            mMaxScale / values[Matrix.MSCALE_X], event.getX(),
                            event.getY());
                } else {
                    matrix.postScale(mMinScale / values[Matrix.MSCALE_X],
                            mMinScale / values[Matrix.MSCALE_X], event.getX(),
                            event.getY());
                }
            }

        }
    }

    /**
     * 设置图片的最大和最小的缩放比例
     *
     * @param mMaxScale
     * @param mMinScale
     */
    public void setPicZoomHeightWidth(float mMaxScale, float mMinScale) {
        this.mMaxScale = mMaxScale;
        this.mMinScale = mMinScale;
    }

}
