package com.alin.hourse.mvp.test;

/**
 * @创建者 hailin
 * @创建时间 2017/11/22 9:43
 * @描述 ${}.
 */

public class TestTool {
    private static TestTool instance;


    public static TestTool getInstance(){
        if (instance == null) {
            synchronized (TestTool.class){
                if (instance == null) {
                    instance = new TestTool();
                }
            }
        }

        return instance;
    }
}
