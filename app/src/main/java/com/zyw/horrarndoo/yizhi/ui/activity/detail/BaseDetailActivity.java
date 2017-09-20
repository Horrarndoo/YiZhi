package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatActivity;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.sdk.utils.NetworkConnectionUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.detail.BaseDetailContract;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/20.
 * <p>
 */

public abstract class BaseDetailActivity<P extends BasePresenter, M extends IBaseModel> extends
        BaseMVPCompatActivity<P, M> implements BaseDetailContract.IBaseDetailView{

    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_detail_copyright)
    TextView tvDetailcopyright;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.fl_net_view)
    FrameLayout flNetView;
    @BindView(R.id.v_network_error)
    View vNetworkError;

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
                loadDetail();
            }
        });

        loadDetail();
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
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void showNetworkError() {
        Logger.e("Network error.");
        vNetworkError.setVisibility(View.VISIBLE);
    }

    /**
     * 加载详情，交由子类实现
     */
    protected abstract void loadDetail();
}
