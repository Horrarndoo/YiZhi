package com.zyw.horrarndoo.yizhi.presenter.gankio;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.gankio.GankIoMainContract;
import com.zyw.horrarndoo.yizhi.model.gankio.GankIoMainModel;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoMainPresenter extends GankIoMainContract.GankIoMainPresenter{

    @NonNull
    public static GankIoMainPresenter newInstance() {
        return new GankIoMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public GankIoMainContract.IGankIoMainModel getModel() {
        return GankIoMainModel.newInstance();
    }

    @Override
    public void onStart() {
        //getTabList();
    }
}
