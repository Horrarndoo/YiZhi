package com.zyw.horrarndoo.yizhi.presenter.detail;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.detail.DoubanMovieMoreDetailConaract;
import com.zyw.horrarndoo.yizhi.model.detail.DoubanMovieMoreDetailModel;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 */

public class DoubanMovieMoreDetailPresenter extends DoubanMovieMoreDetailConaract.DoubanMovieMoreDetailPresenter{

    @NonNull
    public static DoubanMovieMoreDetailPresenter newInstance() {
        return new DoubanMovieMoreDetailPresenter();
    }

    @Override
    public void loadDoubanMovieMoreDetail(String url) {
        if (mIView == null)
            return;

        try {
            mIView.showDoubanMovieMoreDetail(url);
        } catch (Exception e) {
            mIView.showNetworkError();
            e.printStackTrace();
        }
    }

    @Override
    public DoubanMovieMoreDetailConaract.IDoubanMovieMoreDetailModel getModel() {
        return DoubanMovieMoreDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
