package com.example.wang.qke.classes;

/**
 * Created by wang on 2017/8/12.
 */

public class OrderTime {

    private String startTime;
    private String endTime;
    private String workTimeSoltOid;

    private String workDay;
    private String isWork;
    private String workDayOid;
    private String weekName;

    private int btnId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkTimeSoltOid() {
        return workTimeSoltOid;
    }

    public void setWorkTimeSoltOid(String workTimeSoltOid) {
        this.workTimeSoltOid = workTimeSoltOid;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getIsWork() {
        return isWork;
    }

    public void setIsWork(String isWork) {
        this.isWork = isWork;
    }

    public String getWorkDayOid() {
        return workDayOid;
    }

    public void setWorkDayOid(String workDayOid) {
        this.workDayOid = workDayOid;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public int getBtnId() {
        return btnId;
    }

    public void setBtnId(int btnId) {
        this.btnId = btnId;
    }
}
