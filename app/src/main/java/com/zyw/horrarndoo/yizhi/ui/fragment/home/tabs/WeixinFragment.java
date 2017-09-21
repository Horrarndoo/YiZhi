package com.zyw.horrarndoo.yizhi.ui.fragment.home.tabs;

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
import com.zyw.horrarndoo.yizhi.adapter.WeixinAdapter;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.WeixinContract;
import com.zyw.horrarndoo.yizhi.model.bean.weixin.WeixinChoiceItemBean;
import com.zyw.horrarndoo.yizhi.presenter.home.tabs.WeixinPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class WeixinFragment extends BaseMVPCompatFragment<WeixinContract.WeixinPresenter,
        WeixinContract.IWeixinModel> implements WeixinContract.IWeixinView, BaseQuickAdapter
        .RequestLoadMoreListener {

    @BindView(R.id.rv_weixin)
    RecyclerView rvWexin;

    private WeixinAdapter mWeixinAdapter;

    public static WeixinFragment newInstance() {
        Bundle args = new Bundle();
        WeixinFragment fragment = new WeixinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weixin;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mWeixinAdapter = new WeixinAdapter(R.layout.item_recycle);
        mWeixinAdapter.setOnLoadMoreListener(this, rvWexin);
        mWeixinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (WeixinChoiceItemBean) adapter.getItem(position));
            }
        });
        rvWexin.setAdapter(mWeixinAdapter);
        rvWexin.setLayoutManager(new LinearLayoutManager(mContext));

        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvWexin.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadLatestList();
            }
        });

        mPresenter.loadLatestList();//第一次显示时请求最新的list
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WeixinPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<WeixinChoiceItemBean> list) {
        Logger.e(list.toString());
        mWeixinAdapter.addData(list);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mWeixinAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        mWeixinAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mWeixinAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mWeixinAdapter.loadMoreEnd();
    }

    @Override
    public void onLoadMoreRequested() {
        mWeixinAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }
}
