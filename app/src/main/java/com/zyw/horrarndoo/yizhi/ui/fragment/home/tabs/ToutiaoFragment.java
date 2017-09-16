package com.zyw.horrarndoo.yizhi.ui.fragment.home.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class ToutiaoFragment extends BaseMVPCompatFragment{

    public static ToutiaoFragment newInstance() {
        Bundle args = new Bundle();
        ToutiaoFragment fragment = new ToutiaoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_toutiao;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
