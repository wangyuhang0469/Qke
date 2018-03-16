package com.example.wang.qke.classes;

/**
 * Created by Administrator on 2018/3/15.
 */

public class WorkDate {

    String tv_weekName;
    String tv_workDay;

    String up_workDayLabel;
    String up_bookingDateStr;

    public String getTv_weekName() {
        return tv_weekName;
    }

    public void setTv_weekName(String tv_weekName) {
        this.tv_weekName = tv_weekName;
    }

    public String getTv_workDay() {
        return tv_workDay.substring(5, 10);
    }

    public void setTv_workDay(String tv_workDay) {
        this.tv_workDay = tv_workDay;
    }

    public String getUp_workDayLabel() {
        return tv_weekName.substring(2);
    }

    public void setUp_workDayLabel(String up_workDayLabel) {
        this.up_workDayLabel = up_workDayLabel;
    }

    public String getUp_bookingDateStr() {
        return tv_workDay.substring(0, 10);
    }

    public void setUp_bookingDateStr(String up_bookingDateStr) {
        this.up_bookingDateStr = up_bookingDateStr;
    }
}
