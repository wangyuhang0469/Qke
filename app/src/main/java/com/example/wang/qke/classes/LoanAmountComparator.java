package com.example.wang.qke.classes;

import java.util.Comparator;

/**
 * Created by wang on 2017/8/13.
 */

public class LoanAmountComparator implements Comparator{
    @Override
    public int compare(Object lhs, Object rhs) {
        LoanPlan a = (LoanPlan) lhs;
        LoanPlan b = (LoanPlan) rhs;
        float loanAmountA = Float.parseFloat(a.getLoanAmount());
        float loanAmountB = Float.parseFloat(b.getLoanAmount());

        return Float.compare (loanAmountB ,loanAmountA);
    }
}