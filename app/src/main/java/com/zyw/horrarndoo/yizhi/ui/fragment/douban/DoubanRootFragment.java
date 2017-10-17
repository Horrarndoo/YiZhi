package com.zyw.horrarndoo.yizhi.ui.fragment.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.fragment.BaseCompatFragment;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.ui.fragment.douban.child.DoubanFragment;

/**
 * Created by Horrarndoo on 2017/9/23.
 * <p>
 */

public class DoubanRootFragment extends BaseCompatFragment {
    public static DoubanRootFragment newInstance() {
        Bundle args = new Bundle();
        DoubanRootFragment fragment = new DoubanRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //Logger.e("onLazyInitView");
        //加载子fragment
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, DoubanFragment.newInstance());
        } else {  // 这里可能会出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(DoubanFragment.class) == null) {
                loadRootFragment(R.id.fl_container, DoubanFragment.newInstance());
            }
        }
    }
}
