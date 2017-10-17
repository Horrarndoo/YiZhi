package com.zyw.horrarndoo.yizhi.presenter.home.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.WeixinContract;
import com.zyw.horrarndoo.yizhi.model.bean.weixin.WeixinChoiceItemBean;
import com.zyw.horrarndoo.yizhi.model.bean.weixin.WeixinChoiceListBean;
import com.zyw.horrarndoo.yizhi.model.home.tabs.WeixinChoiceModel;
import com.zyw.horrarndoo.yizhi.ui.activity.detail.WeixinChoiceDetailActivity;

import io.reactivex.functions.Consumer;

import static com.zyw.horrarndoo.yizhi.global.MyApplication.app;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinPresenter extends WeixinContract.WeixinPresenter {
    private int mCurrentPage;
    private final int mPageStrip = 20;
    private String mDttype;

    private boolean isLoading;

    @NonNull
    public static WeixinPresenter newInstance() {
        return new WeixinPresenter();
    }

    @Override
    public void loadLatestList() {
        mCurrentPage = 1;
        mRxManager.register(mIModel.getWeixinChoiceList(mCurrentPage, mPageStrip, mDttype, app
                .JU_HE_APP_KEY).subscribe(new Consumer<WeixinChoiceListBean>() {
            @Override
            public void accept(WeixinChoiceListBean wangyiNewsListBean) throws Exception {
                if (mIView == null)
                    return;

                if (wangyiNewsListBean.getError_code().equals("0")) {
                    mCurrentPage++;
                    mIView.updateContentList(wangyiNewsListBean.getResult().getList());
                } else {
                    mIView.showNetworkError();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable())
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
            mRxManager.register(mIModel.getWeixinChoiceList(mCurrentPage, mPageStrip, mDttype,
                    app.JU_HE_APP_KEY).subscribe
                    (new Consumer<WeixinChoiceListBean>() {

                        @Override
                        public void accept(WeixinChoiceListBean wangyiNewsListBean) throws
                                Exception {
                            isLoading = false;
                            if (mIView == null)
                                return;

                            if (wangyiNewsListBean.getError_code().equals("0")) {
                                mCurrentPage++;
                                mIView.updateContentList(wangyiNewsListBean.getResult().getList());
                            } else {
                                mIView.showLoadMoreError();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            isLoading = false;
                            if (mIView == null)
                                return;
                            mIView.showLoadMoreError();
                        }
                    }));
        }
    }

    @Override
    public void onItemClick(final int position, WeixinChoiceItemBean item) {
        mRxManager.register(mIModel.recordItemIsRead(item.getId()).subscribe(new Consumer<Boolean>() {
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

        if (mIView == null)
            return;

        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_URL, item.getUrl());
        bundle.putString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_TITLE, item.getTitle());
        bundle.putString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_IMAGE_URL, item.getFirstImg());
        bundle.putString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_COPYRIGHT, item.getSource());
        mIView.startNewActivity(WeixinChoiceDetailActivity.class, bundle);
    }

    @Override
    public WeixinContract.IWeixinModel getModel() {
        return WeixinChoiceModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
