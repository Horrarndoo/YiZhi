package com.zyw.horrarndoo.yizhi.model.bean.rxbus;

import android.net.Uri;

/**
 * Created by Horrarndoo on 2017/9/26.
 * <p>
 * RxBus传递头像uri bean
 */

public class RxEventHeadBean {
    private Uri uri;

    public RxEventHeadBean(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "RxEventHeadBean{" +
                "uri=" + uri +
                '}';
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
