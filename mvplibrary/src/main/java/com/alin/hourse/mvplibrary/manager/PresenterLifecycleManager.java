package com.alin.hourse.mvplibrary.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.mvplibrary.base.XPresenter;
import com.alin.hourse.mvplibrary.cache.ParceFn;
import com.alin.hourse.mvplibrary.cache.PresenterStorage;
import com.alin.hourse.mvplibrary.factory.PresenterFactory;
import com.alin.hourse.mvplibrary.impl.PresenterLifecycleImpl;

/**
 * @创建者 hailin
 * @创建时间 2017/11/21 16:34
 * @描述 ${Presenter 生命周期管理器}.
 */

public class PresenterLifecycleManager<P extends XPresenter> implements PresenterLifecycleImpl {
    private static final String PRESENTER_ID_KEY = "presenter_id_key";
    private P mPresenter;
    private PresenterFactory mPresenterFactory;
    private Bundle mBundle;

    public PresenterLifecycleManager(PresenterFactory<P> presenterFactory){
        if (presenterFactory == null)
            throw new IllegalArgumentException("setPresenterFactory() should be called before onResume()");
        this.mPresenterFactory = presenterFactory;
    }


    public void setPresenterFactory(@Nullable PresenterFactory presenterFactory){
        this.mPresenterFactory = presenterFactory;
    }

    public PresenterFactory getPresenterFactory(){
       return mPresenterFactory;
    }

    public void getPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    /**
     * Presenter缓存思路：
     * 1.存储过程：
     *     1)用HashMap缓存Presenter。
     *     2)将PresenterId缓存到bundle中，若内存中有新的bundle出现时（如横竖屏切换、内存吃紧强制退出app，保存了上次退出的bundle），都重新缓存PresenterId，onSaveInstanceState().
     *    注意：每次bundle出现时，都需要重新缓存bundle，onRestoreInstanceState()
     * 2.取出过程：
     *     1）先将缓存在bundle中的PresenterId取出，然后通过PresenterId从HashMap集合中取出Presenter
     *     2）若内存中没有缓存bundle，然后PresenterFactory中回调获取Presenter
     *
     * */
    public P getPresenter() {
        if (mBundle != null) {
            String presenterKey = mBundle.getString(PRESENTER_ID_KEY);
            if (!TextUtils.isEmpty(presenterKey)) {
                mPresenter = PresenterStorage.INSTANCE.getPresenter(presenterKey);
            }
        }

        if (mPresenter == null && mPresenterFactory != null) {  //程序走到这，说明Map集合中还未缓存Presenter，需要将Presenter缓存至map中
            mPresenter = (P) mPresenterFactory.createPresenter();
            PresenterStorage.INSTANCE.add(mPresenter);
        }
        return mPresenter;
    }

    /**
     * @deprecated  将PresenterId存入bundle中
     * @return
     */
    public Bundle onSaveInstanceState() {
        mBundle = new Bundle();
        getPresenter();
        if (mPresenter != null) {
            String presenterId = PresenterStorage.INSTANCE.getPresenterId(mPresenter);
            if (presenterId == null) {  //若当前的Presenter未缓存到Map集合，则重新存入Map集合，然后重新将presenterId取出
                PresenterStorage.INSTANCE.add(mPresenter);
                presenterId = PresenterStorage.INSTANCE.getPresenterId(mPresenter);
            }
            mBundle.putString(PRESENTER_ID_KEY,presenterId);
        }
        return mBundle;
    }

    /**
     * @deprecated  将Bundle对象先要序列化到内存中，然后再反序列化取出，后面用的时候可以直接从内存中取出。如getPresenter()中需要用到bundle
     * @param bundle
     */
    public void onRestoreInstanceState(@Nullable Bundle bundle) {
        AppUtil.checkNotNull(bundle,"bundle == null,onRestoreInstanceState() exception !");
        ParceFn.unMarshell(ParceFn.marshell(bundle));
    }


    @Override
    public void onCreate(Object view) {
        getPresenter();
        mPresenter.attachView(view);
        mPresenter.onCreate();
    }

    @Override
    public void onStart() {
        getPresenter();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        getPresenter();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        getPresenter();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        getPresenter();
        mPresenter.onStop();
    }

    @Override
    public void onDestory() {
        getPresenter();
        mPresenter.onDestory();
    }
}
