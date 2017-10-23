package com.zyw.horrarndoo.yizhi.ui.fragment.book.child.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.adapter.BookCustomAdapter;
import com.zyw.horrarndoo.yizhi.contract.book.tabs.BookCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookItemBean;
import com.zyw.horrarndoo.yizhi.presenter.book.tabs.BookCustomPresenter;

import java.util.List;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant.ARG_KEY_DOUBAN_BOOK_TAGS;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookCustomFragment extends BaseRecycleFragment<BookCustomContract
        .BookCustomPresenter, BookCustomContract.IBookCustomModel> implements BookCustomContract
        .IBookCustomView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_book)
    RecyclerView rvBook;

    private String mBookTags = "文学";
    private BookCustomAdapter mBookCustomAdapter;

    public static BookCustomFragment newInstance(String bookTags) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY_DOUBAN_BOOK_TAGS, bookTags);
        BookCustomFragment fragment = new BookCustomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestBookList();
    }

    @Override
    public void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            mBookTags = args.getString(ARG_KEY_DOUBAN_BOOK_TAGS);
        }
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return BookCustomPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_custom;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //初始化一个空list的adapter，网络错误时使用，第一次加载到数据时重新初始化adapter并绑定recycleview
        mBookCustomAdapter = new BookCustomAdapter(R.layout.item_book_custom);
        rvBook.setAdapter(mBookCustomAdapter);
        rvBook.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void updateContentList(List<BookItemBean> list) {
        Logger.e(list.toString());
        if (mBookCustomAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mBookCustomAdapter.addData(list);
        }
    }

    @Override
    public void showNetworkError() {
        mBookCustomAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mBookCustomAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mBookCustomAdapter.loadMoreEnd(true);
    }

    @Override
    public String getBookTags() {
        return mBookTags;
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestBookList();
    }

    @Override
    protected void showLoading() {
        mBookCustomAdapter.setEmptyView(loadingView);
    }

    @Override
    public void onLoadMoreRequested() {
        mBookCustomAdapter.loadMoreComplete();
        mPresenter.loadMoreBookList();
    }

    private void initRecycleView(List<BookItemBean> list) {
        mBookCustomAdapter = new BookCustomAdapter(R.layout.item_book_custom, list);
        mBookCustomAdapter.setOnLoadMoreListener(this, rvBook);
        mBookCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (BookItemBean) adapter.getItem(position),
                        (ImageView) view.findViewById(R.id.iv_item_image));
            }
        });
        rvBook.setAdapter(mBookCustomAdapter);
    }
}
