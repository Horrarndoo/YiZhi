package com.zyw.horrarndoo.yizhi.model.movie;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.sdk.helper.RetrofitCreateHelper;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.api.DoubanApi;
import com.zyw.horrarndoo.yizhi.contract.movie.MovieMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.HotMovieBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class MovieMainModel extends BaseModel implements MovieMainContract.IMovieMainModel {

    @NonNull
    public static MovieMainModel newInstance() {
        return new MovieMainModel();
    }

    @Override
    public Observable<HotMovieBean> getHotMovieList() {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getHotMovie()
                .compose(RxHelper.<HotMovieBean>rxSchedulerHelper());
    }
}
