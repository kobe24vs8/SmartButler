package com.example.administrator.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.adapter.CourierAdapter;
import com.example.administrator.smartbutler.entity.CourierData;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   CourierActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/21  16:09
 * 描述:    快递查询
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button et_get_courier;
    private ListView mListView;

    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_courier);

        initView();
    }
    //初始化View
    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);

        et_get_courier = (Button) findViewById(R.id.et_get_courier);
        et_get_courier.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_get_courier:
                /*
                * 1.获取输入框的内容
                * 2.判断是否为空
                * 3.拿到数据去请求数据(JSON)
                * 4.解析JSON
                * 5.listView适配器
                * 6.实体类(Item)
                * 7.显示效果
                *
                * */
                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接Uri
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY +
                                "&com=" + name + "&no=" + number;

                //2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //拿到数据去请求JSON
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
//                            Toast.makeText(CourierActivity.this, t , Toast.LENGTH_SHORT).show();
                            L.d("JSON:" + t );
                            parsingJSON(t);
                        }
                    });

                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //数据的解析
    private void parsingJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));

                mList.add(data);
            }
            //数据倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            mListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
