package com.zyw.horrarndoo.yizhi.model.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.detail.DoubanMovieMoreDetailConaract;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 */

public class DoubanMovieMoreDetailModel extends BaseModel implements
        DoubanMovieMoreDetailConaract.IDoubanMovieMoreDetailModel {

    @NonNull
    public static DoubanMovieMoreDetailModel newInstance() {
        return new DoubanMovieMoreDetailModel();
    }
}
