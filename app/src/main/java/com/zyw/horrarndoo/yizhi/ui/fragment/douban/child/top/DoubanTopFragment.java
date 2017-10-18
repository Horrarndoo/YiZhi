package com.zyw.horrarndoo.yizhi.ui.fragment.douban.child.top;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.DoubanTopAdapter;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanTopContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.presenter.douban.DoubanTopPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class DoubanTopFragment extends BaseRecycleFragment<DoubanTopContract
        .DoubanTopPresenter, DoubanTopContract.IDoubanTopModel> implements DoubanTopContract
        .IDoubanTopView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_douban_top_movie)
    RecyclerView rvDoubanTopMovie;

    private DoubanTopAdapter mDoubanTopAdapter;

    public static DoubanTopFragment newInstance() {
        Bundle args = new Bundle();
        DoubanTopFragment fragment = new DoubanTopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban_top;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadTopMovieList();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("豆瓣电影Top250");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });

        mDoubanTopAdapter = new DoubanTopAdapter(R.layout.item_douban_top_movie);
        rvDoubanTopMovie.setAdapter(mDoubanTopAdapter);
        //getItemCount()返回值<=0,要设置LinearLayoutManager，否则后面数据更新RecycleView也不执行onBindViewHolder;
        rvDoubanTopMovie.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
        //        Logger.e(list.toString());
        if (mDoubanTopAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mDoubanTopAdapter.addData(list);
        }
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadTopMovieList();
    }

    @Override
    protected void showLoading() {
        mDoubanTopAdapter.setEmptyView(loadingView);
    }

    @Override
    public void showNetworkError() {
        mDoubanTopAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoMoreData() {
        mDoubanTopAdapter.loadMoreEnd(true);
    }

    @Override
    public void showLoadMoreError() {
        mDoubanTopAdapter.loadMoreFail();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return DoubanTopPresenter.newInstance();
    }

    @Override
    public void onLoadMoreRequested() {
        //这里loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中直接showNoMoreData，出现无限显示加载item
        mDoubanTopAdapter.loadMoreComplete();
        mPresenter.loadMoreTopMovie();
    }

    private void initRecycleView(List<SubjectsBean> list) {
        mDoubanTopAdapter = new DoubanTopAdapter(R.layout.item_douban_top_movie, list);
        mDoubanTopAdapter.setOnLoadMoreListener(this, rvDoubanTopMovie);
        mDoubanTopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.e("position " + position + " is clicked.");
            }
        });
        rvDoubanTopMovie.setAdapter(mDoubanTopAdapter);
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
        rvDoubanTopMovie.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
    }
}
