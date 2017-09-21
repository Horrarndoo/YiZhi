package com.zyw.horrarndoo.yizhi.contract.home.tabs;

import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyListBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 * 知乎头条接口
 */

public interface ZhihuContract {

    abstract class ZhihuPresenter extends BaseTabsContract.BaseTabsPresenter<IZhihuModel,
            IZhihuView, ZhihuDailyItemBean> {
    }

    interface IZhihuModel extends BaseTabsContract.IBaseTabsModel {
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
    }

    interface IZhihuView extends BaseTabsContract.IBaseTabsView<ZhihuDailyItemBean> {
    }
}
