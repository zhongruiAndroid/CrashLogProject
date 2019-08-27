package com.github.crashlog;

import android.os.Environment;

import java.io.File;

/***
 *   created by android on 2019/8/27
 */
public class CrashLog implements Thread.UncaughtExceptionHandler {
    private String rootPath=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"crash";
    private String nameSuffix=".log";
    private static CrashLog singleObj;
        private CrashLog() {
    }
    public static CrashLog get(){
        if(singleObj==null){
            synchronized (CrashLog.class){
                if(singleObj==null){
                    singleObj=new CrashLog();
                }
            }
        }
        return singleObj;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
