package com.zyw.horrarndoo.yizhi.contract.home.tabs;

import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsListBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 *     网易新闻接口
 */

public interface WangyiContract {
    abstract class WangyiPresenter extends BaseTabsContract.BaseTabsPresenter<IWangyiModel,
            IWangyiView, WangyiNewsItemBean> {
    }

    interface IWangyiModel extends BaseTabsContract.IBaseTabsModel {
        /**
         * 获取网易新闻list
         *
         * @param id id
         * @return Observable
         */
        Observable<WangyiNewsListBean> getNewsList(int id);
    }

    interface IWangyiView extends BaseTabsContract.IBaseTabsView<WangyiNewsItemBean> {
    }
}
