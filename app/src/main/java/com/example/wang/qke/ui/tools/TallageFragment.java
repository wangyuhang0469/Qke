package com.example.wang.qke.ui.tools;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.TransferTallage;
import com.example.wang.qke.classes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallageFragment extends BaseFragment {


    @Bind(R.id.rg1)
    RadioGroup rg1;
    @Bind(R.id.rg2)
    RadioGroup rg2;
    @Bind(R.id.rg3)
    RadioGroup rg3;
    @Bind(R.id.ll_1)
    RelativeLayout ll1;
    @Bind(R.id.rg4)
    RadioGroup rg4;
    @Bind(R.id.rg5)
    RadioGroup rg5;
    @Bind(R.id.rg6)
    RadioGroup rg6;
    @Bind(R.id.area)
    TextView et_area;
    @Bind(R.id.ownerName)
    EditText et_ownerName;
    @Bind(R.id.danwei)
    RelativeLayout danwei;
    @Bind(R.id.idNo)
    EditText et_idNo;
    @Bind(R.id.shenfenzheng)
    RelativeLayout shenfenzheng;
    @Bind(R.id.certNum)
    EditText et_certNum;
    @Bind(R.id.buildArea)
    EditText et_buildArea;
    @Bind(R.id.houseArea)
    EditText et_houseArea;
    @Bind(R.id.registerPrice)
    EditText et_registerPrice;
    @Bind(R.id.certCode_img)
    ImageView certCodeImg;
    @Bind(R.id.certCode)
    EditText et_certCode;
    @Bind(R.id.spinner1)
    Spinner spinner1;

    public TallageFragment() {
        // Required empty public constructor
    }

    private String radiobutton = "0";
    private String zslx = "0";
    private String selectBdc = "2015";

    private String isOnlyHouse = "1";
    private String isFirst = "1";
    private String buyYear = "1";
    private String areaId;

    private String responseCookie;
    private int count = 0;

    private StringRequest stringRequest;
    private StringRequest stringRequest2;
    private RequestQueue requestQueue;
    private ImageRequest imageRequest;

    private TransferTallage transferTallage = new TransferTallage();
    private boolean lock = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tallage, container, false);
        ButterKnife.bind(this, view);

        requestQueue = Volley.newRequestQueue(getActivity());


        stringRequest = new StringRequest("http://123.207.61.165/outside/getverify?count=" + count,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//
                            JSONObject object = new JSONObject(response);
                            object = object.getJSONObject("result");
                            String img = object.getString("img");
                            responseCookie = object.getString("responseCookie");

                            imageRequest = new ImageRequest(
                                    "http://123.207.61.165/uploads/verifyCode/ransfer/" + img,
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap response) {
                                            if (certCodeImg != null) {
                                                certCodeImg.setBackgroundDrawable(new BitmapDrawable(response));
                                                count++;
                                            }
                                        }
                                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.e("adv未接收", error.getMessage(), error);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        requestQueue.add(imageRequest);
                        Log.d("loopers", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


        //-------------post表单------------------------------------------------


        stringRequest2 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/ransfer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (et_certCode != null) {
                    JSONObject object = null;
                    try {


                        object = new JSONObject(response);
                        String resultCode = object.getString("resultCode");
                        String msg = object.getString("msg");

                        if (resultCode.equals("00")) {
                            object = object.getJSONObject("result");

                            String owner = object.getString("owner");
                            String zslx = object.getString("zslx");

                            transferTallage.setOwner(owner);
                            transferTallage.setZslx(zslx);
                            if (owner.equals("个人")) {
                                transferTallage.setIdNo(object.getString("idNum"));
                            } else {
                                transferTallage.setOwnerName(object.getString("ownerName"));
                            }
                            if (!zslx.equals("房地产权证书")) {
                                transferTallage.setSelectBdc(object.getString("selectBdc"));
                            }
                            transferTallage.setId(object.getString("id"));
                            transferTallage.setBuildArea(et_buildArea.getText().toString());
                            transferTallage.setCertNo(object.getString("certNum"));
                            transferTallage.setDesc(object.getString("desc"));
                            transferTallage.setUnitPrice(object.getString("unitPrice"));
                            double d=object.getDouble("housePrice");
                            String housePrice=new DecimalFormat("0.00").format(d);
                            transferTallage.setHousePrice(housePrice);
                            transferTallage.setCreateTime(object.getString("createTime"));
                            transferTallage.setHasTax(object.getString("hasTax"));

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


                            Intent intent = new Intent();
                            intent.setClass(getActivity(), TransferTallageDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("transferTallage", transferTallage);
                            intent.putExtras(bundle);
                            startActivity(intent);


                        } else {
                            msg = msg.replace("\n", "");
                            msg = msg.replace("\r", "");
                            msg = msg.replace("\t", "");
                            msg = msg.replace(" ", "");
                            toast(msg);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    lock = true;


                    Log.d("json-------------", response);
                    requestQueue.add(stringRequest);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (et_area != null) {
                    Log.e("TAG----------------------------", error.getMessage(), error);
                    lock = true;
                    toast("查询失败");
                    requestQueue.add(stringRequest);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("step", "1");
                map.put("radiobutton", radiobutton);
                map.put("zslx", zslx);
                map.put("ownerName", et_ownerName.getText().toString());
                map.put("idNo", et_idNo.getText().toString());
                map.put("certNo", et_certNum.getText().toString());
                map.put("buildArea", et_buildArea.getText().toString());
                map.put("selectBdc", selectBdc);
                map.put("certCode", et_certCode.getText().toString());
                map.put("responseCookie", responseCookie);
                map.put("houseArea", et_houseArea.getText().toString());
                map.put("isOnlyHouse", isOnlyHouse);
                map.put("isFirst", isFirst);
                map.put("registerPrice", et_registerPrice.getText().toString());
                map.put("buyYear", buyYear);
                map.put("areaId", areaId);


                Log.e("=======", " uid : " + User.getInstance().getUid() + " step : 1" + " radiobutton: " + radiobutton +
                        " zslx :" + zslx + " ownerName :" + et_ownerName.getText().toString() +
                        "  idNo " + et_idNo.getText().toString() +
                        " certNo  " + et_certNum.getText().toString() +
                        " buildArea  " + et_buildArea.getText().toString() +
                        " selectBdc  " + selectBdc +
                        " certCode  " + et_certCode.getText().toString() +
                        "  responseCookie " + responseCookie+
                        " houseArea  " + et_houseArea.getText().toString() +
                        " isOnlyHouse  " + isOnlyHouse +
                        " isFirst  " + isFirst +
                        " registerPrice  " + et_registerPrice.getText().toString() +
                        "  buyYear " + buyYear + "  areaId : " + areaId
                );
//                Log.e("=======",
//                        " id :" + transferTallage.getId() + " step :" + "2" +
//                                "  buildArea " + transferTallage.getBuildArea() +
//                                " houseArea  " + et_houseArea.getText().toString() +
//                                " isOnlyHouse  " + isOnlyHouse +
//                                " isFirst  " + isFirst +
//                                " registerPrice  " + et_registerPrice.getText().toString() +
//                                "  buyYear " + buyYear + "  areaId : " + et_area.getText().toString());
                return map;
            }
        };

        requestQueue.add(stringRequest);


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1_left:
                        radiobutton = "0";
                        shenfenzheng.setVisibility(View.VISIBLE);
                        danwei.setVisibility(View.GONE);
                        break;
                    case R.id.rb1_right:
                        radiobutton = "1";
                        danwei.setVisibility(View.VISIBLE);
                        shenfenzheng.setVisibility(View.GONE);
                        break;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb2_left:
                        zslx = "0";
                        ll1.setVisibility(View.GONE);
                        break;
                    case R.id.rb2_right:
                        zslx = "1";
                        ll1.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb3_left:
                        selectBdc = "2015";
                        break;
                    case R.id.rb3_middle:
                        selectBdc = "2016";
                        break;
                    case R.id.rb3_right:
                        selectBdc = "2017";
                        break;
                }
            }
        });

        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb4_left:
                        buyYear = "1";
                        break;
                    case R.id.rb4_middle:
                        buyYear = "2";
                        break;
                    case R.id.rb4_right:
                        buyYear = "3";
                        break;
                }
            }
        });

        rg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb5_left:
                        isOnlyHouse = "1";
                        break;
                    case R.id.rb5_right:
                        isOnlyHouse = "0";
                        break;
                }
            }
        });

        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb6_left:
                        isFirst = "1";
                        break;
                    case R.id.rb6_right:
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



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option1);
        adapter1.setDropDownViewResource(R.layout.spinner_item2);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    et_area.setText(adapter.getItem(position));
                    areaId = String.valueOf(position);
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (!(radiobutton.equals("0") && et_idNo.getText().length() == 0)) {
            if (!(radiobutton.equals("1") && et_ownerName.getText().length() == 0)) {
                if (et_certNum.getText().length() != 0 && et_buildArea.getText().length() != 0 && et_certCode.getText().length() != 0
                        && et_area.getText().length() != 0 && et_houseArea.getText().length() != 0 && et_registerPrice.getText().length() != 0) {
                    if (et_idNo.getText().length() == 15 || et_idNo.getText().length() == 18) {
                        if (lock) {
                            lock = false;
                            toast("查询中...");
                            requestQueue.add(stringRequest2);
                        }
                    } else {
                        toast("身份证号错误");
                    }
                } else {
                    toast("请输入完整信息");
                }
            } else {
                toast("请输入单位名称");
            }
        } else {
            toast("请填写身份证号");
        }
    }

    @OnClick(R.id.certCode_img)
    public void onView2Clicked() {
        requestQueue.add(stringRequest);
    }
}
