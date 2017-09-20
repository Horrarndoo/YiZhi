package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatActivity;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.detail.WangyiDetailContract;
import com.zyw.horrarndoo.yizhi.presenter.detail.WangyiDetailPresenter;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/19.
 * <p>
 */

public class WangyiDailyDetailActivity extends BaseMVPCompatActivity<WangyiDetailContract
        .WangyiDetailPresenter, WangyiDetailContract.IWangyiDetailModel> implements
        WangyiDetailContract.IWangyiDetailView {

    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;
    @BindView(R.id.fl_net_view)
    FrameLayout flNetView;
    @BindView(R.id.v_network_error)
    View vNetworkError;

    private String mTitle, mUrl, mImageUrl;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mUrl = bundle.getString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_URL);
            mTitle = bundle.getString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_TITLE);
            mImageUrl = bundle.getString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_IMAGE_URL);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, "");
        collapsingToolbar.setTitle(mTitle);
        Glide.with(mContext).load(mImageUrl).crossFade().into(ivDetail);

        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        vNetworkError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadNewsDetail(mUrl);
            }
        });

        mPresenter.loadNewsDetail(mUrl);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wangyi_detail;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WangyiDetailPresenter.newInstance();
    }

    @Override
    public void showNewsDetail(String url) {
        flNetView.setVisibility(View.GONE);
        wvDetailContent.loadUrl(url);
    }

    @Override
    public void showNetworkError() {
        Logger.e("Network error.");
        vNetworkError.setVisibility(View.VISIBLE);
    }
}
