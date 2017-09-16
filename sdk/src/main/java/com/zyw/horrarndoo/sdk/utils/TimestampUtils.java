package com.zyw.horrarndoo.sdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimestampUtils {
    /**
     * 获取当前的时间戳，时区为北京
     *
     * @return
     */
    public static String getCurrentTimestamp() {
        //时间戳的格式必须为 yyyy-MM-dd HH:mm:ss
        String timestamp = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timestamp = format.format(new Date());
        return timestamp;
    }

    /**
     * 获取当前的时间戳，时区为北京
     *
     * @return
     */
    public static String getCurrentTime(long times) {
        //时间戳的格式必须为 yyyy-MM-dd HH:mm:ss
        Date date = new Date(Long.valueOf(times));
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        LogUtils.e("timetimetimetimetimetimetime为：" + time);

        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        return time;
    }

    //法国时间：东一区
    public static String getDateTimeByGMT(int timeZone) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (timeZone) {
            case 1:
                dff.setTimeZone(TimeZone.getTimeZone("GMT+1"));
                break;
            case 8:
                dff.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                //LogUtils.i("采用东八区时区");
                break;
        }

        String time = dff.format(new Date());
        //LogUtils.i("东八区时区时间为--》》" + time);
        return time;
    }
}
