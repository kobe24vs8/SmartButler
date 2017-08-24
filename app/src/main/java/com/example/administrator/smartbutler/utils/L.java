package com.example.administrator.smartbutler.utils;

import android.util.Log;

import java.util.logging.LogRecord;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.utils
 * 文件名:   L
 * 创建者:   LDW
 * 创建时间: 2017/7/18  10:23
 * 描述:    Log的封装
 */
public class L {

    //设置开关,默认状态是开启
    private final static boolean DEBUG = true;
    //TAG
    private final static String TAG = "SmartButler";

    //四个等级DIWE
    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
