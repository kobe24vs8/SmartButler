package com.example.administrator.smartbutler.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.adapter.GirlAdapter;
import com.example.administrator.smartbutler.entity.GirlData;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.PicassoUtils;
import com.example.administrator.smartbutler.utils.StaticClass;
import com.example.administrator.smartbutler.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


public class GirlFragment extends Fragment {

    private GridView mGridView;
    private List<GirlData> mList = new ArrayList<>();
    private GirlAdapter adapter;
    private ImageView iv_imageView;



    //提示框
    private CustomDialog dialog;
    //保存图片的url
    private List<String> mGirlUrl = new ArrayList<>();
    //PhotoView
    private PhotoViewAttacher mAttacher;

    /**
     * 逻辑
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView图片缩放
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);

        findView(view);

        return view;
    }

    //初始化View
    private void findView(View view) {

        mGridView = (GridView) view.findViewById(R.id.mGridView);

        //Dialog提示框显示点击的图片
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, R.layout.dialog_girl,
                        R.style.Theme_dialog, Gravity.CENTER);
        //点击图片外的屏幕返回
        dialog.setCancelable(true);
        iv_imageView = (ImageView) dialog.findViewById(R.id.iv_imgView);

        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                L.i(t);
//                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                parsingJSON(t);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //加载图片
                PicassoUtils.LoadImageView(getActivity(), mGirlUrl.get(position), iv_imageView);
                //使用PhotoView缩放
                mAttacher = new PhotoViewAttacher(iv_imageView);
                //更新图片大小
                mAttacher.update();
                dialog.show();
            }
        });

    }

    private void parsingJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                String publishedAt = json.getString("publishedAt");

                //单独保存图片的url
                mGirlUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                data.setPublishedAt(publishedAt);
                mList.add(data);
            }
            adapter = new GirlAdapter(getActivity(), mList);
            //设置适配器
            mGridView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
