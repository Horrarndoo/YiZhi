package com.zyw.horrarndoo.yizhi.ui.fragment.personal.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BaseCompatFragment;
import com.zyw.horrarndoo.yizhi.R;

/**
 * Created by Horrarndoo on 2017/9/26.
 * <p>
 */

public class PersonalLowerFragment extends BaseCompatFragment{

    public static PersonalLowerFragment newInstance() {
        Bundle args = new Bundle();
        PersonalLowerFragment fragment = new PersonalLowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_lower;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
