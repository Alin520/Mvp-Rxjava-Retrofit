package com.alin.hourse.common.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alin.hourse.common.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @创建者 hailin
 * @创建时间 2017/11/17 13:25
 * @描述 ${}.
 */

public abstract class CommonFragment extends Fragment{

    /**
     *  是否打开EventBus
     * @return
     */

    protected abstract int getContentViewId();

    public static boolean openEventBus() {
        return false;
    }

    protected void initialize(Bundle savedInstanceState,@Nullable View view){
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




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (openEventBus()){
            EventBus.getDefault().register(this);
        }

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        initialize(savedInstanceState,view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (openEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
