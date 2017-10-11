package com.zyw.horrarndoo.yizhi.presenter.home;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.home.HomeMainContract;
import com.zyw.horrarndoo.yizhi.model.home.HomeMainModel;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class HomeMainPresenter extends HomeMainContract.HomeMainPresenter {

    @NonNull
    public static HomeMainPresenter newInstance() {
        return new HomeMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public HomeMainContract.IHomeMainModel getModel() {
        return HomeMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
