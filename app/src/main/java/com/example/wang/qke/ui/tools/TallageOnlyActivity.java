package com.example.wang.qke.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.TransferTallage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TallageOnlyActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rg1)
    RadioGroup rg1;
    @Bind(R.id.rg2)
    RadioGroup rg2;
    @Bind(R.id.rg3)
    RadioGroup rg3;
    @Bind(R.id.area)
    TextView et_area;
    @Bind(R.id.houseArea)
    EditText et_houseArea;
    @Bind(R.id.registerPrice)
    EditText et_registerPrice;
    @Bind(R.id.loading)
    TextView loading;
    @Bind(R.id.spinner1)
    Spinner spinner1;

    private String isOnlyHouse = "1";
    private String isFirst = "1";
    private String registerPrice;
    private String buyYear = "1";
    private String areaId;
    private String area;

    private RequestQueue mQueue;
    private StringRequest stringRequest;

    private boolean lock;

    private TransferTallage transferTallage = new TransferTallage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tallage_only);
        ButterKnife.bind(this);

        title.setText("计算税费");

        final Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();
        transferTallage = (TransferTallage) intent.getSerializableExtra("transferTallage");

        mQueue = Volley.newRequestQueue(this);


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/ransfer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {

                    object = new JSONObject(response);
                    String resultCode = object.getString("resultCode");
                    String msg = object.getString("msg");

                    if (resultCode.equals("00")) {
                        object = object.getJSONObject("result");

//                        String owner = object.getString("owner");
//                        String zslx = object.getString("zslx");
//
//                        transferTallage.setOwner(owner);
//                        transferTallage.setZslx(zslx);
//                        if (owner.equals("个人")) {
//                            transferTallage.setIdNo(object.getString("idNum"));
//                        }else {
//                            transferTallage.setOwnerName(object.getString("ownerName"));
//                        }
//                        if (!zslx.equals("房地产权证书")){
//                            transferTallage.setSelectBdc(object.getString("selectBdc"));
//                        }
//                        transferTallage.setId(object.getString("id"));
//                        transferTallage.setCertNo(object.getString("certNum"));
//                        transferTallage.setDesc(object.getString("desc"));
//                        transferTallage.setUnitPrice(object.getString("unitPrice"));
//                        transferTallage.setHousePrice(object.getString("housePrice"));
//                        transferTallage.setCreateTime(object.getString("createTime"));
//                        transferTallage.setHasTax(object.getString("hasTax"));

                        transferTallage.setHasTax("1");
                        transferTallage.setValueAddedTax(object.getString("valueAddedTax"));
                        transferTallage.setMaintenanceTax(object.getString("maintenanceTax"));
                        transferTallage.setEducationSurtax(object.getString("educationSurtax"));
                        transferTallage.setLocalEducationSurtax(object.getString("localEducationSurtax"));
                        transferTallage.setStampDuty(object.getString("stampDuty"));
                        transferTallage.setPersonalTaxFerry(object.getString("personalTaxFerry"));
                        transferTallage.setPersonalTaxCheck(object.getString("personalTaxCheck"));
                        transferTallage.setTransactionTax(object.getString("transactionTax"));
                        transferTallage.setProcedureTax(object.getString("procedureTax"));
                        transferTallage.setAppliqueExpense(object.getString("appliqueExpense"));
                        transferTallage.setCheckExpense(object.getString("checkExpense"));

                    } else {
                        toast(msg);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                lock = true;
                loading.setVisibility(View.GONE);

                bundle.putSerializable("result", transferTallage);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                finish();

                Log.d("json-------------", response);
                Log.d("json-------------", transferTallage.getBuildArea());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                lock = true;
                toast("查询失败");
                loading.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", transferTallage.getId());
                map.put("step", "2");
                map.put("buildArea", transferTallage.getBuildArea());
                map.put("housePrice", transferTallage.getHousePrice());
                map.put("houseArea", et_houseArea.getText().toString());
                map.put("isOnlyHouse", isOnlyHouse);
                map.put("isFirst", isFirst);
                map.put("registerPrice", et_registerPrice.getText().toString());
                map.put("buyYear", buyYear);
                map.put("areaId", et_area.getText().toString());


                Log.e("=======",
                        " id :" + transferTallage.getId() + " step :" + "2" +
                                "  buildArea " + transferTallage.getBuildArea() +
                                " houseArea  " + et_houseArea.getText().toString() +
                                " isOnlyHouse  " + isOnlyHouse +
                                " isFirst  " + isFirst +
                                " registerPrice  " + et_registerPrice.getText().toString() +
                                "  buyYear " + buyYear + "  areaId : " + et_area.getText().toString()
                );
                return map;
            }
        };

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1_left:
                        buyYear = "1";
                        break;
                    case R.id.rb1_middle:
                        buyYear = "2";
                        break;
                    case R.id.rb1_right:
                        buyYear = "3";
                        break;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb2_left:
                        isOnlyHouse = "1";
                        break;
                    case R.id.rb2_right:
                        isOnlyHouse = "0";
                        break;
                }
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb3_left:
                        isFirst = "1";
                        break;
                    case R.id.rb3_right:
                        isFirst = "0";
                        break;
                }
            }
        });



        //------------------------------------下拉菜单------------------------------------------
        List<String> option1 = new ArrayList<String>();
        option1.add("请选择：");
        option1.add("罗湖");
        option1.add("福田");
        option1.add("南山");
        option1.add("盐田");
        option1.add("宝安");
        option1.add("龙华");
        option1.add("龙岗");
        option1.add("光明");
        option1.add("坪山");
        option1.add("大鹏");



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item1, R.id.tv_spinner, option1);
        adapter1.setDropDownViewResource(R.layout.spinner_item2);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    et_area.setText(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });







    }

    @OnClick({R.id.back, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn:
                lock = false;
                loading.setVisibility(View.VISIBLE);
                mQueue.add(stringRequest);
                break;
        }
    }
}
