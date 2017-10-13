package com.zyw.horrarndoo.yizhi.contract.gankio.tabs;

import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoCustomItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoCustomListBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/13.
 * <p>
 */

public interface GankIoCustomContract {
    abstract class GankIoCustomPresenter extends BaseTabsContract
            .BaseTabsPresenter<IGankIoCustomModel, IGankIoCustomView, GankIoCustomItemBean> {

    }

    interface IGankIoCustomModel extends BaseTabsContract.IBaseTabsModel {
        /**
         * 请求GankIo每日数据list
         *
         * @param type    type 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
         * @param prePage 请求个数： 数字，大于0
         * @param page    请求第几页：数字，大于0
         * @return Observable
         */
        Observable<GankIoCustomListBean> getCustomGankIoList(String type, int prePage, int page);
    }

    interface IGankIoCustomView extends BaseTabsContract.IBaseTabsView<GankIoCustomItemBean> {
        /**
         * 返回定制消息类型
         * @return 定制消息类型 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
         */
        String getCustomType();
    }
}
