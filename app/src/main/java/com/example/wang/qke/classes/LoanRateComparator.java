package com.example.wang.qke.classes;

import java.util.Comparator;

/**
 * Created by wang on 2017/8/13.
 */
public class LoanRateComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        LoanPlan a = (LoanPlan) lhs;
        LoanPlan b = (LoanPlan) rhs;
        float loanRateA = Float.parseFloat(a.getLoanRate());
        float loanRateB = Float.parseFloat(b.getLoanRate());

        return Float.compare (loanRateA ,loanRateB);
    }
}