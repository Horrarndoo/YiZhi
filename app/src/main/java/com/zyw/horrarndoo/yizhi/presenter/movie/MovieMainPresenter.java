package com.zyw.horrarndoo.yizhi.presenter.movie;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.zyw.horrarndoo.yizhi.cache.Cache;
import com.zyw.horrarndoo.yizhi.contract.movie.MovieMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.HotMovieBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.SubjectsBean;
import com.zyw.horrarndoo.yizhi.model.movie.MovieMainModel;
import com.zyw.horrarndoo.yizhi.ui.activity.detail.MovieDetailActivity;
import com.zyw.horrarndoo.yizhi.ui.fragment.movie.child.top.TopMoiveFragment;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class MovieMainPresenter extends MovieMainContract.MovieMainPresenter {

    @NonNull
    public static MovieMainPresenter newInstance() {
        return new MovieMainPresenter();
    }

    @Override
    public void loadHotMovieList() {
        if (mIModel == null || mIView == null)
            return;

        mRxManager.register(mIModel.getHotMovieList().subscribe(new Consumer<HotMovieBean>() {
            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                if (mIView == null)
                    return;

                mIView.updateContentList(hotMovieBean.getSubjects());
                Cache.saveHotMovieCache(hotMovieBean.getSubjects());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable())
                        mIView.showToast("Network error.");

                    if (Cache.getHotMovieCache().size() == 0) {//没有缓存缓存，显示网络错误界面
                        mIView.showNetworkError();
                    } else {
                        mIView.updateContentList(Cache.getHotMovieCache());//加载缓存
                    }
                }
            }
        }));
    }

    @Override
    public void onItemClick(int position, SubjectsBean item, ImageView imageView) {
//        Logger.e("position " + position + " is clicked.");
        MovieDetailActivity.start(mIView.getBindActivity(), item, imageView);
    }

    @Override
    public void onHeaderClick() {
        mIView.startNewFragment(TopMoiveFragment.newInstance());
    }

    @Override
    public MovieMainContract.IMovieMainModel getModel() {
        return MovieMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
