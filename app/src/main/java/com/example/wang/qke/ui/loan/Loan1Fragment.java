package com.example.wang.qke.ui.loan;



import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class Loan1Fragment extends BaseFragment {



    @Bind(R.id.loanAmount)
    EditText loanAmount;

    private TextView textViews[] = new TextView[8];//{ge,shi,bai,qian,wan,shiwan,baiwan,qianwan};


    public Loan1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan1, container, false);
        ButterKnife.bind(this, view);


        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle.getString("str") != null) {

            String much = bundle.getString("str");
            loanAmount.setText(much);
        }
        getActivity().getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


//    public void setLoanAmount(){
//        if (loanAmount.getText().toString().length()!=0) {
//            LoanActivity loanActivity = (LoanActivity) getActivity();
//            loanActivity.loanAmount = loanAmount.getText().toString();
//            loanActivity.next1();
//
//        }else {
//            toast("请输入贷款金额");
//        }
//    }


}
