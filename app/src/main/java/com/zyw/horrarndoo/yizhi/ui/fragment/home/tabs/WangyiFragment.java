package com.zyw.horrarndoo.yizhi.ui.fragment.home.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.WangyiAdapter;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.WangyiContract;
import com.zyw.horrarndoo.yizhi.model.bean.wangyi.WangyiNewsItemBean;
import com.zyw.horrarndoo.yizhi.presenter.home.tabs.WangyiPresenter;
import com.zyw.horrarndoo.yizhi.ui.widgets.RvLoadMoreView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class WangyiFragment extends BaseMVPCompatFragment<WangyiContract.WangyiPresenter,
        WangyiContract.IWangyiModel> implements WangyiContract.IWangyiView, BaseQuickAdapter
        .RequestLoadMoreListener {

    @BindView(R.id.rv_wangyi)
    RecyclerView rvWangyi;

    private WangyiAdapter mWangyiAdapter;

    public static WangyiFragment newInstance() {
        Bundle args = new Bundle();
        WangyiFragment fragment = new WangyiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wangyi;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mWangyiAdapter = new WangyiAdapter(R.layout.item_wangyi);
        mWangyiAdapter.setLoadMoreView(new RvLoadMoreView());
        mWangyiAdapter.setEnableLoadMore(true);
        mWangyiAdapter.setOnLoadMoreListener(this, rvWangyi);
        mWangyiAdapter.openLoadAnimation();//开启默认动画载入（仅开启加载新item时开启动画）
        mWangyiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (WangyiNewsItemBean) adapter.getItem(position));
            }
        });
        rvWangyi.setAdapter(mWangyiAdapter);
        rvWangyi.setLayoutManager(new LinearLayoutManager(mContext));

        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvWangyi.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadNewsList();
            }
        });

        mPresenter.loadNewsList();//第一次显示时请求最新的日报list
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WangyiPresenter.newInstance();
    }

    @Override
    public void updateNewsList(List<WangyiNewsItemBean> list) {
        //Logger.e(list.toString());
        mWangyiAdapter.addData(list);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mWangyiAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        mWangyiAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mWangyiAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mWangyiAdapter.loadMoreEnd();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMoreNewsList();
        mWangyiAdapter.loadMoreComplete();
    }
}
