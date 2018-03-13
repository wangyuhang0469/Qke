package com.example.wang.qke.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/8/13.
 */

public class RepaymentFilter {

    private List<LoanPlan> list;
    private List<LoanPlan> newList;


    public RepaymentFilter(List<LoanPlan> list) {
        this.list = list;
        this.newList = new ArrayList<>();
    }

    public List<LoanPlan> filtered(String m){
        for (int i = 0; i < list.size() ; i++){
            LoanPlan loanPlan = list.get(i);
            if (loanPlan.getRepayment().contains(m)){
                newList.add(loanPlan);
            }
        }
        return newList;
    }



    public List<LoanPlan> getOldList(){
        return  this.list;
    }


}
