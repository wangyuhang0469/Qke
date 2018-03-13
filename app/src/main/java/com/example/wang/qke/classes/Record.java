package com.example.wang.qke.classes;

import java.io.Serializable;

/**
 * Created by wang on 2017/8/21.
 */

public class Record implements Serializable{
    private String id;
    private String createTime;
    private String uid;
    private String inqueryWay;
    private String certType;
    private String certNo;
    private String certYear;
    private String idNum;
    private String epName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInqueryWay() {
        return inqueryWay;
    }

    public void setInqueryWay(String inqueryWay) {
        this.inqueryWay = inqueryWay;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertYear() {
        return certYear;
    }

    public void setCertYear(String certYear) {
        this.certYear = certYear;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }
}
