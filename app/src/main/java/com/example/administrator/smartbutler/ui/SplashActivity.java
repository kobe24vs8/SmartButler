package com.example.administrator.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.smartbutler.MainActivity;
import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.ShareUtils;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.example.administrator.smartbutler.utils.UtilsTools;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   SplashActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/18  14:51
 * 描述:    闪屏页面的设置
 */
public class SplashActivity extends AppCompatActivity {


    /**
     *
     * 1.延时2000ms
     * 2.判断程序是否是第一次运行
     * 3.自定义字体
     * 4.全屏主题
     *
     */

    private TextView tv_splash;

    //延时2000ms
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()) {
                        //第一次运行，跳到引导页
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        //非第一次运行，跳到主界面
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //初始化View
        initView();
    }

    private void initView() {

        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilsTools.setFont(this, tv_splash);

    }

    private boolean isFirst() {
        //第一次运行就是默认的true
        Boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_ISFIRST, true);
        //第一次运行
        if (isFirst) {
            //执行第一次运行之后的操作，isFirst都是false
            ShareUtils.putBoolean(this, StaticClass.SHARE_ISFIRST, false);
            //第一运行
            return true;
        } else {
            return false;
        }
    }


    //禁止返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

