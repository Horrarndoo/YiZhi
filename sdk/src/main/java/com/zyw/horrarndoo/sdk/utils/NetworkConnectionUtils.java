package com.zyw.horrarndoo.sdk.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.IOException;
import java.util.List;

import static com.zyw.horrarndoo.sdk.utils.LogUtils.e;

/**
 * Created by Horrarndoo on 2017/8/31.
 * <p>
 * Wifi连接工具类
 */
public class NetworkConnectionUtils {
    private final static String TAG = "NetworkConnectionUtils";

    public NetworkConnectionUtils() {
    }

    /**
     * 连接指定
     *
     * @param manager
     * @param wifiSSID
     * @return
     */
    public static boolean connectToSocketWifi(WifiManager manager, String wifiSSID) {
        LogUtils.i("要连接的socket wifi====>" + wifiSSID);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + wifiSSID + "\"";
        wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
        wifiConfiguration.wepKeys[0] = "\"" + "\""; //小米手机MIUI7/华为EMUI4.1 需要webKey

        int networkId = manager.addNetwork(wifiConfiguration);

        if (networkId != -1) {
            manager.enableNetwork(networkId, true);
            e("连接设备成功");
            return true;
        } else {
            e("第一次连接失败，尝试第二次。");
            WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
            wifiConfiguration2.SSID = "\"" + wifiSSID + "\"";
            //wifiConfiguration.wepKeys[0] = "\"" + "\"";//去掉webKey  //小米手机MIUI8不能有webKey
            wifiConfiguration2.allowedKeyManagement.set(KeyMgmt.NONE);
            networkId = manager.addNetwork(wifiConfiguration2);
            if (networkId != -1) {
                manager.enableNetwork(networkId, true);
                e("连接设备成功");
                return true;
            }
            e("连接设备失败");
        }
        return false;
    }

    /**
     * 获取要连接的wifi节点各个配置选项的加密类型
     *
     * @param ssid
     * @return wifiConfiguration
     */
    public static WifiConfiguration getWifiConfiguration(WifiManager manager, String ssid, String
            password) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + ssid + "\"";

        List<ScanResult> list = manager.getScanResults();
        for (ScanResult scResult : list) {
            if (ssid.equals(scResult.SSID)) {
                String capabilities = scResult.capabilities;
                LogUtils.i("capabilities=" + capabilities);
                if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
                    wifiConfiguration.preSharedKey = "\"" + password + "\"";
                    LogUtils.i("wep");
                } else if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
                    wifiConfiguration.preSharedKey = "\"" + password + "\"";
                    LogUtils.i("wpa");
                } else {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
                    LogUtils.i("none");
                }
            }
        }
        return wifiConfiguration;
    }

    /**
     * 给温控器成功发送联网命令后，连接温控器连接的wifi节点
     *
     * @param context  上下文对象
     * @param ssid     ssid
     * @param password 密码
     */
    public static void connectWifiSSID(Context context, WifiManager manager, String ssid, String
            password) {
        e("reSetNetwork----------连接设备连入的路由---" + ssid);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            e("当前手机系统>=Android6.0，采取兼容模式");
            new WifiAutoConnectManager(manager).connect(ssid, password, WifiAutoConnectManager
                    .getCipherType(context, ssid));
        } else {
            int networkId = manager.addNetwork(getWifiConfiguration(manager, ssid, password));
            if (networkId != -1) {
                manager.enableNetwork(networkId, true);
            }
        }
    }

    /**
     * 格式化RouterSSID
     *
     * @param strRouterSSID 要格式化的当前连接的路由ssid
     * @return 去除"\"后的RouterSSID字符串
     */
    public static String formatRouterSSID(String strRouterSSID) {
        //e("formate routerSSID before---" + strRouterSSID);
        if (strRouterSSID.contains("\"")) {
            strRouterSSID = strRouterSSID.replaceAll("\"", "");
            //e("formate routerSSID after---" + strRouterSSID);
        }
        return strRouterSSID;
    }

    /**
     * Ping
     * 用于确定手机是否已经连接上指定设备ip地址
     */
    public static boolean pingTest(String IPOrDomainName) {

        boolean isSuccess = false;
        int status;
        String result = "failed";
        Process p;
        try {
            p = Runtime.getRuntime().exec("ping -c 1 " + IPOrDomainName);//
            // m_strForNetAddress是输入的网址或者Ip地址
            status = p.waitFor();// status 只能获取是否成功，无法获取更多的信息

            if (status == 0) {
                result = "success";
                isSuccess = true;
            }

        } catch (IOException | InterruptedException e) {
            e(e);
        }
        LogUtils.d("Ping result = " + result);
        return isSuccess;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有网络
     *
     * @return 返回值
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;

    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction(Intent.ACTION_VIEW);
        activity.startActivityForResult(intent, requestCode);
    }
}
