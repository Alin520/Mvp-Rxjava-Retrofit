package com.alin.hourse.common.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alin.hourse.common.annotations.TargetLog;

/**
 * @创建者 hailin
 * @创建时间 2017/8/21 12:41
 * @描述 ${}.
 */
public class LogUtil {
    private static boolean logEnabled = true;

    /**
     *  @deprecated 打印日志
     * @param clazz
     * @param log
     * @param <T>
     */
    public static  <T> void showLog(@Nullable Class<T> clazz,@Nullable String log){
        showLog(clazz,log,null);
    }

    public static  <T> void showLog(@Nullable Class<T> clazz,@Nullable String log,LogUtil.Logs type){
      dispatchLog(clazz,log,type);
    }

    private static <T> void dispatchLog(@Nullable Class<T> clazz, String log, Logs type) {
        TargetLog targetLog = clazz.getAnnotation(TargetLog.class);
        AppUtil.checkNotNull(clazz,"print log Class is null!");
        if (targetLog != null) {
            Class<?> aClass = targetLog.value();
            if (aClass != null) {
                String className = aClass.getName();
                if (!TextUtils.isEmpty(className)) {
                    handleLog(className,log,type);
                }
            }
        }
    }

    private static void handleLog(@Nullable String className,@Nullable String log, Logs type){
        if (type == null) {
            type = LogUtil.Logs.d;
        }
        if (className != null) {
            switch (type){
                case v:
                    v(className,log);
                    break;
                case i:
                    i(className,log);
                    break;
                case d:
                    d(className,log);
                    break;
                case w:
                    w(className,log);
                    break;
                case e:
                    e(className,log);
                    break;
            }
        }
    }


    public static void i(String tag,String msg){
        if (logEnabled) {
            Log.i(tag,msg);
            saveLog(tag,msg);
        }
    }

    public static void e(String tag,String msg){
        if (logEnabled) {
            Log.e(tag,msg);
            saveLog(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if (logEnabled) {
            Log.d(tag,msg);
            saveLog(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if (logEnabled) {
            Log.w(tag,msg);
            saveLog(tag,msg);
        }
    }

    public static void v(String tag,String msg){
        if (logEnabled) {
            Log.v(tag,msg);
            saveLog(tag,msg);
        }
    }

    public enum Logs{
        v,i,d,w,e
    }

    public static void setLogEnabled(boolean enable){
        logEnabled = enable;
    }

//    保存日志
    private static void saveLog(String tag, String msg) {
    }
}
