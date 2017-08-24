package com.example.administrator.smartbutler.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.adapter.WechatAdapter;
import com.example.administrator.smartbutler.entity.WechatData;
import com.example.administrator.smartbutler.ui.PhoneActivity;
import com.example.administrator.smartbutler.ui.WebViewActivity;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WechatFragment extends Fragment {

    private ListView mListView;

    private List<WechatData> mList = new ArrayList<>();

    //标题
    private List<String> mList_title = new ArrayList<>();
    //新闻的Url
    private List<String> mList_url = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);

        findView(view);

        return view;
    }


    //初始化view
    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key= " + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
//                L.i(t);
                parsingJSON(t);
            }
        });


        //ListView的点击时间
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);

//                L.i("position:" + position);
                //向WebviewActivity传递数据
                intent.putExtra("title", mList_title.get(position));
//                L.i("title:" + mList_title.get(position));

                intent.putExtra("url", mList_url.get(position));
                startActivity(intent);
            }
        });
    }

    private void parsingJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WechatData data = new WechatData();

                String title = json.getString("title");

                String url = json.getString("url");

                data.setTitle(title);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);

                //获取到title和Url的集合
                mList_title.add(title);
                mList_url.add(url);
            }

            WechatAdapter adapter = new WechatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
