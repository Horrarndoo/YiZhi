package com.zyw.horrarndoo.yizhi.presenter.detail;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zyw.horrarndoo.sdk.utils.FileUtils;
import com.zyw.horrarndoo.yizhi.contract.detail.BaseDetailContract;

/**
 * Created by Horrarndoo on 2017/9/28.
 * <p>
 */

public abstract class BaseDetailPresenter<M extends BaseDetailContract.IBaseDetailModel, V extends
        BaseDetailContract.IBaseDetailView> extends BaseDetailContract.BaseDetailPresenter<M, V> {

    @Override
    public void saveImage(final FragmentActivity activity, final String imgUrl) {
        if(mIView.popupWindowIsShowing())
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
}
