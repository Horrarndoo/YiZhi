package com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoDayFragment extends BaseMVPCompatFragment{

    public static GankIoDayFragment newInstance() {
        Bundle args = new Bundle();
        GankIoDayFragment fragment = new GankIoDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_day;
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
