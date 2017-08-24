package com.example.administrator.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.entity
 * 文件名:   MyUser
 * 创建者:   LDW
 * 创建时间: 2017/7/19  10:54
 * 描述:    个人用户信息
 */
public class MyUser extends BmobUser {

    private int age;
    private Boolean sex;
    private String desc;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
