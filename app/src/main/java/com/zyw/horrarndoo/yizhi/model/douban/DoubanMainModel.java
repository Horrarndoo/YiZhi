package com.zyw.horrarndoo.yizhi.model.douban;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.api.DoubanApi;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.HotMovieBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class DoubanMainModel extends BaseModel implements DoubanMainContract.IDoubanMainModel {

    @NonNull
    public static DoubanMainModel newInstance() {
        return new DoubanMainModel();
    }

    @Override
    public Observable<HotMovieBean> getHotMovieList() {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getHotMovie()
                .compose(RxHelper.<HotMovieBean>rxSchedulerHelper());
    }
}
