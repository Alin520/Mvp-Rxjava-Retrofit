package com.alin.hourse.mvp.test;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alin.hourse.mvp.R;

/**
 * @创建者 hailin
 * @创建时间 2017/11/21 9:19
 * @描述 ${}.
 */

public class FirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);
//        Intent intent = getIntent();
//        if (intent != null) {
//            String tag = intent.getStringExtra("tag");
//            Log.e("FirstActivity:",tag);
//        }

        TestTool instance = TestTool.getInstance();
        Log.e("First--TestTool:",instance.toString());
        String curProcessName = getCurProcessName(this);
        Log.e("First--Process:",curProcessName);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            String tag = intent.getStringExtra("tag");
            Log.e("First--onNewIntent:",tag);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = new Intent(this,SecondActivity.class);
//        intent.putExtra("tag","FirstActivity...");
//        startActivity(intent);
//    }

    public void click(View view ){
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("tag","FirstActivity...");
        startActivity(intent);
    }

    String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
