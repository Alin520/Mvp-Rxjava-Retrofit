package com.alin.hourse.common.util;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 hailin
 * @创建时间 2017/8/29 11:18
 * @描述 ${}.
 */

public class StorageUtil {
    public static Uri getPictureRootPath(String defaultPath)
    {
        Uri pictureRootPath = null;
        File sdRoot = Environment.getExternalStorageDirectory();
        File cameraRoot = new File(sdRoot, "DCIM/Camera");

        if(cameraRoot.exists() && cameraRoot.isDirectory())
        {
            pictureRootPath = Uri.fromFile(cameraRoot);
        }
        else
        {
            File picturesRoot = new File(sdRoot, "Pictures");

            if(picturesRoot.exists() && picturesRoot.isDirectory())
            {
                pictureRootPath = Uri.fromFile(picturesRoot);
            }
        }

        if(pictureRootPath == null)
        {
            File defaultRoot = new File(sdRoot, defaultPath);

            if(!defaultRoot.exists())
            {
                mkdirs(defaultRoot);
            }

            pictureRootPath = Uri.fromFile(defaultRoot);
        }

        return pictureRootPath;
    }


    /**
     * java.io.File中的mkdirs方法在Android中是失灵的
     * @param file
     */
    public static void mkdirs(File file)
    {
        List<File> files = new ArrayList<>();
        File parent;

        if(!file.exists())
        {
            files.add(file);
        }

        while((parent = file.getParentFile()) != null && !parent.exists())
        {
            files.add(0, parent);
            file = parent;
        }

        for(File dir: files)
        {
            dir.mkdir();
        }
    }
}
