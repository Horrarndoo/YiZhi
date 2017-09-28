package com.zyw.horrarndoo.yizhi.contract.detail;

import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

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
         * 保存图片点击
         *
         * @param activity {@link FragmentActivity}
         * @param imgUrl   imgUrl
         */
        public abstract void saveImageClicked(FragmentActivity activity, String imgUrl);

        /**
         * 跳转图片详情按钮点击
         *
         * @param imgUrl imgUrl
         */
        public abstract void gotoImageBrowseClicked(String imgUrl);

        /**
         * 图片长按事件
         *
         * @param hitTestResult {@link WebView.HitTestResult}
         */
        public abstract void imageLongClicked(WebView.HitTestResult hitTestResult);

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

        /**
         * 跳转到图片详情页
         *
         * @param imgUrl 图片url
         */
        void gotoImageBrowse(String imgUrl);
    }
}
