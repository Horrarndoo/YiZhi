package com.zyw.horrarndoo.yizhi.presenter.detail;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zyw.horrarndoo.sdk.utils.FileUtils;
import com.zyw.horrarndoo.yizhi.contract.detail.BaseWebViewLoadContract;

/**
 * Created by Horrarndoo on 2017/9/28.
 * <p>
 */

public abstract class BaseWebViewLoadPresenter<M extends BaseWebViewLoadContract
        .IBaseWebViewLoadModel, V extends BaseWebViewLoadContract.IBaseWebViewLoadView> extends
        BaseWebViewLoadContract.BaseWebViewLoadPresenter<M, V> {

    @Override
    public void saveImageClicked(final FragmentActivity activity, final String imgUrl) {
        if (mIView.popupWindowIsShowing())
            mIView.dismissPopupWindow();

        Glide.with(activity).load(imgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super
                    Bitmap> glideAnimation) {
                FileUtils.saveBitmap(activity, imgUrl, resource, new FileUtils.SaveResultCallback
                        () {
                    @Override
                    public void onSavedSuccess() {
                        if (mIView != null) {
                            mIView.showToast("图片保存成功");
                        }
                    }

                    @Override
                    public void onSavedFailed() {
                        if (mIView != null) {
                            mIView.showToast("图片保存失败");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void gotoImageBrowseClicked(String imgUrl) {
        if (mIView == null)
            return;
        if (mIView.popupWindowIsShowing())
            mIView.dismissPopupWindow();

        mIView.gotoImageBrowse(imgUrl);
    }

    @Override
    public void imageLongClicked(WebView.HitTestResult hitTestResult) {
        if (null == hitTestResult)
            return;

        int type = hitTestResult.getType();
        switch (type) {
            case WebView.HitTestResult.UNKNOWN_TYPE:
                return;
            case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                break;
            case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                break;
            case WebView.HitTestResult.GEO_TYPE:
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                break;
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                break;
            case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                if (mIView != null)
                    mIView.showPopupWindow();
                break;
            default:
                break;
        }
    }
}
