package com.zyw.horrarndoo.yizhi.contract.gankio.tabs;

import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayBean;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayItemBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public interface GankIoDayContract {
    abstract class GankIoDayPresenter extends BaseTabsContract.BaseTabsPresenter<IGankIoDayModel,
            IGankIoDayView, GankIoDayItemBean> {
        /**
         * “更多”按钮点击事件
         *
         * @param position position
         * @param item     item
         */
        public abstract void onMoreClick(int position, GankIoDayItemBean item);
    }

    interface IGankIoDayModel extends BaseTabsContract.IBaseTabsModel {
        //        /**
        //         * 请求GankIo每日数据list
        //         *
        //         * @param type    type 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
        //         * @param prePage 请求个数： 数字，大于0
        //         * @param page    请求第几页：数字，大于0
        //         * @return Observable
        //         */
        //        Observable<GankIoDataBean> getGankIoDayList(String type, int prePage, int page);

        /**
         * 请求GankIo每日数据list
         *
         * @param year  年
         * @param month 月
         * @param day   日
         * @return Observable
         */
        Observable<GankIoDayBean> getGankIoDayList(String year, String month, String day);
    }

    interface IGankIoDayView extends BaseTabsContract.IBaseTabsView<GankIoDayItemBean> {
    }
}
