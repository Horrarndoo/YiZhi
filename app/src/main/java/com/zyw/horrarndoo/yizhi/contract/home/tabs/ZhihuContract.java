package com.zyw.horrarndoo.yizhi.contract.home.tabs;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseFragment;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */

public interface ZhihuContract {
    //知乎接口
    abstract class ZhihuPresenter extends BasePresenter<IZhihuModel, IZhihuView> {
        /**
         * 加载更多日报（之前的旧日报）
         * <p>
         * 在model中根据日期获取
         */
        public abstract void loadMoreDaily();

        /**
         * 加载最新知乎日报
         */
        public abstract void loadLastDaily();

        /**
         * item点击事件
         *
         * @param position position
         * @param item     item
         */
        public abstract void onItemClick(int position, ZhihuDailyItemBean item);
    }

    interface IZhihuModel extends IBaseModel {
        /**
         * 根据日期获取日报list --> 20170910
         *
         * @param date 日期
         * @return Observable
         */
        Observable<ZhihuDailyListBean> getDailyList(String date);

        /**
         * 获取日报list
         *
         * @return Observable
         */
        Observable<ZhihuDailyListBean> getDailyList();

        /**
         * 记录item已阅到数据库
         *
         * @param key key(item.id值作为key)
         */
        Observable<Boolean> recordItemIsRead(String key);
    }

    interface IZhihuView extends IBaseFragment {
        /**
         * 更新日报list
         *
         * @param list 日报list
         */
        void updateDailyList(List<ZhihuDailyItemBean> list);

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
    }
}
