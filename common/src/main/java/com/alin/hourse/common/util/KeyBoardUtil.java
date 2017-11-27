package com.alin.hourse.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @创建者 hailin
 * @创建时间 2017/8/24 11:29
 * @描述 ${}.
 */

public class KeyBoardUtil {

    private static InputMethodManager sManager;

    //    打开或者关闭键盘
    public static void showOrHideKeyboard(Activity activity, boolean show){
        if (sManager != null) {
            sManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        View currentFocus = activity.getCurrentFocus();
        if (show) {     //打开键盘
            if (currentFocus != null) {     //有焦点
                sManager.showSoftInput(currentFocus,0);
                if (!sManager.isActive()) {     //键盘若还是关闭，就强制打开
                    sManager.showSoftInput(currentFocus,InputMethodManager.SHOW_FORCED);
                }
            }else {      //没有获取焦点,强制弹出键盘
                sManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        }else if (currentFocus != null){
            sManager.hideSoftInputFromInputMethod(currentFocus.getWindowToken(),0);
        }
    }

//    延时打开键盘
    public static void showKeyboardDelayed(final View view, int delayedTime){
        if (delayedTime < 0){
            delayedTime = 0;
        }
        if (view != null) {
            view.requestFocus();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (view != null && view.isFocused()) {
                    showOrHideKeyboard((Activity) view.getContext(),true);
                }
            }
        },delayedTime);
    }

//    获取键盘状态，打开或者关闭
    public static boolean isKeyboard(Activity activity){
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            if (manager.isActive()) {
                return true;
            }
        }
        return false;
    }
}
