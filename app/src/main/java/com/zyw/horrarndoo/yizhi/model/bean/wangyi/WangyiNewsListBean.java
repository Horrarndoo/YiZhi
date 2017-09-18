package com.zyw.horrarndoo.yizhi.model.bean.wangyi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 */
public class WangyiNewsListBean {

    @SerializedName("T1348647909107")
    ArrayList<WangyiNewsItemBean> newsList;

    public ArrayList<WangyiNewsItemBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(ArrayList<WangyiNewsItemBean> newsList) {
        this.newsList = newsList;
    }
}
