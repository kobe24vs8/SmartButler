package com.example.administrator.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.example.administrator.smartbutler.view.DispatchLinearLayout;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.service
 * 文件名:   SmaService
 * 创建者:   LDW
 * 创建时间: 2017/7/26  13:45
 * 描述:    TODO
 */
public class SmaService extends Service implements View.OnClickListener {

    private SmsReceiver smsReceiver;
    private IntentFilter intentFilter;

    //Home键
    private HomeWatchReceiver homeWatchReceiver;

    private String smsPhone;

    private String smsContent;

    private WindowManager wm;
    private WindowManager.LayoutParams layoutparams;
    private DispatchLinearLayout mView;

    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initService();
    }

    public void initService() {
        L.i("init service");

        //动态注册
        intentFilter = new IntentFilter();
        intentFilter.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intentFilter.setPriority(Integer.MAX_VALUE);
        smsReceiver = new SmsReceiver();
        //注册
        registerReceiver(smsReceiver, intentFilter);

        homeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intent = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeWatchReceiver, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop service");
        //取消注册
        unregisterReceiver(smsReceiver);
    }



    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(StaticClass.SMS_ACTION)) {
                L.i("来短信了！");

                //获取短信的内容，返回的是一个Object的数组
                Object [] objects = (Object[]) intent.getExtras().get("pdus");
                //遍历数组得到相关的信息
                for (Object obj : objects) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    smsPhone = sms.getOriginatingAddress();
                    //内容
                    smsContent = sms.getMessageBody();
                    L.i("短信的内容: " + smsPhone + " : " + smsContent);

                    showWindow();
                }

            }
        }
    }

    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutparams = new WindowManager.LayoutParams();
        //定义宽高
        layoutparams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutparams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //定义标记
        layoutparams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutparams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutparams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);


        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);

        tv_phone.setText("发件人:" + smsPhone);
        tv_content.setText(smsContent);

        //添加view到窗口
        wm.addView(mView, layoutparams);

        mView.setDispatchEventKeyListener(mDispatchEventKeyListener);
    }

    private DispatchLinearLayout.DispatchEventKeyListener mDispatchEventKeyListener =
            new DispatchLinearLayout.DispatchEventKeyListener() {
        @Override
        public Boolean dispatchKeyEvent(KeyEvent event) {
            //判断是否按返回键
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                L.i("我按了返回键");
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                if (mView.getParent() != null) {
                    wm.removeView(mView);
                }
                break;
        }
    }

    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    private final static String SYSTEM_DIALOGS_REASON_KRY = "reason";
    private final static String SYSTEM_DIALOGS_HOME_KRY = "homekey";

    //监听home键的广播
    public class HomeWatchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOGS_REASON_KRY);
                if (reason.equals(SYSTEM_DIALOGS_HOME_KRY)) {
                    L.i("我按了返回键");
                    if (mView.getParent() != null) {
                        wm.removeView(mView);
                    }
                }
            }
        }
    }
}
