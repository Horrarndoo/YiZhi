package com.zyw.horrarndoo.yizhi.model.gankio.tabs;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.config.DBConfig;
import com.zyw.horrarndoo.sdk.config.ItemState;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.DBUtils;
import com.zyw.horrarndoo.yizhi.api.GankioApi;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoDayContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public class GankIoDayModel extends BaseModel implements GankIoDayContract.IGankIoDayModel {
    @NonNull
    public static GankIoDayModel newInstance() {
        return new GankIoDayModel();
    }

    @Override
    public Observable<GankIoDayBean> getGankIoDayList(String year, String month, String day) {
        return RetrofitCreateHelper.createApi(GankioApi.class, GankioApi.HOST).getGankIoDay(year,
                month, day).compose(RxHelper.<GankIoDayBean>rxSchedulerHelper());
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig
                        .TABLE_GANKIO_DAY, key, ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }
}
