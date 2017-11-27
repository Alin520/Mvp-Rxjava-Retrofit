package com.alin.hourse.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alin.hourse.common.manager.ActivityManager;
import com.alin.hourse.common.util.CommonUtil;
import com.alin.hourse.common.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * @创建者 hailin
 * @创建时间 2017/11/17 11:50
 * @描述 ${所有Activity基类}.
 */
public abstract class CommonActivity extends AppCompatActivity {

    private InputMethodManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetStatusBar();
        setContentView(getContentViewId());
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        if (openEventBus()){
            EventBus.getDefault().register(this);
        }
        initialize(savedInstanceState);
//        initPermissions();
    }

    protected abstract int getContentViewId();

    //设置状态栏颜色值
    private Integer getStatusBarColor() {
        return null;
    }

    /**
     *  是否打开EventBus
     * @return
     */
    public static boolean openEventBus() {
        return false;
    }

//    /**
//     *  是否需要申请权限
//     * @return
//     */
//    public PermissionsController.Permission openPermissionRequest() {
//        ArrayList<PermissionsController.Permission>  mPermissions = new ArrayList<>();
//        mPermissions.add(PermissionsController.Permission.Storage);
//        mPermissions.add(PermissionsController.Permission.Location);
//        return PermissionsController.Permission.Storage;
//    }

    protected void initialize(Bundle savedInstanceState){
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);
//        对listview、SrollView、webview没有作用
        CommonUtil.getContentView(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showOrHideKeyboard(CommonActivity.this,false);
            }
        });
    }

    //    设置状态栏
    public void resetStatusBar(){
        Integer statusBarColor = getStatusBarColor();
        if (statusBarColor != null) {
            if (statusBarColor.intValue() == 0) {
                CommonUtil.hideStatusBarIfSupporter(this);
            }else {
                CommonUtil.setStatusBarColorIfSupporter(this,statusBarColor);
            }
        }
    }

    /**
     * 展示日志
     * @param log
     */
    public void showLog(@Nullable String log){
        showLog(log,null);
    }

    public void showLog(String log, LogUtil.Logs type) {
        LogUtil.showLog(getClass(),log,type);
    }

    public boolean isFinishOrDestory(){
        return this.isFinishing() || this.isDestroyed();
    }


//    private void initPermissions() {
//        PermissionsController.Permission permissions = openPermissionRequest();
//        new PermissionsController(this).startRequestPermission(permissions,this);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,PermissionsController.class);
//    }


//    //    同意，申请权限成功
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> permissions) {
//        PermissionsController.handlePermissons(true,requestCode,permissions);
//    }
//
//    //    取消，申请权限失败
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> permissions) {
//        PermissionsController.handlePermissons(false,requestCode,permissions);
//    }
//
//    /**
//     *  权限请求成功
//     * @param requestCode
//     */
//    @Override
//    public void onRequestPermissionsSuccess(int requestCode) {
//        Log.e("RequestPermissions","Success ==" + requestCode);
//    }
//
//    /**
//     *  权限请求失败
//     * @param requestCode
//     */
//    @Override
//    public void onRequestPermissionsFail(int requestCode) {
//        Log.e("RequestPermissions","Fail ==" + requestCode);
//
//    }

    @Override
    protected void onDestroy() {
        if (openEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

        ActivityManager.getInstance().removeActivity(this);
    }
}
