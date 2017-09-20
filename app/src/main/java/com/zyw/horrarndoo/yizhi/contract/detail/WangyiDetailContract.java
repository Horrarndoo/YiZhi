package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseActivity;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsDetailBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/19.
 * <p>
 */

public interface WangyiDetailContract {
    //网易接口
    abstract class WangyiDetailPresenter extends BasePresenter<IWangyiDetailModel,
            IWangyiDetailView> {
        /**
         * 加载新闻详情
         * @param url url
         */
        public abstract void loadNewsDetail(String url);
    }

    interface IWangyiDetailModel extends IBaseModel {
        /**
         * 获取日报详情
         *
         * @param id 新闻id
         * @return Observable
         */
        Observable<WangyiNewsDetailBean> getNewsDetail(String id);
    }

    interface IWangyiDetailView extends IBaseActivity {
        /**
         * 显示新闻详细内容
         *
         * @param url url
         */
        void showNewsDetail(String url);

        /**
         * 显示网络错误
         */
        void showNetworkError();
    }
}
