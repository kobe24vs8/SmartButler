package com.example.administrator.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.GirlData;
import com.example.administrator.smartbutler.utils.PicassoUtils;

import java.util.List;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.adapter
 * 文件名:   GirlAdapter
 * 创建者:   LDW
 * 创建时间: 2017/7/25  10:32
 * 描述:    美女社区适配器
 */
public class GirlAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<GirlData> mList;

    private WindowManager windowManager;
    private int width, height;

    public GirlAdapter(Context mContext, List<GirlData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //判断是否为第一次加载
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.publishedAt = (TextView) convertView.findViewById(R.id.tv_publishedAt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //解析图片
        GirlData data = mList.get(position);
        String url = data.getImgUrl();
        viewHolder.publishedAt.setText(data.getPublishedAt());
        PicassoUtils.LoadImageViewSize(mContext, url, width/2, 550, viewHolder.imageView);
        return convertView;
    }


    private class ViewHolder {
        private ImageView imageView;
        private TextView publishedAt;
    }

}
