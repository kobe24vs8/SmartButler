package com.example.administrator.smartbutler;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.smartbutler.fragment.BulterFragment;
import com.example.administrator.smartbutler.fragment.GirlFragment;
import com.example.administrator.smartbutler.fragment.UserFragment;
import com.example.administrator.smartbutler.fragment.WechatFragment;
import com.example.administrator.smartbutler.ui.SettingActivity;
import com.example.administrator.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Tablayout
    private TabLayout mTablayout;
    //Viewpager
    private ViewPager mViewpager;
    //title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;

    //悬浮按钮
    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去除ToolBar的阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();

    }


    //初始化数据
    private void initData() {

        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.text_bulter));
        mTitle.add(getString(R.string.text_wechat));
        mTitle.add(getString(R.string.text_girl));
        mTitle.add(getString(R.string.text_user));

        mFragment = new ArrayList<>();

        mFragment.add(new BulterFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());

    }

    //初始化view
    private void initView() {
        mTablayout = (TabLayout) findViewById(R.id.mTablayout);
        mViewpager = (ViewPager) findViewById(R.id.mViewpager);
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);

        fab_setting.setOnClickListener(this);

        //默认状态是隐藏悬浮按钮
        fab_setting.setVisibility(View.GONE);

        //预加载
        mViewpager.setOffscreenPageLimit(mFragment.size());

        //ViewPager的滑动监听
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //监听事件
                Log.i("TAG", "onPageSelected: " + position);

                if (position == 0) {
                    //隐藏悬浮按钮
                    fab_setting.setVisibility(View.GONE);
                } else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            //选中Item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回Item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置Item的标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }

        });

        //ViewPaget进行绑定到TabLayout
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

}
