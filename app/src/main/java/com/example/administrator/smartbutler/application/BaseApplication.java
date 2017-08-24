package com.example.administrator.smartbutler.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;


import cn.bmob.v3.Bmob;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.application
 * 文件名:   BaseApplication
 * 创建者:   LDW
 * 创建时间: 2017/7/17  15:49
 * 描述:    Application
 */


public class BaseApplication extends Application{

    //创建
    @Override
    public void onCreate() {
        super.onCreate();

        //Bmob初始化
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
