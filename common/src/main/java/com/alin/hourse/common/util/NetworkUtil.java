package com.alin.hourse.common.util;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.alin.hourse.common.receiver.NetworkChangeReceiver;

import java.lang.reflect.Method;

/**
 * @创建者 hailin
 * @创建时间 2017/8/22 11:58
 * @描述 ${网络状态接听}.
 */

public class NetworkUtil {
    private final static String TAG = NetworkUtil.class.getSimpleName();
    private static final NetworkUtil INSTANCE = new NetworkUtil();
    private NetworkChangeReceiver mChangeReceiver;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private int i = 0;
    public static NetworkUtil getInstance(){
        return INSTANCE;
    }

    /**
     * 注册网络监听
     * */
    public void netWorkWatchState(final Context context){
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {     // >5.0系统
//            //有网可以用
//            mNetworkCallback = new ConnectivityManager.NetworkCallback(){
//                @Override
//                public void onAvailable(Network network) {      //当前网络获取
//                    super.onAvailable(network);
//                    EventBus.getDefault().post(new NetworkChangeEvent(isNetworkConnected(context)));
//                    LogUtil.e(TAG,"onAvailable....");
//                }
//
//                @Override
//                public void onLost(Network network) {      //当前网络失去
//                    super.onLost(network);
////                    EventBus.getDefault().post(new NetworkChangeEvent(NetworkState.NETWORK_NONE));
//                    EventBus.getDefault().post(new NetworkChangeEvent(isNetworkConnected(context)));
//                    LogUtil.e(TAG,"onLost....");
//                }
//            };
//
//            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            manager.registerNetworkCallback(new NetworkRequest.Builder().build(), mNetworkCallback);
//        }else {
//            mChangeReceiver = new NetworkChangeReceiver();
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//            context.registerReceiver(new NetworkChangeReceiver(),filter);
//        }

        mChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(new NetworkChangeReceiver(),filter);
    }

    /**
     * 反注册网络监听
     * */
    public void unregisterNetworkReceiver(Context context){
        if (mChangeReceiver != null) {
            context.unregisterReceiver(mChangeReceiver);
            mChangeReceiver = null;
        }else {
            if (mNetworkCallback != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                manager.unregisterNetworkCallback(mNetworkCallback);
                mNetworkCallback = null;
            }
        }
    }

    /**
     * 获取ConnectivityManager
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取ConnectivityManager
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }



    public NetworkState checkNetworkState(Context context){
        NetworkState networkState = null;
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {    //有网络
                boolean connected = networkInfo.isConnected();
                boolean available = networkInfo.isAvailable();
                if (networkInfo.isConnected()) {
                    int networkType = networkInfo.getType();
                    if (networkType == ConnectivityManager.TYPE_WIFI) {     //wifi
                        networkState = NetworkState.NETWORK_WIFI;
                        LogUtil.e(TAG,"wifi.......connected=" + connected + ",available=" + available);
                    }else if (networkType == ConnectivityManager.TYPE_MOBILE){      //4g、3g、2g
                        networkState = getNetworkType(context);
                        LogUtil.e(TAG,"mobile.......connected=" + connected + ",available=" + available);
                    }
                }else {   //无网络
                    LogUtil.e(TAG,"none...111....connected=" + connected + ",available=" + available);
                }

            }else {     //无网络
                networkState = NetworkState.NETWORK_NONE;
                LogUtil.e(TAG,"none...222....");
            }
        }else {
            networkState = NetworkState.NETWORK_NONE;
            LogUtil.e(TAG,"none...33333....");
        }

        return networkState;
    }

    /**
     * 判断当前网络是否已经连接
     *
     * @param context 上下文
     * @return 当前网络是否已经连接。false：尚未连接
     */
    public static boolean isConnectedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED;
    }

    /**
     * 获取当前网络的状态
     *
     * @param context 上下文
     * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
     */
    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        NetworkInfo networkInfo
                = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getState() : null;
    }

    /**
     * 检测网络是否为可用状态
     */
    public static boolean isAvailable(Context context) {
        return isWifiAvailable(context) || (isMobileAvailable(context) && isMobileEnabled(context));
    }

    /**
     * 判断是否有可用状态的Wifi，以下情况返回false：
     * 1. 设备wifi开关关掉;
     * 2. 已经打开飞行模式；
     * 3. 设备所在区域没有信号覆盖；
     * 4. 设备在漫游区域，且关闭了网络漫游。
     *
     * @return boolean wifi为可用状态（不一定成功连接，即Connected）即返回ture
     */
    public static boolean isWifiAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.getType() == ConnectivityManager.TYPE_WIFI) { return net.isAvailable(); }
            }
        }
        return false;
    }

    /**
     * 设备是否打开移动网络开关
     *
     * @return boolean 打开移动网络返回true，反之false
     */
    public static boolean isMobileEnabled(Context context) {
        try {
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(getConnectivityManager(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 反射失败，默认开启
        return true;
    }

    /**
     *
     * @param context
     * @return true有可用网络
     */
    public static boolean isMobileAvailable(Context context) {
        NetworkInfo[] nets = getConnectivityManager(context).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.getType() == ConnectivityManager.TYPE_MOBILE) { return net.isAvailable(); }
            }
        }
        return false;
    }

    /**
     * 打开网络设置界面
     *
     * @param activity Activity
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * GPRS    2G(2.5) General Packet Radia Service 114kbps
     * EDGE    2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * UMTS    3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
     * CDMA    2G 电信 Code Division Multiple Access 码分多址
     * EVDO_0  3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * 1xRTT   2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * HSDPA   3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * HSUPA   3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * HSPA    3G (分HSDPA,HSUPA) High Speed Packet Access
     * IDEN    2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * EVDO_B  3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * LTE     4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * EHRPD   3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * HSPAP   3G HSPAP 比 HSDPA 快些
     *
     * @return {@link  NetworkState}
     */
    public static NetworkState getNetworkType(Context context) {
        int type = getConnectedTypeINT(context);
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
                return NetworkState.NETWORK_WIFI;
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                int teleType = getTelephonyManager(context).getNetworkType();
                switch (teleType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkState.NETWORK_Net2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkState.NETWORK_Net3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkState.NETWORK_Net4G;
                    default:
                        return NetworkState.NETWORK_NONE;
                }
            default:
                return NetworkState.NETWORK_NONE;
        }
    }

    /**
     * get connected network type by {@link ConnectivityManager}
     *
     * such as WIFI, MOBILE, ETHERNET, BLUETOOTH, etc.
     *
     * @return {@link ConnectivityManager#TYPE_WIFI}, {@link ConnectivityManager#TYPE_MOBILE},
     * {@link ConnectivityManager#TYPE_ETHERNET}...
     */
    public static int getConnectedTypeINT(Context context) {
        NetworkInfo net = getConnectivityManager(context).getActiveNetworkInfo();
        if (net != null) {
            return net.getType();
        }
        return -1;
    }

    public  enum NetworkState{
//        None(Config.NETWORK_NONE),Wifi(Config.NETWORK_WIFI),Mobile(Config.NETWORK_MOBILE);
        NETWORK_NONE,NETWORK_WIFI,NETWORK_MOBILE,NETWORK_Net2G,NETWORK_Net3G,NETWORK_Net4G;
        int networkState;

        public void setNetworkState(int networkState){
            this.networkState = networkState;
        }



    }

}
