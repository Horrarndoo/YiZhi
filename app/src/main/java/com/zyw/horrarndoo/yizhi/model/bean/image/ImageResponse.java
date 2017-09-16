package com.zyw.horrarndoo.yizhi.model.bean.image;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */
public class ImageResponse {
    @SerializedName("data")
    private ImageData data;

    public ImageData getData() {
        return data;
    }

    public void setData(ImageData data) {
        this.data = data;
    }
}
