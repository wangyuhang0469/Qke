package com.example.wang.qke.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/8/13.
 */
public class Filter2 {

    private List<RegistrationArea> list;
    private List<RegistrationArea> newList;


    public Filter2(List<RegistrationArea> list) {
        this.list = list;
        this.newList = new ArrayList<>();
    }

    public List<RegistrationArea> filtered(String m){
        for (int i = 0; i < list.size() ; i++){
            RegistrationArea RegistrationArea = list.get(i);
            if (RegistrationArea.getRegistrationAreaName().contains(m)){
                newList.add(RegistrationArea);
            }
        }
        return newList;
    }



    public List<RegistrationArea> getOldList(){
        return  this.list;
    }


}