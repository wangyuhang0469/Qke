package com.example.wang.qke.classes;

import java.io.Serializable;

/**
 * Created by wang on 2017/8/17.
 */

public class Order implements Serializable{


    private String id;
    private String uid;
    private String realname;
    private String idNum;
    private String mobNum;
    private String event;
    private String bookTime;
    private String registrationAreaName;
    private String fzFileNo;
    private String bookCode;

    private String proveType;
    private String proveCode;
    private String houseName;
    private String state;
    private String createTime;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getRegistrationAreaName() {
        return registrationAreaName;
    }

    public void setRegistrationAreaName(String registrationAreaName) {
        this.registrationAreaName = registrationAreaName;
    }

    public String getFzFileNo() {
        return fzFileNo;
    }

    public void setFzFileNo(String fzFileNo) {
        this.fzFileNo = fzFileNo;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProveType() {
        return proveType;
    }

    public void setProveType(String proveType) {
        this.proveType = proveType;
    }

    public String getProveCode() {
        return proveCode;
    }

    public void setProveCode(String proveCode) {
        this.proveCode = proveCode;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
