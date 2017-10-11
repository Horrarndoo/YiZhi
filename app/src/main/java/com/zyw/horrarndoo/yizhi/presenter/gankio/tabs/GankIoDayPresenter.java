package com.zyw.horrarndoo.yizhi.presenter.gankio.tabs;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.zyw.horrarndoo.yizhi.contract.gankio.tabs.GankIoDayContract;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayBean;
import com.zyw.horrarndoo.yizhi.model.bean.gankio.GankIoDayItemBean;
import com.zyw.horrarndoo.yizhi.model.gankio.tabs.GankIoDayModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public class GankIoDayPresenter extends GankIoDayContract.GankIoDayPresenter {
    private String mYear = "2016";
    private String mMonth = "11";
    private String mDay = "24";

    @NonNull
    public static GankIoDayPresenter newInstance() {
        return new GankIoDayPresenter();
    }

    @Override
    public void loadLatestList() {
        if (mIModel == null || mIView == null)
            return;
        //GankIo每日数据大部分时间返回空值，这里直接写死一个日期数据
        mRxManager.register(mIModel.getGankIoDayList(mYear, mMonth, mDay).subscribe(new Consumer<GankIoDayBean>() {
            @Override
            public void accept(GankIoDayBean gankIoDayBean) throws Exception {
                if (mIView == null)
                    return;
                List<GankIoDayItemBean> list = new ArrayList<>();
                list.add(gankIoDayBean.getResults().getAndroid().get(0));
                list.add(gankIoDayBean.getResults().getiOS().get(0));
                list.add(gankIoDayBean.getResults().getFront().get(0));
                list.add(gankIoDayBean.getResults().getWelfare().get(0));
                list.add(gankIoDayBean.getResults().getRestMovie().get(0));
                mIView.updateContentList(list);
            }
        }));
    }

    @Override
    public void loadMoreList() {
        //每日数据没有更多
    }

    @Override
    public void onItemClick(int position, GankIoDayItemBean item) {
        Logger.e(item.toString());
    }

    @Override
    public void onMoreClick(int position, GankIoDayItemBean item) {
//        Logger.e(item.toString());
    }

    @Override
    public GankIoDayContract.IGankIoDayModel getModel() {
        return GankIoDayModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
