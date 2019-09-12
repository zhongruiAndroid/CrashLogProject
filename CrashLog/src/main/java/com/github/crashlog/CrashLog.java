package com.github.crashlog;

import android.content.Context;

/***
 *   created by android on 2019/8/27
 */
public class CrashLog implements Thread.UncaughtExceptionHandler {
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

    public CrashLogHelper init(Context context) {
        this.context = context;
        if (this.uncaughtException == null) {
            this.uncaughtException = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        return CrashLogHelper.get();
    }

    public void uncaughtException(Thread t, Throwable throwable) {
        CrashLogHelper.get().saveLog(context,throwable);
        if (this.uncaughtException!=null&&this.uncaughtException.equals(this)==false) {
            this.uncaughtException.uncaughtException(t, throwable);
        }

    }
}
