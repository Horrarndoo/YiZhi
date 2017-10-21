package com.zyw.horrarndoo.yizhi.presenter.movie;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.zyw.horrarndoo.yizhi.contract.movie.TopMovieContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.HotMovieBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.SubjectsBean;
import com.zyw.horrarndoo.yizhi.model.movie.TopMovieModel;
import com.zyw.horrarndoo.yizhi.ui.activity.detail.MovieDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class TopMoviePresenter extends TopMovieContract.TopMoivePresenter {

    private int mStart;
    private int mCount = 30;
    private boolean isLoading;

    @NonNull
    public static TopMoviePresenter newInstance() {
        return new TopMoviePresenter();
    }

    @Override
    public void loadTopMovieList() {
        if (mIModel == null || mIView == null)
            return;

        mStart = 0;
        //一次加载20条数据
        mRxManager.register(mIModel.getTopMovieList(mStart, mCount).subscribe(new Consumer<HotMovieBean>() {
            @Override
            public void accept(HotMovieBean hotMovieBean) throws Exception {
                if (mIView == null)
                    return;

                mStart += mCount;
                mIView.updateContentList(hotMovieBean.getSubjects());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView == null)
                    return;

                if (mIView.isVisiable())
                    mIView.showToast("Network error.");

                mIView.showNetworkError();
            }
        }));
    }

    @Override
    public void loadMoreTopMovie() {
        if (!isLoading) {
            isLoading = true;
            //一次加载20条数据
            mRxManager.register(mIModel.getTopMovieList(mStart, mCount).subscribe(new Consumer<HotMovieBean>() {
                @Override
                public void accept(HotMovieBean hotMovieBean) throws
                        Exception {
                    isLoading = false;
                    if (mIView == null)
                        return;

                    if (hotMovieBean != null && hotMovieBean.getSubjects() != null &&
                            hotMovieBean.getSubjects().size() > 0) {
                        mStart += mCount;
                        mIView.updateContentList(hotMovieBean.getSubjects());
                    } else {
                        mIView.showNoMoreData();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    isLoading = false;
                    if (mIView != null) {
                        mIView.showLoadMoreError();
                    }
                }
            }));
        }
    }

    @Override
    public void onItemClick(int position, SubjectsBean item, ImageView imageView) {
        //        Logger.e("position " + position + " is clicked.");
        MovieDetailActivity.start(mIView.getBindActivity(), item, imageView);
    }

    @Override
    public TopMovieModel getModel() {
        return TopMovieModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
