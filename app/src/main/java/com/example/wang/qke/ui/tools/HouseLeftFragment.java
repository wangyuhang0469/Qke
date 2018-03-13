package com.example.wang.qke.ui.tools;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.ToolHouse;
import com.example.wang.qke.classes.User;
import com.kyleduo.switchbutton.SwitchButton;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseLeftFragment extends BaseFragment implements MyListener {


    @Bind(R.id.left)
    RadioButton rb1_left;
    @Bind(R.id.right)
    RadioButton rb1_right;
    @Bind(R.id.chooseHouse)
    EditText chooseHouse;
    @Bind(R.id.sb1)
    SwitchButton sb1;
    @Bind(R.id.sb2)
    SwitchButton sb2;
    @Bind(R.id.sb3)
    SwitchButton sb3;
    @Bind(R.id.ll_2)
    RelativeLayout ll2;
    @Bind(R.id.ll_1)
    LinearLayout ll1;
    @Bind(R.id.area)
    EditText area;
    @Bind(R.id.transactionPrice)
    EditText transactionPrice;
    @Bind(R.id.registerPrice)
    EditText registerPrice;
    @Bind(R.id.rg1)
    RadioGroup rg1;
    @Bind(R.id.rg2)
    RadioGroup rg2;
    @Bind(R.id.loading)
    TextView loading;

    private String id01;
    private String id02;
    private String id03;
    private String name;

    private StringRequest stringRequest;
    private RequestQueue mQueue;

    private String hasTax = "0";
    private String owner = "0";
    private String houseYear = "0";
    private String isOnlyHouse = "0";
    private String isLoan = "0";

    private String taxTotal;
    private String loanKeep;
    private String averagePrice;
    private String amount;
    private String buildingArea;
    private String time;

    private boolean lock = true;

    private ChooseHouseDalog dalog;
    private MyListener myListener = new MyListener() {
        @Override
        public void refreshActivity(String text, String id1, String id2, String id3) {

            chooseHouse.setText(text);
            name = text;
            id01 = id1;
            id02 = id2;
            id03 = id3;


        }
    };

    public HouseLeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_left, container, false);
        ButterKnife.bind(this, view);

        rb1_left.setText("   个人   ");
        rb1_left.setTextSize(13);
        rb1_right.setText("   单位   ");
        rb1_right.setTextSize(13);

        mQueue = Volley.newRequestQueue(getContext());


        sb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll1.setVisibility(View.VISIBLE);
                    hasTax = "1";

                } else {
                    ll1.setVisibility(View.GONE);
                    hasTax = "0";

                }
            }
        });

        sb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    isOnlyHouse = "1";

                } else {

                    isOnlyHouse = "0";

                }
            }
        });

        sb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll2.setVisibility(View.GONE);
                    isLoan = "1";

                } else {
                    ll2.setVisibility(View.VISIBLE);
                    isLoan = "0";

                }
            }
        });


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.left:
                        owner = "0";
                        break;
                    case R.id.right:
                        owner = "1";
                        break;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rd2_left:
                        houseYear = "0";

                        break;
                    case R.id.rd2_middle:
                        houseYear = "1";

                        break;
                    case R.id.rd2_right:
                        houseYear = "2";

                        break;
                }
            }
        });

        //-------------post------------------------------------------------


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/inquery", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (loading != null) {
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response);
                        String resultCode = object.getString("resultCode");
                        String msg = object.getString("msg");

                        if (resultCode.equals("00")) {
                            object = object.getJSONObject("result");
                            averagePrice = object.getString("averagePrice");
                            amount = object.getString("amount");
                            buildingArea = object.getString("buildingArea");
                            time = object.getString("time");
                            if (hasTax.equals("1")) {
                                taxTotal = object.getString("taxTotal");
                                loanKeep = object.getString("loanKeep");
                            }

                            ToolHouse toolHouse = new ToolHouse();


                            Intent intent = new Intent(getActivity(), HouseDetailsActivity.class);

                            //用Bundle携带数据
                            Bundle bundle = new Bundle();

                            bundle.putString("name", name);

                            bundle.putString("buildingArea", buildingArea);

                            bundle.putString("averagePrice", averagePrice);
                            bundle.putString("amount", amount);
                            bundle.putString("time", time);


                            toolHouse.setName(name);
                            toolHouse.setAveragePrice(averagePrice);
                            toolHouse.setAmount(amount);
                            toolHouse.setBuildingArea(buildingArea);
                            toolHouse.setTime(time);

                            if (hasTax.equals("1")) {

                                bundle.putString("taxTotal", taxTotal);
                                bundle.putString("loanKeep", loanKeep);
                                bundle.putString("houseYear", houseYear);
                                bundle.putString("registerPrice", registerPrice.getText().toString());
                                bundle.putString("owner", owner);

                                toolHouse.setTaxTotal(taxTotal);
                                toolHouse.setLoanKeep(loanKeep);
                                toolHouse.setHouseYear(houseYear);
                                toolHouse.setRegisterPrice(registerPrice.getText().toString());
                                toolHouse.setOwner(owner);

                                if (isLoan.equals("0"))
                                    bundle.putString("transactionPrice", transactionPrice.getText().toString());

                                toolHouse.setTransactionPrice(transactionPrice.getText().toString());

                            }


                            HouseActivity houseActivity = (HouseActivity) getActivity();
                            houseActivity.updataRF(toolHouse);

                            if (houseActivity.form.equals("2")) {
                                houseActivity.getData(amount);
                            } else {

                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        } else {
                            toast(msg);
                        }
                        lock = true;
                        loading.setVisibility(View.GONE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("json-------------", response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (loading != null) {
                    Log.e("TAG----------------------------", error.getMessage(), error);
                    loading.setVisibility(View.GONE);
                    toast("查询失败");
                    lock = true;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("token", User.getInstance().getToken());
                map.put("step", "4");
                map.put("name", name);
                map.put("construction_id", id01);
                map.put("building_id", id02);
                map.put("house_id", id03);
                map.put("area", area.getText().toString());
                map.put("hasTax", hasTax);//0
                map.put("registerPrice", registerPrice.getText().toString());
                map.put("owner", owner);
                map.put("houseYear", houseYear);//0
                map.put("isOnlyHouse", isOnlyHouse);//0
                map.put("isLoan", isLoan);//0
                map.put("transactionPrice", transactionPrice.getText().toString());

//                Log.e("====",)

                return map;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, //timeout时间
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {


        if (area.getText().length() != 0) {
            if ( !(hasTax.equals("1") && registerPrice.getText().length() ==0) ) {
                if (!(hasTax.equals("1") &&isLoan.equals("0") && transactionPrice.getText().length() == 0)) {
                    if (lock) {
                        if (name != null) {
                            mQueue.add(stringRequest);
                            loading.setVisibility(View.VISIBLE);
                            lock = false;
                        } else {
                            Toast.makeText(getContext(), "请输入物业关键字，并点击物业右侧图标进入检索页面", Toast.LENGTH_LONG).show();
                        }
                    }
                }else {toast("请填写成交金额");}
            }else {toast("请填写登记价格");}
        }else {toast("请填写建筑面积");}

//        startActivity(HouseDetailsActivity.class, null);
    }

    @OnClick(R.id.im_1)
    public void onView2Clicked() {


        if (!chooseHouse.getText().toString().equals("")){
            dalog = new ChooseHouseDalog(getContext(), R.style.FullScreenDialog, chooseHouse.getText().toString(), myListener);
            dalog.show();
        }else {
            toast("请先输入物业名关键字");

        }


    }

    @Override
    public void refreshActivity(String text, String id1, String id2, String id3) {
        chooseHouse.setText(text);
        name = text;
        id01 = id1;
        id02 = id2;
        id03 = id3;

    }
}
