package com.zyw.horrarndoo.yizhi.ui.fragment.book.child.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.contract.book.tabs.BookCustomContract;
import com.zyw.horrarndoo.yizhi.model.bean.douban.book.BookItemBean;
import com.zyw.horrarndoo.yizhi.presenter.book.tabs.BookCustomPresenter;

import java.util.List;

import static com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant.ARG_KEY_DOUBAN_BOOK_TAGS;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookCustomFragment extends BaseRecycleFragment<BookCustomContract
        .BookCustomPresenter, BookCustomContract.IBookCustomModel> implements BookCustomContract
        .IBookCustomView {

    private String mBookTags = "文学";

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
        ((TextView) view.findViewById(R.id.tv_test)).setText(mBookTags);
    }

    @Override
    protected void onErrorViewClick(View view) {
    }

    @Override
    protected void showLoading() {
    }

    @Override
    public void updateContentList(List<BookItemBean> list) {
        Logger.e(list.toString());
    }

    @Override
    public void showNetworkError() {
    }

    @Override
    public void showLoadMoreError() {
    }

    @Override
    public void showNoMoreData() {
    }

    @Override
    public String getBookTags() {
        return mBookTags;
    }
}
