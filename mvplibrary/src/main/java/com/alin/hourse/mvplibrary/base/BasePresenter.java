package com.alin.hourse.mvplibrary.base;

import android.support.annotation.Nullable;

import com.alin.hourse.common.annotations.TargetLog;
import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.common.util.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @创建者 hailin
 * @创建时间 2017/11/21 16:37
 * @描述 ${Presenter 的基类}.
 */
@TargetLog(BasePresenter.class)
public class BasePresenter<V> {
    private CopyOnWriteArrayList<OnDestoryListener> mOnDestoryListeners = new CopyOnWriteArrayList<>();
    private Map<String,OnDestoryListener>           mDestoryListenerMap            = new HashMap<>();
    public V mView;
    private String mDestoryListenerKey;

    public V getView(){
        AppUtil.checkNotNull(mView,"view == null ,current is not attchView view!");
       return this.mView;
    }

    public void attachView(V view){
        this.mView = view;
        onAttachView(view);
    }


    public void onAttachView(V view) {}

    public void onCreate(){}

    public void onStart(){}

    public void onResume(){}

    public void onPause(){}

    public void onStop(){}

    public void onDestory(){
        if (mDestoryListenerMap != null && !mDestoryListenerMap.isEmpty()) {
            for (Map.Entry<String, OnDestoryListener> entry : mDestoryListenerMap.entrySet()) {
                if (entry.getKey().equals(mDestoryListenerKey)) {
                    OnDestoryListener listener = entry.getValue();
                    listener.onDestory();
                    LogUtil.showLog(BasePresenter.class,"remove destoryListenerKey==" + mDestoryListenerKey);
                }
            }

            if (mDestoryListenerMap.containsKey(mDestoryListenerKey)) {
                mDestoryListenerMap.remove(mDestoryListenerKey);
            }

            LogUtil.showLog(BasePresenter.class,"DestoryListenerMap size ==" + mDestoryListenerMap.size());
        }


//        if (!mOnDestoryListeners.isEmpty()) {
//            for (OnDestoryListener listener : mOnDestoryListeners) {
//                listener.onDestory();
//            }
//        }
    }

    public void addDestoryListener(@Nullable String destoryListenerKey, OnDestoryListener listener){
//        mOnDestoryListeners.add(listener);
        mDestoryListenerKey = destoryListenerKey;
        mDestoryListenerMap.put(destoryListenerKey,listener);
        LogUtil.showLog(BasePresenter.class,"address==" + mDestoryListenerMap);
    }

    public void addOnDestroyListener(OnDestoryListener listener){
        mOnDestoryListeners.add(listener);
    }

    public interface OnDestoryListener{
        void onDestory();
    }
}
