package com.example.administrator.smartbutler.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.service.SmaService;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.ShareUtils;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   SettingActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/18  9:26
 * 描述:    设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Switch sw_speak;

    private Switch sw_sms;

    private LinearLayout ll_update;
    private TextView tv_version;

    //版本名字和版本号
    private String versionName;
    private int versionCode;

    //活动传递的url
    private String url;

    //扫一扫
    private LinearLayout ll_scan;
    private TextView tv_scan;

    //分享
    private LinearLayout ll_qr;

    //百度地图
    private LinearLayout ll_my_location;

    //关于软件
    private LinearLayout ll_regard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定点击悬浮按钮触发的View
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {

        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);

        //初始状态的false，默认是关闭
        Boolean isSpeak = ShareUtils.getBoolean(this, "is_speak", false);
        sw_speak.setChecked(isSpeak);

        sw_sms = (Switch) findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);

        Boolean isSms = ShareUtils.getBoolean(this, "is_sms", false);
        sw_speak.setChecked(isSms);

        tv_version = (TextView) findViewById(R.id.tv_version);

        ll_update = (LinearLayout) findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);

        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(this);

        tv_scan = (TextView) findViewById(R.id.tv_sacn);

        ll_qr = (LinearLayout) findViewById(R.id.ll_qr);
        ll_qr.setOnClickListener(this);

        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
        ll_my_location.setOnClickListener(this);

        ll_regard = (LinearLayout) findViewById(R.id.ll_regard);
        ll_regard.setOnClickListener(this);

        //获取当前的版本号
        try {
            getVersionNameCode();
            tv_version.setText("检测版本: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sw_speak:
                //切换开关
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存开关现在的状态
                ShareUtils.putBoolean(this, "is_speak", sw_speak.isChecked());
                break;

            case R.id.sw_sms:
                //切换开关
                sw_sms.setSelected(!sw_speak.isSelected());
                //保存开关现在的状态
                ShareUtils.putBoolean(this, "is_sms", sw_speak.isChecked());
                if (sw_sms.isChecked()) {
                    startService(new Intent(this, SmaService.class));
                } else {
                    stopService(new Intent(this, SmaService.class));
                }
                break;

            case R.id.ll_update:
                /**
                 * 逻辑
                 * 1.请求服务器的配置文件，拿到code
                 * 2.比较
                 * 3.Dialog提示
                 * 4.跳转到更新界面，并把Url传递过去
                 */
                RxVolley.get(StaticClass.CHECK_UPDTE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("json: " + t);
                        parsingJSON(t);
                    }
                });
                break;

            case R.id.ll_scan:
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;

            case R.id.ll_qr:
                startActivity(new Intent(this, QrCodeActivity.class));
                break;

            case R.id.ll_my_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;

            case R.id.ll_regard:
                startActivity(new Intent(this, RegardActivity.class));
                break;
        }

    }
    //解析数据
    private void parsingJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int code = jsonObject.getInt("versionCode");
            String content = jsonObject.getString("content");
            url = jsonObject.getString("url");
            L.i("code: " + code);
            //判断版本号
            if (code > versionCode) {
                showUpdateDialog(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本啦")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    //获取版本号
    private void getVersionNameCode() throws PackageManager.NameNotFoundException{
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        versionName = info.versionName;
        versionCode = info.versionCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            tv_scan.setText(scanResult);
        }
    }
}
