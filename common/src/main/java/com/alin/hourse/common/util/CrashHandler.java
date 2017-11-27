package com.alin.hourse.common.util;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

/**
 * @创建者 hailin
 * @创建时间 2017/8/18 11:48
 * @描述 ${}.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler mCrashHandler;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public static CrashHandler getInstance(){
        if (mCrashHandler == null) {
            synchronized (CrashHandler.class){
                if (mCrashHandler == null) {
                    mCrashHandler = new CrashHandler();
                }
            }
        }
        return mCrashHandler;
    }

    private void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (mDefaultHandler != null && !handleException(throwable)) {
            //如果用户没有处理，就交给系统处理
            mDefaultHandler.uncaughtException(thread,throwable);
        }else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Process.killProcess(Process.myPid());
            System.exit(1);

        }
    }

    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        HashMap<String, String> colectData = FileUtils.collectDeviceInfo(mContext);
        if (colectData != null) {
            try {
                FileUtils.saveCrashInfo2File(throwable, colectData);
            }catch (IOException io){
                Log.e(TAG, "an error occured while writing file...", io);
            }
        }

        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序开小差了呢..", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        };
        return true;
    }
}
