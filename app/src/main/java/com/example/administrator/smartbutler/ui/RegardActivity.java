package com.example.administrator.smartbutler.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.UtilsTools;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   RegardActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/28  20:22
 * 描述:    关于软件
 */
public class RegardActivity extends BaseActivity {

    private CircleImageView profile_image;
    private ListView mListView;

    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_regard);

        //去除阴影
        getSupportActionBar().setElevation(0);

        initView();
    }

    private void initView() {
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        mListView = (ListView) findViewById(R.id.mListView);

        mList.add("应用名: " + getString(R.string.app_name));
        mList.add("版本号: " + UtilsTools.getVersion(this));
        mList.add("官网: www.imooc.com");

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
    }


}
