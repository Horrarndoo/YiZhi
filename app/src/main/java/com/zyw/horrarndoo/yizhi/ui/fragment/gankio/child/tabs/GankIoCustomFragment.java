package com.zyw.horrarndoo.yizhi.ui.fragment.gankio.child.tabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cocosw.bottomsheet.BottomSheet;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.sdk.rxbus.RxBus;
import com.zyw.horrarndoo.sdk.rxbus.Subscribe;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.GankIoCustomAdapter;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoCustomItemBean;
import com.zyw.horrarndoo.yizhi.presenter.gankio.tabs.GankIoCustomPresenter;

import java.util.List;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_GANKIO_CUSTOM_TYPE;
import static com.zyw.horrarndoo.yizhi.constant.RxBusCode.RX_BUS_CODE_GANKIO_PARENT_FAB_CLICK;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoCustomFragment extends BaseRecycleFragment<GankIoCustomContract
        .GankIoCustomPresenter, GankIoCustomContract.IGankIoCustomModel> implements
        GankIoCustomContract.IGankIoCustomView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_gankio_custom)
    RecyclerView rvGankIoCustom;

    private View headView;
    private String mCustomType = "all";
    private GankIoCustomAdapter mGankIoCustomAdapter;

    public static GankIoCustomFragment newInstance() {
        Bundle args = new Bundle();
        GankIoCustomFragment fragment = new GankIoCustomFragment();
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
        return R.layout.fragment_gank_io_custom;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //初始化一个空list的adapter，网络错误时使用，第一次加载到数据时重新初始化adapter并绑定recycleview
        mGankIoCustomAdapter = new GankIoCustomAdapter(null);
        rvGankIoCustom.setAdapter(mGankIoCustomAdapter);
        rvGankIoCustom.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoCustomPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<GankIoCustomItemBean> list) {
        //Logger.e(list.toString());
        if (mGankIoCustomAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mGankIoCustomAdapter.addData(list);
        }
    }

    @Override
    public void refeshCustomList(List<GankIoCustomItemBean> list) {
        if (mGankIoCustomAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mGankIoCustomAdapter.replaceData(list);
        }

        initHeadView();
    }

    @Override
    public void itemNotifyChanged(int position) {
        mGankIoCustomAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        mGankIoCustomAdapter.setEmptyView(errorView);
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestList();
    }

    @Override
    protected void showLoading() {
        mGankIoCustomAdapter.setEmptyView(loadingView);
    }

    @Override
    public void showLoadMoreError() {
        mGankIoCustomAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mGankIoCustomAdapter.loadMoreEnd(false);
    }

    @Override
    public String getCustomType() {
        return mCustomType;
    }

    @Override
    public void onLoadMoreRequested() {
        //这里loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中直接showNoMoreData，出现无限显示加载item
        mGankIoCustomAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    private void initRecycleView(List<GankIoCustomItemBean> list) {
        mGankIoCustomAdapter = new GankIoCustomAdapter(list);
        mGankIoCustomAdapter.setOnLoadMoreListener(this, rvGankIoCustom);
        mGankIoCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //由于有headview click position需要+1 adapter.getItem返回的是数据list的position，所以不用+1
                mPresenter.onItemClick(position + 1, (GankIoCustomItemBean) adapter.getItem
                        (position));
            }
        });
        initHeadView();
        mGankIoCustomAdapter.addHeaderView(headView);
        rvGankIoCustom.setAdapter(mGankIoCustomAdapter);
    }

    private void initHeadView() {
        if (headView == null) {
            headView = ResourcesUtils.inflate(R.layout.sub_gank_io_custom_head);
        }

        final TextView tvHeadName = (TextView) headView.findViewById(R.id.tv_type_name);
        headView.findViewById(R.id.ll_choose_catalogue).setOnClickListener(new
                CatalogueClickListenerImp(tvHeadName));
        if (mCustomType.equals("all")) {
            tvHeadName.setText("全部");
        }else{
            tvHeadName.setText(mCustomType);
        }
    }

    public class CatalogueClickListenerImp implements View.OnClickListener {
        TextView tvHeadName;

        public CatalogueClickListenerImp(@NonNull TextView textView) {
            tvHeadName = textView;
        }

        @Override
        public void onClick(View v) {
            showBottomSheet(tvHeadName);
        }
    }

    private void showBottomSheet(final TextView tvHeadName) {
        new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                .title("选择分类")
                .sheet(R.menu.gank_io_custom_bottom_sheet)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.item_gank_all:
                                mCustomType = "all";
                                tvHeadName.setText("全部");
                                break;
                            case R.id.item_gank_app:
                                mCustomType = "App";
                                tvHeadName.setText("App");
                                break;
                            case R.id.item_gank_android:
                                mCustomType = "Android";
                                tvHeadName.setText("Android");
                                break;
                            case R.id.item_gank_ios:
                                mCustomType = "iOS";
                                tvHeadName.setText("iOS");
                                break;
                            case R.id.item_gank_front:
                                mCustomType = "前端";
                                tvHeadName.setText("前端");
                                break;
                            case R.id.item_gank_video:
                                mCustomType = "休息视频";
                                tvHeadName.setText("休息视频");
                                break;
                            case R.id.item_gank_tuozhan:
                                mCustomType = "拓展资源";
                                tvHeadName.setText("拓展资源");
                                break;
                        }
                        mPresenter.customTypeChange(mCustomType);
                    }
                }).show();
    }

    /**
     * parent fab click
     */
    @Subscribe(code = RX_BUS_CODE_GANKIO_PARENT_FAB_CLICK)
    public void rxBusEvent() {
        //Logger.e("fab click.");
        showBottomSheet((TextView) headView.findViewById(R.id.tv_type_name));
    }

    /**
     * day页面查看更多事件触发
     */
    @Subscribe(code = RX_BUS_CODE_GANKIO_CUSTOM_TYPE)
    public void rxBusEvent(String customType) {
        //Logger.e("customType = " + customType);
        mCustomType = customType;
        mPresenter.customTypeChange(mCustomType);
    }
}
