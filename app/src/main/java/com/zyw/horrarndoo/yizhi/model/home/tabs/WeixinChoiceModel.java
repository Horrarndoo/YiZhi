package com.zyw.horrarndoo.yizhi.model.home.tabs;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.config.DBConfig;
import com.zyw.horrarndoo.sdk.config.ItemState;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.DBUtils;
import com.zyw.horrarndoo.yizhi.api.WeixinApi;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.WeixinContract;
import com.zyw.horrarndoo.yizhi.model.bean.weixin.WeixinChoiceListBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinChoiceModel extends BaseModel implements WeixinContract.IWeixinModel {

    @NonNull
    public static WeixinChoiceModel newInstance() {
        return new WeixinChoiceModel();
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig
                        .TABLE_WEIXIN, key, ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }

    @Override
    public Observable<WeixinChoiceListBean> getWeixinChoiceList(int page, int pageStrip, String
            dttype, String key) {
        return RetrofitCreateHelper.createApi(WeixinApi.class, WeixinApi.HOST).getWeixinChoiceList
                (page, pageStrip, dttype, key).compose(RxHelper
                .<WeixinChoiceListBean>rxSchedulerHelper());
    }
}
