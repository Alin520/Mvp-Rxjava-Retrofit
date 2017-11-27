package com.alin.hourse.mvp.test;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 9:50
 * @描述 ${}.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("MyApplication",getCurProcessName(this));
    }

    String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
