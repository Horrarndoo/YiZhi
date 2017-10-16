package com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyw.horrarndoo.sdk.base.BaseMVPCompatFragment;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.sdk.rxbus.Subscribe;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.GankIoWelfareAdapter;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoWelfareContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoWelfareItemBean;
import com.zyw.horrarndoo.yizhi.presenter.gankio.tabs.GankIoWelfarePresenter;

import java.util.List;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_GANKIO_WELFARE_TYPE;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoWelfareFragment extends BaseMVPCompatFragment<GankIoWelfareContract
        .GankIoWelfarePresenter, GankIoWelfareContract.IGankIoWelfareModel> implements
        GankIoWelfareContract.IGankIoWelfareView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_gankio_welfare)
    RecyclerView rvGankIoWelfare;

    GankIoWelfareAdapter mGankIoWelfareAdapter;

    public static GankIoWelfareFragment newInstance() {
        Bundle args = new Bundle();
        GankIoWelfareFragment fragment = new GankIoWelfareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        //        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_welfare;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvGankIoWelfare.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadLatestList();
            }
        });
        rvGankIoWelfare.setLayoutManager(new GridLayoutManager(mActivity, 2));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoWelfarePresenter.newInstance();
    }

    @Override
    public void updateContentList(List<GankIoWelfareItemBean> list) {
        //Logger.e(list.toString());
        if (mGankIoWelfareAdapter == null) {
            initRecycleView(list);
        } else {
            mGankIoWelfareAdapter.addData(list);
        }
    }

    @Override
    public void itemNotifyChanged(int position) {
    }

    @Override
    public void showNetworkError() {
        mGankIoWelfareAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mGankIoWelfareAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mGankIoWelfareAdapter.loadMoreEnd(false);
    }

    @Override
    public void onLoadMoreRequested() {
        //这里loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中直接showNoMoreData，出现无限显示加载item
        mGankIoWelfareAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    private void initRecycleView(List<GankIoWelfareItemBean> list) {
        mGankIoWelfareAdapter = new GankIoWelfareAdapter(R.layout.item_gank_io_welfare, list);
        mGankIoWelfareAdapter.setOnLoadMoreListener(this, rvGankIoWelfare);
        mGankIoWelfareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (GankIoWelfareItemBean) adapter.getItem(position));
            }
        });
        rvGankIoWelfare.setAdapter(mGankIoWelfareAdapter);
    }

    /**
     * day页面查看更多事件触发
     */
    @Subscribe(code = RX_BUS_CODE_GANKIO_WELFARE_TYPE)
    public void rxBusEvent() {
        rvGankIoWelfare.smoothScrollToPosition(0);
    }
}
