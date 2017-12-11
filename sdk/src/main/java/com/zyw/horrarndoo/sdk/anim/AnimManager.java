package com.zyw.horrarndoo.sdk.anim;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class AnimManager {
    /**
     * Alpha and scaleX 动画
     * Alpha 0->1
     * ScaleX 0.8->1
     *
     * @param context    context
     * @param view       view
     * @param startDelay 动画开始前延时（ms）
     * @param duration   动画持续时间（ms）
     */
    public static void animAlphaAndScaleX(Context context, @NonNull View view, int startDelay, int
            duration) {
        view.setAlpha(0f);
        view.setScaleX(0.8f);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(context))
                    .start();
        }else{
            view.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(context, android.R.interpolator.linear))
                    .start();
        }
    }

    /**
     * Alpha and scale X Y 动画
     * Alpha 0->1
     * ScaleX 0->1
     * ScaleY 0->1
     *
     * @param context    context
     * @param view       view
     * @param startDelay 动画开始前延时（ms）
     * @param duration   动画持续时间（ms）
     */
    public static void animAlphaAndScale(Context context, @NonNull View view, int startDelay, int
            duration) {
        view.setAlpha(0f);
        view.setScaleX(0f);
        view.setScaleY(0f);

        view.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setStartDelay(startDelay)
                .setDuration(duration)
                .setInterpolator(AnimationUtils.loadInterpolator(context,
                        android.R.interpolator.overshoot)).start();
    }
}
