package com.zyw.horrarndoo.yizhi.ui.activity;

import android.os.Bundle;

import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class FlashActivity extends BaseCompatActivity {

    private boolean mIsCancle;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxHelper.<Long>rxSchedulerHelper())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long l) throws Exception {
                        if (!mIsCancle) {
                            startActivity(MainActivity.class);
                            finish();
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public void onBackPressedSupport() {
        mIsCancle = true;
        finish();
    }
}
