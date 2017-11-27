package com.alin.hourse.mvp.module;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alin.hourse.common.annotations.TargetLog;
import com.alin.hourse.common.util.LogUtil;
import com.alin.hourse.mvp.R;
import com.alin.hourse.mvp.module.bean.TestBean;
import com.alin.hourse.mvplibrary.Contract;
import com.alin.hourse.mvplibrary.MvpActivity;
import com.alin.hourse.mvplibrary.factory.TargetPresenter;

import butterknife.OnClick;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 15:54
 * @描述 ${}.
 */
@TargetLog(TestTwoActivity.class)
@TargetPresenter(TestTwoPresenter.class)
public class TestTwoActivity extends MvpActivity<TestTwoPresenter> implements Contract.IView<TestBean>{

    private TestTwoPresenter mPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_two;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter = createPresenter();
    }

    @OnClick({R.id.second_tv})
    public void onClick(View view ){
        switch (view.getId()){
            case R.id.number_tv:
                mPresenter.startWork();
                break;
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showContentView() {

    }

    @Override
    public void showError(String errorInfo, int errorCode) {

    }

    @Override
    public void showContentData(TestBean data) {
        if (data != null) {
            LogUtil.showLog(TestActivity.class,data.toString());
        }
    }
}
