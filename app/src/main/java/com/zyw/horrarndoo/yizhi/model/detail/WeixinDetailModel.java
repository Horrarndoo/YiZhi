package com.zyw.horrarndoo.yizhi.model.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.sdk.base.BaseModel;
import com.zyw.horrarndoo.yizhi.contract.detail.WeixinDetailContract;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinDetailModel extends BaseModel implements WeixinDetailContract.IWeixinDetailModel {
    @NonNull
    public static WeixinDetailModel newInstance() {
        return new WeixinDetailModel();
    }
}
