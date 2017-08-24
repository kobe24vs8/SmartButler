package com.example.administrator.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   UpdateActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/27  11:05
 * 描述:    TODO
 */
public class UpdateActivity extends BaseActivity {

    public static final int HANDLER_LOADING = 10001;
    public static final int HANDLER_OK = 10002;
    public static final int HANDLER_ON = 10003;
    
    private TextView tv_size;
    private String path;
    private String url;
    private NumberProgressBar number_progress_bar;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_LOADING:
                    //拿取数据
                    Bundle bundle = msg.getData();
                    long transferredBytes = bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    tv_size.setText(transferredBytes + "/" + totalSize);
                    //设置进度
                    int progress = (int) (((float) ((float) transferredBytes )/ ((float) totalSize)) * 100);
                    number_progress_bar.setProgress(progress);
                    break;
                case HANDLER_OK:
                    tv_size.setText("下载成功");
                    //启动应用安装
                    startInstallApk();
                    break;
                case HANDLER_ON:
                    tv_size.setText("下载失败");
                    break;
            }
        }
    };

    private void startInstallApk() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //判断系统的等级
        if (Build.VERSION.SDK_INT >= 24) {
            File file = new File(path);
            Uri apkUri = FileProvider.getUriForFile(UpdateActivity.this,
                    "com.example.smartbulter.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else  {
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update);
        
        initView();
    }

    private void initView() {

        number_progress_bar = (NumberProgressBar) findViewById(R.id.number_progress_bar);

        tv_size = (TextView) findViewById(R.id.tv_size);
        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";

        //下载(更新UI，只能在主线程)
        url = getIntent().getStringExtra("url");
        if (url != null) {
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    L.i("transferredBytes : " + transferredBytes + "totalSize : " + totalSize);

                    Message msg = new Message();
                    msg.what = HANDLER_LOADING;
                    Bundle bundle = new Bundle();
                    bundle.putLong("transferredBytes", transferredBytes);
                    bundle.putLong("totalSize", totalSize);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.e("成功");
                    handler.sendEmptyMessage(HANDLER_OK);
                }

                @Override
                public void onFailure(VolleyError error) {
                    L.e("失败");
                    handler.sendEmptyMessage(HANDLER_ON);
                }
            });
        }

    }
}
