package com.zyw.horrarndoo.yizhi.model.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.detail.WangyiDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsDetailBean;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/19.
 * <p>
 */

public class WangyiDetailModel extends BaseModel implements WangyiDetailContract
        .IWangyiDetailModel {

    @NonNull
    public static WangyiDetailModel newInstance() {
        return new WangyiDetailModel();
    }

    @Override
    public Observable<WangyiNewsDetailBean> getNewsDetail(String url) {
        return null;
    }
}
