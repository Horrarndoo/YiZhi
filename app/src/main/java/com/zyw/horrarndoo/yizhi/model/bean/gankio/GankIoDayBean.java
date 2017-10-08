package com.zyw.horrarndoo.yizhi.model.bean.gankio;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoDayBean implements Serializable {

    @SerializedName("error")
    private boolean error;
    @SerializedName("results")
    private ResultsBean results;
    @SerializedName("category")
    private List<String> category;

    public static class ResultsBean {
        /**
         * _id : 56cc6d23421aa95caa707a69
         * createdAt : 2015-08-06T07:15:52.65Z
         * desc : 类似Link Bubble的悬浮式操作设计
         * images : ["http://img.gank.io/2f0b6c5f-6de7-4ba3-94ad-98bf721ee447"]
         * source : web
         * publishedAt : 2015-08-07T03:57:48.45Z
         * type : Android
         * url : https://github.com/recruit-lifestyle/FloatingView
         * used : true
         * who : mthli
         */

        @SerializedName("Android")
        private List<GankIoDailyBean> Android;

        @SerializedName("iOS")
        private List<GankIoDailyBean> iOS;

        @SerializedName("前端")
        private List<GankIoDailyBean> front;

        @SerializedName("App")
        private List<GankIoDailyBean> app;

        @SerializedName("休息视频")
        private List<GankIoDailyBean> restMovie;

        @SerializedName("拓展资源")
        private List<GankIoDailyBean> resource;

        @SerializedName("瞎推荐")
        private List<GankIoDailyBean> recommend;

        @SerializedName("福利")
        private List<GankIoDailyBean> welfare;


        public List<GankIoDailyBean> getAndroid() {
            return Android;
        }

        public List<GankIoDailyBean> getiOS() {
            return iOS;
        }

        public List<GankIoDailyBean> getRestMovie() {
            return restMovie;
        }

        public List<GankIoDailyBean> getResource() {
            return resource;
        }

        public List<GankIoDailyBean> getRecommend() {
            return recommend;
        }

        public List<GankIoDailyBean> getWelfare() {
            return welfare;
        }

        public List<GankIoDailyBean> getFront() {
            return front;
        }

        public List<GankIoDailyBean> getApp() {
            return app;
        }
    }

    public boolean isError() {
        return error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public List<String> getCategory() {
        return category;
    }
}
