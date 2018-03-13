package com.example.wang.qke.ui.loan;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.LoanPlan;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoanPlanActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.canLoan)
    TextView canLoan;
    @Bind(R.id.loanRate)
    TextView loanRate;
    @Bind(R.id.duration)
    TextView duration;
    @Bind(R.id.repayment)
    TextView repayment;
    @Bind(R.id.mortgage)
    TextView mortgage;


    private LoanPlan loanPlan;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_plan);
        ButterKnife.bind(this);


        Intent intent = this.getIntent();
        loanPlan = (LoanPlan) intent.getSerializableExtra("loanPlan");


        title.setText(loanPlan.getProduct());

        String url = "http://123.207.61.165/outside/loanplans/" + loanPlan.getId();


        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient());

        loanRate.setText(loanPlan.getLoanRate() + "%");
        duration.setText(loanPlan.getDuration() + "年");
        repayment.setText(loanPlan.getRepayment());
        String str = loanPlan.getCanLoan();
        if (str.length()>4) {
            str = str.substring(0, str.length() - 4);
        }else {
            str = "少于1";
        }
        canLoan.setText(str + "万");
        mortgage.setText(loanPlan.getMortgage()+"%");

//        product.setText(loanPlan.getProduct());
//        company.setText(loanPlan.getCompany());
//        loanAmount.setText(loanPlan.getLoanAmount() + "(万元)");
//        startTime.setText(loanPlan.getStartTime() + "至");
//        endTime.setText(loanPlan.getEndTime());
//        loanRate.setText(loanPlan.getLoanRate());
//        rateFloat.setText(loanPlan.getRateFloat());
//        duration.setText(loanPlan.getDuration() + "年");
//        propertyFloat.setText(loanPlan.getPropertyFloat());
//        repayment.setText(loanPlan.getRepayment());
//        minAge.setText(loanPlan.getMinAge());
//        maxAge.setText(loanPlan.getMaxAge());
//        overdueAmout.setText(loanPlan.getOverdueAmout());
//        speed.setText(loanPlan.getSpeed());


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
