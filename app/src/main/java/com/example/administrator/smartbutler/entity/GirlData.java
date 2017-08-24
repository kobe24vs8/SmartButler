package com.example.administrator.smartbutler.entity;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.entity
 * 文件名:   GirlData
 * 创建者:   LDW
 * 创建时间: 2017/7/25  10:31
 * 描述:    妹子的数据
 */
public class GirlData {

    //妹子的Url
    private String imgUrl;

    //具体更新的时间
    private String publishedAt;

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
