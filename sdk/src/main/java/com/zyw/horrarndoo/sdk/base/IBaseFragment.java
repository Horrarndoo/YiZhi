package com.zyw.horrarndoo.sdk.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Horrarndoo on 2017/9/6.
 * <p>
 * BaseFragment接口
 */

public interface IBaseFragment extends IBaseView {
    /**
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     *                              true 目标fragment也出栈
     *                              <p>
     *                              false 出栈到目标fragment，目标fragment不出栈
     */
    void popToFragment(Class<?> targetFragmentClass, boolean includeTargetFragment);

    /**
     * 跳往新的Fragment
     *
     * @param supportFragment 要跳往的Fragment(继承自supportFragment)
     */
    void startNewFragment(@NonNull SupportFragment supportFragment);

    /**
     * 跳往新的Fragment,并出栈当前fragment
     *
     * @param supportFragment 要跳往的Fragment(继承自supportFragment)
     */
    void startNewFragmentWithPop(@NonNull SupportFragment supportFragment);

    /**
     * 跳往新的Fragment
     *
     * @param supportFragment 要跳往的Fragment(继承自supportFragment)
     * @param requestCode     requestCode
     */
    void startNewFragmentForResult(@NonNull SupportFragment supportFragment, int requestCode);

    /**
     * 设置Fragment返回Result
     *
     * @param resultCode resultCode
     * @param data       result data
     */
    void setOnFragmentResult(int resultCode, Bundle data);

    /**
     * 跳往新的Activity
     *
     * @param clz 要跳往的Activity
     */
    void startNewActivity(@NonNull Class<?> clz);

    /**
     * 跳往新的Activity
     *
     * @param clz    要跳往的Activity
     * @param bundle 携带的bundle数据
     */
    void startNewActivity(@NonNull Class<?> clz, Bundle bundle);

    /**
     * 跳往新的Activity
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode);

    /**
     * 返回当前fragment是否可见
     * @return 当前fragment是否可见
     */
    boolean isVisiable();

    /**
     * 返回当前fragment绑定的activity
     * @return activity
     */
    Activity getBindActivity();
}
