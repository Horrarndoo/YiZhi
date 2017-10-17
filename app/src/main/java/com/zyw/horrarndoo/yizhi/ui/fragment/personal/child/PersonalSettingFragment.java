package com.zyw.horrarndoo.yizhi.ui.fragment.personal.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.fragment.BaseCompatFragment;
import com.zyw.horrarndoo.yizhi.R;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/26.
 * <p>
 */

public class PersonalSettingFragment extends BaseCompatFragment{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static PersonalSettingFragment newInstance() {
        Bundle args = new Bundle();
        PersonalSettingFragment fragment = new PersonalSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_setting;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }
}
