package com.zyw.horrarndoo.yizhi.model.bean.gankio;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 * GnakIo每日数据item Bean
 */

public class GankIoDayItemBean implements MultiItemEntity{
    public static final int GANK_IO_DAY_ITEM_DAY_NORMAL = 1;
    public static final int GANK_IO_DAY_ITEM_DAY_REFESH = 2;
    public int itemType = 1;

    // 存储单独设置的值，用来显示title
    @SerializedName("type_title")
    private String type_title;
    // 随机图URL
    @SerializedName("image_url")
    private String image_url;

    @SerializedName("_id")
    private String _id;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("desc")
    private String desc;
    @SerializedName("publishedAt")
    private String publishedAt;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    @SerializedName("used")
    private boolean used;
    @SerializedName("who")
    private String who;

    @SerializedName("source")
    private String source;
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

    public String getSource() {
        return source;
    }

    public List<String> getImages() {
        return images;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    @Override
    public String toString() {
        return "GankIoDayResultBean{" +
                "type_title='" + type_title + '\'' +
                ", image_url='" + image_url + '\'' +
                ", _id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", source='" + source + '\'' +
                ", images=" + images +
                '}';
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
