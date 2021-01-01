package com.icebuf.alogger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.icebuf.alog.ALog;
import com.icebuf.alog.ALogTag;
import com.icebuf.alog.libsample.LogTest;


@ALogTag("app/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ALog.init(this, new AppLogTagModule());

        ALog.i("hello");
        ALog.d("sdadasdas");

        LogTest.show();
//        Test1.log();
        Test2.log();
    }
}