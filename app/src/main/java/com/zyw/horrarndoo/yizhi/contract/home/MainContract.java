package com.zyw.horrarndoo.yizhi.contract.home;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseFragment;
import com.zyw.horrarndoo.sdk.base.IBaseModel;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 * 主页Contract
 */

public interface MainContract {
    //主页接口
    abstract class MainPresenter extends BasePresenter<IMainModel, IMainView> {
        public abstract void getTabList();
    }

    interface IMainModel extends IBaseModel {
        String[] getTabs();
    }

    interface IMainView extends IBaseFragment {
        void showTabList(String[] tabs);
    }
}
