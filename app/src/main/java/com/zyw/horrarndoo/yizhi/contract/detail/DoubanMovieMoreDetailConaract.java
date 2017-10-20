package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.yizhi.presenter.detail.BaseDetailPresenter;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 * 豆瓣电影更多，webview加载更多详情
 */

public interface DoubanMovieMoreDetailConaract {
    abstract class DoubanMovieMoreDetailPresenter extends
            BaseDetailPresenter<IDoubanMovieMoreDetailModel, IDoubanMovieMoreDetailView> {
        /**
         * 加载豆瓣电影更多详情
         *
         * @param url url
         */
        public abstract void loadDoubanMovieMoreDetail(String url);
    }

    interface IDoubanMovieMoreDetailModel extends BaseDetailContract.IBaseDetailModel {
    }

    interface IDoubanMovieMoreDetailView extends BaseDetailContract.IBaseDetailView {
        /**
         * 显示豆瓣电影更多详情
         *
         * @param url url
         */
        void showDoubanMovieMoreDetail(String url);
    }
}
