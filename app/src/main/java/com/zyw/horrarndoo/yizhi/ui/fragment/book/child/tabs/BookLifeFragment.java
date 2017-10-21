package com.zyw.horrarndoo.yizhi.ui.fragment.book.child.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.fragment.BaseRecycleFragment;
import com.zyw.horrarndoo.yizhi.R;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookLifeFragment extends BaseRecycleFragment{

    public static BookLifeFragment newInstance() {
        Bundle args = new Bundle();
        BookLifeFragment fragment = new BookLifeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onErrorViewClick(View view) {

    }

    @Override
    protected void showLoading() {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_life;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
