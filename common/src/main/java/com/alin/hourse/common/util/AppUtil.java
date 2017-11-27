package com.alin.hourse.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alin.hourse.common.R;
import com.alin.hourse.common.annotations.TargetLog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;



/**
 * @创建者 hailin
 * @创建时间 2017/8/21 18:17
 * @描述 ${}.
 */
@TargetLog(AppUtil.class)
public class AppUtil {
    private static final AppUtil INSTANCE = new AppUtil();
    private Context mAppContext;

    public static AppUtil getInstance(){
        return INSTANCE;
    }
//    判断当前进场是否是主进程
    public static boolean isMainProcess(Context context){
       String processName = getProcessName(context);
        return processName != null && processName.equals(context.getPackageName());
    }

    private static String getProcessName(Context context) {
        String processName = null;
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = manager.getRunningAppProcesses();
        if (appProcesses != null && !appProcesses.isEmpty()) {
            int myPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo process : appProcesses) {
                if (process.pid == myPid) {
                    processName = process.processName;
                    return processName;
                }
            }
        }
        return processName;
    }

/***
 * 前台进程：onCreate、onStart、onResume、onPause
 * 非前台进程：onStop、onDestory
 * 判断当前进程是否是前台进程
 * */
    public static boolean isAppForeground(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
            if (infos != null && !infos.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo info : infos) {
                    if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {   //前台进程
                        return info.processName.equals(context.getPackageName());
                    }
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean hideStatusBarIfSupported(Activity activity){
        boolean hasHide = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            hasHide = true;
        }

        return hasHide;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setStatusBarColorIfSupported(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
            this.hideStatusBarIfSupported(activity);
            getContentView(activity).setFitsSystemWindows(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);
        }
    }

//    获取最底层的rootView
    public View getContentView(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

//    校验Context是否初始化
    public static void testContextInitialize(Context context){
        if (context == null)
            throw new ExceptionInInitializerError(context.getResources().getString(R.string.testContextInitialize));
    }

    /**
     * @deprecated
     *  对传入参数判空校验
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T t) {
        return  checkNotNull(t,null);
    }

    public static <T> T checkNotNull(T t, @NonNull String errorInfo) {
        if (t == null)
            throw new IllegalArgumentException(TextUtils.isEmpty(errorInfo) ? "传入参数异常！" : errorInfo);
        return t;
    }

    public static void test(){
        LogUtil.showLog(AppUtil.class,"AppUtil打印的log...",LogUtil.Logs.e);
    }


    /**
     * @deprecated  校验传入参数是否>= 0
     * @param number
     * @return
     */
    public static Number checkNotZero(@Nullable Number number) {
        return checkNotZero(number,null);
    }

    public static Number checkNotZero(@Nullable Number number, String errorInfo) {
        if (number == null){
            throw new IllegalArgumentException(TextUtils.isEmpty(errorInfo) ? "传入参数==null！" : errorInfo);
        }else if (number instanceof Integer){
            if (number.intValue() < 0) {
                throw new IllegalArgumentException(TextUtils.isEmpty(errorInfo) ? "传入参数<=0！" : errorInfo);
            }
        }else if (number instanceof Double){
            if (number.doubleValue() < 0) {
                throw new IllegalArgumentException(TextUtils.isEmpty(errorInfo) ? "传入参数<=0！" : errorInfo);
            }
        }else if (number instanceof  Float){
            if (number.floatValue() < 0) {
                throw new IllegalArgumentException(TextUtils.isEmpty(errorInfo) ? "传入参数<=0！" : errorInfo);
            }
        }else if (number instanceof Long){
            if (number.longValue() < 0) {
                throw new IllegalArgumentException(TextUtils.isEmpty(errorInfo) ? "传入参数<=0！" : errorInfo);
            }
        }
        return number;
    }
}
