package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.yizhi.presenter.detail.BaseWebViewLoadPresenter;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 * webview加载更多详情，传入url显示webview
 */

public interface WebViewLoadConaract {
    abstract class WebViewLoadPresenter extends BaseWebViewLoadPresenter<IWebViewLoadModel,
                IWebViewLoadView> {
        /**
         * 加载url
         *
         * @param url url
         */
        public abstract void loadUrl(String url);
    }

    interface IWebViewLoadModel extends BaseWebViewLoadContract.IBaseWebViewLoadModel {
    }

    interface IWebViewLoadView extends BaseWebViewLoadContract.IBaseWebViewLoadView {
        /**
         * 显示url详情
         *
         * @param url url
         */
        void showUrlDetail(String url);
    }
}
