package com.alin.hourse.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建者 hailin
 * @创建时间 2017/8/18 14:02
 * @描述 ${}.
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    private static HashMap<String,String> map;

    public static HashMap<String,String> collectDeviceInfo(Context context){
        map = new HashMap<>();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = packageInfo.versionName;
            String versionCode = packageInfo.versionCode + "";
            map.put("versionName",versionName);
            map.put("versionCode",versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(),field.get(null).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e(TAG, "an error params when collect package info", e);
                }

            }
        }
        return map;
    }


    public static String saveCrashInfo2File(Throwable throwable,Map<String, String> infos) throws IOException {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            buffer.append(key + "=" +value + "\n");
        }
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        Throwable cause = throwable.getCause();
//        通过while循环，将所有的异常信息都用PrintWriter存储
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = throwable.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        buffer.append(result);

//        保存错误日志
        String time = Const.FORMAT.format(new Date());
        String fileName = "crash-" + time + "-" + Const.CurrentTime + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(path + fileName);
            fos.write(buffer.toString().getBytes());
            fos.close();
        }

        return fileName;
    }
}
