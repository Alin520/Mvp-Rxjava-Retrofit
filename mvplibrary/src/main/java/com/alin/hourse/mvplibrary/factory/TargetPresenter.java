package com.alin.hourse.mvplibrary.factory;

import com.alin.hourse.mvplibrary.base.XPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @创建者 hailin
 * @创建时间 2017/11/21 17:29
 * @描述 ${获取目标Presenter}.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetPresenter {
    Class<? extends XPresenter>  value();
}
