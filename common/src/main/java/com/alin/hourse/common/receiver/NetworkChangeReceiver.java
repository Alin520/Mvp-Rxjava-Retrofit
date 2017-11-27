package com.alin.hourse.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.alin.hourse.common.util.NetworkUtil;
import com.alin.hourse.common.util.ToastUtil;


/**
 * @创建者 hailin
 * @创建时间 2017/8/22 22:32
 * @描述 ${广播接受者  网络变化}.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkUtil networkUtil = NetworkUtil.getInstance();
            NetworkUtil.NetworkState networkState = networkUtil.checkNetworkState(context);
            switch (networkState){
                case NETWORK_WIFI:
                    ToastUtil.showCustomToast(context,"this is wifi network now...",0);
//                    TODO:  WIFI
                    break;
                case NETWORK_Net2G:
                    ToastUtil.showCustomToast(context,"this network is 2g now...",0);
                case NETWORK_Net3G:
                    ToastUtil.showCustomToast(context,"this network is 3g now...",0);
                case NETWORK_Net4G:
                    ToastUtil.showCustomToast(context,"this network is 4g now...",0);
//                    TODO:  移动网络

                    break;
                case NETWORK_NONE:
//                    TODO:  没有网络
                    ToastUtil.showCustomToast(context,"this is not network now...",0);
                    break;

            }
        }

    }
}
