package com.zyw.horrarndoo.yizhi.api;

import com.zyw.horrarndoo.yizhi.model.bean.weixin.WeixinChoiceListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public interface WeixinApi {
    public static final String HOST = "http://v.juhe.cn";

    @GET("/weixin/query")
    Observable<WeixinChoiceListBean> getWeixinChoiceList(@Query("pno") int page, @Query("ps") int
            ps, @Query("dtype") String dttype, @Query("key") String key);
}
