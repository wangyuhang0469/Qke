package com.example.wang.qke.ui.tools;

import android.os.Bundle;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseDetailsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_amount)
    TextView tvAmount;
    @Bind(R.id.tv_loanKeep)
    TextView tvLoanKeep;
    @Bind(R.id.tv_averagePrice)
    TextView tvAveragePrice;
    @Bind(R.id.tv_taxTotal)
    TextView tvTaxTotal;
    @Bind(R.id.tv_buildingArea)
    TextView tvBuildingArea;
    @Bind(R.id.tv_owner)
    TextView tvOwner;
    @Bind(R.id.tv_houseYear)
    TextView tvHouseYear;
    @Bind(R.id.tv_transactionPrice)
    TextView tvTransactionPrice;
    @Bind(R.id.tv_registerPrice)
    TextView tvRegisterPrice;
    @Bind(R.id.tv_time)
    TextView tvTime;

    private String taxTotal;
    private String loanKeep;
    private String averagePrice;
    private String amount;
    private String buildingArea;
    private String time;

    private String name ;
    private String transactionPrice ;
    private String houseYear ;
    private String registerPrice ;
    private String owner ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);
        ButterKnife.bind(this);

        title.setText("房价查询");


        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        name = bundle.getString("name");
        averagePrice = bundle.getString("averagePrice");
        amount = bundle.getString("amount");
        buildingArea = bundle.getString("buildingArea");
        time = bundle.getString("time");


        taxTotal = bundle.getString("taxTotal");
        loanKeep = bundle.getString("loanKeep");
        houseYear = bundle.getString("houseYear");
        registerPrice = bundle.getString("registerPrice");
        owner = bundle.getString("owner");

        transactionPrice = bundle.getString("transactionPrice");


        tvName.setText(name);
        tvBuildingArea.setText("建筑面积："+buildingArea+"(㎡)");
        tvLoanKeep.setText("0元");
        tvTaxTotal.setText("预估税费：0元");
        tvAveragePrice.setText("预估单价："+averagePrice+"元");
        tvAmount.setText(amount+"元");
        tvTime.setText("申请时间："+time);


        if (houseYear != null) {


            if (owner.equals("1")){
                owner = "单位";
            }else {
                owner = "个人";
            }

            if (houseYear.equals("1")){
                houseYear= "满2年";
            }else if (houseYear.equals("2")){
                houseYear= "满5年";
            }else{
                houseYear = "未满2年";
            }


            tvTaxTotal.setText("预估税费："+taxTotal+"元");
            tvLoanKeep.setText(loanKeep+"元");

            tvHouseYear.setText("购房期限：" + houseYear);
            tvRegisterPrice.setText("登记价格：" + registerPrice+"元");
            tvOwner.setText("权利人：" + owner);
            if (transactionPrice != null) {
                tvTransactionPrice.setText("成交价格：" + transactionPrice+"元");
            }else {
                tvTransactionPrice.setVisibility(TextView.GONE);
            }
        }else {
            tvHouseYear.setVisibility(TextView.GONE);
            tvRegisterPrice.setVisibility(TextView.GONE);
            tvOwner.setVisibility(TextView.GONE);
            tvTransactionPrice.setVisibility(TextView.GONE);
        }

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
