package com.alin.hourse.mvplibrary.cache;

import com.alin.hourse.common.annotations.TargetLog;
import com.alin.hourse.common.util.AppUtil;
import com.alin.hourse.common.util.LogUtil;
import com.alin.hourse.mvplibrary.base.BasePresenter;
import com.alin.hourse.mvplibrary.base.XPresenter;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 11:03
 * @描述 ${}.
 */
@TargetLog(PresenterStorage.class)
public enum PresenterStorage {
    INSTANCE;

    /**
     * 通过Presenter 查找id
     */
    private Map<XPresenter,String> mPresenterFindId = new WeakHashMap<>();

    /**
     * 通过id 查找Presenter
     */
    private Map<String,XPresenter> mIdFindPresenter = new WeakHashMap<>();


    /**
     *  @deprecated  获取id
     * @param presenter
     * @param <P>
     * @return
     */
    public <P extends XPresenter> String getPresenterId(P presenter) {
        AppUtil.checkNotNull(presenter,"presenter == null,getPresenterId input presenter is not null!");
        return !mPresenterFindId.isEmpty() ? mPresenterFindId.get(presenter) : null;
    }

    /**
     *  @deprecated  获取presenter
     * @param presenterId
     * @param <P>
     * @return
     */
    public <P extends XPresenter> P getPresenter(String presenterId) {
        AppUtil.checkNotNull(presenterId,"presenterId == null,getPresenter input id is not null!");
        return !mIdFindPresenter.isEmpty() ? (P) mIdFindPresenter.get(presenterId) : null;
    }

    /**
     *  @deprecated  添加presenter
     * @return
     */
    public void add(final XPresenter presenter){
        if (presenter != null) {
            final String presenterId = PresenterStorage.class.getSimpleName() + "/" + System.nanoTime() + "/" + (int)(Math.random() * Integer.MAX_VALUE);
            mIdFindPresenter.put(presenterId,presenter);
            mPresenterFindId.put(presenter,presenterId);
            LogUtil.showLog(PresenterStorage.class,"add...presenter"  + mIdFindPresenter.size());
            presenter.addDestoryListener(presenterId,new BasePresenter.OnDestoryListener() {
                @Override
                public void onDestory() {
                    String removeId = mPresenterFindId.remove(presenter);
                    mIdFindPresenter.remove(removeId);
                    LogUtil.showLog(PresenterStorage.class,"当前被删除的Presenter=" + presenter.toString() + ",PresenterId=" + removeId);
                }
            });
        }
    }

    public void add(String presenterId){

    }

    public void clear(){
        if (!mIdFindPresenter.isEmpty()) {
            mIdFindPresenter.clear();
        }

        if (!mPresenterFindId.isEmpty()) {
            mPresenterFindId.clear();
        }
    }

}
