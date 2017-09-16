package com.zyw.horrarndoo.sdk;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 */

public class RxManager {
    private static RxManager rxManager;
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察源
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();// 管理订阅者者
    public RxBus mRxBus = RxBus.getInstance();

    public RxManager() {
    }

    public static RxManager getInstance() {
        if (rxManager == null) {
            synchronized (RxManager.class) {
                if (rxManager == null)
                    rxManager = new RxManager();
            }
        }
        return rxManager;
    }

    public void on(final String eventName, Consumer<Object> consumer) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        mCompositeDisposable.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void add(Disposable d) {
        mCompositeDisposable.add(d);
    }

    public void clear() {
        rxManager = null;
        mCompositeDisposable.dispose();// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet())
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}