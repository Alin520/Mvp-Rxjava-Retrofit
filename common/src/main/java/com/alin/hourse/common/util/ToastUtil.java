package com.alin.hourse.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alin.hourse.common.R;


/**
 * @创建者 hailin
 * @创建时间 2017/8/21 10:52
 * @描述 ${}.
 */

public class ToastUtil {
    public static void showCenterToast(Context context, CharSequence msg)
    {
        showToast(context, msg, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    public static void showCenterToast(Context context, int msgId)
    {
        showToast(context, msgId, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    public static void showTopToast(Context context, CharSequence msg)
    {
        showToast(context, msg, Toast.LENGTH_SHORT, Gravity.TOP);
    }

    public static void showTopToast(Context context, int msgId)
    {
        showToast(context, msgId, Toast.LENGTH_SHORT, Gravity.TOP);
    }

    public static void showBottomToast(Context context, CharSequence msg)
    {
        showToast(context, msg, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void showBottomToast(Context context, int msgId)
    {
        showToast(context, msgId, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void showToast(Context context, int msgId, int duration, int gravity)
    {
        CharSequence msg = context.getResources().getString(msgId);
        showToast(context, msg, duration, gravity);
    }

    public static void showToast(Context context, CharSequence msg, int duration, int gravity)
    {
        if (!TextUtils.isEmpty(msg)) {
            int yOffset = 0;
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            if ((gravity & Gravity.TOP) != 0) {
                yOffset =  CommonUtil.dp2px(context,15);
            }

            if ((gravity & Gravity.BOTTOM) != 0) {
                toast.setGravity(gravity,0,yOffset);
            }

            toast.show();
        }
    }

    public static void showCustomToast(Context context, int msgId, int duration)
    {
        CharSequence msg = context.getResources().getString(msgId);
        showCustomToast(context, msg, duration);
    }

    @SuppressLint("InflateParams")
    public static void showCustomToast(Context context, CharSequence msg, int duration)
    {
        if(msg != null && msg.length() > 0) {
            if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
                duration = Toast.LENGTH_SHORT;
            }
            Toast toast = new Toast(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.toast_layout, null);
            TextView content = (TextView)view.findViewById(R.id.textView);
            content.setText(msg.toString());
            toast.setView(view);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
