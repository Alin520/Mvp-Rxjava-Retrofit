package com.alin.hourse.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.alin.hourse.common.base.CommonActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * @创建者 hailin
 * @创建时间 2017/8/21 10:56
 * @描述 ${}.
 */

public class CommonUtil {
    private static Context sContext;
    private static InputMethodManager mManager;
    private static final long SHOW_KEY_BOARD_DEFAULT = 200L;

    public static int dp2px(Context context, int pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        float px = pxValue * density;
        int pxInt = (int) px;
        return pxInt == 0 ? pxInt : pxInt + 1;
    }

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        float dp = pxValue / scale;
        int dpInt = (int)dp;
        return dp == dpInt? dpInt: dpInt + 1;
    }

    /**
     * 获取ContentView
     * @param activity
     * @return
     */
    public static View getContentView(@Nullable Activity activity){
        return ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * @param show
     *  true 弹出键盘
     */
    public static void showOrHideKeyboard(Activity activity,boolean show) {
        if (mManager == null) {
            mManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        }

        View focusView = activity.getCurrentFocus();
        if (show) {
            if (focusView != null) {
                mManager.showSoftInput(focusView,InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }else {
                mManager.toggleSoftInput(InputMethodManager.RESULT_SHOWN,InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }else {
            if (focusView != null) {
                mManager.hideSoftInputFromInputMethod(focusView.getWindowToken(),InputMethodManager.RESULT_SHOWN);
            }
        }
    }

    /**
     * 延时弹出键盘
     */
    public void showKeyboardDelay(Activity activity) {
        View focusView = activity.getCurrentFocus();
        showKeyboardDelay(focusView,SHOW_KEY_BOARD_DEFAULT);
    }

    public void showKeyboardDelay(@Nullable View focusView) {
        showKeyboardDelay(focusView,SHOW_KEY_BOARD_DEFAULT);
    }

    public void showKeyboardDelay(Activity activity,long delay) {
        View focusView = activity.getCurrentFocus();
        showKeyboardDelay(focusView,delay);
    }

    public void showKeyboardDelay(@Nullable final View focusView, long delay) {
        if (focusView != null) {
            focusView.requestFocus();
        }

        Handler handler = new HandlerUtil.Build(Looper.getMainLooper()).builer().getHandler();
        if (handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showOrHideKeyboard((Activity) focusView.getContext(),true);
                }
            },delay);
        }

    }

    /**
     *  设置主题颜色
     * @param
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean hideStatusBarIfSupporter(CommonActivity activity) {
        boolean hasHide = false;
        Window window = activity.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(0);
            hasHide = true;
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            hasHide = true;
        }

        return hasHide;
    }

    public static void setStatusBarColorIfSupporter(CommonActivity activity, int color) {
        getContentView(activity).setFitsSystemWindows(true);
        Window window = activity.getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);
        }
    }
}
