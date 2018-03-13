package com.example.wang.qke.classes;

import android.os.Message;

import java.io.Serializable;

import static com.example.wang.qke.ui.main.AdvisoryFragment.h3;
import static com.example.wang.qke.ui.main.MyselfFragment.h2;
import static com.example.wang.qke.ui.myself.PersonalFragment.h;

/**
 * Created by wang on 2017/7/31.
 */
public class User implements Serializable {

    private String uid = "";
    private String login="";
    private String mobNum="";
    private String role="";
    private String inviteCode ="";
    private String token="";
    private Boolean status =false;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(final Boolean status) {

        Thread thread = new Thread(){
            public void run(){
                Message msg = h.obtainMessage();
                Message msg2 = h2.obtainMessage();
                Message msg3 = h3.obtainMessage();
                if (status){
                    msg.what = 1;
                    msg2.what = 1;
                    msg3.what = 1;
                }else {
                    msg.what = 0;
                    msg2.what = 0;
                }
                h.sendMessage(msg);
                h2.sendMessage(msg2);
                h3.sendMessage(msg3);
            }
        };
        thread.start();


        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMobNum() {
        return mobNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clear(){
        this.uid="";
        this.login="";
        this.mobNum="";
        this.role="";
        this.inviteCode="";
        this.token="";
        this.setStatus(false);


    }




    private static class UserHolder {
                static final User INSTANCE = new User();
    }

            public static User getInstance() {
                return UserHolder.INSTANCE;
            }

            /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
            private User() {
            }

            /**
     * readResolve方法应对单例对象被序列化时候
     */
            private Object readResolve() {
               return getInstance();
            }
}
