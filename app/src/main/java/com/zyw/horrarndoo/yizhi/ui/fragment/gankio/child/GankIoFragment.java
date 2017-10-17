package com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.adapter.FragmentAdapter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.sdk.rxbus.Subscribe;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.constant.RxBusCode;
import com.zyw.horrarndoo.yizhi.constant.TabFragmentIndex;
import com.zyw.horrarndoo.yizhi.contract.gankio.GankIoMainContract;
import com.zyw.horrarndoo.yizhi.presenter.gankio.GankIoMainPresenter;
import com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs.GankIoCustomFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs.GankIoDayFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs.GankIoWelfareFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_GANKIO_SELECT_TO_CHILD;

/**
 * Created by Horrarndoo on 2017/9/23.
 * <p>
 */

public class GankIoFragment extends BaseMVPCompatFragment<GankIoMainContract.GankIoMainPresenter,
        GankIoMainContract.IGankIoMainModel> implements GankIoMainContract.IGankIoMainView {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.vp_fragment)
    ViewPager vpFragment;
    @BindView(R.id.fab_classify)
    FloatingActionButton fabClassify;

    private List<Fragment> fragments;

    public static GankIoFragment newInstance() {
        Bundle args = new Bundle();
        GankIoFragment fragment = new GankIoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        //        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (vpFragment.getCurrentItem() == 1) {
                    if (verticalOffset == 0) {
                        fabClassify.show();
                    } else {
                        fabClassify.hide();
                    }
                }
            }
        });
        fabClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.get().send(RxBusCode.RX_BUS_CODE_GANKIO_PARENT_FAB_CLICK);
            }
        });
        vpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                fabClassify.setVisibility(View.GONE);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    fabClassify.setVisibility(View.VISIBLE);
                } else {
                    fabClassify.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoMainPresenter.newInstance();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTabList();
    }

    @Override
    public void showTabList(String[] tabs) {
        Logger.w(Arrays.toString(tabs));
        for (int i = 0; i < tabs.length; i++) {
            tlTabs.addTab(tlTabs.newTab().setText(tabs[i]));
            switch (i) {
                case TabFragmentIndex.TAB_GANK_DAY_INDEX:
                    fragments.add(GankIoDayFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_GANK_CUSTOM_INDEX:
                    fragments.add(GankIoCustomFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_GANK_WELFARE_INDEX:
                    fragments.add(GankIoWelfareFragment.newInstance());
                    break;
                default:
                    fragments.add(GankIoDayFragment.newInstance());
                    break;
            }
        }
        vpFragment.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        vpFragment.setCurrentItem(TabFragmentIndex.TAB_GANK_DAY_INDEX);//要设置到viewpager
        // .setAdapter后才起作用
        tlTabs.setupWithViewPager(vpFragment);
        tlTabs.setVerticalScrollbarPosition(TabFragmentIndex.TAB_GANK_DAY_INDEX);
        //tlTabs.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        for (int i = 0; i < tabs.length; i++) {
            tlTabs.getTabAt(i).setText(tabs[i]);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragments = new ArrayList<>();
    }

    /**
     * 切换tabs
     */
    @Subscribe(code = RX_BUS_CODE_GANKIO_SELECT_TO_CHILD)
    public void rxBusEvent(Integer index) {
        Logger.e("index = " + index);
        tlTabs.setVerticalScrollbarPosition(index);
        vpFragment.setCurrentItem(index);
    }
}
