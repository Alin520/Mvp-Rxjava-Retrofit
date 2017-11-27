package com.alin.hourse.mvplibrary;

import com.alin.hourse.mvplibrary.base.IBaseView;
import com.alin.hourse.mvplibrary.base.XPresenter;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 13:22
 * @描述 ${}.
 */

public interface Contract {

    interface IView<T> extends IBaseView{
        void showContentData(T data);
    }

    class IPresenter extends XPresenter<IView> {

       public void  getDataFromNet(){}

       public void getMoreDataFromNet(){}
    }
}
