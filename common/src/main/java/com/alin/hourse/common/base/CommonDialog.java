package com.alin.hourse.common.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alin.hourse.common.R;
import com.alin.hourse.common.util.CommonUtil;

/**
 * @创建者 hailin
 * @创建时间 2017/8/24 17:26
 * @描述 ${}.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public abstract class CommonDialog extends AppCompatDialogFragment implements DialogInterface.OnKeyListener {

    private AppCompatActivity mActivity;
    private int               mLayoutId;
    private boolean           mShowInCenter;
    private AppCompatDialog   mDialog;
    private DialogCallback    mCallback;
    private long              mLastTime;
    android.support.v4.app.FragmentManager fm;

    public CommonDialog(AppCompatActivity activity, int layoutId, boolean showInCenter, DialogCallback callback){
        mActivity = activity;
        mLayoutId = layoutId;
        mShowInCenter = showInCenter;
        mCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = createDialog();
            Point screenSize = new Point();
            mActivity.getWindowManager().getDefaultDisplay().getSize(screenSize);
            WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
            layoutParams.width = screenSize.x;
            layoutParams.gravity = mShowInCenter ? Gravity.CENTER : Gravity.BOTTOM | Gravity.FILL_HORIZONTAL;
            View contentView = LayoutInflater.from(mActivity).inflate(mLayoutId, null);
            mDialog.setContentView(contentView);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setOnKeyListener(this);
            if (mCallback != null) {
                mCallback.initChildren(this,mDialog,contentView);
            }

            ViewGroup.LayoutParams params = contentView.getLayoutParams();
            if (mShowInCenter && contentView != null) {
                int margin = CommonUtil.dp2px(mActivity, 30);
                ((ViewGroup.MarginLayoutParams)params).leftMargin = margin;
                ((ViewGroup.MarginLayoutParams)params).rightMargin = margin;
            }
        }

        if (mCallback != null) {
            mCallback.afterCreated(this,mDialog);
        }
        return mDialog;
    }

    public void show(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTime < 1000 || mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed()) {
           return;
        }
        try {

            if (fm == null) {
                fm = mActivity.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                android.support.v4.app.Fragment fragmentDialog = fm.findFragmentByTag("fragmentDialog");
                if (ft != null) {
                    if (fragmentDialog != null && fragmentDialog.isAdded()) {
                        ft.remove(this);
                        ft.commitAllowingStateLoss();
                    }
                }
            }
        }catch (Throwable t){
            t.printStackTrace();
        }

        this.show(fm,"fragmentDialog");
        mLastTime = System.currentTimeMillis();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (mCallback != null) {
            mCallback.onDismiss();
        }
        super.onDismiss(dialog);
    }

    @Override
    public void dismiss() {
        try {
            if (mActivity != null && !mActivity.isFinishing() && !mActivity.isDestroyed() && this.isAdded()) {
                super.dismissAllowingStateLoss();
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public boolean isShowing(){
        return mDialog != null && mDialog.isShowing();
    }

    public void settCallback(DialogCallback callback){
        this.mCallback = callback;
    }

    public DialogCallback getCallback(){
        return this.mCallback;
    }

    private AppCompatDialog createDialog() {
        return  new AppCompatDialog(mActivity,getThemeResId());
    }

//    dialog主题
    private int getThemeResId() {
        return mShowInCenter ? R.style.centerDialog : R.style.otherDialog;
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0 && !event.isCanceled()) {
            return true;
        } else {
            return false;
        }
    }


    public interface DialogCallback{
        void initChildren(CommonDialog commonDialog, Dialog dialog, View contentView);
        void afterCreated(AppCompatDialogFragment fragment, Dialog dialog);
        void onDismiss();
    }
}
