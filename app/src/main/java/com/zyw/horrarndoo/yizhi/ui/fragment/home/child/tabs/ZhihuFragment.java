package com.zyw.horrarndoo.yizhi.ui.fragment.home.child.tabs;

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
        return R.layout.fragment_home_zhihu;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mZhihuAdapter = new ZhihuAdapter(R.layout.item_recycle_home);
        mZhihuAdapter.setOnLoadMoreListener(this, rvZhihu);
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
                mPresenter.loadLatestList();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();//第一次显示时请求最新的list
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ZhihuPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<ZhihuDailyItemBean> list) {
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
    public void showNoMoreData() {
        mZhihuAdapter.loadMoreEnd(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mZhihuAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }
}
