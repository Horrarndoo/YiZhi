package com.zyw.horrarndoo.yizhi.presenter.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.detail.WangyiDetailContract;
import com.zyw.horrarndoo.yizhi.model.detail.WangyiDetailModel;

/**
 * Created by Horrarndoo on 2017/9/19.
 * <p>
 */

public class WangyiDetailPresenter extends WangyiDetailContract.WangyiDetailPresenter{
    @NonNull
    public static WangyiDetailPresenter newInstance() {
        return new WangyiDetailPresenter();
    }

    @Override
    public void loadNewsDetail(String url) {
        if(mIView == null)
            return;

        try {
            mIView.showNewsDetail(url);
        }catch (Exception e){
            e.printStackTrace();
            mIView.showNetworkError();
        }
    }

    @Override
    public WangyiDetailContract.IWangyiDetailModel getModel() {
        return WangyiDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
