package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyDetailBean;
import com.zyw.horrarndoo.yizhi.presenter.detail.BaseWebViewLoadPresenter;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/13.
 * <p>
 * 知乎日报详情页接口
 */

public interface ZhihuDetailContract {
    abstract class ZhihuDetailPresenter extends BaseWebViewLoadPresenter<IZhihuDetailModel, IZhihuDetailView> {
        /**
         * 加载日报详情
         */
        public abstract void loadDailyDetail(String id);
    }

    interface IZhihuDetailModel extends BaseWebViewLoadContract.IBaseWebViewLoadModel {
        /**
         * 获取日报详情
         *
         * @param id 日报id
         * @return Observable
         */
        Observable<ZhihuDailyDetailBean> getDailyDetail(String id);
    }

    interface IZhihuDetailView extends BaseWebViewLoadContract.IBaseWebViewLoadView {
        /**
         * 显示日报详细内容
         *
         * @param bean ZhihuDailyDetailBean
         */
        void showDailyDetail(ZhihuDailyDetailBean bean);
    }
}
