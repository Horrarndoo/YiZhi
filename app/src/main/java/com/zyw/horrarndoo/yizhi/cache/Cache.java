package com.zyw.horrarndoo.yizhi.cache;

import com.zyw.horrarndoo.sdk.utils.SpUtils;
import com.zyw.horrarndoo.yizhi.model.bean.douban.moviechild.SubjectsBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/17.
 * <p>
 */

public class Cache {
    /**
     * 获取豆瓣电影hot cache
     *
     * @return 豆瓣电影hot cache
     */
    public static List<SubjectsBean> getDoubanHotCache() {
        return SpUtils.getDataList("douban_hot_cache", SubjectsBean.class);
    }

    /**
     * 保存豆瓣电影hot cache
     */
    public static void saveDoubanHotCache(List<SubjectsBean> list) {
        SpUtils.setDataList("douban_hot_cache", list);
    }
}
