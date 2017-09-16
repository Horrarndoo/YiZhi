package com.zyw.horrarndoo.sdk.utils;

import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Horrarndoo on 2017/8/31.
 * <p>
 * 读取文件工具类
 */
public class FileReadUtils {
    private static final String TAG = "FileReadUtils";

    /**
     * Convert byte[] to hex string.将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取升级数据
     * 以字符串数组形式返回
     *
     * @param fileName   文件名称
     * @param fileFolder 文件路径
     * @return
     * @throws IOException
     */
    public static String[] getUpdateData(String fileName, String fileFolder)
            throws IOException {
        String[] data = null;
        FileInputStream fis = new FileInputStream(getFile(fileName, fileFolder));
        DataInputStream dis = new DataInputStream(fis);
        int length = dis.available();
        Log.i(TAG, "data length=" + length);
        data = new String[length / 128]; //data = new String[length / 64];
        for (int j = 0; j < (length / 128); j++) {
            byte[] byte64 = new byte[128];
            dis.read(byte64);
            String str = "";
            String temp1 = "";
            //str = bytesToHexString(byte64);
            for (int i = 0; i < byte64.length; i++) {
                temp1 = String.format("%02X", byte64[i]);
                //temp1 = String.format("%02X", byte64[i]);//格式化为2位16进制大写字符，不足补0
                str += temp1;

            }
            //data[j] = String.format("%04X", j) + "," + str;
            data[j] = str;
        }
        dis.close();
        return data;
    }

    /**
     * 获取升级数据
     * 以字符串数组形式返回
     *
     * @param fileName   文件名称
     * @param fileFolder 文件路径
     * @return
     * @throws IOException
     */
    public static byte[] getUpdateByteData(String fileName, String fileFolder)
            throws IOException {
        byte[] data;
        FileInputStream fis = new FileInputStream(getFile(fileName, fileFolder));
        DataInputStream dis = new DataInputStream(fis);
        int length = dis.available();
        Log.i(TAG, "data length=" + length);
        data = new byte[length]; //data = new String[length / 64];
        dis.read(data, 0, data.length);

        dis.close();
        return data;
    }

    /**
     * 根据文件名称和路径，获取sd卡中的文件，以File形式返回byte
     */
    public static File getFile(String fileName, String folder)
            throws IOException {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File pathFile = new File(Environment.getExternalStorageDirectory()
                    + folder);
            // && !pathFile .isDirectory()
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            File file = new File(pathFile, fileName);
            return file;
        }
        return null;
    }

    /**
     * 根据文件名称和路径，获取sd卡中的文件，判断文件是否存在，存在返回true
     */
    public static Boolean checkFile(String fileName, String folder)
            throws IOException {

        File targetFile = getFile(fileName, folder);

        if (!targetFile.exists()) {
            return false;
        } else {
            return true;
        }
    }
}
