package com.zyw.horrarndoo.yizhi.model.bean.image;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */
public class ImageData {
    @SerializedName("images")
    private ArrayList<ImageItem> images;

    public ArrayList<ImageItem> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageItem> images) {
        this.images = images;
    }
}
