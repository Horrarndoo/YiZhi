package com.zyw.horrarndoo.yizhi.model;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.home.MainContract;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 * 主页model
 */

public class MainModel extends BaseModel implements MainContract.IMainModel {

    @NonNull
    public static MainModel newInstance() {
        return new MainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"知乎日报", "热点新闻", "每日头条", "微信精选"};
    }
}
