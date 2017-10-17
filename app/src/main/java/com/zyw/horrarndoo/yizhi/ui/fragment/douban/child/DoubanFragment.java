package com.zyw.horrarndoo.yizhi.ui.fragment.douban.child;

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
import com.zyw.horrarndoo.yizhi.adapter.DoubanAdapter;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.presenter.douban.DoubanMianPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class DoubanFragment extends BaseMVPCompatFragment<DoubanMainContract.DoubanMainPresenter,
        DoubanMainContract.IDoubanMainModel> implements DoubanMainContract.IDoubanMainView {

    @BindView(R.id.rv_douban_top_movie)
    RecyclerView rvDoubanTopMovie;

    private DoubanAdapter mDoubanAdapter;

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
        errorView = mActivity.getLayoutInflater().inflate(R.layout.view_network_error,
                (ViewGroup) rvDoubanTopMovie.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadHotMovieList();
            }
        });
    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
//        Logger.e(list.toString());
        if(mDoubanAdapter == null){
            initRecycleView(list);
        }else {
            mDoubanAdapter.addData(list);
        }
    }

    @Override
    public void showNetworkError() {
        mDoubanAdapter.setEmptyView(errorView);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return DoubanMianPresenter.newInstance();
    }

    private void initRecycleView(List<SubjectsBean> list){
        mDoubanAdapter = new DoubanAdapter(R.layout.item_douban_hot_movie, list);
        mDoubanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (SubjectsBean) adapter.getItem(position));
            }
        });
        rvDoubanTopMovie.setAdapter(mDoubanAdapter);
        rvDoubanTopMovie.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
