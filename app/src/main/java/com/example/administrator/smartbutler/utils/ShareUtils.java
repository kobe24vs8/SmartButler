package com.example.administrator.smartbutler.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.utils
 * 文件名:   ShareUtils
 * 创建者:   LDW
 * 创建时间: 2017/7/18  10:53
 * 描述:    SharePreference的封装类
 */
public class ShareUtils {

    //数据库的名字
    public static String NAME = "config";

    //键key  值value
    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    //键key 默认值
    public  static String getString(Context mContext, String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    //键key  值value
    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    //键key 默认值
    public  static int getInt(Context mContext, String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    //键key  值value
    public static void putBoolean(Context mContext, String key, Boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    //键key 默认值
    public  static Boolean getBoolean(Context mContext, String key, Boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    //删除单个数据
    public static void deleShare(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //删除全部数据
    public static void deleAll(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
