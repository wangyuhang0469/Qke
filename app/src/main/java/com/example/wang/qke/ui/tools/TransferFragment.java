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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends BaseFragment {


    @Bind(R.id.rg1)
    RadioGroup rg1;

    @Bind(R.id.rg2)
    RadioGroup rg2;

    @Bind(R.id.rg3)
    RadioGroup rg3;
    @Bind(R.id.idNo)
    EditText et_idNo;

    @Bind(R.id.shenfenzheng)
    RelativeLayout shenfenzheng;
    @Bind(R.id.ownerName)
    EditText et_ownerName;
    @Bind(R.id.danwei)
    RelativeLayout danwei;
    @Bind(R.id.certNum)
    EditText et_certNum;
    @Bind(R.id.buildArea)
    EditText et_buildArea;
    @Bind(R.id.certCode_img)
    ImageView certCodeImg;
    @Bind(R.id.certCode)
    EditText et_certCode;
    @Bind(R.id.ll_1)
    RelativeLayout ll1;
    @Bind(R.id.loading)
    TextView loading;

    public TransferFragment() {
        // Required empty public constructor
    }


    private String radiobutton = "0";
    private String zslx = "0";
    private String selectBdc = "2015";

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
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        ButterKnife.bind(this, view);

        requestQueue = Volley.newRequestQueue(getActivity());


        stringRequest = new StringRequest("http://123.207.61.165/outside/getverify?count=" + count,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (loading != null) {
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

                if (loading != null) {
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
                            String str=new DecimalFormat("0.00").format(d);
                            transferTallage.setHousePrice(str);
                            transferTallage.setCreateTime(object.getString("createTime"));
                            transferTallage.setHasTax(object.getString("hasTax"));


                            Intent intent = new Intent();
                            intent.setClass(getActivity(), TransferTallageDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("transferTallage", transferTallage);
                            intent.putExtras(bundle);
                            startActivity(intent);


                        } else {
                            msg = msg.replace("\n","");
                            msg = msg.replace("\r","");
                            msg = msg.replace("\t","");
                            msg = msg.replace(" ","");
                            toast(msg);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    lock = true;
                    loading.setVisibility(View.GONE);


                    Log.d("json-------------", response);
                    requestQueue.add(stringRequest);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (loading!= null) {
                    Log.e("TAG----------------------------", error.getMessage(), error);
                    loading.setVisibility(View.GONE);
                    toast("查询失败");
                    lock = true;
                    requestQueue.add(stringRequest);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("step", "0");
                map.put("radiobutton", radiobutton);
                map.put("zslx", zslx);
                map.put("ownerName", et_ownerName.getText().toString());
                map.put("idNo", et_idNo.getText().toString());
                map.put("certNo", et_certNum.getText().toString());
                map.put("buildArea", et_buildArea.getText().toString());
                map.put("selectBdc", selectBdc);
                map.put("certCode", et_certCode.getText().toString());
                map.put("responseCookie", responseCookie);


                Log.e("=======", " uid : " + User.getInstance().getUid() + " step : 0" + " radiobutton: " + radiobutton +
                        " zslx :" + zslx + " ownerName :" + et_ownerName.getText().toString() +
                        "  idNo " + et_idNo.getText().toString() +
                        " certNo  " + et_certNum.getText().toString() +
                        " buildArea  " + et_buildArea.getText().toString() +
                        " selectBdc  " + selectBdc +
                        " certCode  " + et_certCode.getText().toString() +
                        "  responseCookie " + responseCookie
                );



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


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (!(radiobutton.equals("0") && et_idNo.getText().length() ==0)) {
            if (!(radiobutton.equals("1") && et_ownerName.getText().length() == 0)){
                if (et_certNum.getText().length() != 0  && et_buildArea.getText().length() != 0  && et_certCode.getText().length() != 0) {
                    if (  et_idNo.getText().length() ==15 ||et_idNo.getText().length() ==18) {
                        if (lock) {
                            lock = false;
                            loading.setVisibility(View.VISIBLE);
                            requestQueue.add(stringRequest2);
                        }
                    }else {toast("身份证号错误");}
                }else {toast("请输入完整信息");}
            }else {toast("请输入单位名称");}
        }else {toast("请填写身份证号");}
    }

    @OnClick(R.id.certCode_img)
    public void onView2Clicked() {
        requestQueue.add(stringRequest);
    }
}
