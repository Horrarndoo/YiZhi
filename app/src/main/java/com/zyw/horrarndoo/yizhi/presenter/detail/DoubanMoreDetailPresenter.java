package com.zyw.horrarndoo.yizhi.presenter.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.detail.DoubanMoreDetailConaract;
import com.zyw.horrarndoo.yizhi.model.detail.DoubanMoreDetailModel;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 */

public class DoubanMoreDetailPresenter extends DoubanMoreDetailConaract.DoubanMoreDetailPresenter {

    @NonNull
    public static DoubanMoreDetailPresenter newInstance() {
        return new DoubanMoreDetailPresenter();
    }

    @Override
    public void loadDoubanMoreDetail(String url) {
        if (mIView == null)
            return;

        try {
            mIView.showDoubanMoreDetail(url);
        } catch (Exception e) {
            mIView.showNetworkError();
            e.printStackTrace();
        }
    }

    @Override
    public DoubanMoreDetailConaract.IDoubanMoreDetailModel getModel() {
        return DoubanMoreDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
