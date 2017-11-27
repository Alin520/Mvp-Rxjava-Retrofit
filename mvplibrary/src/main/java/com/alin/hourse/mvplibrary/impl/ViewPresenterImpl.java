package com.alin.hourse.mvplibrary.impl;

import com.alin.hourse.mvplibrary.base.XPresenter;
import com.alin.hourse.mvplibrary.factory.PresenterFactory;
import com.alin.hourse.mvplibrary.manager.PresenterLifecycleManager;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 10:16
 * @描述 ${具体presenter的设置}.
 */

public interface ViewPresenterImpl<P extends XPresenter> {

    P createPresenter();

    void setPresenterFactory(PresenterFactory<P> presenterFactory);

    PresenterFactory<P> getPresenterFactory();

    void setPresenterManager(PresenterLifecycleManager presenterManager);

    PresenterLifecycleManager getPresenterManager();
}
