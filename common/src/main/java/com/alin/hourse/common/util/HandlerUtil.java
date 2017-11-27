package com.alin.hourse.common.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 *
 * @deprecated  Message msg = new HandlerUtil.Build(Looper.getMainLooper()).builer().sendEmptyMessage(0).receiveMessage(null);
 * @创建者 hailin
 * @创建时间 2017/11/17 16:22
 * @描述 ${Handler 工具类}.
 */

public class HandlerUtil {
    private Handler mHandler;
    private Build mBuild;
    private Message mMsg;

    public HandlerUtil(Handler handler, Build build) {
        mHandler = handler;
        mBuild = build;
    }

    /**
     * 获取handler
     * @return
     */
    public Handler getHandler(){
        return mHandler;
    }

    public Message getMessage(){
        if (mMsg == null) {
            mMsg = mHandler.obtainMessage();
        }
        return mMsg;
    }

    /**
     * @deprecated  发送Empty消息
     * @param
     */
    public HandlerUtil sendEmptyMessage(int what){
        mHandler.sendEmptyMessage(what);
        return this;
    }

    public HandlerUtil sendEmptyMessageDelayed(int what,long delayMills){
        AppUtil.checkNotZero(delayMills);
        mHandler.sendEmptyMessageDelayed(what,delayMills);
        return this;
    }

    /**
     * @deprecated  发送消息
     * @param
     */
    public HandlerUtil sendMessage(@Nullable int what,Object object){
        sendMessageDelay(what,object,0L);
        return this;
    }

    public HandlerUtil sendMessageDelay(@Nullable int what,Object object,long delayMills){
        AppUtil.checkNotZero(delayMills);
        mMsg = mHandler.obtainMessage();
        mMsg.what = what;
        mMsg.obj = object;
        mHandler.sendMessageDelayed(mMsg,delayMills);
        return this;
    }

    /**
     * @deprecated  销毁Handler
     */
    public void destoryHandler(){
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    public static class Build{
        private static Handler sHandler;
        private HandlerUtil mHandlerUtil;

        public Build(@Nullable Looper mainLooper){
            if (sHandler == null) {
                sHandler = new Handler(mainLooper){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        receiveMessage(msg);
                    }
                };
            }
        }

        public HandlerUtil builer(){
            if (mHandlerUtil == null) {
                mHandlerUtil = new HandlerUtil(sHandler, this);
            }
            return mHandlerUtil;
        }
        /**
         * @deprecated  接收消息
         * @param msg
         * @return
         */
        public void receiveMessage(Message msg){}

    }
}
