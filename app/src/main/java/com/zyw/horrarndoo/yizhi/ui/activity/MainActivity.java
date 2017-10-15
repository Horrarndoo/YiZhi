package com.zyw.horrarndoo.yizhi.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.helper.BottomNavigationViewHelper;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.sdk.rxbus.Subscribe;
import com.zyw.horrarndoo.sdk.utils.AppUtils;
import com.zyw.horrarndoo.sdk.utils.FileUtils;
import com.zyw.horrarndoo.sdk.utils.SpUtils;
import com.zyw.horrarndoo.sdk.utils.ToastUtils;
import com.zyw.horrarndoo.sdk.widgets.MovingImageView;
import com.zyw.horrarndoo.sdk.widgets.MovingViewAnimator.MovingState;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.model.bean.rxbus.RxEventHeadBean;
import com.zyw.horrarndoo.yizhi.ui.fragment.douban.DoubanRootFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.gankio.GankIoRootFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.home.HomeRootFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.home.child.HomeFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.personal.PersonalRootFragment;

import java.io.File;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.zyw.horrarndoo.yizhi.constant.HeadConstant.HEAD_IMAGE_NAME;
import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI;

/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 * 主页activity
 */

public class MainActivity extends BaseCompatActivity implements HomeFragment
        .OnOpenDrawerLayoutListener {

    @BindView(R.id.nv_menu)
    NavigationView nvMenu;
    @BindView(R.id.dl_root)
    DrawerLayout dlRoot;
    @BindView(R.id.bviv_bar)
    BottomNavigationView bottomNavigationView;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    private MovingImageView mivMenu;
    private CircleImageView civHead;
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void initData() {
        super.initData();
        //        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeRootFragment.newInstance();
            mFragments[SECOND] = GankIoRootFragment.newInstance();
            mFragments[THIRD] = DoubanRootFragment.newInstance();
            mFragments[FOURTH] = PersonalRootFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, SECOND,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeRootFragment.class);
            mFragments[SECOND] = findFragment(GankIoRootFragment.class);
            mFragments[THIRD] = findFragment(DoubanRootFragment.class);
            mFragments[FOURTH] = findFragment(PersonalRootFragment.class);
        }

        mivMenu = (MovingImageView) nvMenu.getHeaderView(0).findViewById(R.id.miv_menu);
        civHead = (CircleImageView) nvMenu.getHeaderView(0).findViewById(R.id.civ_head);

        //此处实际应用中替换成服务器拉取图片
        Uri headUri = Uri.fromFile(new File(getCacheDir(), HEAD_IMAGE_NAME + ".jpg"));
        if (headUri != null) {
            String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), headUri);
            Bitmap bitmap = BitmapFactory.decodeFile(cropImagePath);
            if (bitmap != null) {
                civHead.setImageBitmap(bitmap);
            }
        }


        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlRoot.closeDrawer(GravityCompat.START);
                bottomNavigationView.setSelectedItemId(R.id.menu_item_personal);
            }
        });

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        showHideFragment(mFragments[FIRST]);
                        break;
                    case R.id.menu_item_gank_io:
                        showHideFragment(mFragments[SECOND]);
                        break;
                    case R.id.menu_item_douban:
                        showHideFragment(mFragments[THIRD]);
                        break;
                    case R.id.menu_item_personal:
                        showHideFragment(mFragments[FOURTH]);
                        break;
                }
                return true;
            }
        });

        nvMenu.getMenu().findItem(R.id.item_setting).setTitle(SpUtils.getNightModel(mContext) ?
                "夜间模式" : "日间模式");
        nvMenu.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.group_item_test1:
                        ToastUtils.showToast("group item test1 is clicked");
                        break;
                    case R.id.group_item_test2:
                        ToastUtils.showToast("group item test2 is clicked");
                        break;
                    case R.id.group_item_test3:
                        ToastUtils.showToast("group item test3 is clicked");
                        break;
                    case R.id.item_setting:
                        SpUtils.setNightModel(mContext, !SpUtils.getNightModel(mContext));
                        MainActivity.this.reload();
                        break;
                    case R.id.item_about:
                        startActivity(AboutActivity.class);
                        break;
                }

                item.setCheckable(false);
                dlRoot.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        dlRoot.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mivMenu.pauseMoving();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (mivMenu.getMovingState() == MovingState.stop) {
                    mivMenu.startMoving();
                } else if (mivMenu.getMovingState() == MovingState.pause) {
                    mivMenu.resumeMoving();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mivMenu.stopMoving();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (mivMenu.getMovingState() == MovingState.stop) {
                    mivMenu.startMoving();
                } else if (mivMenu.getMovingState() == MovingState.pause) {
                    mivMenu.resumeMoving();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
                setIsTransAnim(false);
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

    /**
     * RxBus接收图片Uri
     *
     * @param bean RxEventHeadBean
     */
    @Subscribe(code = RX_BUS_CODE_HEAD_IMAGE_URI)
    public void rxBusEvent(RxEventHeadBean bean) {
        Uri uri = bean.getUri();
        if (uri == null) {
            return;
        }
        String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), uri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        civHead.setImageBitmap(bitMap);
    }
}
