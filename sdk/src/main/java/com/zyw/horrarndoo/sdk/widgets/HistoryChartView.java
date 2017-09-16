package com.zyw.horrarndoo.sdk.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.ValueAnimator;
import com.zyw.horrarndoo.sdk.R;
import com.zyw.horrarndoo.sdk.utils.DisplayUtils;

/**
 * 历史记录查询图表 基于pathMeasure+DashPathEffect+属性动画实现
 * 绘制时采取双缓冲绘制
 *
 * @author zyw
 * @creation 2017-03-06
 */
public class HistoryChartView extends View {

    private String TAG = "HistoryChartView";

    TypedArray ta;

    private float MarginTop = 100;

    private float MarginBottom = 100;

    private float MarginLeft = 100;

    private float MarginRight = 100;

    private float mYLabelSize = 50;

    private float mXlabelSize = 35;

    private float mXUnitTextSize;

    private float mYUnitTextSize;

    // 圆半径
    private int circleRadius = 8;

    private int lineStrokeWidth = 3;

    private int dataStrokeWidth = 3;

    private static final long ANIM_DURATION = 1500;
    private PathMeasure mRoomTempPathMeasure;
    private PathMeasure mTargetTempPathMeasure;
    private Path mRoomTempPath;
    private Path mTargetTempPath;
    private Paint mBmpPaint;
    /**
     * 柱形绘制进度
     */
    private float mRectFration;

    // X,Y轴的单位长度
    private float Xscale = 20;
    private float Yscale = 20;

    // 绘制X轴总长度
    private float xLength;
    // 绘制Y轴总长度
    private float yLength;

    // X轴第1个节点的偏移位置
    private float xFirstPointOffset;

    // y轴显示的节点间隔距离
    private int yScaleForData = 1;

    // x轴显示的节点间隔距离
    private int xScaleForData = 1;

    // 画线颜色
    private int lineColor;

    private int roomTempLineColor;
    private int targetTempLineColor;
    private int powerTimeLineColor;
    private int mUnitColor;

    private String mXUnitText;
    private String mY1UnitText;
    private String mY2UnitText;

    private int mMode = 1;// 从Activity传过来的模式值 1：天 2：周 3：月 4：年

    // 原点坐标
    private float Xpoint;
    private float Ypoint;

    // X,Y轴上面的显示文字
    private String[] Xlabel = {"1", "2", "3", "4", "5", "6", "7"};
    private String[] Ylabel = {"0", "9", "18", "27", "36"};
    private String[] Ylabel2 = {"0", "25", "50", "75", "100"};

    private final static int X_SCALE_FOR_DATA_DAY = 2;
    private final static int X_SCALE_FOR_DATA_WEEK = 1;
    private final static int X_SCALE_FOR_DATA_YEAR = 1;
    private final static int X_SCALE_FOR_DATA_MOUNTH = 5;

    private final static int DAY_MODE = 0;
    private final static int WEEK_MODE = 1;
    private final static int MONTH_MODE = 2;
    private final static int YEAR_MODE = 3;

    // 曲线数据
    private float[] roomTempDataArray = {15, 15, 15, 15, 15, 15, 15};
    private float[] targetTempDataArray = {16, 16, 16, 16, 16, 16, 16};
    private float[] powerOnTimeDataArray = {100, 100, 100, 100, 100, 100, 100};

    /**
     * 各条柱形图当前top值数组
     */
    private Float[] rectCurrentTops;

    private ValueAnimator mValueAnimator;

    private Paint linePaint;
    private Paint targetTempPaint;
    private Paint roomTempPaint;
    private PathEffect mRoomTempEffect;
    private PathEffect mtargetTempEffect;
    //定义一个内存中的图片，该图片将作为缓冲区
    Bitmap mCacheBitmap = null;
    //定义cacheBitmap上的Canvas对象
    Canvas mCacheCanvas = null;

    public HistoryChartView(Context context, String[] xlabel, String[] ylabel,
                            float[] roomDataArray) {
        super(context);
        this.Xlabel = xlabel;
        this.Ylabel = ylabel;
        this.roomTempDataArray = roomDataArray;
    }

    public HistoryChartView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //Logger.e("HistoryChartView(Context context, AttributeSet attrs, int defStyleAttr)");

        ta = context.obtainStyledAttributes(attrs, R.styleable.HistoryChartView);

        setDefaultAttrrbutesValue();

        initPaint();

        initData();

        initParams();

        initPath();

        ta.recycle();
    }

    public HistoryChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryChartView(Context context) {
        this(context, null);
    }

    /**
     * 设置显示数据
     *
     * @param strAlldata 历史数据全状态
     * @param mode       历史数据模式
     */
    public void setData(String strAlldata, int mode) {
        //Logger.e("history chart view strAlldata = " + strAlldata);
        String[] allHistroyArray = strAlldata.split("-");

        String[] arrayRoomTempData = allHistroyArray[0].split(",");
        String[] arraySetTempData = allHistroyArray[1].split(",");
        String[] arrayPowerTimeData = allHistroyArray[2].split(",");

        mMode = mode;

        initXData(arrayRoomTempData);

        initRoomTempData(arrayRoomTempData);

        initTargetTempData(arraySetTempData);

        initPowerOnTimeData(arrayPowerTimeData);

        initData();

        initParams();

        initPath();

        startAnimation();
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setColor(lineColor);
        linePaint.setDither(true);
        linePaint.setStrokeWidth(lineStrokeWidth);

        roomTempPaint = new Paint();
        roomTempPaint.setStyle(Paint.Style.STROKE);
        roomTempPaint.setAntiAlias(true);
        roomTempPaint.setColor(roomTempLineColor);
        roomTempPaint.setDither(true);
        roomTempPaint.setStrokeWidth(dataStrokeWidth);

        targetTempPaint = new Paint();
        targetTempPaint.setStyle(Paint.Style.STROKE);
        targetTempPaint.setAntiAlias(true);
        targetTempPaint.setColor(targetTempLineColor);
        targetTempPaint.setDither(true);
        targetTempPaint.setStrokeWidth(dataStrokeWidth);

        mBmpPaint = new Paint();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        mRoomTempPath = new Path();
        mTargetTempPath = new Path();

        rectCurrentTops = new Float[roomTempDataArray.length];
    }

    /**
     * 初始化宽高比例等数据
     */
    public void initParams() {
        // LogUtils.error(TAG, "initParams");
        Xpoint = MarginLeft;

        xLength = this.getWidth() - MarginLeft - MarginRight
                - (MarginRight + MarginLeft) / 16;
        yLength = this.getHeight() - MarginTop - MarginBottom;

        Ypoint = this.getHeight() - MarginBottom + mYLabelSize / 3;
        Xscale = (xLength - xFirstPointOffset * 2) / (this.Xlabel.length - 1);
        Yscale = yLength / (this.Ylabel.length - 1);
    }

    /**
     * 初始化path
     */
    private void initPath() {
        initRoomTempPath(roomTempDataArray);
        initTargetTempPath(targetTempDataArray);
    }

    /**
     * 初始化设定温度数据
     *
     * @param arraySetTempData 设定温度数据
     */
    private void initTargetTempData(String[] arraySetTempData) {
        targetTempDataArray = new float[arraySetTempData.length];
        for (int i = 0; i < arraySetTempData.length; i++) {
            if (arraySetTempData[i].length() > 0) {
                targetTempDataArray[i] = Float.parseFloat(arraySetTempData[i]);
            }
        }
    }

    /**
     * 初始化房间温度数据
     *
     * @param arrayRoomTempData 房间温度数据
     */
    private void initRoomTempData(String[] arrayRoomTempData) {
        roomTempDataArray = new float[arrayRoomTempData.length];
        for (int i = 0; i < arrayRoomTempData.length; i++) {
            if (arrayRoomTempData[i].length() > 0) {
                roomTempDataArray[i] = Float.parseFloat(arrayRoomTempData[i]);
                // LogUtils.error(TAG, "" + roomTempDataArray[i]);
            }
        }
    }

    /**
     * 初始化开机时间数据
     *
     * @param arrayPowerTimeData 开机时间数据
     */
    private void initPowerOnTimeData(String[] arrayPowerTimeData) {
        powerOnTimeDataArray = new float[arrayPowerTimeData.length];
        for (int i = 0; i < arrayPowerTimeData.length; i++) {
            if (arrayPowerTimeData[i].length() > 0) {
                powerOnTimeDataArray[i] = Float
                        .parseFloat(arrayPowerTimeData[i]);
            }
        }
    }

    /**
     * 初始化X轴数据
     */
    private void initXData(String[] tempData) {
        switch (mMode) {
            case DAY_MODE:
                xScaleForData = X_SCALE_FOR_DATA_DAY;
                setXUnitText(getResources().getString(R.string.history_x_unit_hour));
                break;
            case WEEK_MODE:
                xScaleForData = X_SCALE_FOR_DATA_WEEK;
                setXUnitText(getResources().getString(R.string.history_x_unit_day));
                break;
            case MONTH_MODE:
                xScaleForData = X_SCALE_FOR_DATA_MOUNTH;
                setXUnitText(getResources().getString(R.string.history_x_unit_day));
                break;
            case YEAR_MODE:
                xScaleForData = X_SCALE_FOR_DATA_YEAR;
                setXUnitText(getResources()
                        .getString(R.string.history_x_unit_month));
                break;
            default:
                break;
        }

        Xlabel = new String[tempData.length];
        for (int i = 0; i < Xlabel.length; i++) {
            Xlabel[i] = Integer.toString(i + 1);
        }
    }

    private void setDefaultAttrrbutesValue() {
        float MarginTopPx = ta.getDimension(
                R.styleable.HistoryChartView_margin_top, 50);
        float MarginBottomPx = ta.getDimension(
                R.styleable.HistoryChartView_margin_bottom, 50);
        float MarginLeftPx = ta.getDimension(
                R.styleable.HistoryChartView_margin_left, 50);
        float MarginRightPx = ta.getDimension(
                R.styleable.HistoryChartView_margin_right, 50);

        float yLabelSizePx = ta.getDimension(
                R.styleable.HistoryChartView_ylabel_text_size, 30);
        float xlabelSizePx = ta.getDimension(
                R.styleable.HistoryChartView_xlabel_text_size, 20);
        float xUnitSizePx = ta.getDimension(
                R.styleable.HistoryChartView_x_unit_text_size, 30);
        float yUnitSizePx = ta.getDimension(
                R.styleable.HistoryChartView_y_unit_text_size, 30);

        float xFirstPointOffsetPx = ta.getDimension(
                R.styleable.HistoryChartView_x_first_point_offset, 30);
        float lineStrokeWidthPx = ta.getDimension(
                R.styleable.HistoryChartView_line_stroke_width, 5);
        float dataStrokeWidthPx = ta.getDimension(
                R.styleable.HistoryChartView_data_stroke_width, 5);
        float circleRadiusPx = ta.getDimension(
                R.styleable.HistoryChartView_circle_radius, 6);

        xFirstPointOffset = DisplayUtils.px2sp(xFirstPointOffsetPx);

        MarginTop = DisplayUtils.px2dp(MarginTopPx);
        MarginBottom = DisplayUtils.px2dp(MarginBottomPx);
        MarginLeft = DisplayUtils.px2dp(MarginLeftPx);
        MarginRight = DisplayUtils.px2dp(MarginRightPx);

        mYLabelSize = DisplayUtils.px2sp(yLabelSizePx);
        mXlabelSize = DisplayUtils.px2sp(xlabelSizePx);

        mXUnitTextSize = DisplayUtils.px2sp(xUnitSizePx);
        mYUnitTextSize = DisplayUtils.px2sp(yUnitSizePx);

        lineStrokeWidth = DisplayUtils.px2sp(lineStrokeWidthPx);
        dataStrokeWidth = DisplayUtils.px2sp(dataStrokeWidthPx);
        circleRadius = DisplayUtils.px2sp(circleRadiusPx);

        lineColor = ta.getColor(R.styleable.HistoryChartView_line_color,
                getResources().getColor(R.color.light_yellow));
        roomTempLineColor = ta.getColor(
                R.styleable.HistoryChartView_first_data_line_color,
                getResources().getColor(R.color.indoor_temp));
        targetTempLineColor = ta.getColor(
                R.styleable.HistoryChartView_second_data_line_color,
                getResources().getColor(R.color.setpoint_temp));

        powerTimeLineColor = ta.getColor(
                R.styleable.HistoryChartView_rect_background_color,
                getResources().getColor(R.color.power_time));

        mUnitColor = ta.getColor(R.styleable.HistoryChartView_unit_color,
                getResources().getColor(R.color.light_grey));

        mXUnitText = ta.getString(R.styleable.HistoryChartView_x_unit_text);
        mY1UnitText = ta.getString(R.styleable.HistoryChartView_y1_unit_text);
        mY2UnitText = ta.getString(R.styleable.HistoryChartView_y2_unit_text);
    }

    /**
     * 设置X轴单位符号
     *
     * @param xUnit x轴单位符号
     */
    public void setXUnitText(String xUnit) {
        mXUnitText = xUnit;
    }

    /**
     * 绘制单位符号
     *
     * @param canvas canvas
     */
    private void drawUnit(Canvas canvas) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStrokeWidth(dataStrokeWidth);
        p.setColor(mUnitColor);

        drawXUnit(canvas, p);
        drawY1Unit(canvas, p);
        drawY2Unit(canvas, p);
    }

    // 画横轴
    private void drawXLine(Canvas canvas, Paint p) {
        p.setColor(getResources().getColor(R.color.light_yellow));
        canvas.drawLine(Xpoint, Ypoint, xLength + MarginLeft, Ypoint, p);
    }

    // 画灰色横轴
    private void drawGreyXLine(Canvas canvas, Paint p) {
        p.setColor(getResources().getColor(R.color.grey_line));
        float startX = Xpoint + MarginLeft / 4;
        // 纵向
        for (int i = yScaleForData; (yLength - i * Yscale) >= 0; i += yScaleForData) {
            float startY = Ypoint - i * Yscale;
            canvas.drawLine(startX - MarginLeft / 4, startY, xLength
                    + MarginLeft, startY, p);
        }
    }

    // 画数据
    private void drawData(Canvas canvas, float[] data, int dataColor) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStrokeWidth(dataStrokeWidth);
        p.setTextSize(mXlabelSize);
        // 横向
        for (int i = 0; i < Xlabel.length; i++) {
            int xLableInt = Integer.parseInt(Xlabel[i]);
            float startX = Xpoint + i * Xscale + xFirstPointOffset;
            if (xLableInt % xScaleForData == 0) {
                p.setColor(lineColor);
                canvas.drawText(this.Xlabel[i], startX - mXlabelSize / 3,
                        Ypoint + mXlabelSize * 3 / 2, p);
            }
            p.setColor(dataColor);
            canvas.drawCircle(startX, getDataY(data[i], Ylabel), circleRadius,
                    p);
        }

        p.setTextSize(mYLabelSize);
        // 纵向
        for (int i = 0; (yLength - i * Yscale) >= 0; i += yScaleForData) {
            p.setColor(lineColor);
            canvas.drawText(this.Ylabel[i], MarginLeft / 4,
                    getDataY(Float.valueOf(Ylabel[i]), Ylabel) + mYLabelSize
                            / 3, p);
            canvas.drawText(this.Ylabel2[i], this.getWidth() - MarginLeft,
                    getDataY(Float.valueOf(Ylabel2[i]), Ylabel2) + mYLabelSize
                            / 3, p);
        }
    }

    // 获取room temp绘线Path数据
    private void initRoomTempPath(float[] data) {
        mRoomTempPath.reset();
        // Path path = new Path();
        float pointX;
        float pointY;
        // 横向
        mRoomTempPath.moveTo(Xpoint + xFirstPointOffset,
                getDataY(data[0], Ylabel));
        mRoomTempPath.moveTo(Xpoint + xFirstPointOffset,
                getDataY(data[0], Ylabel));
        for (int i = 0; i < Xlabel.length; i++) {
            float startX = Xpoint + i * Xscale + xFirstPointOffset;
            // 绘制数据连线
            if (i != 0) {
                pointX = Xpoint + (i - 1) * Xscale + xFirstPointOffset;
                pointY = getDataY(data[i - 1], Ylabel);
                mRoomTempPath.lineTo(pointX, pointY);
            }
            if (i == Xlabel.length - 1) {
                pointX = startX;
                pointY = getDataY(data[i], Ylabel);
                mRoomTempPath.lineTo(pointX, pointY);
            }
        }
        mRoomTempPathMeasure = new PathMeasure(mRoomTempPath, false);
    }

    /**
     * 获取target temp绘线Path数据
     *
     * @param data target temp绘线Path数据
     */
    private void initTargetTempPath(float[] data) {
        mTargetTempPath.reset();
        float pointX;
        float pointY;
        // 横向
        mTargetTempPath.moveTo(Xpoint + xFirstPointOffset,
                getDataY(data[0], Ylabel));
        for (int i = 0; i < Xlabel.length; i++) {
            float startX = Xpoint + i * Xscale + xFirstPointOffset;
            // 绘制数据连线
            if (i != 0) {
                pointX = Xpoint + (i - 1) * Xscale + xFirstPointOffset;
                pointY = getDataY(data[i - 1], Ylabel);
                mTargetTempPath.lineTo(pointX, pointY);
            }
            if (i == Xlabel.length - 1) {
                pointX = startX;
                pointY = getDataY(data[i], Ylabel);
                mTargetTempPath.lineTo(pointX, pointY);
            }
        }
        mTargetTempPathMeasure = new PathMeasure(mTargetTempPath, false);
    }

    // 绘制矩形图
    private void drawRect(Canvas canvas, float[] data, int dataColor) {
        Paint p = new Paint();
        float left;
        float top;
        float right;
        float bottom;
        float stopY = getDataY(Float.parseFloat(Ylabel[Ylabel.length - 1]),
                Ylabel);// 灰色线Y轴位置
        float rectYScale = (Ypoint - stopY) / 100;

        p.setAntiAlias(true);
        p.setStrokeWidth(dataStrokeWidth);
        p.setColor(dataColor);

        // 横向
        for (int i = 0; i < Xlabel.length; i++) {
            // 绘制柱形图
            if (i != 0) {
                left = Xpoint + (i - 1) * Xscale + xFirstPointOffset + Xscale
                        / 6;
                top = Ypoint - data[i - 1] * rectYScale + lineStrokeWidth;// 要绘制的rect最终top值
                // 起点top + (起点top - 终点top) * mRectFration
                rectCurrentTops[i] = Ypoint - (Ypoint - top) * mRectFration;// 根据fraction动态更新top值
                right = left + Xscale * 4 / 6;
                bottom = Ypoint;
                canvas.drawRect(left, rectCurrentTops[i], right, bottom, p);//
                // 每次valueAnimator更新时重绘最新top值
            }
        }
    }

    private void drawY1Unit(Canvas canvas, Paint p) {
        int maxYLabelValue = Integer.valueOf(Ylabel[Ylabel.length - 1]);
        p.setTextSize(mYUnitTextSize);
        float textWidth = p.measureText(mY1UnitText);
        canvas.drawText(mY1UnitText, MarginLeft / 2 - textWidth / 2,
                getDataY(maxYLabelValue, Ylabel) - mYLabelSize - mYLabelSize
                        / 5, p);
    }

    private void drawY2Unit(Canvas canvas, Paint p) {
        int maxYLabel2Value = Integer.valueOf(Ylabel2[Ylabel2.length - 1]);
        p.setTextSize(mYUnitTextSize);
        float textWidth = p.measureText(mY2UnitText);
        canvas.drawText(mY2UnitText, this.getWidth() - MarginRight / 2
                - textWidth * 3 / 4, getDataY(maxYLabel2Value, Ylabel2)
                - mYLabelSize - mYLabelSize / 5, p);
    }

    private void drawXUnit(Canvas canvas, Paint p) {
        p.setTextSize(mXUnitTextSize);
        float textWidth = p.measureText(mXUnitText);
        canvas.drawText(mXUnitText, this.getWidth() / 2 - textWidth / 2, Ypoint
                + mXlabelSize * 3 + mXlabelSize / 5, p);
    }

    /**
     * 获取data对应绘制Y点值
     */
    private float getDataY(float dataY, String[] Ylabel) {
        float y0 = 0;
        float y1 = 0;
        try {
            y0 = Float.parseFloat(Ylabel[0]);
            y1 = Float.parseFloat(Ylabel[1]);
        } catch (Exception e) {
            return 0;
        }
        try {
            return Ypoint - ((dataY - y0) * Yscale / (y1 - y0));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // LogUtils.error(TAG, "onLayout");
        initParams();

        if (onViewLayoutListener != null) {
            onViewLayoutListener.onLayoutSuccess();
        }

        //创建一个与该View相同大小的缓冲区
        mCacheBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config
                .ARGB_8888);
        mCacheCanvas = new Canvas();
        //设置cacheCanvas将会绘制到内存中cacheBitmap上
        mCacheCanvas.setBitmap(mCacheBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCacheCanvas.drawColor(Color.BLACK);

        drawGreyXLine(mCacheCanvas, linePaint);

        drawUnit(mCacheCanvas);

        if (powerOnTimeDataArray.length > 1) {
            drawRect(mCacheCanvas, powerOnTimeDataArray, powerTimeLineColor);
        }

        mCacheCanvas.drawPath(mRoomTempPath, roomTempPaint);

        if (roomTempDataArray.length > 1) {
            drawData(mCacheCanvas, roomTempDataArray, roomTempLineColor);
        }

        mCacheCanvas.drawPath(mTargetTempPath, targetTempPaint);

        if (targetTempDataArray.length > 1) {
            drawData(mCacheCanvas, targetTempDataArray, targetTempLineColor);
        }

        drawXLine(mCacheCanvas, linePaint);

        //将cacheBitmap绘制到该View组件
        canvas.drawBitmap(mCacheBitmap, 0, 0, mBmpPaint);
    }

    /**
     * 开启动画
     */
    private void startAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        final float targetTempLength = mTargetTempPathMeasure.getLength();
        final float roomTempLength = mRoomTempPathMeasure.getLength();
        mValueAnimator = ValueAnimator.ofFloat(1, 0);
        mValueAnimator.setDuration(ANIM_DURATION);
        // 减速插值器
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (Float) animation.getAnimatedValue();
                // 更新mtargetTempEffect
                mtargetTempEffect = new DashPathEffect(new float[]{
                        targetTempLength, targetTempLength}, fraction
                        * targetTempLength);
                targetTempPaint.setPathEffect(mtargetTempEffect);
                // 更新mRoomTempEffect
                mRoomTempEffect = new DashPathEffect(new float[]{
                        roomTempLength, roomTempLength}, fraction
                        * roomTempLength);
                roomTempPaint.setPathEffect(mRoomTempEffect);
                // 更新rect绘制fraction进度
                mRectFration = 1 - fraction;// fraction是1->0 我们需要的柱形图绘制比例是0->1
                postInvalidate();
            }
        });
        mValueAnimator.start();
    }

    public interface OnViewLayoutListener {
        void onLayoutSuccess();
    }

    public void setOnViewLayoutListener(
            OnViewLayoutListener onViewLayoutListener) {
        this.onViewLayoutListener = onViewLayoutListener;
    }

    private OnViewLayoutListener onViewLayoutListener;
}

