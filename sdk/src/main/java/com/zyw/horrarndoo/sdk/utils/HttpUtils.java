package com.zyw.horrarndoo.sdk.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.WebSettings;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 * HttpUtils 主要用于获取UserAgent
 */

public class HttpUtils {
    /**
     * 获取UserAgent
     *
     * @return UserAgent
     */
    @NonNull
    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(AppUtils.getContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String makeUA() {
        final String ua = Build.BRAND + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE;
        return ua;
    }
}
