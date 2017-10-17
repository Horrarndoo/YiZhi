package com.zyw.horrarndoo.sdk.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreferences工具类封装
 */
public class SpUtils {
    private static SharedPreferences sp;
    private static String mPreferencesName = "share_preference_default";

    /**
     * 设置preferencesName
     *
     * @param preferencesName preferencesName
     */
    private void setPreferencesName(String preferencesName) {
        mPreferencesName = preferencesName;
    }

    /**
     * 写入boolean变量至sp中
     *
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putBoolean(Context ctx, String key, boolean value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 读取boolean标示从sp中
     *
     * @param ctx      上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 写入String变量至sp中
     *
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putString(Context ctx, String key, String value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    /**
     * 读取String标示从sp中
     *
     * @param ctx      上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static String getString(Context ctx, String key, String defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }


    /**
     * 写入int变量至sp中
     *
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值
     */
    public static void putInt(Context ctx, String key, int value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 读取int标示从sp中
     *
     * @param ctx      上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static int getInt(Context ctx, String key, int defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }


    /**
     * 从sp中移除指定节点
     *
     * @param ctx 上下文环境
     * @param key 需要移除节点的名称
     */
    public static void remove(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(mPreferencesName, Context
                    .MODE_PRIVATE);
        }
        sp.edit().remove(key).apply();
    }

    /**
     * 保存List
     *
     * @param key      sp key值
     * @param datalist list
     * @param <T>      item 类型
     */
    public static <T> void setDataList(String key, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        SpUtils.putString(AppUtils.getContext(), key, strJson);
    }

    /**
     * 获取List
     *
     * @param key sp key值
     * @param <T> item 类型
     * @return list
     */
    public static <T> List<T> getDataList(String key, Class<T> cls) {
        List<T> datalist = new ArrayList<T>();
        String strJson = SpUtils.getString(AppUtils.getContext(), key, null);

        if (null == strJson) {
            return datalist;
        }

        try {
            Gson gson = new Gson();
            //        datalist = gson.fromJson(strJson, new TypeToken<List<T>>(){}.getType());
            JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
            for (final JsonElement elem : array) {
                datalist.add(gson.fromJson(elem, cls));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return datalist;
    }

    public static int getThemeIndex(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("ThemeIndex", 5);
    }

    public static void setThemeIndex(Context context, int index) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt("ThemeIndex", index).apply();
    }

    public static boolean getNightModel(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("pNightMode", false);
    }

    public static void setNightModel(Context context, boolean nightModel) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("pNightMode", nightModel).apply();
    }
}
