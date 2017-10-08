package com.zyw.horrarndoo.sdk.anim;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 * Toolbar动画Manager
 */

public class ToolbarAnimManager {
    /**
     * Toolbar 进场动画
     * <p>
     * Textview&ActionMenuView渐变动画
     *
     * @param context context
     * @param toolbar toolbar
     */
    public static void animIn(Context context, @NonNull Toolbar toolbar) {
        ImageButton ibIcon = null;
        TextView tvTitle = null;
        ActionMenuView amvTheme = null;
        int childCount = toolbar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = toolbar.getChildAt(i);
            if(child instanceof ImageButton) {
                ibIcon = (ImageButton) child;
                continue;
            }

            if (child instanceof ActionMenuView) {
                amvTheme = (ActionMenuView) child;
                continue;
            }

            if (child instanceof TextView)
                tvTitle = (TextView) child;
        }

        if(ibIcon != null)
        animNavigationIcon(context, ibIcon);

        if(tvTitle != null)
        animTitle(context, tvTitle);

        if(amvTheme != null)
        animMenu(context, amvTheme);
    }

    /**
     * Toolbar Title动画
     * <p>
     * NavigationIcon渐变动画
     *
     * @param context  context
     * @param imageButton 执行动画的view
     */
    public static void animNavigationIcon(Context context, @NonNull ImageButton imageButton) {
        AnimManager.animAlphaAndScaleX(context, imageButton, 500, 900);
    }

    /**
     * Toolbar Title动画
     * <p>
     * ActionMenuView渐变动画
     *
     * @param context  context
     * @param textView 执行动画的view
     */
    public static void animTitle(Context context, @NonNull TextView textView) {
        AnimManager.animAlphaAndScaleX(context, textView, 500, 900);
    }

    /**
     * Toolbar ActionMenuView动画
     * <p>
     * ActionMenuView渐变动画
     *
     * @param context context
     * @param avm     执行动画的view
     */
    public static void animMenu(Context context, @NonNull ActionMenuView avm) {
        AnimManager.animAlphaAndScale(context, avm, 500, 200); // filter
        AnimManager.animAlphaAndScale(context, avm, 700, 200); // overflow
    }
}
