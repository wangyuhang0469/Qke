package com.example.wang.qke.classes;

/**
 * Created by Administrator on 2018/3/15.
 */

public class WorkTime {

    String tv_startTime;
    String tv_endTime;


    String up_workTimeSoltOid;
    String up_workTimeSoltName;

    public String getTv_startTime() {
        return tv_startTime;
    }

    public void setTv_startTime(String tv_startTime) {
        this.tv_startTime = tv_startTime;
    }

    public String getTv_endTime() {
        return tv_endTime;
    }

    public void setTv_endTime(String tv_endTime) {
        this.tv_endTime = tv_endTime;
    }



    public String getUp_workTimeSoltOid() {
        return up_workTimeSoltOid;
    }

    public void setUp_workTimeSoltOid(String up_workTimeSoltOid) {
        this.up_workTimeSoltOid = up_workTimeSoltOid;
    }

    public String getUp_workTimeSoltName() {
        return tv_startTime + "-" + tv_endTime;
    }

    public void setUp_workTimeSoltName(String up_workTimeSoltName) {
        this.up_workTimeSoltName = up_workTimeSoltName;
    }

}
