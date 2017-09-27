package com.zyw.horrarndoo.yizhi.ui.fragment.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.zyw.horrarndoo.sdk.base.BaseCompatFragment;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.sdk.rxbus.Subscribe;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.ui.fragment.personal.child.PersonalLowerFragment;
import com.zyw.horrarndoo.yizhi.ui.fragment.personal.child.PersonalUpperFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;

import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_CHILD_FRAGMENT_BACK;

/**
 * Created by Horrarndoo on 2017/9/23.
 * <p>
 */

public class PersonalFragment extends BaseCompatFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_personal_container_upper)
    FrameLayout flContainerUpper;
    @BindView(R.id.fl_personal_container_lower)
    FrameLayout flContainerLower;

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("我的");
    }

    @Override
    public void initData() {
        super.initData();
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //Logger.e("onLazyInitView");
        //加载子fragment
        if (savedInstanceState == null) {
            loadFragment();
        } else {  // 这里可能会出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(PersonalUpperFragment.class) == null) {
                loadFragment();
            }
        }
    }

    private void loadFragment() {
        loadRootFragment(R.id.fl_personal_container_upper, PersonalUpperFragment.newInstance());
        loadRootFragment(R.id.fl_personal_container_lower, PersonalLowerFragment.newInstance());
    }

    @Subscribe(code = RX_BUS_CODE_CHILD_FRAGMENT_BACK)
    public void onChildBackPressed() {
        onBackPressedSupport();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 2) {
            popChild();
        } else {
            ((SupportActivity)getActivity()).onBackPressedSupport();
        }
        return true;
    }
}
