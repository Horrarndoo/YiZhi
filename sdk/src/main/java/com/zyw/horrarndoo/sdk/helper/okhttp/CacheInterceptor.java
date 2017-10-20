package com.zyw.horrarndoo.sdk.helper.okhttp;


import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.NetworkConnectionUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.zyw.horrarndoo.sdk.utils.HttpUtils.getUserAgent;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 * CacheInterceptor
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkConnectionUtils.isNetworkConnected(AppUtils.getContext())) {
            // 有网络时, 缓存1小时
            int maxAge = 60 * 60;
            request = request.newBuilder()
                    .removeHeader("User-Agent")
                    .header("User-Agent", getUserAgent())
                    .build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // 无网络时，缓存为4周
            int maxStale = 60 * 60 * 24 * 28;
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .removeHeader("User-Agent")
                    .header("User-Agent", getUserAgent())
                    .build();

            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        //        Request request = chain.request();
        //        if (!NetworkConnectionUtils.isConnected(AppUtils.getContext())) {
        //            request = request.newBuilder()
        //                    .cacheControl(CacheControl.FORCE_CACHE)
        //                    .build();
        //        }
        //        Response response = chain.proceed(request);
        //        if (NetworkConnectionUtils.isConnected(AppUtils.getContext())) {
        //            int maxAge = 0;
        //            // 有网络时, 不缓存, 最大保存时长为0
        //            response.newBuilder()
        //                    .header("Cache-Control", "public, max-age=" + maxAge)
        //                    .removeHeader("Pragma")
        //                    .build();
        //        } else {
        //            // 无网络时，设置超时为4周
        //            int maxStale = 60 * 60 * 24 * 28;
        //            response.newBuilder()
        //                    .header("Cache-Control", "public, only-if-cached, max-stale=" +
        // maxStale)
        //                    .removeHeader("Pragma")
        //                    .build();
        //        }
        //        return response;
    }
}
