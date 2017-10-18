package com.zyw.horrarndoo.yizhi.contract.douban;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.sdk.base.IBaseView;
import com.zyw.horrarndoo.yizhi.model.bean.douban.MovieDetailBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public interface DoubanMovieDetailContract {
    abstract class DoubanMovieDetailPresenter extends BasePresenter<IDoubanMovieDetailModel,
            IDoubanMovieDetailView> {
        /**
         * 加载电影详情
         *
         * @param id 电影id
         */
        public abstract void loadMovieDetail(String id);
    }

    interface IDoubanMovieDetailModel extends IBaseModel {
        /**
         * 获取电影详情
         *
         * @param id 电影id
         * @return 电影详情
         */
        Observable<MovieDetailBean> getMovieDetail(String id);
    }

    interface IDoubanMovieDetailView extends IBaseView {
        /**
         * 显示电影详情
         *
         * @param bean 电影详情bean
         */
        void showMovieDetail(MovieDetailBean bean);

        /**
         * 显示网络错误
         */
        void showNetworkError();

        /**
         * 显示加载中
         */
        void showLoading();
    }
}
