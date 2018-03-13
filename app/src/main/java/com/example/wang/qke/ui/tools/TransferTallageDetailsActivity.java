package com.example.wang.qke.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.TransferTallage;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferTallageDetailsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.owner)
    TextView owner;
    @Bind(R.id.idNum_ownerName)
    TextView idNumOwnerName;
    @Bind(R.id.zslx)
    TextView zslx;
    @Bind(R.id.certNo)
    TextView certNo;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.housePrice)
    TextView housePrice;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.allTax)
    TextView allTax;
    @Bind(R.id.suggest)
    TextView suggest;
    @Bind(R.id.valueAddedTax)
    TextView valueAddedTax;
    @Bind(R.id.maintenanceTax)
    TextView maintenanceTax;
    @Bind(R.id.educationSurtax)
    TextView educationSurtax;
    @Bind(R.id.localEducationSurtax)
    TextView localEducationSurtax;
    @Bind(R.id.personalTax)
    TextView personalTax;
    @Bind(R.id.transactionTax)
    TextView transactionTax;
    @Bind(R.id.procedureTax)
    TextView procedureTax;
    @Bind(R.id.checkExpense)
    TextView checkExpense;
    @Bind(R.id.stampDuty)
    TextView stampDuty;
    @Bind(R.id.appliqueExpense)
    TextView appliqueExpense;
    @Bind(R.id.tv001)
    TextView tv001;

    private TransferTallage transferTallage = new TransferTallage();
    private String hasTax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tallage);
        ButterKnife.bind(this);

        title.setText("过户价/税费详情");

        Intent intent = this.getIntent();
        transferTallage = (TransferTallage) intent.getSerializableExtra("transferTallage");

        updata();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                transferTallage = (TransferTallage) data.getSerializableExtra("result");

                updata();

                break;
            default:
                break;
        }
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.btn)
    public void onView2Clicked() {

        Intent intent = new Intent(this, TallageOnlyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("transferTallage", transferTallage);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);

    }

    public void updata() {

        owner.setText("权利人：" + transferTallage.getOwner());
        if (transferTallage.getOwner().equals("个人")) {
            idNumOwnerName.setText("身份证：" + transferTallage.getIdNo());
        } else {
            idNumOwnerName.setText("单位：" + transferTallage.getOwnerName());
        }
        zslx.setText("证书类型：" + transferTallage.getZslx());
        if (transferTallage.getZslx().equals("房地产权证书")) {
            certNo.setText("房产证号：" + transferTallage.getCertNo());
        } else {
            certNo.setText("房产证号：" + transferTallage.getCertNo() + "    粤" + transferTallage.getSelectBdc());
        }

        desc.setText(transferTallage.getDesc());
        housePrice.setText(transferTallage.getHousePrice() + "元");

        hasTax = transferTallage.getHasTax();

        if (hasTax.equals("0")) {
            ll.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
        } else {
            ll.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);

            valueAddedTax.setText(transferTallage.getValueAddedTax()+"元");
            maintenanceTax.setText(transferTallage.getMaintenanceTax()+"元");
            educationSurtax.setText(transferTallage.getEducationSurtax()+"元");
            localEducationSurtax.setText(transferTallage.getLocalEducationSurtax()+"元");
            stampDuty.setText(transferTallage.getStampDuty()+"元");
            transactionTax.setText(transferTallage.getTransactionTax()+"元");
            procedureTax.setText(transferTallage.getProcedureTax()+"元");
            appliqueExpense.setText(transferTallage.getAppliqueExpense()+"元");
            checkExpense.setText(transferTallage.getCheckExpense()+"元");

            double valueAddedTaxs = Double.parseDouble((transferTallage.getValueAddedTax()));
            double maintenanceTax = Double.parseDouble((transferTallage.getMaintenanceTax()));
            double educationSurtax = Double.parseDouble((transferTallage.getEducationSurtax()));
            double localEducationSurtax = Double.parseDouble((transferTallage.getLocalEducationSurtax()));
            double stampDuty = Double.parseDouble((transferTallage.getStampDuty()));
            double transactionTax = Double.parseDouble((transferTallage.getTransactionTax()));
            double procedureTax = Double.parseDouble((transferTallage.getProcedureTax()));
            double appliqueExpense = Double.parseDouble((transferTallage.getAppliqueExpense()));
            double checkExpense = Double.parseDouble((transferTallage.getCheckExpense()));

            double personalTaxCheck = Double.parseDouble((transferTallage.getPersonalTaxCheck()));
            double personalTaxFerry  = Double.parseDouble(transferTallage.getPersonalTaxFerry());
            double allTaxFerry = valueAddedTaxs + maintenanceTax + educationSurtax + localEducationSurtax + stampDuty + personalTaxFerry + transactionTax +procedureTax + checkExpense + appliqueExpense;
            double allTaxCheek = valueAddedTaxs + maintenanceTax + educationSurtax + localEducationSurtax + stampDuty + personalTaxCheck + transactionTax +procedureTax + checkExpense + appliqueExpense;
            String allTaxFerry1=new DecimalFormat("0.00").format(allTaxFerry);
            String allTaxCheek1=new DecimalFormat("0.00").format(allTaxCheek);


//            String allTaxFerry1 = String.valueOf(allTaxFerry);
//            String allTaxCheek1 = String.valueOf(allTaxCheek);



            if (personalTaxCheck < personalTaxFerry) {
                allTax.setText("税费合计："+allTaxCheek1+"元(核定)");
                suggest.setText("核实："+allTaxFerry1+"元，核定税费较低，建议您过户时选择核定用户。");
                tv001.setText("个人所得税(核定)：");
                personalTax.setText(transferTallage.getPersonalTaxCheck()+"元");
            }if (personalTaxCheck > personalTaxFerry) {
                allTax.setText("税费合计："+allTaxFerry1+"元(核实)");
                suggest.setText("核定："+allTaxCheek1+"元，核实税费较低，建议您过户时选择核实用户。");
                tv001.setText("个人所得税(核实)：");
                personalTax.setText(transferTallage.getPersonalTaxFerry()+"元");
            }else {
                allTax.setText("税费合计："+allTaxFerry1+"元");
                suggest.setVisibility(View.GONE);
                tv001.setText("个人所得税：");
                personalTax.setText(transferTallage.getPersonalTaxFerry()+"元");
            }
        }

    }
}
