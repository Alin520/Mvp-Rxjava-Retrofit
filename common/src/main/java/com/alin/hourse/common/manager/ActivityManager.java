package com.alin.hourse.common.manager;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @创建者 hailin
 * @创建时间 2017/8/18 11:18
 * @描述 ${}.
 */

public class ActivityManager {
    private static ActivityManager sInstance = new ActivityManager();
    private  Stack<Activity> mActivityStack = new Stack<>();

    public static ActivityManager getInstance(){
        return sInstance;
    }

    public int getCount(){
        if (!mActivityStack.isEmpty()) {
            return mActivityStack.size();
        }
        return 0;
    }
    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity){
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        synchronized (mActivityStack){
            mActivityStack.add(activity);
        }
    }

    /**
     * 移除Activity
     * */
    public  synchronized void removeActivity(Activity activity){
        synchronized (ActivityManager.class){
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity(Activity activity){
        if (mActivityStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend KJActivity");
        }
        synchronized (mActivityStack){
           return mActivityStack.isEmpty() ? null : mActivityStack.lastElement();
        }
    }

    /**
     * 获取指定的Activity
     */
    public  synchronized Activity getActivity(Class<?> clazz){
        Activity findActivity = null;
        synchronized (mActivityStack){
            if (!mActivityStack.isEmpty()) {
                for(Activity activity : mActivityStack) {
                    if (activity.getClass().equals(clazz)) {
                        findActivity = activity;
                    }
                }
            }
        }
        return findActivity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public synchronized void finishCurrentActivity()
    {
        Activity activity;
        synchronized (mActivityStack){
            if (!mActivityStack.isEmpty()) {
                activity = mActivityStack.lastElement();
                if (activity != null) {
                    mActivityStack.remove(activity);
                }
                activity.finish();
            }
        }
    }


    /**
     * 结束指定的Activity
     */
    public synchronized void finishFixActivity(Activity activity)
    {
        if(activity != null)
        {
            synchronized(mActivityStack)
            {
                mActivityStack.remove(activity);
            }

            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishFixActivity(Class<?> clazz) {
        ArrayList<Activity> activities = new ArrayList<>();
        synchronized(mActivityStack) {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(clazz)) {
                    removeActivity(activity);
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public synchronized void finishAllActivity() {
        synchronized(mActivityStack) {
            if (!mActivityStack.isEmpty()) {
                for (Activity activity : mActivityStack) {
                    activity.finish();
                }

                mActivityStack.clear();
            }
        }
    }

    /**
     * 结束除指定Activity外的其他所有Activity
     * @param activity 不需要结束的Activity
     */
    public synchronized void finishAllActivityExcept(Activity activity)
    {
        synchronized(mActivityStack)
        {
            for(Activity mActivity: mActivityStack)
            {
                if(mActivity != activity && mActivity != null)
                {
                    mActivity.finish();
                }
            }

            mActivityStack.clear();

            if(activity != null)
            {
                mActivityStack.add(activity);
            }
        }
    }

    /**
     * 结束除指定Activity外的其他所有Activity
     * @param clazz 不需要结束的Activity
     */
    public synchronized void finishAllActivityExcept(Class<?> clazz)
    {
        List<Activity> activities = new ArrayList<>();

        synchronized(mActivityStack)
        {
            for(Activity activity: mActivityStack)
            {
                if(activity != null && activity.getClass() != clazz)
                {
                    activities.add(activity);
                }
            }

            for(Activity activity: activities)
            {
                finishFixActivity(activity);
            }

            activities.clear();
        }
    }

    @Deprecated
    public void appExit(Context context){
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        }catch (Exception e){
           Runtime.getRuntime().exit(-1);
        }

    }

}
