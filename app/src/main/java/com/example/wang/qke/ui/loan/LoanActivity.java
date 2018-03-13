package com.example.wang.qke.ui.loan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoanActivity extends BaseActivity {

//    @Bind(R.id.first)
//    LinearLayout first;
    @Bind(R.id.second)
    LinearLayout second;
    @Bind(R.id.third)
    LinearLayout third;
    @Bind(R.id.next)
    Button next;

    public String uid;
    public String loanAmount;
    public String step;
    public String age;
    public String overdueLoan;
    public String loanFrequency;

    public String mobNum;
    @Bind(R.id.previous)
    TextView previous;


//    private Loan1Fragment loan1Fragment;
    private Loan2Fragment loan2Fragment;
    private Loan3Fragment loan3Fragment;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private int i = 0;

    public boolean lock = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan1);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = this.getIntent();
        String m = intent.getStringExtra("much");


//        loan1Fragment = new Loan1Fragment();
        loan2Fragment = new Loan2Fragment();
        loan3Fragment = new Loan3Fragment();


        Bundle bundle = new Bundle();
        bundle.putString("str", m);
        loan2Fragment.setArguments(bundle);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.loan_content, loan3Fragment).commit();
        second.setSelected(true);


    }


    @OnClick(R.id.next)
    public void onViewClicked() {
        ft = fm.beginTransaction();
        switch (i) {
//            case 0:
//                loan1Fragment.setLoanAmount();
//
//                break;
//            case 1:
//                loan3Fragment.addRequestQueue();
//
//                break;
//            case 2:
//                loan2Fragment.commit();
//                break;



            case 0:
                if (lock) {
                    lock = false;
                    loan3Fragment.addRequestQueue();
                }
                break;


            case 1:
                loan2Fragment.commit();

                break;

        }


    }

    @OnClick(R.id.close)
    public void onCloseClicked() {
        finish();
    }

//    public void next1() {
//        ft.replace(R.id.loan_content, loan3Fragment).commit();
//        first.setSelected(false);
//        second.setSelected(true);
//        next.setText("提交信息");
//        previous.setVisibility(View.VISIBLE);
//        i++;
//    }

    public void next2() {
        ft.replace(R.id.loan_content, loan2Fragment).commit();
        second.setSelected(false);
        third.setSelected(true);
        next.setText("提  交");
        previous.setVisibility(View.VISIBLE);
        i++;
    }


    @OnClick(R.id.previous)
    public void onView2Clicked() {


        ft = fm.beginTransaction();
        switch (i) {
//            case 0:
//                break;
//            case 1:
//                ft.replace(R.id.loan_content, loan1Fragment).commit();
//                first.setSelected(true);
//                second.setSelected(false);
//                previous.setVisibility(View.GONE);
//                i--;
//                break;
//            case 2:
//                ft.replace(R.id.loan_content, loan3Fragment).commit();
//                second.setSelected(true);
//                third.setSelected(false);
//                i--;
//                break;


            case 0:
//                ft.replace(R.id.loan_content, loan1Fragment).commit();
//                first.setSelected(true);
//                second.setSelected(false);
//                previous.setVisibility(View.GONE);
//                i--;
                break;
            case 1:

                    ft.replace(R.id.loan_content, loan3Fragment).commit();
                    second.setSelected(true);
                    third.setSelected(false);
                    previous.setVisibility(View.GONE);
                    next.setText("下一步");
                    i--;
                    break;

        }


    }
}
