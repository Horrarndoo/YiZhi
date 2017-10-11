package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.yizhi.presenter.detail.BaseDetailPresenter;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public interface GankIoDetailContract {
    abstract class GankIoDetailPresenter extends BaseDetailPresenter<IGankIoDetailModel,
            IGankIoDetailView> {
        /**
         * 加载GankIo详情
         *
         * @param url url
         */
        public abstract void loadGankIoDetail(String url);
    }

    interface IGankIoDetailModel extends BaseDetailContract.IBaseDetailModel {
    }

    interface IGankIoDetailView extends BaseDetailContract.IBaseDetailView {
        /**
         * 显示GankIo详细内容
         *
         * @param url url
         */
        void showGankIoDetail(String url);
    }
}
