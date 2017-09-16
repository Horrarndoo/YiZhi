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
import com.zyw.horrarndoo.yizhi.adapter.ZhihuAdapter;
import com.zyw.horrarndoo.yizhi.contract.home.tabs.ZhihuContract;
import com.zyw.horrarndoo.yizhi.model.bean.zhihu.ZhihuDailyItemBean;
import com.zyw.horrarndoo.yizhi.presenter.home.tabs.ZhihuPresenter;
import com.zyw.horrarndoo.yizhi.ui.widgets.RvLoadMoreView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class ZhihuFragment extends BaseMVPCompatFragment<ZhihuContract.ZhihuPresenter,
        ZhihuContract.IZhihuModel> implements ZhihuContract.IZhihuView, BaseQuickAdapter
        .RequestLoadMoreListener {

    @BindView(R.id.rv_zhihu)
    RecyclerView rvZhihu;

    private ZhihuAdapter mZhihuAdapter;

    public static ZhihuFragment newInstance() {
        Bundle args = new Bundle();
        ZhihuFragment fragment = new ZhihuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mZhihuAdapter = new ZhihuAdapter(R.layout.item_zhihu);
        mZhihuAdapter.setLoadMoreView(new RvLoadMoreView());
        mZhihuAdapter.setEnableLoadMore(true);
        mZhihuAdapter.setOnLoadMoreListener(this, rvZhihu);
        mZhihuAdapter.openLoadAnimation();//开启默认动画载入（仅开启加载新item时开启动画）
        mZhihuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (ZhihuDailyItemBean) adapter.getItem(position));
            }
        });
        rvZhihu.setAdapter(mZhihuAdapter);
        rvZhihu.setLayoutManager(new LinearLayoutManager(mContext));

        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvZhihu.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadLastDaily();
            }
        });

        mPresenter.loadLastDaily();//第一次显示时请求最新的日报list
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ZhihuPresenter.newInstance();
    }

    @Override
    public void updateDailyList(List<ZhihuDailyItemBean> list) {
        //        Logger.e(list.toString());
        mZhihuAdapter.addData(list);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mZhihuAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        //Logger.e("Network error.");
        mZhihuAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        //Logger.e("load more error.");
        mZhihuAdapter.loadMoreFail();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMoreDaily();
        mZhihuAdapter.loadMoreComplete();
    }
}
