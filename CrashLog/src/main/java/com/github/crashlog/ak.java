//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.crashlog;

import android.content.Context;
import java.lang.Thread.UncaughtExceptionHandler;

class ak implements UncaughtExceptionHandler {
    private static final ak a = new ak();
    private UncaughtExceptionHandler b;
    private Context c;

    public static ak a() {
        return a;
    }

    private ak() {
    }

    public void a(Context var1) {
        this.c = var1;
        if (this.b == null) {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }

    }

    public void uncaughtException(Thread var1, Throwable var2) {
        ExceptionAnalysis.getInstance().saveCrashInfo(this.c, var2, true);
        if (!this.b.equals(this)) {
            this.b.uncaughtException(var1, var2);
        }

    }
}
