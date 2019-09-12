package com.github.crashlog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 *   created by android on 2019/9/12
 */
public class CrashLogHelper {
    private String defaultSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash";
    private String defaultFileNameSuffix = ".txt";

    private String extraLogInfo="";
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
        if(TextUtils.isEmpty(fileNameSuffix)){
            fileNameSuffix=defaultFileNameSuffix;
        }else if(fileNameSuffix.startsWith(".")==false){
            fileNameSuffix="."+fileNameSuffix;
        }
        this.fileNameSuffix = fileNameSuffix;
    }

    public void saveLog(Context context, Throwable throwable){
        int granted = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(granted==PackageManager.PERMISSION_GRANTED){
            saveLog(context,throwable,savePath,fileNameSuffix);
        }else{
            //如果没有写入sd卡根目录的权限，就将log日志保存到外部缓存目录
            String path = context.getExternalCacheDir() + File.separator+ "crash";
            saveLog(context,throwable,path,fileNameSuffix);
        }

    }
    /***
     *
     * @param context
     * @param throwable 异常信息
     * @param path 文件夹路径
     * @param fileNameSuffix 文件名后缀
     */
    public void saveLog(Context context,Throwable throwable,String path,String fileNameSuffix)  {
        String fileName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        File file=new File(path);
        if(file.exists()==false){
            file.mkdirs();
        }
        File fileSave = new File(path, fileName+fileNameSuffix);
        PrintWriter printWriter= null;
        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileSave)));
            printWriter.println(fileName);
            printWriter.println(extraLogInfo);
            printWriter.println();
            printWriter.println(getDeviceInfo(context));
            throwable.printStackTrace(printWriter);
            printWriter.close();
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setExtraLogInfo(String extraLogInfo) {
        if(TextUtils.isEmpty(extraLogInfo)){
            return;
        }
        this.extraLogInfo = extraLogInfo;
    }
    private StringBuilder getDeviceInfo(Context context){
        StringBuilder stringBuilder=new StringBuilder();
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            int labelRes = packageInfo.applicationInfo.labelRes;

            String appName = context.getResources().getString(labelRes);
            long versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            stringBuilder.append("App Name:");
            stringBuilder.append(appName);
            stringBuilder.append("\r\n");

            stringBuilder.append("App VersionCode:");
            stringBuilder.append(versionCode);
            stringBuilder.append("\r\n");

            stringBuilder.append("App VersionName:");
            stringBuilder.append(versionName);
            stringBuilder.append("\r\n");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        stringBuilder.append("系统 VersionCode:");
        stringBuilder.append(Build.VERSION.RELEASE);
        stringBuilder.append("\r\n");

        stringBuilder.append("手机制造商:");
        stringBuilder.append(Build.MANUFACTURER);
        stringBuilder.append("\r\n");

        stringBuilder.append("手机型号:");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append("\r\n");

        stringBuilder.append("手机CPU架构:");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stringBuilder.append(Arrays.toString(Build.SUPPORTED_ABIS));
        }else{
            stringBuilder.append(Build.CPU_ABI);
        }
        stringBuilder.append("\r\n");
        return stringBuilder;
    }

}
