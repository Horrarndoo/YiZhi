package com.zyw.horrarndoo.yizhi.presenter.home;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.home.MainContract;
import com.zyw.horrarndoo.yizhi.model.home.HomeModel;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class MainPresenter extends MainContract.MainPresenter {

    @NonNull
    public static MainPresenter newInstance() {
        return new MainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public MainContract.IMainModel getModel() {
        return HomeModel.newInstance();
    }

    @Override
    public void onStart() {
        getTabList();
    }
}
