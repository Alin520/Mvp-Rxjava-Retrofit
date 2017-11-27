package com.alin.hourse.mvplibrary;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alin.hourse.common.base.CommonFragment;
import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.mvplibrary.base.XPresenter;
import com.alin.hourse.mvplibrary.factory.PresenterFactory;
import com.alin.hourse.mvplibrary.factory.ReflectionPresenterFactory;
import com.alin.hourse.mvplibrary.impl.ViewPresenterImpl;
import com.alin.hourse.mvplibrary.manager.PresenterLifecycleManager;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 17:59
 * @描述 ${}.
 */

public abstract class MvpFragment<P extends XPresenter> extends CommonFragment implements ViewPresenterImpl<P> {
    private static final String                    KEY_PRESENTER_BUNDLE = "key_presenter_bundle";    //异常时的存储presenter的key
    private              PresenterLifecycleManager mPresenterManager    = new PresenterLifecycleManager(ReflectionPresenterFactory.fromViewClass(getClass()));
    private PresenterFactory mPresenterFactory;


    @Override
    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState, View view);

    @Override
    protected void initialize(Bundle savedInstanceState, @Nullable View view) {
        super.initialize(savedInstanceState, view);
        if (mPresenterManager != null) {
            mPresenterManager.onCreate(this);
        }

        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle(KEY_PRESENTER_BUNDLE);
            if (bundle != null) {
                mPresenterManager.onRestoreInstanceState(bundle);
            }
        }

        init(savedInstanceState,view);
    }

    //    生产Presenter
    @Override
    public P createPresenter(){
        return mPresenterManager != null ? (P) mPresenterManager.getPresenter() : null;
    }

    @Override
    public PresenterLifecycleManager getPresenterManager() {
        return mPresenterManager;
    }

    @Override
    public void setPresenterManager(PresenterLifecycleManager presenterManager) {
        this.mPresenterManager = presenterManager;
    }

    @Override
    public PresenterFactory getPresenterFactory() {
        AppUtil.checkNotNull(mPresenterManager,"PresenterManager == null,setPresenterFactory() please fisrt setPresenterManager()");
        return mPresenterManager.getPresenterFactory();
    }

    @Override
    public void setPresenterFactory(@Nullable PresenterFactory presenterFactory) {
        AppUtil.checkNotNull(mPresenterManager, "PresenterManager == null,setPresenterFactory() please fisrt setPresenterManager()");
        AppUtil.checkNotNull(presenterFactory, "presenterFactory == null");
        mPresenterManager.setPresenterFactory(presenterFactory);
    }

    /***
     * call：被调用情景
     * 1.横竖屏切换
     * 2.前后台切换
     * 3.内存吃紧，回收内存，APP强制退出
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenterManager != null) {
            outState.putBundle(KEY_PRESENTER_BUNDLE,mPresenterManager.onSaveInstanceState());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenterManager != null) {
            mPresenterManager.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenterManager != null) {
            mPresenterManager.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenterManager != null) {
            mPresenterManager.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenterManager != null) {
            mPresenterManager.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenterManager != null) {
            mPresenterManager.onDestory();
        }
    }
}
