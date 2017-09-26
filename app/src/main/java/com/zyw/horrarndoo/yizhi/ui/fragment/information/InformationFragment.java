package com.zyw.horrarndoo.yizhi.ui.fragment.information;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;

/**
 * Created by Horrarndoo on 2017/9/23.
 * <p>
 */

public class InformationFragment extends BaseMVPCompatFragment {
    public static InformationFragment newInstance() {
        Bundle args = new Bundle();
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //Logger.e("onLazyInitView");
        //加载子fragment
    }
}
