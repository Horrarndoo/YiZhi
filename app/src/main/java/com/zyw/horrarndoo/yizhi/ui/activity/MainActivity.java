package com.zyw.horrarndoo.yizhi.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.utils.ToastUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.ui.fragment.home.MainFragment;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 * 主页activity
 */

public class MainActivity extends BaseCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener, MainFragment.OnOpenDrawerLayoutListener {

    @BindView(R.id.nv_menu)
    NavigationView nvMenu;
    @BindView(R.id.dl_root)
    DrawerLayout dlRoot;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        nvMenu.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_item_publish:
                ToastUtils.showToast("publish is clicked");
                break;
            case R.id.group_item_tv:
                ToastUtils.showToast("tv is clicked");
                break;
            case R.id.group_item_map:
                ToastUtils.showToast("map is clicked");
                break;
            case R.id.item_setting:
                ToastUtils.showToast("setting is clicked");
                break;
            case R.id.item_about:
                startActivity(AboutActivity.class);
                break;
        }

        item.setCheckable(false);
        dlRoot.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressedSupport() {

        if (dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.closeDrawer(GravityCompat.START);
            return;
        }

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            //如果当前存在fragment>1，当前fragment出栈
            pop();
        } else {
            //如果已经到root fragment了，2秒内点击2次退出
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtils.showToast(R.string.press_again);
            }
        }
    }

    @Override
    public void onOpen() {
        if (!dlRoot.isDrawerOpen(GravityCompat.START)) {
            dlRoot.openDrawer(GravityCompat.START);
        }
    }
}
