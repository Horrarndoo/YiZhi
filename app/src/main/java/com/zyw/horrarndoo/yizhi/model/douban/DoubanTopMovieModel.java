package com.zyw.horrarndoo.yizhi.model.douban;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.api.DoubanApi;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanTopMovieContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.HotMovieBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class DoubanTopMovieModel extends BaseModel implements DoubanTopMovieContract.IDoubanTopMovieModel {

    @NonNull
    public static DoubanTopMovieModel newInstance() {
        return new DoubanTopMovieModel();
    }

    @Override
    public Observable<HotMovieBean> getTopMovieList(int start, int count) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getMovieTop250
                (start, count).compose(RxHelper.<HotMovieBean>rxSchedulerHelper());
    }
}
