package com.alin.hourse.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alin.hourse.common.permissions.PermissionsController;
import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.common.util.Config;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @创建者 hailin
 * @创建时间 2017/11/21 14:49
 * @描述 ${}.
 */

public abstract class PermissionActivity extends CommonActivity implements EasyPermissions.PermissionCallbacks {
    private static PermissionsController.RequestPermissionsListener mPermissionsListener;
    private List<String[]> mPermissionsHas;
    private ArrayList<PermissionsController.Permission> mPermissions;
    @Override
    protected void initialize(Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        startRequestPermission();
    }

    /**
     *  是否需要申请权限
     * @return
     */
    public List<PermissionsController.Permission> openPermissionRequest() {
        mPermissions = new ArrayList<>();
        mPermissions.add(PermissionsController.Permission.Storage);
//        mPermissions.add(PermissionsController.Permission.Location);
        return mPermissions;
    }


    public void startRequestPermission(){
        AppUtil.checkNotNull(this,"request Permissions is not assign view!");
//        AppUtil.checkNotNull(mPermissions,"Permissions = null,权限请求为空！");
        List<PermissionsController.Permission> permissionList = openPermissionRequest();
        mPermissionsHas = new ArrayList<>();
        for (PermissionsController.Permission permission : permissionList) {
            switch (permission) {


                //                case Camera:
//                    requestCameraPermission("相机权限未开启~", PermissionsController.PERMISSIONS_CARMERA);
//                    break;
//                case Sms:
//                    requestSmsPermission("短信权限未开启~", PermissionsController.PERMISSIONS_SMS);
//                    break;
//                case Location:
//                    requestLocationPermission("定位权限未开启~", PermissionsController.PERMISSIONS_LOCATION);
//                    break;
//                case Phone:
//                    requestPhonePermission("电话权限未开启~", PermissionsController.PERMISSIONS_PHONE);
//                    break;
//                case Storage:
//                    requestStroagePermission("内存卡权限未开启~", PermissionsController.PERMISSIONS_STORAGE);
//                    break;
//                case Contacts:
//                    requestContactsPermission("联系人权限未开启~", PermissionsController.PERMISSIONS_CONTACTS);
//                    break;
//                case Calendar:
//                    requestCalendarPermission("日历权限未开启~", PermissionsController.PERMISSIONS_CALENDAR);
//                    break;
//                case Sensors:
//                    requestSensorsPermission("传感器权限未开启~", PermissionsController.PERMISSIONS_SENSORS);
//                    break;
//                default:
//                    break;



                case Camera:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_CARMERA);
//                    requestPermission("相机权限未开启~", PermissionsController.PERMISSIONS_CARMERA);

                    break;
                case Sms:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_SMS);
//                    requestPermission("短信权限未开启~", PermissionsController.PERMISSIONS_SMS);

                    break;
                case Location:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_LOCATION);
//                    requestPermission("定位权限未开启~", PermissionsController.PERMISSIONS_LOCATION);

                    break;
                case Phone:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_PHONE);
//                    requestPermission("电话权限未开启~", PermissionsController.PERMISSIONS_PHONE);

                    break;
                case Storage:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_STORAGE);
//                    requestPermission("内存卡权限未开启~", PermissionsController.PERMISSIONS_STORAGE);

                    break;
                case Contacts:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_CONTACTS);
//                    requestPermission("联系人权限未开启~", PermissionsController.PERMISSIONS_CONTACTS);

                    break;
                case Calendar:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_CALENDAR);
//                    requestPermission("日历权限未开启~", PermissionsController.PERMISSIONS_CALENDAR);

                    break;
                case Sensors:
                    mPermissionsHas.add(PermissionsController.PERMISSIONS_SENSORS);
//                    requestPermission("传感器权限未开启~", PermissionsController.PERMISSIONS_SENSORS);
                    break;
                default:
                    break;
            }

        }


//        requestPermission("传感器权限未开启~", mPermissionsHas);


    }

    //    相机权限
    @AfterPermissionGranted(Config.RESQUEST_CODE)
    public void requestPermission(String description,String[] permissions){
        dispatchRequestPermission(description,Config.RESQUEST_CODE,permissions);
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
        dispatchRequestPermission(description, Config.RESQUEST_CONTACTS_CODE,permissions);
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
        if (EasyPermissions.hasPermissions(this,permissions)) {    //权限已经申请成功
            if (mPermissionsListener != null) {
                mPermissionsListener.onRequestPermissionsSuccess(requestCode);
            }
        }else {
            if (permissions != null && permissions.length > 0) {
                EasyPermissions.requestPermissions(this,description,requestCode,permissions);
            }
        }

    }

    /**
     * @deprecated   同意、取消权限的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }


    //    同意，申请权限成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> permissions) {
        PermissionsController.handlePermissons(true,requestCode,permissions);
    }

    //    取消，申请权限失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> permissions) {
        PermissionsController.handlePermissons(false,requestCode,permissions);
    }
}
