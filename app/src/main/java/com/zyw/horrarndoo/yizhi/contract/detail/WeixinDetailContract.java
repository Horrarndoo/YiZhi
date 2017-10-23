package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.yizhi.presenter.detail.BaseWebViewLoadPresenter;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 * 微信精选详情页接口
 */

public interface WeixinDetailContract {

    abstract class WeixinDetailPresenter extends BaseWebViewLoadPresenter<IWeixinDetailModel,
                        IWeixinDetailView> {
        /**
         * 加载微信精选详情
         *
         * @param url url
         */
        public abstract void loadWeixinChoiceDetail(String url);
    }

    interface IWeixinDetailModel extends BaseWebViewLoadContract.IBaseWebViewLoadModel {
    }

    interface IWeixinDetailView extends BaseWebViewLoadContract.IBaseWebViewLoadView {
        /**
         * 显示微信精选详细内容
         *
         * @param url url
         */
        void showWeixinChoiceDetail(String url);
    }
}
