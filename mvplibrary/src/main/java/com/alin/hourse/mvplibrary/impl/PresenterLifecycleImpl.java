package com.alin.hourse.mvplibrary.impl;

/**
 * @创建者 hailin
 * @创建时间 2017/11/21 16:28
 * @描述 ${Presenter 的生命周期}.
 */

public interface PresenterLifecycleImpl {
    void onCreate(Object view);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestory();
}
