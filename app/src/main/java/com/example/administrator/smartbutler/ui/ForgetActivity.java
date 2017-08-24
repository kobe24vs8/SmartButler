package com.example.administrator.smartbutler.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.smartbutler.R;
import com.example.administrator.smartbutler.entity.MyUser;
import com.example.administrator.smartbutler.utils.ShareUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.ui
 * 文件名:   ForgetActivity
 * 创建者:   LDW
 * 创建时间: 2017/7/19  17:13
 * 描述:    修改和重置密码
 */
public class ForgetActivity extends BaseActivity implements View.OnClickListener {

    private EditText btn_eamil;
    private Button btn_forget_passward;

    private EditText btn_old_passward;
    private EditText btn_new_passward;
    private EditText btn_input_passward;
    private Button btn_modify_passward;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {

        btn_eamil = (EditText) findViewById(R.id.btn_email);
        btn_forget_passward = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_passward.setOnClickListener(this);

        btn_old_passward = (EditText) findViewById(R.id.btn_old_passward);
        btn_new_passward = (EditText) findViewById(R.id.btn_new_passward);
        btn_input_passward = (EditText) findViewById(R.id.btn_input_passward);
        btn_modify_passward = (Button) findViewById(R.id.btn_modify_passward);
        btn_modify_passward.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                //1.获取输入框中的邮箱
                final String email = btn_eamil.getText().toString().trim();
                //2.判断是佛为空
                if (!TextUtils.isEmpty(email)) {
                    //3.邮箱重置密码
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetActivity.this, "邮件已发送至:" + email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgetActivity.this, "邮件发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "输入的邮箱不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_modify_passward:
                //1.获取输入框中的内容
                String old_passward = btn_old_passward.getText().toString().trim();
                String new_passward = btn_new_passward.getText().toString().trim();
                String input_passward = btn_input_passward.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(old_passward) && !TextUtils.isEmpty(new_passward)
                        && !TextUtils.isEmpty(input_passward)) {
                    //3.判断两次密码是否一致
                    if (new_passward.equals(input_passward)) {
                        //4.重置密码
                        MyUser.updateCurrentUserPassword(old_passward, new_passward, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetActivity.this, "密码重置成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetActivity.this, "密码重置失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ForgetActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgetActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
