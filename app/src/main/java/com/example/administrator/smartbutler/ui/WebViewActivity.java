package com.example.administrator.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.L;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   WebViewActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/24  18:25
 * 描述:    TODO
 */
public class WebViewActivity extends BaseActivity {

    private ProgressBar pro_bar;
    private WebView mwebview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);

        initView();
    }

    //初始化View
    private void initView() {

        mwebview = (WebView) findViewById(R.id.mwebview);
        pro_bar = (ProgressBar) findViewById(R.id.pro_bar);

        //webViewActivity接受到WeChatFragment传递过来的数据
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");

 //       L.i("title" + title);
 //      L.i("url:" + url);
        //设置标题
        getSupportActionBar().setTitle(title);

        //支持JS
        mwebview.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mwebview.getSettings().setSupportZoom(true);
        mwebview.getSettings().setBuiltInZoomControls(true);

        //接口回调
        mwebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //加载完毕，把进度条隐藏
                    pro_bar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        //不需要外调浏览器打开
        mwebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mwebview.loadUrl(url);
    }
}
