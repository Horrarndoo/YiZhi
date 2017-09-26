package com.zyw.horrarndoo.sdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Horrarndoo on 2017/8/31.
 * <p>
 * 读取文件工具类
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

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

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        }
        else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }
}
