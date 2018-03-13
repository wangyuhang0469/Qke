package com.example.wang.qke.classes;

/**
 * Created by wang on 2017/8/16.
 */

public class RegistrationArea {
    private String registrationAreaOid;
    private String registrationAreaName;

    public RegistrationArea(String registrationAreaOid, String registrationAreaName) {
        this.registrationAreaOid = registrationAreaOid;
        this.registrationAreaName = registrationAreaName;
    }

    public String getRegistrationAreaOid() {
        return registrationAreaOid;
    }

    public void setRegistrationAreaOid(String registrationAreaOid) {
        this.registrationAreaOid = registrationAreaOid;
    }

    public String getRegistrationAreaName() {
        return registrationAreaName;
    }

    public void setRegistrationAreaName(String registrationAreaName) {
        this.registrationAreaName = registrationAreaName;
    }
}
