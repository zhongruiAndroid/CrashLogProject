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

    public CrashLog init(Context context) {
        if(context==null){
            throw new IllegalStateException("CrashLog.init() context is null");
        }
        this.context = context;
        if (this.uncaughtException == null) {
            this.uncaughtException = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        return this;
    }

    public void uncaughtException(Thread t, Throwable throwable) {
        CrashLogHelper.get().saveLog(context,throwable);
        if (this.uncaughtException!=null&&this.uncaughtException.equals(this)==false) {
            this.uncaughtException.uncaughtException(t, throwable);
        }
    }

    public CrashLog setSavePath(String savePath) {
        CrashLogHelper.get().setSavePath(savePath);
        return this;
    }
    public CrashLog setExtraLogInfo(String extraLogInfo) {
        CrashLogHelper.get().setExtraLogInfo(extraLogInfo);
        return this;
    }
    public CrashLog setFileNameSuffix(String fileNameSuffix) {
        CrashLogHelper.get().setFileNameSuffix(fileNameSuffix);
        return this;
    }
}
