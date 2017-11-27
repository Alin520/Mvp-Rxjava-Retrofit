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
@TargetLog(TestTwoPresenter.class)
public class TestTwoPresenter extends Contract.IPresenter {

    @Override
    public void startWork() {
        getDataFromNet();
    }

    @Override
    public void getDataFromNet() {
        AppUtil.checkNotNull(mView,"View == null,use TestPresenter is not attachView view!");
        TestBean bean = new TestBean("测试mvp架构第二次。。。", 24);
        mView.showContentData(bean);
        mView.showContentView();
    }




    @Override
    public void onDestory() {
        super.onDestory();
        LogUtil.showLog(TestTwoPresenter.class,"onDestory");
    }
}
