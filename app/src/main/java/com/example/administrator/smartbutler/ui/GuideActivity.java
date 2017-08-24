package com.example.administrator.smartbutler.ui;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.smartbutler.MainActivity;
import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.UtilsTools;

import java.util.ArrayList;
import java.util.List;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.fragment
 * 文件名:   GuideActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/18  15:25
 * 描述:    TODO
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    //ViewPager
    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    private View view1, view2, view3;
    //小圆点
    private ImageView point1, point2, point3;
    //跳过
    private ImageView iv_back;
    //字
    private TextView tv_one, tv_two, tv_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        //初始化ViewPager
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewpager);

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);

        //加载布局
        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);


        //设置字体
        tv_one = (TextView) view1.findViewById(R.id.tv_one);
        tv_two = (TextView) view2.findViewById(R.id.tv_two);
        tv_three = (TextView) view3.findViewById(R.id.tv_three);
        UtilsTools.setFont(this, tv_one);
        UtilsTools.setFont(this, tv_two);
        UtilsTools.setFont(this, tv_three);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);



        //进入主界面的按钮
        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        //把布局加载到容器中
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //小圆点的初始状态
        setPointImg(true, false, false);

        //监听ViewPager的滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //Pager的切换
            @Override
            public void onPageSelected(int position) {
                L.i("position" + position);

                switch (position) {
                    case 0:
                        setPointImg(true, false, false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false, true, false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false, false, true);
                        iv_back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //把布局加载到ViewPager中
        mViewPager.setAdapter(new GuideAdapter());

    }

    //设计按钮的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
            case R.id.iv_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //替换布局
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //删除布局
            ((ViewPager) container).removeView(mList.get(position));
          //  super.destroyItem(container, position, object);
        }
    }

    //小圆点的选中图片
    private void setPointImg(Boolean isCheck1, Boolean isCheck2, Boolean isCheck3) {
        if (isCheck1) {
            point1.setImageResource(R.drawable.point_on);
        } else {
            point1.setImageResource(R.drawable.point_off);
        }

        if (isCheck2) {
            point2.setImageResource(R.drawable.point_on);
        } else {
            point2.setImageResource(R.drawable.point_off);
        }

        if (isCheck3) {
            point3.setImageResource(R.drawable.point_on);
        } else {
            point3.setImageResource(R.drawable.point_off);
        }

    }

}
