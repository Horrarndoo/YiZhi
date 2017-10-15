package com.zyw.horrarndoo.sdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore
                    .Images.ImageColumns.DATA}, null, null, null);
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

    public static void copyFile(File sourcefile, File targetFile) {
        FileInputStream input = null;
        BufferedInputStream inbuff = null;
        FileOutputStream out = null;
        BufferedOutputStream outbuff = null;

        try {

            input = new FileInputStream(sourcefile);
            inbuff = new BufferedInputStream(input);

            out = new FileOutputStream(targetFile);
            outbuff = new BufferedOutputStream(out);

            byte[] b = new byte[1024 * 5];
            int len = 0;
            while ((len = inbuff.read(b)) != -1) {
                outbuff.write(b, 0, len);
            }
            outbuff.flush();
        } catch (Exception ex) {
        } finally {
            try {
                if (inbuff != null)
                    inbuff.close();
                if (outbuff != null)
                    outbuff.close();
                if (out != null)
                    out.close();
                if (input != null)
                    input.close();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * 保存图片到本机
     *
     * @param context            context
     * @param fileName           文件名
     * @param file               file
     * @param saveResultCallback 保存结果callback
     */
    public static void saveImage(final Context context, final String fileName, final File file,
                                final SaveResultCallback saveResultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(Environment.getExternalStorageDirectory(), "yizhi");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String saveFileName = "yizhi_pic";
                if (fileName.contains(".png") || fileName.contains(".gif")) {
                    String fileFormat = fileName.substring(fileName.lastIndexOf("."));
                    saveFileName = MD5Utils.getMD5("yizhi_pic" + fileName) + fileFormat;
                } else {
                    saveFileName = MD5Utils.getMD5("yizhi_pic" + fileName) + ".png";
                }
                saveFileName = saveFileName.substring(20);//取前20位作为SaveName
                File savefile = new File(appDir, saveFileName);
                try {
                    InputStream is = new FileInputStream(file);
                    FileOutputStream fos = new FileOutputStream(savefile);
                    byte[] buffer = new byte[1024 * 1024];//1M缓冲区
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(savefile);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }

    /**
     * 保存Bitmap到本机
     *
     * @param context            context
     * @param fileName           bitmap文件名
     * @param bmp                bitmap
     * @param saveResultCallback 保存结果callback
     */
    public static void saveBitmap(final Context context, final String fileName, final Bitmap bmp,
                                  final SaveResultCallback
                                          saveResultCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(Environment.getExternalStorageDirectory(), "yizhi");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                //                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                // 设置以当前时间格式为图片名称
                String saveFileName = MD5Utils.getMD5("yizhi_pic" + fileName) + ".png";
                saveFileName = saveFileName.substring(20);//取前20位作为SaveName
                File file = new File(appDir, saveFileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }

    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }
}


