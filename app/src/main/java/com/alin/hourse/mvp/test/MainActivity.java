package com.alin.hourse.mvp.test;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alin.hourse.common.annotations.TargetLog;
import com.alin.hourse.mvp.R;
import com.alin.hourse.mvp.module.TestActivity;

@TargetLog(MainActivity.class)
public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.log_test_tv)
//    public  TextView          mTextView;
//    private Handler           mHandler;
//    private HandlerUtil.Build mBuild;
//    private HandlerUtil mHandlerUtil;
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initialize(Bundle savedInstanceState) {
//        super.initialize(savedInstanceState);
//
//        TranslateAnimation animation = new TranslateAnimation(0,0,8,8);
//        mTextView.setText("测试log");
//        mBuild = new HandlerUtil.Build(getMainLooper()) {
//            @Override
//            public void receiveMessage(Message msg) {
//                if (msg.what == 10) {
//                    String result = (String) msg.obj;
//                    showLog("receiveMessage==" + result);
//                }
//            }
//        };
//
//    }



//    @OnClick(R.id.log_test_tv)
//    public void click(View v){
//        switch (v.getId()){
//            case R.id.log_test_tv:
//                showLog("我是来自MainActivity的log。。。");
//                AppUtil.test();
//
//                mBuild.builer().sendMessage(10, "我是来自MainActivity的message");
//                break;
//        }
//
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            String tag = intent.getStringExtra("tag");
            Log.e("Main--onNewIntent:",tag);
        }
    }
//
    public void click(View view ){
        Intent intent = new Intent(this,TestActivity.class);
        intent.putExtra("tag","MainActivity...");
        startActivity(intent);
    }
}
