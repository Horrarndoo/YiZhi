package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.utils.HtmlUtils;
import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.detail.ZhihuDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyDetailBean;
import com.zyw.horrarndoo.yizhi.presenter.detail.ZhihuDetailPresenter;

/**
 * Created by Horrarndoo on 2017/9/13.
 * <p>
 */

public class ZhihuDailyDetailActivity extends BaseDetailActivity<ZhihuDetailContract
        .ZhihuDetailPresenter, ZhihuDetailContract.IZhihuDetailModel> implements
        ZhihuDetailContract.IZhihuDetailView {

    private String mId;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        mId = bundle.getString(BundleKeyConstant.ARG_KEY_ZHIHU_DETAIL_ID);
        super.initData();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ZhihuDetailPresenter.newInstance();
    }

    @Override
    public void showDailyDetail(ZhihuDailyDetailBean bean) {
        flNetView.setVisibility(View.GONE);
        Glide.with(mContext).load(bean.getImage()).crossFade().into(ivDetail);
        collapsingToolbar.setTitle(bean.getTitle());
        tvDetailcopyright.setText(bean.getImage_source());
        String htmlData = HtmlUtils.createHtmlData(bean.getBody(), bean.getCss(), bean.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtils.MIME_TYPE, HtmlUtils.ENCODING);
    }

    @Override
    protected void loadDetail() {
        mPresenter.loadDailyDetail(mId);
    }
}
