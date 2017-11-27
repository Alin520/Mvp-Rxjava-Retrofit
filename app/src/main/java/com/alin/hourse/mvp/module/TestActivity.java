package com.alin.hourse.mvp.module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alin.hourse.common.annotations.TargetLog;
import com.alin.hourse.common.util.LogUtil;
import com.alin.hourse.common.view.RoundRectLinearLayout;
import com.alin.hourse.mvp.R;
import com.alin.hourse.mvp.module.bean.TestBean;
import com.alin.hourse.mvplibrary.Contract;
import com.alin.hourse.mvplibrary.MvpActivity;
import com.alin.hourse.mvplibrary.factory.TargetPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 13:37
 * @描述 ${}.
 */
@TargetLog(TestActivity.class)
@TargetPresenter(TestPresenter.class)
public class TestActivity extends MvpActivity<TestPresenter> implements Contract.IView<TestBean>{

    @BindView(R.id.test_rllyt)
    RoundRectLinearLayout mTestRllyt;

    private TestPresenter mPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        //绑定
    }

    @OnClick({R.id.number_tv,R.id.code_tv})
    public void onClick(View view ){
        switch (view.getId()){
            case R.id.number_tv:
                mPresenter.startWork();
                break;
            case R.id.code_tv:
                startActivity(new Intent(TestActivity.this,TestTwoActivity.class));
                break;
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showContentView() {
        mTestRllyt.showContentView();
    }

    @Override
    public void showError(String errorInfo, int errorCode) {
        mTestRllyt.showEmptyView();
    }


    @Override
    public void showContentData(TestBean data) {
        if (data != null) {
            LogUtil.showLog(TestActivity.class,data.toString());
        }
    }


}
