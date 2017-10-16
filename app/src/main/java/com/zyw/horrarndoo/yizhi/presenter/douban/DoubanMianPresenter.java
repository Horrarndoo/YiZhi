package com.zyw.horrarndoo.yizhi.presenter.douban;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.HotMovieBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.model.douban.DoubanMainModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class DoubanMianPresenter extends DoubanMainContract.DoubanMainPresenter {

    @NonNull
    public static DoubanMianPresenter newInstance() {
        return new DoubanMianPresenter();
    }

    @Override
    public void loadHotMovieList() {
        if(mIModel == null || mIView == null)
            return;
        mRxManager.register(mIModel.getHotMovieList().subscribe(new Consumer<HotMovieBean>() {
            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                Logger.e(hotMovieBean.toString());
            }
        }));
    }

    @Override
    public void onItemClick(int position, SubjectsBean item) {
        Logger.e("position " + position + "is clicked.");
    }

    @Override
    public DoubanMainContract.IDoubanMainModel getModel() {
        return DoubanMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
