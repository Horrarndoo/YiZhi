package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.detail.WeixinDetailContract;
import com.zyw.horrarndoo.yizhi.presenter.detail.WeixinDetailPresenter;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinChoiceDetailActivity extends BaseDetailActivity<WeixinDetailContract
        .WeixinDetailPresenter, WeixinDetailContract.IWeixinDetailModel> implements
        WeixinDetailContract.IWeixinDetailView {

    private String mTitle, mUrl, mImageUrl, mCopyright;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUrl = bundle.getString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_URL);
            mTitle = bundle.getString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_TITLE);
            mImageUrl = bundle.getString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_IMAGE_URL);
            mCopyright = bundle.getString(BundleKeyConstant.ARG_KEY_WEIXIN_DETAIL_COPYRIGHT);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        tvDetailTitle.setText(mTitle);
        tvDetailcopyright.setText(mCopyright);
        Glide.with(mContext).load(mImageUrl).crossFade().into(ivDetail);
        appBar.setExpanded(false);
    }

    @Override
    public void showWeixinChoiceDetail(String url) {
        flNetView.setVisibility(View.GONE);
        wvDetailContent.loadUrl(url);
    }

    @Override
    protected void loadDetail() {
        mPresenter.loadWeixinChoiceDetail(mUrl);
    }

    @Override
    protected String getToolbarTitle() {
        return ResourcesUtils.getString(R.string.weixin_detail_title);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WeixinDetailPresenter.newInstance();
    }
}
