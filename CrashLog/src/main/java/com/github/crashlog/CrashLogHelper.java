package com.github.crashlog;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 *   created by android on 2019/9/12
 */
public class CrashLogHelper {
    private String defaultSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash";
    private String defaultFileNameSuffix = ".log";

    private String savePath=defaultSavePath;
    private String fileNameSuffix=defaultFileNameSuffix;

    private static CrashLogHelper singleObj;
    private CrashLogHelper() {
    }
    public static CrashLogHelper get() {
        if (singleObj == null) {
            synchronized (CrashLogHelper.class) {
                if (singleObj == null) {
                    singleObj = new CrashLogHelper();
                }
            }
        }
        return singleObj;
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    public void setFileNameSuffix(String fileNameSuffix) {
        this.fileNameSuffix = fileNameSuffix;
    }

    public void saveLog(Context context, Throwable throwable){
        String fileName="";
        saveLog(context,throwable,savePath,null,fileNameSuffix);
    }
    /***
     *
     * @param context
     * @param throwable 异常信息
     * @param path 文件夹路径
     * @param fileName 文件名
     * @param fileNameSuffix 文件名后缀
     */
    public void saveLog(Context context,Throwable throwable,String path,String fileName,String fileNameSuffix){
        StringWriter stringWriter=new StringWriter();
        PrintWriter printWriter=new PrintWriter(stringWriter);

        throwable.printStackTrace(printWriter);

        String logString = stringWriter.toString();

        Log.i("=====","====="+logString);
    }

}
