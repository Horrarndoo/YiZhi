package com.zyw.horrarndoo.yizhi.ui.fragment.douban.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.presenter.douban.DoubanMianPresenter;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class DoubanFragment extends BaseMVPCompatFragment<DoubanMainContract.DoubanMainPresenter,
        DoubanMainContract.IDoubanMainModel> implements DoubanMainContract.IDoubanMainView {

    public static DoubanFragment newInstance() {
        Bundle args = new Bundle();
        DoubanFragment fragment = new DoubanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban_;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadHotMovieList();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
        Logger.e(list.toString());
    }

    @Override
    public void showNetworkError() {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return DoubanMianPresenter.newInstance();
    }
}
