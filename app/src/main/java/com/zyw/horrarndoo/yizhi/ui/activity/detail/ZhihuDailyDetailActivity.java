package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatActivity;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.utils.HtmlUtils;
import com.zyw.horrarndoo.sdk.utils.NetworkConnectionUtils;
import com.zyw.horrarndoo.sdk.utils.SpUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;
import com.zyw.horrarndoo.yizhi.contract.detail.ZhihuDetailContract;
import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyDetailBean;
import com.zyw.horrarndoo.yizhi.model.detail.ZhihuDetailModel;
import com.zyw.horrarndoo.yizhi.presenter.detail.ZhihuDetailPresenter;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/13.
 * <p>
 */

public class ZhihuDailyDetailActivity extends BaseMVPCompatActivity<ZhihuDetailContract
        .ZhihuDetailPresenter, ZhihuDetailModel> implements
        ZhihuDetailContract.IZhihuDetailView {

    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.tv_detail_copyright)
    TextView tvDetailcopyright;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.fl_net_view)
    FrameLayout flNetView;
    @BindView(R.id.v_network_error)
    View vNetworkError;


    private String mId;

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        mId = bundle.getString(BundleKeyConstant.ARG_KEY_ZHIHU_DETAIL);
        super.initData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, "");
        WebSettings settings = wvDetailContent.getSettings();
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (NetworkConnectionUtils.isConnected(mContext)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.night:
                        item.setChecked(!item.isChecked());
                        SpUtils.setNightModel(mContext, item.isChecked());
                        ZhihuDailyDetailActivity.this.reload();
                        break;
                }
                return false;
            }
        });

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
                mPresenter.loadDailyDetail(mId);
            }
        });

        mPresenter.loadDailyDetail(mId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_detail;
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
    public void showNetworkError() {
        Logger.e("Network error.");
        vNetworkError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressedSupport() {
        if (wvDetailContent.canGoBack()) {
            //获取webView的浏览记录
            WebBackForwardList mWebBackForwardList = wvDetailContent.copyBackForwardList();
            //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                wvDetailContent.goBack();
                return;
            }
        }
        super.onBackPressedSupport();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.night).setChecked(SpUtils.getNightModel(mContext));
        return true;
    }
}
