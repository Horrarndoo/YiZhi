package com.zyw.horrarndoo.yizhi.contract.detail;

import android.support.v4.app.FragmentActivity;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseActivity;
import com.zyw.horrarndoo.sdk.base.IBaseModel;

/**
 * Created by Horrarndoo on 2017/9/20.
 * <p>
 */

public interface BaseDetailContract {
    abstract class BaseDetailPresenter<M extends IBaseDetailModel, V extends IBaseDetailView>
            extends BasePresenter<M, V> {

        /**
         * 保存图片
         *
         * @param activity {@link FragmentActivity}
         * @param imgUrl   imgUrl
         */
        public abstract void saveImage(FragmentActivity activity, String imgUrl);
    }

    interface IBaseDetailModel extends IBaseModel {

    }

    interface IBaseDetailView extends IBaseActivity {
        /**
         * 显示网络错误
         */
        void showNetworkError();

        /**
         * 显示popupWindow
         */
        void showPopupWindow();

        /**
         * 隐藏popupWindow
         */
        void dismissPopupWindow();

        /**
         * 返回popup显示状态
         *
         * @return popup显示状态
         */
        boolean popupWindowIsShowing();
    }
}
