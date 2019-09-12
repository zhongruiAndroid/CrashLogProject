package com.test.crashlog;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.crashlog.CrashLog;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btCrash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashLog.get()
                .init(this)
//                .setFileNameSuffix(".txt")
//                .setSavePath(Environment.getExternalStorageDirectory()+"/12019")
//                .setExtraLogInfo(".cafasfd")
        ;

        initView();
    }

    private void initView() {
        btCrash=findViewById(R.id.btCrash);
        btCrash.setOnClickListener(this);

        Button btCrash2=findViewById(R.id.btCrash2);
        btCrash2.setOnClickListener(this);

        findViewById(R.id.btCrash3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btCrash:
                testCrash1();
            break;
            case R.id.btCrash2:
                testCrash2();
            break;
            case R.id.btCrash3:
                testCrash3();
            break;
        }
    }


    private void testCrash1() {
        throw new IllegalStateException("测试崩溃");
    }
    private void testCrash2() {
        int a=1/0;
    }
    private void testCrash3() {
        int a[]={1,2};
        try {
            int i = a[2];
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
