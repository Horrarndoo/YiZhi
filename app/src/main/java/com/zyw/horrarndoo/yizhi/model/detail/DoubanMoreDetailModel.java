package com.zyw.horrarndoo.yizhi.model.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.detail.DoubanMoreDetailConaract;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 */

public class DoubanMoreDetailModel extends BaseModel implements
        DoubanMoreDetailConaract.IDoubanMoreDetailModel {

    @NonNull
    public static DoubanMoreDetailModel newInstance() {
        return new DoubanMoreDetailModel();
    }
}
