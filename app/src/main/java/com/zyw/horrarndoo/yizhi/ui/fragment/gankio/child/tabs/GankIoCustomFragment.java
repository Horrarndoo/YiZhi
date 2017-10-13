package com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.GankIoCustomAdapter;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoCustomItemBean;
import com.zyw.horrarndoo.yizhi.presenter.gankio.tabs.GankIoCustomPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoCustomFragment extends BaseMVPCompatFragment<GankIoCustomContract
        .GankIoCustomPresenter, GankIoCustomContract.IGankIoCustomModel> implements
        GankIoCustomContract.IGankIoCustomView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_gankio_custom)
    RecyclerView rvGankIoCustom;

    private String mCustomType = "all";
    private GankIoCustomAdapter mGankIoCustomAdapter;

    public static GankIoCustomFragment newInstance() {
        Bundle args = new Bundle();
        GankIoCustomFragment fragment = new GankIoCustomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_custom;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvGankIoCustom.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadLatestList();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoCustomPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<GankIoCustomItemBean> list) {
        Logger.e(list.toString());
        if(mGankIoCustomAdapter == null){
            initRecycleView(list);
        }else{
            mGankIoCustomAdapter.addData(list);
        }
    }

    @Override
    public void itemNotifyChanged(int position) {
        mGankIoCustomAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        mGankIoCustomAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mGankIoCustomAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoMoreData() {
        mGankIoCustomAdapter.loadMoreEnd(false);
    }

    @Override
    public String getCustomType() {
        return mCustomType;
    }

    @Override
    public void onLoadMoreRequested() {
        //这里loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中直接showNoMoreData，出现无限显示加载item
        mGankIoCustomAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    private void initRecycleView(List<GankIoCustomItemBean> list){
        mGankIoCustomAdapter = new GankIoCustomAdapter(list);
        mGankIoCustomAdapter.setOnLoadMoreListener(this, rvGankIoCustom);
        mGankIoCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (GankIoCustomItemBean) adapter.getItem(position));
            }
        });
        rvGankIoCustom.setAdapter(mGankIoCustomAdapter);
        rvGankIoCustom.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
