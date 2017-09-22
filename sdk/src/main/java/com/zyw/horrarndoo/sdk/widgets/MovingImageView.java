/*
 * Copyright (C) 2014 Albert Grobas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zyw.horrarndoo.sdk.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zyw.horrarndoo.sdk.R;


/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 * 自定义可以背景滚动的ImageView
 */
public class MovingImageView extends ImageView {

    private float canvasWidth, canvasHeight;
    private float imageWidth, imageHeight;
    private float offsetWidth, offsetHeight;
    /**
     * 移动类型
     */
    private int movementType;

    /**
     * 限定最大比值
     * canvasHeight/drawableHeight 或者 canvasWidth/drawableWidth
     */
    private float maxRelativeSize;
    /**
     * 最小相对偏移值，图片最起码可以位移图*0.2的距离
     */
    private float minRelativeOffset;
    private int mSpeed;
    private long startDelay;
    private int mRepetitions;
    private boolean loadOnCreate;//load完毕后是否移动

    private MovingViewAnimator mAnimator;

    public MovingImageView(Context context) {
        this(context, null);
    }

    public MovingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MovingImageView, defStyle, 0);

        try {
            maxRelativeSize = ta.getFloat(R.styleable.MovingImageView_miv_max_relative_size, 3.0f);
            minRelativeOffset = ta.getFloat(R.styleable.MovingImageView_miv_min_relative_offset,
                    0.2f);
            mSpeed = ta.getInt(R.styleable.MovingImageView_miv_speed, 50);
            mRepetitions = ta.getInt(R.styleable.MovingImageView_miv_repetitions, -1);
            startDelay = ta.getInt(R.styleable.MovingImageView_miv_start_delay, 0);
            loadOnCreate = ta.getBoolean(R.styleable.MovingImageView_miv_load_on_create, true);
        } finally {
            ta.recycle();
        }

        init();
    }

    private void init() {
        super.setScaleType(ScaleType.MATRIX);
        mAnimator = new MovingViewAnimator(this);
    }

    /**
     * 更新canvas size
     *
     * @param w    new width.
     * @param h    new height.
     * @param oldW old width.
     * @param oldH old height.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        canvasWidth = (float) w - (float) (getPaddingLeft() + getPaddingRight());
        canvasHeight = (float) h - (float) (getPaddingTop() + getPaddingBottom());
        updateAll();
    }

    private void updateAll() {
        if (getDrawable() != null) {
            updateImageSize();
            updateOffsets();
            updateAnimatorValues();
        }
    }

    /**
     * 更新图片Size
     */
    private void updateImageSize() {
        imageWidth = getDrawable().getIntrinsicWidth();//获取图片高度
        imageHeight = getDrawable().getIntrinsicHeight();//获取图片宽度
    }

    /**
     * 更新偏移量，确定动画范围
     */
    private void updateOffsets() {
        float minSizeX = imageWidth * minRelativeOffset;
        float minSizeY = imageHeight * minRelativeOffset;
        offsetWidth = (imageWidth - canvasWidth - minSizeX) > 0 ? imageWidth - canvasWidth : 0;
        offsetHeight = (imageHeight - canvasHeight - minSizeY) > 0 ? imageHeight - canvasHeight : 0;
    }

    /**
     * 更新动画基本数据
     */
    private void updateAnimatorValues() {
        if (canvasHeight == 0 && canvasWidth == 0)
            return;

        float scale = calculateTypeAndScale();
        if (scale == 0)
            return;

        float w = (imageWidth * scale) - canvasWidth;
        float h = (imageHeight * scale) - canvasHeight;

        mAnimator.updateValues(movementType, w, h);
        mAnimator.setStartDelay(startDelay);
        mAnimator.setSpeed(mSpeed);
        mAnimator.setRepetition(mRepetitions);

        if (loadOnCreate) {
            startMoving();
        }
    }

    /**
     * 设置最佳的运动类型
     * 计算缩放比例
     *
     * @return image scale.
     */
    private float calculateTypeAndScale() {
        movementType = MovingViewAnimator.AUTO_MOVE;
        float scale = 1f;
        float scaleByImage = Math.max(imageWidth / canvasWidth, imageHeight / canvasHeight);
        Matrix matrix = new Matrix();

        if (offsetWidth == 0 && offsetHeight == 0) {//图片太小，无法动画，需要放大
            //画布宽度/图片宽度
            float sW = canvasWidth / imageWidth;
            //画布高度/图片高度
            float sH = canvasHeight / imageHeight;

            if (sW > sH) {
                scale = Math.min(sW, maxRelativeSize);//限定最大缩放值
                matrix.setTranslate((canvasWidth - imageWidth * scale) / 2f, 0);
                movementType = MovingViewAnimator.VERTICAL_MOVE;//垂直移动

            } else if (sW < sH) {
                scale = Math.min(sH, maxRelativeSize);//限定最大缩放值
                matrix.setTranslate(0, (canvasHeight - imageHeight * scale) / 2f);
                movementType = MovingViewAnimator.HORIZONTAL_MOVE;//水平移动

            } else {
                scale = Math.max(sW, maxRelativeSize);//限定最大缩放值
                movementType = (scale == sW) ? MovingViewAnimator.NONE_MOVE :
                        MovingViewAnimator.DIAGONAL_MOVE;//对角线移动
            }
        } else if (offsetWidth == 0) {//宽度太小，无法执行水平动画，放大宽度
            scale = canvasWidth / imageWidth;
            movementType = MovingViewAnimator.VERTICAL_MOVE;

        } else if (offsetHeight == 0) {//高度太小，无法执行垂直动画，放大高度
            scale = canvasHeight / imageHeight;//求出画布高度和图片高度的比值用于确定画布起始坐标
            movementType = MovingViewAnimator.HORIZONTAL_MOVE;

        } else if (scaleByImage > maxRelativeSize) {//图片太大，根据最大比值设定图片缩放值
            scale = maxRelativeSize / scaleByImage;
            if (imageWidth * scale < canvasWidth || imageHeight * scale < canvasHeight) {
                scale = Math.max(canvasWidth / imageWidth, canvasHeight / imageHeight);
            }
        }

        matrix.preScale(scale, scale);
        setImageMatrix(matrix);
        return scale;
    }

    /**
     * 禁止设置ScaleType
     *
     * @param scaleType
     */
    @Override
    @Deprecated
    public void setScaleType(ScaleType scaleType) {
        //super.setScaleType(scaleType);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        updateAll();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        updateAll();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateAll();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        updateAll();
    }

    /**
     * 获取animator
     *
     * @return
     */
    public MovingViewAnimator getMovingAnimator() {
        return mAnimator;
    }

    public float getMaxRelativeSize() {
        return maxRelativeSize;
    }

    public void setMaxRelativeSize(float max) {
        maxRelativeSize = max;
        updateAnimatorValues();
    }

    public float getMinRelativeOffset() {
        return minRelativeOffset;
    }

    public void setMinRelativeOffset(float min) {
        minRelativeOffset = min;
        updateAnimatorValues();
    }

    public boolean isLoadOnCreate() {
        return loadOnCreate;
    }

    public void setLoadOnCreate(boolean loadOnCreate) {
        this.loadOnCreate = loadOnCreate;
    }

    /**
     * 开始移动
     * 默认不停的移动
     */
    public void startMoving() {
        startMoving(-1);
    }

    /**
     * 开始移动
     *
     * @param repetition 循环模式
     */
    public void startMoving(int repetition) {
        mAnimator.setRepetition(repetition);
        mAnimator.start();
    }

    /**
     * 恢复移动
     */
    public void resumeMoving() {
        mAnimator.resume();
    }

    /**
     * 暂停移动
     */
    public void pauseMoving() {
        mAnimator.pause();
    }

    /**
     * 停止移动
     */
    public void stopMoving() {
        mAnimator.stop();
    }

    /**
     * 获取当前状态
     *
     * @return
     */
    public MovingViewAnimator.MovingState getMovingState() {
        return mAnimator.getMovingState();
    }
}
