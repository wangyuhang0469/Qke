package com.example.wang.qke.classes;

import java.util.Comparator;

/**
 * Created by wang on 2017/8/13.
 */

public class DurationComparator implements Comparator{
    @Override
    public int compare(Object lhs, Object rhs) {
        LoanPlan a = (LoanPlan) lhs;
        LoanPlan b = (LoanPlan) rhs;
        float durationA = Float.parseFloat(a.getDuration());
        float durationB = Float.parseFloat(b.getDuration());

        return Float.compare (durationB ,durationA);
    }
}