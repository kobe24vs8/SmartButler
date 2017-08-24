package com.example.administrator.smartbutler.entity;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.entity
 * 文件名:   WechatData
 * 创建者:   LDW
 * 创建时间: 2017/7/24  16:29
 * 描述:    TODO
 */
public class WechatData {

    //标题
    private String title;

    //消息来源
    private String source;

    //消息的Url
    private String newsUrl;

    //图片的url
    private String ImgUrl;

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
