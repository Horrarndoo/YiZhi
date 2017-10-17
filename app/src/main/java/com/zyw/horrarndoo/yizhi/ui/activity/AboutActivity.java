package com.zyw.horrarndoo.yizhi.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zyw.horrarndoo.sdk.base.activity.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Horrarndoo on 2017/9/22.
 * <p>
 */

public class AboutActivity extends BaseCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, "关于");
        toolbar.setTitleTextColor(ResourcesUtils.getColor(R.color.md_white));
    }

    @OnClick(R.id.cv_author)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.cv_author:
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://github.com/Horrarndoo"));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent); //启动浏览器
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
}
