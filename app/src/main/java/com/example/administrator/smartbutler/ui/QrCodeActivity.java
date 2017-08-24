package com.example.administrator.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.administrator.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   QrCodeActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/28  15:44
 * 描述:    TODO
 */
public class QrCodeActivity extends BaseActivity {


    private ImageView iv_qrcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrcode);

        initView();
    }

    private void initView() {

        iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);

        int width = getResources().getDisplayMetrics().widthPixels;

        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width / 2, width / 2,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qrcode.setImageBitmap(qrCodeBitmap);
    }
}
