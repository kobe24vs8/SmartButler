package com.example.administrator.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   PhoneActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/23  15:04
 * 描述:    归属地查询
 */
public class PhoneActivity extends BaseActivity implements View.OnClickListener {


    //电话号码
    private EditText phone_number;
    //公司Logo
    private ImageView phone_company;
    //归属地信息
    private TextView phone_result;

    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_del, btn_query;

    //标志位
    Boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        inintView();
    }

    private void inintView() {
        phone_number = (EditText) findViewById(R.id.phone_number);
        phone_company = (ImageView) findViewById(R.id.phone_company);
        phone_result = (TextView) findViewById(R.id.phone_result);

        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);

        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);

        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);

        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);

        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);

        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);

        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);

        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);

        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);

        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);

        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按设置直接全部删除
                phone_number.setText("");
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {

        //1.获取输入框的内容
        String str = phone_number.getText().toString().trim();

        switch (v.getId()) {
            /*
            * 具体的逻辑
            * 1.获取输入框的内容
            * 2.判断是否为空
            * 3.网络请求
            * 4.解析JSON
            * 5.结果显示
            * ---------------
            * 逻辑键盘的实现
            *
            * */
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (flag) {
                    flag = false;
                    str  = "";
                    phone_number.setText("");
                }
                //每次结尾加1
                phone_number.setText(str + ((Button) v).getText());
                //移动光标加1
                phone_number.setSelection(str.length() + 1);
                break;

            case R.id.btn_del:
                //判断输入框是否为空
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    //每次结尾减去1
                    phone_number.setText(str.substring(0, str.length() - 1));
                    //移动光标减1
                    phone_number.setSelection(str.length() - 1);
                }
                break;

            case R.id.btn_query:
                getPhone(str);
                break;
        }

    }

    //获取归属地
    private void getPhone(String str) {
        //拼接Uri
        String url = "http://apis.juhe.cn/mobile/get?phone="
                        + str + "&key=" + StaticClass.PHONE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(PhoneActivity.this, t , Toast.LENGTH_SHORT).show();
                L.d("JSON:" + t );
                parsingJSON(t);
            }
        });
    }

    /*
    * {
        "resultcode":"200",
        "reason":"Return Successd!",
        "result":{
        "province":"浙江",
        "city":"杭州",
        "areacode":"0571",
        "zip":"310000",
        "company":"中国移动",
        "card":"移动动感地带卡"
      }
    * */

    //解析JSON数据
    private void parsingJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");

            //文字显示
            phone_result.setText("归属地: " + province + city + "   " +
                                 "区号:   " + areacode + "\n" +
                                 "邮编:   " + zip + "   " +
                                 "运营商: " + company + "\n" +
                                 "类型:   " + card);
            //图片显示
            switch (company) {
                case "移动":
                    phone_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    phone_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    phone_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }

            flag = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
