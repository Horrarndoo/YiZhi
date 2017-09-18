package com.zyw.horrarndoo.yizhi.api;

import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 */

public interface WangyiApi {
    public final String HOST = "http://c.m.163.com";

    @GET("/nc/article/headline/T1348647909107/{id}-100.html")
    Observable<WangyiNewsListBean> getNewsList(@Path("id") int id);

    @GET("/nc/article/{id}/full.html")
    Observable<String> getNewsDetail(@Path("id") String id);
}
