package com.example.administrator.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.view
 * 文件名:   DispatchLinearLayout
 * 创建者:   LDW
 * 创建时间: 2017/7/26  19:08
 * 描述:    事件分发
 */
public class DispatchLinearLayout extends LinearLayout {

    private DispatchEventKeyListener dispatchEventKeyListener;

    public DispatchEventKeyListener getDispatchEventKeyListener() {
        return dispatchEventKeyListener;
    }

    public void setDispatchEventKeyListener(DispatchEventKeyListener dispatchEventKeyListener) {
        this.dispatchEventKeyListener = dispatchEventKeyListener;
    }


    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //接口
    public static interface DispatchEventKeyListener {
        Boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空，说明调用了 去获取事件
        if (dispatchEventKeyListener != null) {
            return dispatchEventKeyListener.dispatchKeyEvent(event);
        }

        return super.dispatchKeyEvent(event);
    }
}


