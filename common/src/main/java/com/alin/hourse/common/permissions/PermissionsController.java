package com.alin.hourse.common.permissions;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.common.util.Config;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @创建者 hailin
 * @创建时间 2017/11/17 17:58
 * @描述 ${}.
 */

public class PermissionsController implements EasyPermissions.PermissionCallbacks{

    private Activity mActivity;
    private static RequestPermissionsListener mPermissionsListener;
    private List<String[]> mPermissionList;
    private Integer[] mPermissionCodeList;
    /**
     * 相机权限
     */
    public static final String[] PERMISSIONS_CARMERA = new String[]{Manifest.permission.CAMERA};

    /**
     * 定位权限
     */
    public static final String[] PERMISSIONS_LOCATION = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * 短信权限
     */
    public static final String[] PERMISSIONS_SMS= new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS};

    /**
     * 电话权限
     */
    public static final String[] PERMISSIONS_PHONE = new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE};

    /**
     * 内存卡权限
     */
    public static final String[] PERMISSIONS_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 联系人权限
     */
    public static final String[] PERMISSIONS_CONTACTS = new String[]{Manifest.permission.READ_CONTACTS};

    /**
     * 日历权限
     */
    public static final String[] PERMISSIONS_CALENDAR = new String[]{Manifest.permission.READ_CALENDAR};

    /**
     * 传感器权限
     */
    public static final String[] PERMISSIONS_SENSORS = new String[]{Manifest.permission.BODY_SENSORS};


    public PermissionsController(Activity activity){
        this.mActivity = activity;
    }

    /**
     * @deprecated  开始申请权限
//     * @param     申请的权限集合
     * @param permissionsListener
     */
    public void startRequestPermission(PermissionsController.Permission permission,RequestPermissionsListener permissionsListener){
        AppUtil.checkNotNull(mActivity,"request Permissions is not assign view!");
        this.mPermissionList = new ArrayList<>();
        this.mPermissionsListener = permissionsListener;
        AppUtil.checkNotNull(permission,"Permissions = null,权限请求为空！");
//        for (Permission permission : permissionList) {
            switch (permission){
                case Camera:
                    requestCameraPermission("相机权限未开启~",PERMISSIONS_CARMERA);
                    break;
                case Sms:
                    requestSmsPermission("短信权限未开启~",PERMISSIONS_SMS);
                    break;
                case Location:
                    requestLocationPermission("定位权限未开启~",PERMISSIONS_LOCATION);
                    break;
                case Phone:
                    requestPhonePermission("电话权限未开启~",PERMISSIONS_PHONE);
                    break;
                case Storage:
                    requestStroagePermission("内存卡权限未开启~",PERMISSIONS_STORAGE);
                    break;
                case Contacts:
                    requestContactsPermission("联系人权限未开启~",PERMISSIONS_CONTACTS);
                    break;
                case Calendar:
                    requestCalendarPermission("日历权限未开启~",PERMISSIONS_CALENDAR);
                    break;
                case Sensors:
                    requestSensorsPermission("传感器权限未开启~",PERMISSIONS_SENSORS);
                    break;
                default:
                    break;
        }

    }

    //    相机权限
    @AfterPermissionGranted(Config.RESQUEST_CARMERA_CODE)
    public void requestCameraPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_CARMERA_CODE,permissions);
    }

    //    定位权限
    @AfterPermissionGranted(Config.RESQUEST_LOCATION_CODE)
    public void requestLocationPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_LOCATION_CODE,permissions);
    }

    //    短信权限
    @AfterPermissionGranted(Config.RESQUEST_SMS_CODE)
    public void requestSmsPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_SMS_CODE,permissions);
    }

    //    电话权限
    @AfterPermissionGranted(Config.RESQUEST_PHONE_CODE)
    public void requestPhonePermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_PHONE_CODE,permissions);
    }

    //    内存卡权限
    @AfterPermissionGranted(Config.RESQUEST_STORAGE_CODE)
    public void requestStroagePermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_STORAGE_CODE,permissions);
    }

    //    联系人权限
    @AfterPermissionGranted(Config.RESQUEST_CONTACTS_CODE)
    public void requestContactsPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_CONTACTS_CODE,permissions);
    }

    //    日历权限
    @AfterPermissionGranted(Config.RESQUEST_CALENDAR_CODE)
    public void requestCalendarPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_CALENDAR_CODE,permissions);
    }

    //    传感器权限
    @AfterPermissionGranted(Config.RESQUEST_SENSORS_CODE)
    public void requestSensorsPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_SENSORS_CODE,permissions);
    }

    //    权限处理
    private void dispatchRequestPermission(String description, int requestCode, String[] permissions) {
        if (EasyPermissions.hasPermissions(mActivity,permissions)) {    //权限已经申请成功
            if (mPermissionsListener != null) {
                mPermissionsListener.onRequestPermissionsSuccess(requestCode);
            }
        }else {
            if (permissions != null && permissions.length > 0) {
                EasyPermissions.requestPermissions(mActivity,description,requestCode,permissions);
            }
        }

    }

    //    同意，申请权限成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> permissions) {
        handlePermissons(true,requestCode,permissions);
    }

    //    取消，申请权限失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> permissions) {
        handlePermissons(true,requestCode,permissions);
    }

    /**
     *  对请求权限结果的处理
     * @param permissonSuccess 请求的权限是否成功，true 成功
     * @param requestCode
     * @param permissions
     */
    public static void handlePermissons(boolean permissonSuccess, int requestCode, List<String> permissions) {
        if (permissonSuccess) {     //成功
            if (mPermissionsListener != null) {
                mPermissionsListener.onRequestPermissionsSuccess(requestCode);
            }
        }else {
            if (mPermissionsListener != null) {
                mPermissionsListener.onRequestPermissionsFail(requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,PermissionsController.class);
        Log.e("RequestPermissions","Success ==" + requestCode);
    }

    //    @Override
//    public void onRequestPermissionsSuccess(int requestCode) {
//
//    }
//
//    @Override
//    public void onRequestPermissionsFail(int requestCode) {
//
//    }


    public interface RequestPermissionsReslut extends ActivityCompat.OnRequestPermissionsResultCallback {

        @Override
         void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
//            if (mActivity instanceof CommonActivity) {
//                Class<? extends Activity> aClass = mActivity.getClass();
//                Class<?> superclass = aClass.getSuperclass();
//                try {
//                    Method permissionsResult = superclass.getDeclaredMethod("onRequestPermissionsResult", Integer.class, String[].class, Integer[].class);
//                    if (permissionsResult != null) {
//                        permissionsResult.invoke(superclass,requestCode,permissions,grantResults);
//                        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,mActivity);
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


//        //    同意，申请权限成功
//        @Override
//        void onPermissionsGranted(int requestCode, List<String> permissions);
//
//        //    取消，申请权限失败
//        @Override
//        void onPermissionsDenied(int requestCode, List<String> permissions);
    }




    public interface RequestPermissionsListener{
        void onRequestPermissionsSuccess(int requestCode);
        void onRequestPermissionsFail(int requestCode);
    }

    public enum Permission{
        Camera,     //相机
        Sms,        //短信
        Location,   //定位
        Phone,      //电话
        Storage,    //内存卡
        Contacts,   //联系人
        Calendar,   //日历
        Sensors    //传感器
    }
}
