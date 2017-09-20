package com.zyw.horrarndoo.yizhi.contract.detail;

import com.zyw.horrarndoo.sdk.base.IBaseActivity;

/**
 * Created by Horrarndoo on 2017/9/20.
 * <p>
 */

public interface BaseDetailContract {
    interface IBaseDetailView extends IBaseActivity {
        /**
         * 显示网络错误
         */
        void showNetworkError();
    }
}
