package com.zyw.horrarndoo.yizhi.ui.activity.pic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.utils.FileUtils;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick(R.id.fab_save_pic)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_save_pic:
                //将ImagelUrl MD5加密后作为文件名保存
                saveImageToLocal(mImageUrl);
                break;
            default:
                break;
        }
    }

    /**
     * 保存图片到本地
     * @param fileName
     */
    private void saveImageToLocal(String fileName) {
        PhotoView photoViewTemp = pvPic;
        if (photoViewTemp != null) {
            GlideBitmapDrawable glideBitmapDrawable = (GlideBitmapDrawable) photoViewTemp
                    .getDrawable();

            if (glideBitmapDrawable == null) {
                return;
            }

            Bitmap bitmap = glideBitmapDrawable.getBitmap();
            if (bitmap == null) {
                return;
            }

            FileUtils.saveBitmap(this, fileName, bitmap, new FileUtils.SaveResultCallback() {
                @Override
                public void onSavedSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(pvPic, "保存成功", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(ResourcesUtils.getColor(R.color.white))
                                    .show();
                        }
                    });
                }

                @Override
                public void onSavedFailed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(pvPic, "保存失败", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(ResourcesUtils.getColor(R.color.white))
                                    .show();
                        }
                    });
                }
            });
        }
    }
}
