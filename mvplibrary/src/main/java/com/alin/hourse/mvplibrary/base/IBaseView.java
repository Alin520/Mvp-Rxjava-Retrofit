package com.alin.hourse.mvplibrary.base;

import android.content.Context;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 13:29
 * @描述 ${}.
 */

public interface IBaseView {

    Context getViewContext();

    void showContentView();

    void showError(String errorInfo,int errorCode);
}
