package com.zyw.horrarndoo.yizhi.contract.movie;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseActivity;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.MovieDetailBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.PersonBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.SubjectsBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public interface MovieDetailContract {
    abstract class MovieDetailPresenter extends BasePresenter<IMovieDetailModel,
            IMovieDetailView> {
        /**
         * 加载电影详情
         *
         * @param id 电影id
         */
        public abstract void loadMovieDetail(String id);

        /**
         * item点击事件
         *
         * @param position  position
         * @param item      item
         */
        public abstract void onItemClick(int position, PersonBean item);

        /**
         * header点击事件
         * @param bean bean
         */
        public abstract void onHeaderClick(SubjectsBean bean);
    }

    interface IMovieDetailModel extends IBaseModel {
        /**
         * 获取电影详情
         *
         * @param id 电影id
         * @return 电影详情
         */
        Observable<MovieDetailBean> getMovieDetail(String id);
    }

    interface IMovieDetailView extends IBaseActivity {
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
    }
}
