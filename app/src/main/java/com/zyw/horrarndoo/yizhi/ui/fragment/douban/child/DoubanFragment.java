package com.zyw.horrarndoo.yizhi.ui.fragment.douban.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.DoubanAdapter;
import com.zyw.horrarndoo.yizhi.contract.douban.DoubanMainContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;
import com.zyw.horrarndoo.yizhi.presenter.douban.DoubanMainPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class DoubanFragment extends BaseRecycleFragment<DoubanMainContract.DoubanMainPresenter,
        DoubanMainContract.IDoubanMainModel> implements DoubanMainContract.IDoubanMainView {

    @BindView(R.id.rv_douban_hot_movie)
    RecyclerView rvDoubanHotMovie;

    private DoubanAdapter mDoubanAdapter;
    private View headView;

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
        //初始化一个空list的adapter，网络错误时使用，第一次加载到数据时重新初始化adapter并绑定recycleview
        mDoubanAdapter = new DoubanAdapter(R.layout.item_douban_hot_movie);
        rvDoubanHotMovie.setAdapter(mDoubanAdapter);
        rvDoubanHotMovie.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
        //        Logger.e(list.toString());
        if (mDoubanAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mDoubanAdapter.addData(list);
        }
    }

    @Override
    public void showNetworkError() {
        mDoubanAdapter.setEmptyView(errorView);
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadHotMovieList();
    }

    @Override
    protected void showLoading() {
        mDoubanAdapter.setEmptyView(loadingView);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return DoubanMainPresenter.newInstance();
    }

    private void initRecycleView(List<SubjectsBean> list) {
        mDoubanAdapter = new DoubanAdapter(R.layout.item_douban_hot_movie, list);
        mDoubanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //由于有headview click position需要+1 adapter.getItem返回的是数据list的position，所以不用+1
                mPresenter.onItemClick(position + 1, (SubjectsBean) adapter.getItem(position),
                        (ImageView) view.findViewById(R.id.iv_moive_photo));
            }
        });
        initHeadView();
        mDoubanAdapter.addHeaderView(headView);
        rvDoubanHotMovie.setAdapter(mDoubanAdapter);
    }

    private void initHeadView() {
        if (headView == null) {
            headView = ResourcesUtils.inflate(R.layout.sub_douban_top_header);
        }
        headView.findViewById(R.id.ll_movie_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onHeaderClick();
            }
        });
    }
}
