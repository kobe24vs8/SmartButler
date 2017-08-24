package com.example.administrator.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.smartbutler.MainActivity;
import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.MyUser;
import com.example.administrator.smartbutler.utils.ShareUtils;
import com.example.administrator.smartbutler.view.CustomDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   LoginActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/19  9:04
 * 描述:    登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button bsn_registered;
    private EditText etc_name;
    private EditText etc_password;
    private CheckBox keep_pass;
    private Button etc_login;
    private TextView tv_forget;

    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //初始化View
        initView();
    }

    private void initView() {

        bsn_registered = (Button) findViewById(R.id.bsn_registered);
        bsn_registered.setOnClickListener(this);

        etc_name = (EditText) findViewById(R.id.etc_name);
        etc_password = (EditText) findViewById(R.id.etc_password);

        etc_login = (Button) findViewById(R.id.etc_login);
        etc_login.setOnClickListener(this);

        keep_pass = (CheckBox) findViewById(R.id.keep_pass);

        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        //自定义的Dialog
        dialog = new CustomDialog(this, 1000, 1000, R.layout.dialog_loading,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态
        Boolean isChecked = ShareUtils.getBoolean(this, "keeppass", false); //初始状态不选中
        keep_pass.setChecked(isChecked);
        if (isChecked) {
            //设置密码
            etc_name.setText(ShareUtils.getString(this, "name", ""));
            etc_password.setText(ShareUtils.getString(this, "password", ""));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.bsn_registered:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.etc_login:
                //获取EditorText中的用户信息
                String name = etc_name.getText().toString().trim();
                String password = etc_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {

                    dialog.show();

                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {

                                dialog.dismiss();

                                //判断是否通过邮箱验证
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存程序退出之前的CheckBox的状态
        ShareUtils.putBoolean(this, "keeppass", keep_pass.isChecked());

        //如果选中就直接记住密码，否则删除密码
        if (keep_pass.isChecked()) {
            ShareUtils.putString(this, "name", etc_name.getText().toString().trim());
            ShareUtils.putString(this, "password", etc_password.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }

    }
}
