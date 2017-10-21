package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.yizhi.presenter.detail.BaseDetailPresenter;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 * 豆瓣电影更多，webview加载更多详情
 */

public interface DoubanMoreDetailConaract {
    abstract class DoubanMoreDetailPresenter extends
            BaseDetailPresenter<IDoubanMoreDetailModel, IDoubanMoreDetailView> {
        /**
         * 加载豆瓣更多详情
         *
         * @param url url
         */
        public abstract void loadDoubanMoreDetail(String url);
    }

    interface IDoubanMoreDetailModel extends BaseDetailContract.IBaseDetailModel {
    }

    interface IDoubanMoreDetailView extends BaseDetailContract.IBaseDetailView {
        /**
         * 显示豆瓣更多详情
         *
         * @param url url
         */
        void showDoubanMoreDetail(String url);
    }
}
