//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.crashlog;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExceptionAnalysis {
    private static ExceptionAnalysis a = new ExceptionAnalysis();
    private boolean b = false;
    private Context c;
    public ExceptionAnalysis.Callback mCallback;

    public static ExceptionAnalysis getInstance() {
        return a;
    }

    private ExceptionAnalysis() {
    }

    public ExceptionAnalysis(ExceptionAnalysis.Callback var1) {
        this.mCallback = var1;
    }

    public void openExceptionAnalysis(Context var1, boolean var2) {
        if (var1 != null) {
            this.c = var1.getApplicationContext();
        }

        if (this.c != null) {
            if (!this.b) {
                this.b = true;
                ak var3 = ak.a();
                var3.a(this.c);
                if (!var2) {
                }

            }
        }
    }

    public void saveCrashInfo(Context var1, Throwable var2, boolean var3) {
        if (var1 != null) {
            this.c = var1.getApplicationContext();
        }

        if (this.c != null) {
            String var4 = var2.toString();
            String var5 = "";
            if (!TextUtils.isEmpty(var4)) {
                try {
                    String[] var6 = var4.split(":");
                    if (var6.length > 1) {
                        var5 = var6[0];
                    } else {
                        var5 = var4;
                    }
                } catch (Exception var9) {
                    var5 = "";
                }
            }

            if (TextUtils.isEmpty(var5)) {
                var5 = var4;
            }

            StringWriter var10 = new StringWriter();
            var2.printStackTrace(new PrintWriter(var10));
            String var7 = var10.toString();
            byte var8 = 0;
            if (!var3) {
                if (var2 instanceof Exception) {
                    var8 = 11;
                } else if (var2 instanceof Error) {
                    var8 = 12;
                } else {
                    var8 = 13;
                }
            }

            this.saveCrashInfo(this.c, System.currentTimeMillis(), var7, var5, 0, var8);
        }
    }

    public void saveCrashInfo(Context var1, long var2, String var4, String var5, int var6, int var7) {

    }

    private JSONObject a() {
        JSONObject var1 = new JSONObject();

        try {
            var1.put("app_session", 0);
        } catch (Exception var4) {
            ;
        }

        try {
            var1.put("failed_cnt", 0);
        } catch (Exception var3) {
        }

        return var1;
    }

    @SuppressLint({"NewApi"})
    private JSONObject a(Context var1) {
        ActivityManager var2 = (ActivityManager)var1.getSystemService(Context.ACTIVITY_SERVICE);
        if (var2 == null) {
            return null;
        } else {
            MemoryInfo var3 = new MemoryInfo();
            var2.getMemoryInfo(var3);
            JSONObject var4 = new JSONObject();

            try {
                if (VERSION.SDK_INT >= 16) {
                    var4.put("total", var3.totalMem);
                }

                var4.put("free", var3.availMem);
                var4.put("low", var3.lowMemory ? 1 : 0);
            } catch (Exception var6) {
                ;
            }

            return var4;
        }
    }

    public interface Callback {
        void onCallback(JSONObject var1);
    }
}
