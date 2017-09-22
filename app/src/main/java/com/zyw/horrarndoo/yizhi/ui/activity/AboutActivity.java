package com.zyw.horrarndoo.yizhi.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.yizhi.R;

import butterknife.BindView;

/**
 * Created by Horrarndoo on 2017/9/22.
 * <p>
 */

public class AboutActivity extends BaseCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {

        toolbar.setTitle("关于");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(com.zyw.horrarndoo.sdk.R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
}
