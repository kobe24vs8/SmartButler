package com.example.administrator.smartbutler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.CourierData;

import java.util.List;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.adapter
 * 文件名:   CourierAdapter
 * 创建者:   LDW
 * 创建时间: 2017/7/23  10:06
 * 描述:    快递查询
 */
public class CourierAdapter extends BaseAdapter {

    private Context mcontext;
    private List<CourierData> mList;

    //布局加载器
    private LayoutInflater inflater;

    private CourierData data;


    public CourierAdapter(Context mcontext, List<CourierData> mList) {
        this.mcontext = mcontext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //判断是否第一次加载
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_courier_item, null);
            viewHolder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
            //设置缓存(将ViewHolder存储到View中)
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        data = mList.get(position);

        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
