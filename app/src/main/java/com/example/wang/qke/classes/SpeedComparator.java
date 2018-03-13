package com.example.wang.qke.classes;

import java.util.Comparator;

/**
 * Created by wang on 2017/8/13.
 */

public class SpeedComparator implements Comparator{
    @Override
    public int compare(Object lhs, Object rhs) {
        LoanPlan a = (LoanPlan) lhs;
        LoanPlan b = (LoanPlan) rhs;
        float speedA = Float.parseFloat(a.getSpeed());
        float speedB = Float.parseFloat(b.getSpeed());

        return Float.compare (speedA ,speedB);
    }
}