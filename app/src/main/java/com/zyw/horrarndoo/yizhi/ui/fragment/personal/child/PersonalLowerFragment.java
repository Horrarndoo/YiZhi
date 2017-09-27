package com.zyw.horrarndoo.yizhi.ui.fragment.personal.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zyw.horrarndoo.sdk.base.BaseCompatFragment;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.yizhi.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_CHILD_FRAGMENT_BACK;

/**
 * Created by Horrarndoo on 2017/9/26.
 * <p>
 */

public class PersonalLowerFragment extends BaseCompatFragment {

    @BindView(R.id.tv_btn_settings)
    TextView tvBtnSetting;

    public static PersonalLowerFragment newInstance() {
        Bundle args = new Bundle();
        PersonalLowerFragment fragment = new PersonalLowerFragment();
        fragment.setArguments(args);
        return fragment;
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
    public int getLayoutId() {
        return R.layout.fragment_personal_lower;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_btn_settings)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_settings:
                start(PersonalSettingFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        RxBus.get().send(RX_BUS_CODE_CHILD_FRAGMENT_BACK);
        return true;
    }
}
