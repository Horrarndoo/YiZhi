package com.zyw.horrarndoo.sdk.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.zyw.horrarndoo.sdk.AppManager;
import com.zyw.horrarndoo.sdk.R;
import com.zyw.horrarndoo.sdk.global.GlobalApplication;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.SpUtils;
import com.zyw.horrarndoo.sdk.utils.StatusBarUtils;
import com.zyw.horrarndoo.sdk.utils.ThemeUtils;
import com.zyw.horrarndoo.sdk.widgets.WaitPorgressDialog;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 * BaseActivity
 */

public abstract class BaseCompatActivity extends SupportActivity {
    protected GlobalApplication mApplication;
    protected WaitPorgressDialog mWaitPorgressDialog;
    protected Context mContext;//全局上下文对象
    private static final String TRANSLATE_VIEW = "translate_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    private void init(Bundle savedInstanceState) {
        setTheme(ThemeUtils.themeArr[SpUtils.getThemeIndex(this)][
                SpUtils.getNightModel(this) ? 1 : 0]);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        StatusBarUtils.setTransparent(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        initView(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }
    
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }

    /**
     * 初始化数据
     * <p>
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
        mContext = AppUtils.getContext();
        mApplication = (GlobalApplication) getApplication();
        mWaitPorgressDialog = new WaitPorgressDialog(this);
    }

    /**
     * 初始化view
     * <p>
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * <p>
     * 交由子类实现
     *
     * @return layout Id
     */
    protected abstract int getLayoutId();

    /**
     * 显示提示框
     *
     * @param msg 提示框内容字符串
     */
    protected void showProgressDialog(String msg) {
        mWaitPorgressDialog.setMessage(msg);
        mWaitPorgressDialog.show();
    }

    /**
     * 隐藏提示框
     */
    protected void hideProgressDialog() {
        if (mWaitPorgressDialog != null) {
            mWaitPorgressDialog.dismiss();
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [页面跳转]
     *
     * @param view 变化跳转Activity的view
     * @param clz  要跳转的Activity
     */
    public void startActivityCompat(View view, Class<?> clz) {
        ActivityCompat.startActivity(this, new Intent(this, clz), ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, TRANSLATE_VIEW).toBundle());
        startActivity(new Intent(this, clz));
    }

    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    protected void initTitleBar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }
}
