package com.zyw.horrarndoo.yizhi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyw.horrarndoo.sdk.base.BaseCompatActivity;
import com.zyw.horrarndoo.sdk.helper.RxHelper;
import com.zyw.horrarndoo.yizhi.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class FlashActivity extends BaseCompatActivity {
    @BindView(R.id.ll_skip)
    LinearLayout llSkip;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;

    private boolean mIsCancle;
    private int mTime = 1;//方便测试，直接略过启动页面

    @Override
    protected void initView(Bundle savedInstanceState) {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(1)//计时次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return mTime - aLong;// 3-0 3-2 3-1
                    }
                })
                .compose(RxHelper.<Long>rxSchedulerHelper())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
//                        Logger.e("value = " + value);
                        tvCountDown.setText(String.valueOf(value));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        if (!mIsCancle) {
                            startActivity(new Intent(FlashActivity.this, MainActivity.class));
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
        setIsTransAnim(false);
        finish();
    }

    @OnClick(R.id.ll_skip)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_skip:
                mIsCancle = true;
                startActivity(new Intent(FlashActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
