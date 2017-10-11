package com.zyw.horrarndoo.yizhi.ui.activity.pic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.sdk.utils.FileUtils;
import com.zyw.horrarndoo.sdk.utils.ResourcesUtils;
import com.zyw.horrarndoo.yizhi.R;
import com.zyw.horrarndoo.yizhi.constant.BundleKeyConstant;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/9/27.
 * <p>
 */

public class ImageBrowseActivity extends BaseCompatActivity {
    @BindView(R.id.pv_pic)
    PhotoView pvPic;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pb_pic_browse)
    ProgressBar pbPicBrowse;

    private String mImageUrl;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mImageUrl = bundle.getString(BundleKeyConstant.ARG_KEY_IMAGE_BROWSE_URL);
        }
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
        if (mImageUrl.contains("gif")) {
            Glide.with(ImageBrowseActivity.this)
                    .load(mImageUrl)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new GlideDrawableImageViewTarget(pvPic) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<?
                                super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            //在这里添加一些图片加载完成的操作
                            pbPicBrowse.setVisibility(View.GONE);
                        }
                    });
            return;
        }

        Glide.with(ImageBrowseActivity.this)
                .load(mImageUrl)
                .fitCenter()
                .crossFade()
                .into(new GlideDrawableImageViewTarget(pvPic) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //在这里添加一些图片加载完成的操作
                        pbPicBrowse.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_browse;
    }

    @OnClick(R.id.fab_save_pic)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_save_pic:
                if (mImageUrl.contains("gif")) {
                    saveGifToLocal(mImageUrl);
                } else {
                    //将ImagelUrl MD5加密后作为文件名保存
                    saveImageToLocal(mImageUrl);
                }
                break;
            default:
                break;
        }
    }

    private void saveGifToLocal(final String fileName) {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                e.onNext(Glide.with(ImageBrowseActivity.this)
                        .load(mImageUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                e.onComplete();
            }
        }).compose(RxHelper.<File>rxSchedulerHelper())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        FileUtils.saveFile(ImageBrowseActivity.this, fileName, file, new
                                FileUtils.SaveResultCallback() {
                            @Override
                            public void onSavedSuccess() {
                                Snackbar.make(pvPic, "保存成功", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(ResourcesUtils.getColor(R
                                                .color.white)).show();
                            }

                            @Override
                            public void onSavedFailed() {
                                Snackbar.make(pvPic, "保存失败", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(ResourcesUtils.getColor(R
                                                .color.white)).show();
                            }
                        });
                    }
                });
    }

    /**
     * 保存图片到本地
     *
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
