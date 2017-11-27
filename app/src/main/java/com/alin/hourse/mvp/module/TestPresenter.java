package com.alin.hourse.mvp.module;

import com.alin.hourse.common.annotations.TargetLog;
import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.common.util.LogUtil;
import com.alin.hourse.mvp.module.bean.TestBean;
import com.alin.hourse.mvplibrary.Contract;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 13:38
 * @描述 ${}.
 */
@TargetLog(TestPresenter.class)
public class TestPresenter extends Contract.IPresenter {

    @Override
    public void startWork() {
        getDataFromNet();
    }

    @Override
    public void getDataFromNet() {
        AppUtil.checkNotNull(mView,"View == null,use TestPresenter is not attachView view!");
        TestBean bean = new TestBean("测试mvp架构", 12);
        mView.showContentData(bean);
        mView.showContentView();
    }


    public void onCreate(){
        LogUtil.showLog(TestPresenter.class,"onCreate");
    }

    public void onStart(){
        LogUtil.showLog(TestPresenter.class,"onStart");
    }

    public void onResume(){
        LogUtil.showLog(TestPresenter.class,"onResume");
    }

    public void onPause(){
        LogUtil.showLog(TestPresenter.class,"onPause");
    }

    public void onStop(){
        LogUtil.showLog(TestPresenter.class,"onStop");
    }

    @Override
    public void onDestory() {
        super.onDestory();
        LogUtil.showLog(TestPresenter.class,"onDestory");
    }
}
