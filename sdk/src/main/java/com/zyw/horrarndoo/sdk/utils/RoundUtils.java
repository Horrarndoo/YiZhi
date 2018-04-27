package com.zyw.horrarndoo.sdk.utils;

/**
 * Created by Horrarndoo on 2018/1/23.
 * <p>
 * 四舍五入工具类
 */

public class RoundUtils {
    /**
     * 0.5精度四舍五入
     * <p>
     * 例
     * 3.0->3.0
     * 3.1->3.0
     * 3.5->3.5
     * 3.6->4.0
     *
     * @param a 需要处理的值 a >= 0
     * @return 按0.5精度四舍五入处理后的数值
     */
    public static float roundCompat(float a) {
        float temp = a * 10;

        if (temp % 10 == 5) {
            a = Math.round(a) - 0.5f;
        } else if (temp % 10 > 5) {
            a = Math.round(a);
        } else if (temp <= 5) {
            a = Math.round(a);
        } else {
            a = Math.round(a) + 0.5f;
        }
        return a;
    }
}
