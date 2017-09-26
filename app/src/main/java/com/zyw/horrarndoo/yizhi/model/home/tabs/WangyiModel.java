package com.zyw.horrarndoo.yizhi.model.home.tabs;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.config.DBConfig;
import com.zyw.horrarndoo.sdk.config.ItemState;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.DBUtils;
import com.zyw.horrarndoo.yizhi.api.WangyiApi;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.WangyiContract;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsListBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 */

public class WangyiModel implements WangyiContract.IWangyiModel {
    @NonNull
    public static WangyiModel newInstance() {
        return new WangyiModel();
    }

    @Override
    public Observable<WangyiNewsListBean> getNewsList(int id) {
        return RetrofitCreateHelper.createApi(WangyiApi.class, WangyiApi.HOST).getNewsList(id)
                .compose(RxHelper.<WangyiNewsListBean>rxSchedulerHelper());
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig
                        .TABLE_WANGYI, key, ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }
}
