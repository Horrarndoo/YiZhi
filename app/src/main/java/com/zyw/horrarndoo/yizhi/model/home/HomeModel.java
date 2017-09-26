package com.zyw.horrarndoo.yizhi.model.home;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.home.MainContract;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 * 主页model
 */

public class HomeModel extends BaseModel implements MainContract.IMainModel {

    @NonNull
    public static HomeModel newInstance() {
        return new HomeModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"知乎日报", "热点新闻", "微信精选"};
    }
}
