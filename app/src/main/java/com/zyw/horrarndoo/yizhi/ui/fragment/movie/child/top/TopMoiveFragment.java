package com.zyw.horrarndoo.yizhi.ui.fragment.movie.child.top;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.TopMovieAdapter;
import com.zyw.horrarndoo.yizhi.contract.movie.TopMovieContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.SubjectsBean;
import com.zyw.horrarndoo.yizhi.presenter.movie.TopMoviePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class TopMoiveFragment extends BaseRecycleFragment<TopMovieContract.TopMoivePresenter,
        TopMovieContract.ITopMovieModel> implements TopMovieContract.ITopMovieView,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_top_movie)
    RecyclerView rvTopMovie;

    private TopMovieAdapter mTopMovieAdapter;

    public static TopMoiveFragment newInstance() {
        Bundle args = new Bundle();
        TopMoiveFragment fragment = new TopMoiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_top;
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

        mTopMovieAdapter = new TopMovieAdapter(R.layout.item_top_movie);
        rvTopMovie.setAdapter(mTopMovieAdapter);
        //getItemCount()返回值<=0,要设置LinearLayoutManager，否则后面数据更新RecycleView也不执行onBindViewHolder;
        rvTopMovie.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
        //        Logger.e(list.toString());
        if (mTopMovieAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mTopMovieAdapter.addData(list);
        }
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadTopMovieList();
    }

    @Override
    protected void showLoading() {
        mTopMovieAdapter.setEmptyView(loadingView);
    }

    @Override
    public void showNetworkError() {
        mTopMovieAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoMoreData() {
        mTopMovieAdapter.loadMoreEnd(true);
    }

    @Override
    public void showLoadMoreError() {
        mTopMovieAdapter.loadMoreFail();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return TopMoviePresenter.newInstance();
    }

    @Override
    public void onLoadMoreRequested() {
        //这里loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中直接showNoMoreData，出现无限显示加载item
        mTopMovieAdapter.loadMoreComplete();
        mPresenter.loadMoreTopMovie();
    }

    private void initRecycleView(List<SubjectsBean> list) {
        mTopMovieAdapter = new TopMovieAdapter(R.layout.item_top_movie, list);
        mTopMovieAdapter.setOnLoadMoreListener(this, rvTopMovie);
        mTopMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (SubjectsBean) adapter.getItem(position),
                        (ImageView) view.findViewById(R.id.iv_top_moive_photo));
            }
        });
        rvTopMovie.setAdapter(mTopMovieAdapter);
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向,瀑布流
        rvTopMovie.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
    }
}
