package com.github.crashlog;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/***
 *   created by android on 2019/8/27
 */
public class CrashLog implements Thread.UncaughtExceptionHandler {
    private String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash";
    private String nameSuffix = ".log";
    private Context context;
    private Thread.UncaughtExceptionHandler uncaughtException;
    private static CrashLog singleObj;
    private CrashLog() {
    }

    public static CrashLog get() {
        if (singleObj == null) {
            synchronized (CrashLog.class) {
                if (singleObj == null) {
                    singleObj = new CrashLog();
                }
            }
        }
        return singleObj;
    }

    public void init(Context context) {
        this.context = context;
        if (this.uncaughtException == null) {
            this.uncaughtException = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public void uncaughtException(Thread t, Throwable e) {
        ExceptionAnalysis.getInstance().saveCrashInfo(this.context, e, true);
        if (this.uncaughtException!=null&&this.uncaughtException.equals(this)==false) {
            this.uncaughtException.uncaughtException(t, e);
        }

    }
}
