package com.example.administrator.smartbutler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.WechatData;
import com.example.administrator.smartbutler.utils.L;
import com.example.administrator.smartbutler.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.adapter
 * 文件名:   WechatAdapter
 * 创建者:   LDW
 * 创建时间: 2017/7/24  16:26
 * 描述:    微信精选adapter
 */
public class WechatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<WechatData> mList;

    private int width, height;
    private WindowManager wm;

    public WechatAdapter(Context mContext, List<WechatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        L.i("Width:" + width + "Height:" + height);
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
        ViewHoder viewHoder = null;
        if (convertView == null) {
            viewHoder = new ViewHoder();
            convertView = inflater.inflate(R.layout.wechat_item, null);
            viewHoder.ed_title = (TextView) convertView.findViewById(R.id.ed_title);
            viewHoder.ed_source = (TextView) convertView.findViewById(R.id.ed_source);
            viewHoder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        //设置数据
        WechatData data = mList.get(position);

        viewHoder.ed_title.setText(data.getTitle());
        viewHoder.ed_source.setText(data.getSource());

        //加载图片
        PicassoUtils.LoadImageViewSize(mContext, data.getImgUrl(), width/3, 200, viewHoder.iv_img);

        return convertView;
    }


    class ViewHoder {
        private ImageView iv_img;
        private TextView ed_title;
        private TextView ed_source;
    }
}
