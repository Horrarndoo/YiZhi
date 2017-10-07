package com.zyw.horrarndoo.yizhi.ui.widgets;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.zyw.horrarndoo.sdk.utils.DisplayUtils;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;

import static com.zyw.horrarndoo.yizhi.R.id.item_go_image_browse;

/**
 * Created by Horrarndoo on 2017/9/28.
 * <p>
 */

public class WebViewLongClickedPopWindow extends PopupWindow {
    /**
     * 书签条目弹出菜单 * @value * {@value} *
     */
    public static final int FAVORITES_ITEM_POPUPWINDOW = 0;
    /**
     * 书签页面弹出菜单 * @value * {@value} *
     */
    public static final int FAVORITES_VIEW_POPUPWINDOW = 1;
    /**
     * 历史条目弹出菜单 * @value * {@value} *
     */
    public static final int HISTORY_ITEM_POPUPWINDOW = 3;
    /**
     * 历史页面弹出菜单 * @value * {@value} *
     */
    public static final int HISTORY_VIEW_POPUPWINDOW = 4;
    /**
     * 图片项目弹出菜单 * @value * {@value} *
     */
    public static final int IMAGE_VIEW_POPUPWINDOW = 5;
    /**
     * 超链接项目弹出菜单 * @value * {@value} *
     */
    public static final int ACHOR_VIEW_POPUPWINDOW = 6;
    private LayoutInflater itemLongClickedPopWindowInflater;
    private View longClickedPopWindowView;
    private Context mContext;
    private int mType;
    private LinearLayout llPopupRoot;
    private OnItemClickListener mOnItemClickListener;

    private boolean isShowAniming;//show动画是否在执行中
    private boolean isHideAniming;//hide动画是否在执行中

    /**
     * 构造函数 * @param context 上下文 * @param width 宽度 * @param height 高度 *
     */
    public WebViewLongClickedPopWindow(Context context, int type, int width, int height) {
        super(context);
        mContext = context;
        mType = type;
        //创建
        initTab();
        //设置默认选项
        setWidth(width);
        setHeight(height);
        setContentView(longClickedPopWindowView);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(ResourcesUtils.getDrawable(R.drawable.shape_corner_bg));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(DisplayUtils.dp2px(10));
        }
    }

    //实例化
    private void initTab() {
        this.itemLongClickedPopWindowInflater = LayoutInflater.from(mContext);
        switch (mType) {
            case FAVORITES_ITEM_POPUPWINDOW:
                break;
            case FAVORITES_VIEW_POPUPWINDOW: //对于书签内容弹出菜单，未作处理
                break;
            case HISTORY_ITEM_POPUPWINDOW:
                break;
            case HISTORY_VIEW_POPUPWINDOW: //对于历史内容弹出菜单，未作处理
                break;
            case ACHOR_VIEW_POPUPWINDOW: //超链接
                break;
            case IMAGE_VIEW_POPUPWINDOW: //图片
                longClickedPopWindowView = itemLongClickedPopWindowInflater.inflate
                        (R.layout.popup_pic_longclick, null);
                llPopupRoot = (LinearLayout) longClickedPopWindowView.findViewById(R.id
                        .ll_popup_root);

                TextView tvGoImageBrowse = (TextView) getView(item_go_image_browse);
                TextView tvSaveImage = (TextView) getView(R.id.item_save_image);
                tvGoImageBrowse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null)
                            mOnItemClickListener.onShowPicClicked();
                    }
                });

                tvSaveImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener != null)
                            mOnItemClickListener.onSavePicClicked();
                    }
                });
                break;
        }
    }

    public View getView(int id) {
        return longClickedPopWindowView.findViewById(id);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (!isShowAniming) {
            isShowAniming = true;
            popupAnim(llPopupRoot.getRootView(), 0.0f, 1.0f, 150, true);
        }
    }

    @Override
    public void dismiss() {
        if (!isHideAniming) {
            isHideAniming = true;
            popupAnim(llPopupRoot.getRootView(), 1.0f, 0.0f, 150, false);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onShowPicClicked();

        void onSavePicClicked();
    }

    /**
     * popupWindow属性动画
     *
     * @param view     执行属性动画的view
     * @param start    start值
     * @param end      end值
     * @param duration 动画持续时间
     * @param flag     true代表show，false代表hide
     */
    private void popupAnim(final View view, float start, final float end, int duration, final
    boolean flag) {
        ValueAnimator va = ValueAnimator.ofFloat(start, end).setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setPivotX(0);
                view.setPivotY(view.getMeasuredHeight());
                view.setScaleX(value);
                view.setScaleY(value);
                view.setAlpha(value);
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (!flag) {
                    isHideAniming = false;
                    WebViewLongClickedPopWindow.super.dismiss();
                }else {
                    isShowAniming = false;
                }
            }
        });
        va.start();
    }
}
