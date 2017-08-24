package com.example.administrator.smartbutler.entity;

/*
 * 项目名：   SmartButler
 * 包名:     com.example.administrator.smartbutler.entity
 * 文件名:   CourierData
 * 创建者:   LDW
 * 创建时间: 2017/7/23  10:01
 * 描述:    快递的实体类
 */
public class CourierData {
    //时间
    private String datetime;
    //事件
    private String remark;
    //地点
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public String getRemark() {
        return remark;
    }

    public String getZone() {
        return zone;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CourierData{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
