package com.zyw.horrarndoo.yizhi.contract.home.tabs;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseFragment;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 */

public interface WangyiContract {
    abstract class WangyiPresenter extends BasePresenter<IWangyiModel, IWangyiView> {
        /**
         * 加载新闻list
         */
        public abstract void loadNewsList();

        /**
         * 加载更多新闻list
         */
        public abstract void loadMoreNewsList();

        /**
         * item点击事件
         *
         * @param position position
         * @param item     item
         */
        public abstract void onItemClick(int position, WangyiNewsItemBean item);
    }

    interface IWangyiModel extends IBaseModel {
        /**
         * 获取网易新闻list
         *
         * @param id id
         * @return Observable
         */
        Observable<WangyiNewsListBean> getNewsList(int id);

        /**
         * 记录item已阅到数据库
         *
         * @param key key(item.id值作为key)
         */
        Observable<Boolean> recordItemIsRead(String key);
    }

    interface IWangyiView extends IBaseFragment {
        /**
         * 更新news list
         *
         * @param list 日报list
         */
        void updateNewsList(List<WangyiNewsItemBean> list);

        /**
         * 点击事件后，刷新item
         *
         * @param position position
         */
        void itemNotifyChanged(int position);

        /**
         * 显示网络错误
         */
        void showNetworkError();

        /**
         * 显示加载更多错误
         */
        void showLoadMoreError();

        /**
         * 显示没有更多数据
         */
        void showNoMoreData();
    }
}
