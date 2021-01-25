package com.jwlks.blemonitor.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class Dlog {

    static final String TAG = "JWLK";

    private static AppCompatActivity mDebugger;
    private static boolean mDEBUG = false;

    public Dlog(AppCompatActivity appCompatActivity) {
        mDebugger = appCompatActivity;
    }

    public static boolean isDebuggable() {
        boolean debuggable = false;
        try {
            PackageManager pm = mDebugger.getApplicationContext().getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(mDebugger.getApplicationContext().getPackageName(), 0);
            debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
        }

        return debuggable;
    }

    /**
     * Log Level Error
     **/
    public static void e(String message) {
        if (isDebuggable()) Log.e(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Warning
     **/
    public static void w(String message) {
        if (isDebuggable()) Log.w(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Information
     **/
    public static void i(String message) {
        if (isDebuggable()) Log.i(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Debug
     **/
    public static void d(String message) {
        if (isDebuggable()) Log.d(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Verbose
     **/
    public static void v(String message) {
        if (isDebuggable()) Log.v(TAG, buildLogMsg(message));
    }


    private static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("] ");
        sb.append(message);

        return sb.toString();

    }
}
