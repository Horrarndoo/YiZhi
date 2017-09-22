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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 * 滚动view动画
 */
public class MovingViewAnimator {
    /**
     * 水平移动
     */
    public static final int HORIZONTAL_MOVE = 1;
    /**
     * 垂直移动
     */
    public static final int VERTICAL_MOVE = 2;
    /**
     * 对角线移动
     */
    public static final int DIAGONAL_MOVE = 3;
    /**
     * 自动移动
     */
    public static final int AUTO_MOVE = 0;
    /**
     * 不移动
     */
    public static final int NONE_MOVE = -1;

    private AnimatorSet mAnimatorSet;
    private View mView;

    private boolean isRunning;
    private int currentLoop;
    private boolean infiniteRepetition = true;
    private ArrayList<Float> pathDistances;

    private int loopCount = -1;
    private int movementType;
    private float offsetWidth, offsetHeight;
    private int mSpeed = 50;
    private long mDelay = 0;
    private Interpolator mInterpolator;

    private MovingState currentState = MovingState.stop;

    public enum MovingState {
        stop,
        moving,
        pause
    }

    private Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(final Animator animation) {
            //super.onAnimationEnd(animation);
            //运行在主线程
            mView.post((new Runnable() {
                public void run() {
                    if (isRunning) {
                        if (infiniteRepetition) {
                            mAnimatorSet.start();
                        } else {
                            currentLoop--;
                            if (currentLoop > 0) {
                                mAnimatorSet.start();
                            }
                        }
                    }
                }
            }));
        }
    };

    public MovingViewAnimator(View imgView) {
        mView = imgView;
        isRunning = false;
        mAnimatorSet = new AnimatorSet();
        pathDistances = new ArrayList<>();
        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    public MovingViewAnimator(View imgView, int type, float width, float height) {
        this(imgView);
        updateValues(type, width, height);
    }

    private void init() {
        setUpAnimator();
        setUpValues();
    }

    /**
     * 根据移动类型设置不同的动画
     */
    private void setUpAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        pathDistances.clear();

        switch (movementType) {
            case HORIZONTAL_MOVE:
                animatorSet.playSequentially(createHorizontalAnimator(0, offsetWidth),
                        createHorizontalAnimator(offsetWidth, 0));
                break;
            case VERTICAL_MOVE:
                animatorSet.playSequentially(createVerticalAnimator(0, offsetHeight),
                        createVerticalAnimator(offsetHeight, 0));
                break;
            case DIAGONAL_MOVE:
                animatorSet.playSequentially(createDiagonalAnimator(0, offsetWidth, 0,
                        offsetHeight),
                        createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
                break;
            case AUTO_MOVE:
                animatorSet.playSequentially(
                        createVerticalAnimator(0, offsetHeight),
                        createDiagonalAnimator(0, offsetWidth, offsetHeight, 0),
                        createHorizontalAnimator(offsetWidth, 0),
                        createDiagonalAnimator(0, offsetWidth, 0, offsetHeight),
                        createHorizontalAnimator(offsetWidth, 0),
                        createVerticalAnimator(offsetHeight, 0));
        }

        if (mAnimatorSet != null) {
            mAnimatorSet.removeAllListeners();
            stop();
        }
        mAnimatorSet = animatorSet;
    }

    /**
     * 设置参数数据
     */
    private void setUpValues() {
        setSpeed(mSpeed);
        setStartDelay(mDelay);
        setRepetition(loopCount);
        setInterpolator(mInterpolator);
    }

    private void setListener() {
        mAnimatorSet.addListener(animatorListener);
    }

    /**
     * 更新动画值.
     *
     * @param type
     * @param w
     * @param h
     */
    public void updateValues(int type, float w, float h) {
        this.movementType = type;
        this.offsetWidth = w;
        this.offsetHeight = h;
        init();
    }

    public void setMovementType(int type) {
        updateValues(type, offsetWidth, offsetHeight);
    }

    public void setOffsets(float w, float h) {
        updateValues(movementType, w, h);
    }

    public void start() {
        //Log.e("tag", "start.");
        if (movementType != NONE_MOVE) {
            isRunning = true;
            if (!infiniteRepetition)
                currentLoop = loopCount;
            setListener();
            mAnimatorSet.start();
            currentState = MovingState.moving;
        }
    }

    public void cancel() {
        if (isRunning) {
            mAnimatorSet.removeListener(animatorListener);
            mAnimatorSet.cancel();
            currentState = MovingState.stop;
        }
    }

    @TargetApi(19)
    public void pause() {
        //Log.e("tag", "pause.");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        if (mAnimatorSet.isStarted()) {
            mAnimatorSet.pause();
            currentState = MovingState.pause;
        }
    }

    @TargetApi(19)
    public void resume() {
        //Log.e("tag", "resume.");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;

        if (mAnimatorSet.isPaused()) {
            mAnimatorSet.resume();
            currentState = MovingState.moving;
        }
    }

    public void stop() {
        //Log.e("tag", "stop.");
        isRunning = false;
        mAnimatorSet.removeListener(animatorListener);
        mAnimatorSet.end();
        mView.clearAnimation();
        currentState = MovingState.stop;
    }

    /**
     * 设置重复模式
     *
     * @param repetition repetition < 0 循环播放
     *                   repetition > 0 循环repetition次
     */
    public void setRepetition(int repetition) {
        if (repetition < 0)
            infiniteRepetition = true;
        else {
            loopCount = repetition;
            currentLoop = loopCount;
            infiniteRepetition = false;
        }
    }

    public Builder addCustomMovement() {
        return new Builder();
    }

    public void clearCustomMovement() {
        init();
        start();
    }

    public int getMovementType() {
        return movementType;
    }

    public int getRemainingRepetitions() {
        return (infiniteRepetition) ? -1 : currentLoop;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        mAnimatorSet.setInterpolator(interpolator);
    }

    /**
     * 设置动画播放之前的延时时间
     *
     * @param time
     */
    public void setStartDelay(long time) {
        mDelay = time;
        mAnimatorSet.setStartDelay(time);
    }

    /**
     * 设置每个动画对应的持续时间
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        mSpeed = speed;
        List<Animator> listAnimator = mAnimatorSet.getChildAnimations();
        for (int i = 0; i < listAnimator.size(); i++) {
            Animator a = listAnimator.get(i);
            a.setDuration(parseSpeed(pathDistances.get(i)));
        }
    }

    /**
     * 将速度设置值转换成秒
     *
     * @param distance
     * @return
     */
    private long parseSpeed(float distance) {
        return (long) ((distance / (float) mSpeed) * 1000f);
    }

    /**
     * 创建水平移动动画
     *
     * @param startValue
     * @param endValue
     * @return
     */
    private ObjectAnimator createHorizontalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollX", startValue, endValue);
    }

    /**
     * 创建垂直移动动画
     *
     * @param startValue
     * @param endValue
     * @return
     */
    private ObjectAnimator createVerticalAnimator(float startValue, float endValue) {
        pathDistances.add(Math.abs(startValue - endValue));
        return createObjectAnimation("scrollY", startValue, endValue);
    }

    /**
     * 创建对角线移动动画
     *
     * @param startW
     * @param endW
     * @param startH
     * @param endH
     * @return
     */
    private ObjectAnimator createDiagonalAnimator(float startW, float endW, float startH, float
            endH) {
        float diagonal = Pythagoras(Math.abs(startW - endW), Math.abs(startH - endH));
        pathDistances.add(diagonal);
        PropertyValuesHolder pvhX = createPropertyValuesHolder("scrollX", startW, endW);
        PropertyValuesHolder pvhY = createPropertyValuesHolder("scrollY", startH, endH);
        return ObjectAnimator.ofPropertyValuesHolder(mView, pvhX, pvhY);
    }

    private ObjectAnimator createObjectAnimation(String prop, float startValue, float endValue) {
        return ObjectAnimator.ofInt(mView, prop, (int) startValue, (int) endValue);
    }

    private PropertyValuesHolder createPropertyValuesHolder(String prop, float startValue, float
            endValue) {
        return PropertyValuesHolder.ofInt(prop, (int) startValue, (int) endValue);
    }

    private static float Pythagoras(float a, float b) {
        return (float) Math.sqrt((a * a) + (b * b));
    }

    /**
     * 自定义自动移动方式
     */
    public class Builder {

        private ArrayList<Animator> mList;

        private Builder() {
            mList = new ArrayList<>();
            pathDistances.clear();
        }

        public Builder addHorizontalMoveToRight() {
            mList.add(createHorizontalAnimator(0, offsetWidth));
            return this;
        }

        public Builder addHorizontalMoveToLeft() {
            mList.add(createHorizontalAnimator(offsetWidth, 0));
            return this;
        }

        public Builder addVerticalMoveToDown() {
            mList.add(createVerticalAnimator(0, offsetHeight));
            return this;
        }

        public Builder addVerticalMoveToUp() {
            mList.add(createVerticalAnimator(offsetHeight, 0));
            return this;
        }

        public Builder addDiagonalMoveToDownRight() {
            mList.add(createDiagonalAnimator(0, offsetWidth, 0, offsetHeight));
            return this;
        }

        public Builder addDiagonalMoveToDownLeft() {
            mList.add(createDiagonalAnimator(offsetWidth, 0, 0, offsetHeight));
            return this;
        }

        public Builder addDiagonalMoveToUpRight() {
            mList.add(createDiagonalAnimator(0, offsetWidth, offsetHeight, 0));
            return this;
        }

        public Builder addDiagonalMoveToUpLeft() {
            mList.add(createDiagonalAnimator(offsetWidth, 0, offsetHeight, 0));
            return this;
        }

        public void start() {
            mAnimatorSet.removeAllListeners();
            stop();
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playSequentially(mList);
            setListener();
            setUpValues();
            MovingViewAnimator.this.start();
        }
    }

    /**
     * 获取当前状态
     *
     * @return
     */
    public MovingState getMovingState() {
        return currentState;
    }
}