package com.zyw.horrarndoo.yizhi.ui.activity.pic;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;

import butterknife.BindView;

import static com.zyw.horrarndoo.yizhi.constant.IntentKeyConstant.INTENT_KEY_IMAGE_URL;

/**
 * Created by Horrarndoo on 2017/9/27.
 * <p>
 */

public class ImageBrowseActivity extends BaseCompatActivity {
    @BindView(R.id.pv_pic)
    PhotoView pvPic;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String mImageUrl;

    @Override
    protected void initData() {
        super.initData();
        mImageUrl = getIntent().getStringExtra(INTENT_KEY_IMAGE_URL);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolbar.setTitle("图片");
        toolbar.setTitleTextColor(ResourcesUtils.getColor(R.color.md_white));
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        pvPic.enable();
        Glide.with(ImageBrowseActivity.this).load(mImageUrl).fitCenter().crossFade().into
                (pvPic);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_browse;
    }
}
