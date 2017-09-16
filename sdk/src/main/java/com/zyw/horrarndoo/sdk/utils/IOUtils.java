package com.zyw.horrarndoo.sdk.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Horrarndoo on 2017/8/31.
 * <p>
 * IO流工具类
 */
public class IOUtils {
    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtils.e(e);
            }
        }
        return true;
    }
}
