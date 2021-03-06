package com.example.administrator.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.ChatListData;

import java.util.List;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.adapter
 * 文件名:   ChatListAdapter
 * 创建者:   LDW
 * 创建时间: 2017/7/24  10:12
 * 描述:    机器人聊天Adapter
 */
public class ChatListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ChatListData> mList;

 //   private ChatListData data;

    public static final int VALUE_LEFT_TEXT = 1;
    public static final int VALUE_RIGHT_TEXT = 2;

    public ChatListAdapter(Context mContext, List<ChatListData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //根据type的值，加载不同的Item
        int type = getItemViewType(position);
        //判断是否第一次加载
        if (convertView == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item, null);
                    viewHolderLeftText.tv_left_text = (TextView) convertView.findViewById(R.id.btn_left_text);
                    //设置缓存(将ViewHolder存储到View中)
                    convertView.setTag(viewHolderLeftText);
                    break;

                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item, null);
                    viewHolderRightText.tv_right_text = (TextView) convertView.findViewById(R.id.btn_right_text);
                    //设置缓存(将ViewHolder存储到View中)
                    convertView.setTag(viewHolderRightText);
                    break;
            }

        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) convertView.getTag();
                    break;
            }
        }

        //设置数据
        ChatListData data = mList.get(position);
        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }

        return convertView;
    }

    //根据数据源的position返回指定的Item
    @Override
    public int getItemViewType(int position) {
        ChatListData date = mList.get(position);
        int type = date.getType();
        return type;
    }

    //返回所有的Layout的数据
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    class ViewHolderLeftText {
        private TextView tv_left_text;
    }

    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
