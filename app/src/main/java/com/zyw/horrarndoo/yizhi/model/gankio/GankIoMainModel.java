package com.zyw.horrarndoo.yizhi.model.gankio;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.gankio.GankIoMainContract;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoMainModel implements GankIoMainContract.IGankIoMainModel {
    @NonNull
    public static GankIoMainModel newInstance() {
        return new GankIoMainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"每日推荐", "干货定制", "福利"};
    }
}
