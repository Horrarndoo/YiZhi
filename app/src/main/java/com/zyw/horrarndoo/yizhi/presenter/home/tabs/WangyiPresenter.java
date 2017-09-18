package com.zyw.horrarndoo.yizhi.presenter.home.tabs;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.WangyiContract;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsListBean;
import com.zyw.horrarndoo.yizhi.model.tabs.WangyiModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 */

public class WangyiPresenter extends WangyiContract.WangyiPresenter {
    private int mCurrentIndex;
    private boolean isLoading;

    @NonNull
    public static WangyiPresenter newInstance() {
        return new WangyiPresenter();
    }

    @Override
    public WangyiContract.IWangyiModel getModel() {
        return WangyiModel.newInstance();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void loadNewsList() {
        mCurrentIndex = 0;
        mRxManager.register(mIModel.getNewsList(mCurrentIndex).subscribe(new Consumer<WangyiNewsListBean>() {
            @Override
            public void accept(WangyiNewsListBean wangyiNewsListBean) throws Exception {
                mCurrentIndex += 20;
                if (mIView != null)
                    mIView.updateNewsList(wangyiNewsListBean.getNewsList());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    mIView.showToast("Network error.");
                    mIView.showNetworkError();
                }
            }
        }));
    }

    @Override
    public void loadMoreNewsList() {
        if (mCurrentIndex > 400) {
            if (mIView == null)
                return;

            mIView.showNoMoreData();
            return;
        }

        if (!isLoading) {
            isLoading = true;
            mRxManager.register(mIModel.getNewsList(mCurrentIndex).subscribe(new Consumer<WangyiNewsListBean>() {
                @Override
                public void accept(WangyiNewsListBean wangyiNewsListBean) throws Exception {
                    isLoading = false;
                    mCurrentIndex += 20;
                    if (mIView != null)
                        mIView.updateNewsList(wangyiNewsListBean.getNewsList());
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    isLoading = false;
                    mIView.showLoadMoreError();
                }
            }));
        }
    }

    @Override
    public void onItemClick(final int position, WangyiNewsItemBean item) {
        mRxManager.register(mIModel.recordItemIsRead(item.getDocid()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (mIView == null)
                    return;

                if (aBoolean) {
                    mIView.itemNotifyChanged(position);
                } else {
                    Logger.e("写入点击状态值失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));

        //        if (mIView == null)
        //            return;
        //
        //        Bundle bundle = new Bundle();
        //        bundle.putString(BundleKeyConstant.ARG_KEY_ZHIHU_DETAIL, item.getDocid());
        //        mIView.startNewActivity(ZhihuDailyDetailActivity.class, bundle);
    }
}
