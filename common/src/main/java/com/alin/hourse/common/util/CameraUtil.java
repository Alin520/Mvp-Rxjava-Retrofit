package com.alin.hourse.common.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @创建者 hailin
 * @创建时间 2017/8/29 11:15
 * @描述 ${}.
 */

public class CameraUtil {

    public static void startCamera(Activity activity, Uri targetUri, int requestCode)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, targetUri.getPath());
            Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, requestCode);
        }
        else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);
            activity.startActivityForResult(intent, requestCode);
        }
    }


    public static String createPicturePath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Uri uri = StorageUtil.getPictureRootPath(Config.DEFAULT_PICTURE_DIR);
        String name = "Alin_" + dateFormat.format(new Date()) + ".jpg";
        return uri.getPath() + "/" + name;
    }
}
