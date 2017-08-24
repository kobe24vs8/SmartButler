package com.example.administrator.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.utils
 * 文件名:   PicassoUtils
 * 创建者:   LDW
 * 创建时间: 2017/7/25  9:20
 * 描述:    图片的裁剪
 */
public class PicassoUtils {

    //图片的加载
    public static void LoadImageView(Context mContext, String url, ImageView imageView) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    //图片的加载(裁剪到指定大小)
    public static void LoadImageViewSize(Context mContext, String url, int width,
                                         int height, ImageView imageView) {
        Picasso.with(mContext)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    //图片加载(有默认图片)
    public static void LoadImageViewViewHolde(Context mContext, String url, int loadImg,
                                         int errorImg, ImageView imageView) {
        Picasso.with(mContext)
                .load(url)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }

    //裁剪图片
    public static void LoadImageViewCrop(Context mContext, String url, ImageView imageView) {
        Picasso.with(mContext)
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }


    //按比例进行裁剪 矩形
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "LIDAWEI"; }
    }

}
