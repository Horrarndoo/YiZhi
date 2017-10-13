package com.zyw.horrarndoo.yizhi.model.bean.gankio;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */
public class GankIoCustomItemBean implements Serializable, MultiItemEntity {
    /**
     * 普通布局，带略缩图
     */
    public static final int GANK_IO_DAY_ITEM_CUSTOM_NORMAL = 1;
    /**
     * 图片布局，福利
     */
    public static final int GANK_IO_DAY_ITEM_CUSTOM_IMAGE = 2;
    /**
     * 无图布局
     */
    public static final int GANK_IO_DAY_ITEM_CUSTOM_NO_IMAGE = 3;
    public int itemType = 1;

    @SerializedName("_id")
    private String _id;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("desc")
    private String desc;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("source")
    private String source;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    @SerializedName("used")
    private boolean used;
    @SerializedName("who")
    private String who;
    @SerializedName("images")
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }

    @Override
    public String toString() {
        return "ResultsBean{" +
                "who='" + who + '\'' +
                ", used=" + used +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }

    public List<String> getImages() {
        return images;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
