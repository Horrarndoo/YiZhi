package com.zyw.horrarndoo.yizhi.presenter.gankio.tabs;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoCustomItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoCustomListBean;
import com.zyw.horrarndoo.yizhi.model.gankio.tabs.GankIoCustomModel;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/13.
 * <p>
 */

public class GankIoCustomPresenter extends GankIoCustomContract.GankIoCustomPresenter {

    private int mCurrentPage;
    private boolean isLoading;

    @NonNull
    public static GankIoCustomPresenter newInstance() {
        return new GankIoCustomPresenter();
    }

    @Override
    public void loadLatestList() {
        if (mIModel == null || mIView == null)
            return;

        mCurrentPage = 1;
        //一次加载20条数据
        mRxManager.register(mIModel.getCustomGankIoList(mIView.getCustomType(), 10, mCurrentPage)
                .subscribe(new Consumer<GankIoCustomListBean>() {
                    @Override
                    public void accept(GankIoCustomListBean gankIoCustomListBean) throws Exception {
                        if (mIView == null)
                            return;

                        if (gankIoCustomListBean.isError()) {
                            mIView.showNetworkError();
                        } else {
                            mCurrentPage++;
                            mIView.updateContentList(gankIoCustomListBean.getResults());
                        }
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
    public void loadMoreList() {
        if (!isLoading) {
            isLoading = true;
            //一次加载20条数据
            mRxManager.register(mIModel.getCustomGankIoList(mIView.getCustomType(), 20,
                    mCurrentPage).subscribe(new Consumer<GankIoCustomListBean>() {
                @Override
                public void accept(GankIoCustomListBean gankIoCustomListBean) throws
                        Exception {
                    isLoading = false;
                    if (mIView == null)
                        return;

                    if (gankIoCustomListBean.isError()) {
                        mIView.showNetworkError();
                    } else {
                        if (gankIoCustomListBean.getResults().size() > 0) {
                            mCurrentPage++;
                            mIView.updateContentList(gankIoCustomListBean.getResults());
                        } else {
                            mIView.showNoMoreData();
                        }
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    isLoading = false;
                    if (mIView != null) {
                        mIView.showToast("Network error.");
                        mIView.showNetworkError();
                    }
                }
            }));
        }
    }

    @Override
    public void onItemClick(final int position, GankIoCustomItemBean item) {
        mRxManager.register(mIModel.recordItemIsRead(item.getType() + item.get_id()).subscribe
                (new Consumer<Boolean>() {
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
    }

    @Override
    public GankIoCustomContract.IGankIoCustomModel getModel() {
        return GankIoCustomModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
