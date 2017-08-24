package com.example.administrator.smartbutler.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.utils
 * 文件名:   UtilsTools
 * 创建者:   LDW
 * 创建时间: 2017/7/17  16:13
 * 描述:    通用工具类管理
 */
public class UtilsTools {

    //字体的设置
    public static void setFont(Context mContext, TextView textView) {
        //设置字体
        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(typeFace);
    }

    //头像的保存
    public static void putImageToShare(Context mContext, ImageView imageView) {
        BitmapDrawable bitmapdrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapdrawable.getBitmap();
        //1.将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //2.利用base64将字节数组输出流转化为String
        byte [] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //3.保存到shareUtils
        ShareUtils.putString(mContext, "image_title", imgString);
    }

    //头像的获取
    public static void getImageToShare(Context mContext, ImageView imageView) {
        //1.获取String
        String imgString = ShareUtils.getString(mContext, "image_title", "");
        if (!imgString.equals("")) {
            //2.利用base64转化String
            byte [] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }

    //获取版本号
    public static String getVersion(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知";
        }
    }
}
