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
import com.zyw.horrarndoo.yizhi.adapter.GankIoDayAdapter;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoDayContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayItemBean;
import com.zyw.horrarndoo.yizhi.presenter.gankio.tabs.GankIoDayPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoDayFragment extends BaseMVPCompatFragment<GankIoDayContract
        .GankIoDayPresenter, GankIoDayContract.IGankIoDayModel> implements GankIoDayContract
        .IGankIoDayView {

    @BindView(R.id.rv_gankio_day)
    RecyclerView rvGankIoDay;

    GankIoDayAdapter mGankIoDayAdapter;

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
        mGankIoDayAdapter = new GankIoDayAdapter(null);
        mGankIoDayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (GankIoDayItemBean) adapter.getItem(position));
            }
        });
        mGankIoDayAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId())
                {
                    case R.id.ll_more:
                        mPresenter.onMoreClick(position, (GankIoDayItemBean) adapter.getItem(position));
                        break;
                    case R.id.ll_refesh:
                        break;
                }
            }
        });
        rvGankIoDay.setAdapter(mGankIoDayAdapter);
        rvGankIoDay.setLayoutManager(new LinearLayoutManager(mContext));

        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvGankIoDay.getParent(), false);
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
        return GankIoDayPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<GankIoDayItemBean> list) {
        Logger.e(list.toString());
        mGankIoDayAdapter.addData(list);
    }

    @Override
    public void itemNotifyChanged(int position) {
    }

    @Override
    public void showNetworkError() {
        //Logger.e("Network error.");
        mGankIoDayAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
    }

    @Override
    public void showNoMoreData() {
    }
}
