package com.zyw.horrarndoo.yizhi.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatActivity;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.DisplayUtils;
import com.zyw.horrarndoo.sdk.utils.NetworkConnectionUtils;
import com.zyw.horrarndoo.sdk.widgets.NestedScrollWebView;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.detail.BaseDetailContract;
import com.zyw.horrarndoo.yizhi.ui.activity.pic.ImageBrowseActivity;
import com.zyw.horrarndoo.yizhi.ui.widgets.WebViewLongClickedPopWindow;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.IntentKeyConstant.INTENT_KEY_IMAGE_URL;

/**
 * Created by Horrarndoo on 2017/9/20.
 * <p>
 */

public abstract class BaseDetailActivity<P extends BaseDetailContract.BaseDetailPresenter, M
        extends BaseDetailContract.IBaseDetailModel> extends
        BaseMVPCompatActivity<P, M> implements BaseDetailContract.IBaseDetailView {

    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
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
    @BindView(R.id.nswv_detail_content)
    NestedScrollWebView nswvDetailContent;
    @BindView(R.id.fl_net_view)
    FrameLayout flNetView;
    @BindView(R.id.v_network_error)
    View vNetworkError;
    @BindView(R.id.pb_web)
    ProgressBar pvWeb;

    private int downX, downY;
    private WebViewLongClickedPopWindow popWindow;
    private String mImgurl;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, getToolbarTitle());
        initWebSetting(nswvDetailContent.getSettings());
        initWebView();

        vNetworkError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetail();
            }
        });

        popWindow = new WebViewLongClickedPopWindow(BaseDetailActivity.this,
                WebViewLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW, DisplayUtils.dp2px
                (120), ViewGroup.LayoutParams.WRAP_CONTENT);

        popWindow.setOnItemClickListener(new WebViewLongClickedPopWindow.OnItemClickListener() {
            @Override
            public void onShowPicClicked() {
                mPresenter.gotoImageBrowseClicked(mImgurl);
            }

            @Override
            public void onSavePicClicked() {
                mPresenter.saveImageClicked(BaseDetailActivity.this, mImgurl);
            }
        });

        loadDetail();
    }

    @Override
    public void onBackPressedSupport() {
        if (nswvDetailContent.canGoBack()) {
            //获取webView的浏览记录
            WebBackForwardList mWebBackForwardList = nswvDetailContent.copyBackForwardList();
            //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                nswvDetailContent.goBack();
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

    @Override
    public void showPopupWindow() {
        popWindow.showAtLocation(nswvDetailContent, Gravity.TOP | Gravity.LEFT,
                downX, downY + 10);
    }

    @Override
    public void dismissPopupWindow() {
        popWindow.dismiss();
    }

    @Override
    public boolean popupWindowIsShowing() {
        return popWindow.isShowing();
    }

    @Override
    public void gotoImageBrowse(String imgUrl) {
        Logger.e("gotoImageBrowse");
        Intent intent = new Intent();
        intent.putExtra(INTENT_KEY_IMAGE_URL, imgUrl);
        startActivity(ImageBrowseActivity.class, intent);
    }

    /**
     * js接口
     */
    public class SupportJavascriptInterface {
        private Context context;

        public SupportJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(final String img) {
            AppUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    gotoImageBrowse(img);
                }
            });
        }
    }

    protected void initWebView() {
        // 添加js交互接口类，并起别名 imagelistner
        nswvDetailContent.addJavascriptInterface(new SupportJavascriptInterface(this),
                "imagelistner");
        nswvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
                // html加载完成之后，添加监听图片的点击js函数
                addWebImageClickListner(view);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageStarted(view, url, favicon);
            }

            // 注入js函数监听
            protected void addWebImageClickListner(WebView webView) {
                // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，
                // 函数的功能是在图片点击的时候调用本地java接口并传递url过去
                webView.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.imagelistner.openImage(this.src);  " +
                        "    }  " +
                        "}" +
                        "})()");
            }
        });

        nswvDetailContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pvWeb.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pvWeb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pvWeb.setProgress(newProgress);//设置进度值
                }
            }
        });

        nswvDetailContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null == result)
                    return false;

                mPresenter.imageLongClicked(result);
                mImgurl = result.getExtra();

                return true;
            }
        });

        nswvDetailContent.setOnTouchListener(WebViewOnTouchListener);
    }

    View.OnTouchListener WebViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            downX = (int) event.getX();
            downY = (int) event.getY();
            return false;
        }
    };

    /**
     * 初始化WebSetting
     *
     * @param settings WebSetting
     */
    protected void initWebSetting(WebSettings settings) {
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
    }

    /**
     * 加载详情，交由子类实现
     */
    protected abstract void loadDetail();

    /**
     * 返回title，子类实现
     *
     * @return
     */
    protected abstract String getToolbarTitle();
}
