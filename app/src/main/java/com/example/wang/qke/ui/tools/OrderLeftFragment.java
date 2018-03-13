package com.example.wang.qke.ui.tools;


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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.wang.qke.classes.Filter2;
import com.example.wang.qke.classes.MyRadioGroup;
import com.example.wang.qke.classes.Order;
import com.example.wang.qke.classes.OrderTime;
import com.example.wang.qke.classes.RegistrationArea;
import com.example.wang.qke.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderLeftFragment extends BaseFragment implements MyRadioGroup.OnCheckedChangeListener, OrderListener {


    @Bind(R.id.rb1_1)
    RadioButton rb1_1;
    @Bind(R.id.rb2_1)
    RadioButton rb2_1;
    @Bind(R.id.rg)
    MyRadioGroup rg;

    @Bind(R.id.rb3_1)
    RadioButton rb31;
    @Bind(R.id.rb4_1)
    RadioButton rb41;
    @Bind(R.id.rb5_1)
    RadioButton rb51;
    @Bind(R.id.rb1_2)
    RadioButton rb12;
    @Bind(R.id.rb2_2)
    RadioButton rb22;
    @Bind(R.id.rb3_2)
    RadioButton rb32;
    @Bind(R.id.rb4_2)
    RadioButton rb42;
    @Bind(R.id.rb5_2)
    RadioButton rb52;
    @Bind(R.id.certCode_img)
    ImageView certCodeImg;
    @Bind(R.id.bussName)
    TextView tv_bussName;
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner3)
    Spinner spinner3;
    @Bind(R.id.szAreaName)
    TextView szAreaName;
    @Bind(R.id.proveTypeName)
    TextView proveTypeName;
    @Bind(R.id.registrationAreaName)
    TextView registrationAreaName;
    @Bind(R.id.ll_001)
    LinearLayout ll001;
    @Bind(R.id.ll_002)
    RelativeLayout ll002;
    @Bind(R.id.ll_003)
    RelativeLayout ll003;
    @Bind(R.id.rg1)
    RadioGroup rg1;
    @Bind(R.id.houseName)
    EditText houseName;
    @Bind(R.id.fzFileNo)
    EditText fzFileNo;
    @Bind(R.id.personName)
    EditText personName;
    @Bind(R.id.phoneNumber)
    EditText phoneNumber;
    @Bind(R.id.certificateNo)
    EditText certificateNo;
    @Bind(R.id.verificationcodereg)
    EditText verificationcodereg;
    @Bind(R.id.proveCode)
    EditText proveCode;
    @Bind(R.id.hang6)
    LinearLayout hang6;
    @Bind(R.id.hang7)
    LinearLayout hang7;


    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private StringRequest stringRequest2;
    private StringRequest stringRequest3;
    private StringRequest stringRequest4;
    private StringRequest stringRequest5;

    private OrderTime[][] orderTimes;
    private int hang;
    private int lie;
    private JSONArray hangJson;
    private JSONArray lieJson;
    private JSONObject valueJson;
    private int hangIds[] = new int[]{R.id.hang1, R.id.hang2, R.id.hang3, R.id.hang4, R.id.hang5, R.id.hang6, R.id.hang7};
    private int startTimes[] = new int[]{R.id.startTime1, R.id.startTime2, R.id.startTime3, R.id.startTime4, R.id.startTime5, R.id.startTime6, R.id.startTime7};
    private int endTimes[] = new int[]{R.id.endTime1, R.id.endTime2, R.id.endTime3, R.id.endTime4, R.id.endTime5, R.id.endTime6, R.id.endTime7,};
    private int dataids[] = new int[]{R.id.tv_data1, R.id.tv_data2, R.id.tv_data3, R.id.tv_data4, R.id.tv_data5,};
    private int weekdays[] = new int[]{R.id.weekday1, R.id.weekday2, R.id.weekday3, R.id.weekday4, R.id.weekday5,};
    private int ids[][] = new int[][]{
            {R.id.rb1_1, R.id.rb2_1, R.id.rb3_1, R.id.rb4_1, R.id.rb5_1}, {R.id.rb1_2, R.id.rb2_2, R.id.rb3_2, R.id.rb4_2, R.id.rb5_2},
            {R.id.rb1_3, R.id.rb2_3, R.id.rb3_3, R.id.rb4_3, R.id.rb5_3}, {R.id.rb1_4, R.id.rb2_4, R.id.rb3_4, R.id.rb4_4, R.id.rb5_4},
            {R.id.rb1_5, R.id.rb2_5, R.id.rb3_5, R.id.rb4_5, R.id.rb5_5}, {R.id.rb1_6, R.id.rb2_6, R.id.rb3_6, R.id.rb4_6, R.id.rb5_6},
            {R.id.rb1_7, R.id.rb2_7, R.id.rb3_7, R.id.rb4_7, R.id.rb5_7}
    };

    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;

    private List<String> option2 = new ArrayList<String>();
    private List<String> option32 = new ArrayList<String>();
    private List<RegistrationArea> registrationAreas = new ArrayList<>();
    private List<RegistrationArea> registrationAreas2 = new ArrayList<>();
    private Filter2 filter;

    private String bookingDateLabels[] = new String[5];
    private String bookingDateStrs[] = new String[5];
    private String workTimeSoltOids[] = new String[7];
    private String workTimeSoltNames[] = new String[7];
    private String bookingDateLabel;
    private String bookingDateStr;
    private String workTimeSoltOid;
    private String workTimeSoltName;

    private String responseCookie;

    private String bookingSzAreaOid = "";
    private String proveType = "";


    private String registrationAreaOid = "1";

    private String bookingType = "-1060";
    private String bookingTypeName = "领证业务类";
    private String bussName = "领取不动产权证书及登记证明";
    private String szItemNo = "FZ20150730";
    private String XLType = "2";

    private boolean lock = true;
    private boolean lock2 = true;

    private OrderListener orderListener = new OrderListener() {
        @Override
        public void refreshActivity(String a, String b, String c, String d) {
            bookingType = a;
            bookingTypeName = b;
            bussName = c;
            szItemNo = d;
            tv_bussName.setText(bussName);
            Log.e("aaaaa", bookingType + "  " + bookingTypeName + "  " + bussName + "  " + szItemNo);

            hang6.setVisibility(View.GONE);
            hang7.setVisibility(View.GONE);

            ll002.setVisibility(View.GONE);
            if (bussName.equals("领取不动产权证书及登记证明")) {
                ll001.setVisibility(View.GONE);
                ll003.setVisibility(View.VISIBLE);
            } else {
                ll001.setVisibility(View.VISIBLE);
                ll003.setVisibility(View.GONE);

                option2.clear();
                option2.add("请选择：");
                option2.add("不动产权证书或房地产证");
                if (bussName.equals("最高额抵押权首次登记") || bussName.equals("抵押权注销登记")) {
                    if (bussName.equals("抵押权注销登记")) {
                        ll002.setVisibility(View.VISIBLE);
                    }
                    option2.add("合同");
                }


                adapter2 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option2);
                adapter2.setDropDownViewResource(R.layout.spinner_item2);
                spinner2.setAdapter(adapter2);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //获取Spinner控件的适配器
                        ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();

                        switch (position) {
                            case 0:
                                proveTypeName.setText("");
                                break;
                            case 1:
                                proveType = "00";
                                proveTypeName.setText(adapter.getItem(position));
                                break;
                            case 2:
                                proveType = "01";
                                proveTypeName.setText(adapter.getItem(position));
                                break;
                        }
                    }

                    //没有选中时的处理
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


            }

            if (!bussName.equals("抵押权注销登记") && !bussName.equals("二手商品房转移登记（二手房买卖）")) {

                option32.clear();


                option32.add("请选择");
                for (int i = 0; i < registrationAreas.size(); i++) {
                    option32.add(registrationAreas.get(i).getRegistrationAreaName());
                }


                adapter3 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option32);
                adapter3.setDropDownViewResource(R.layout.spinner_item2);
                spinner3.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();
                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //获取Spinner控件的适配器
                        ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();


                        if (position == 0) {

                        } else {

                            registrationAreaOid = registrationAreas.get(position - 1).getRegistrationAreaOid();
                            registrationAreaName.setText(adapter.getItem(position));
                            rg.setBackgroundColor(0x66000000);
                            table();
                            mQueue.add(stringRequest3);

                        }
                    }

                    //没有选中时的处理
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }


            mQueue.add(stringRequest4);

            if (registrationAreaName.getText().length() != 0) {
                rg.setBackgroundColor(0x66000000);
                table();
                mQueue.add(stringRequest3);
            }
        }
    };

    public OrderLeftFragment() {
        // Required empty public constructor
    }

    //=======================================================================主线程===============================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order_left, container, false);
        ButterKnife.bind(this, view);

        final RegistrationArea registrationArea1 = new RegistrationArea("1", "福田登记所");
        final RegistrationArea registrationArea2 = new RegistrationArea("-1000", "南山登记所");
        RegistrationArea registrationArea3 = new RegistrationArea("-1001", "罗湖盐田登记所");
        RegistrationArea registrationArea4 = new RegistrationArea("4", "宝安登记所");
        RegistrationArea registrationArea5 = new RegistrationArea("3", "龙华登记所");
        final RegistrationArea registrationArea6 = new RegistrationArea("2", "龙岗登记所");
        final RegistrationArea registrationArea7 = new RegistrationArea("5", "布吉登记所");
        RegistrationArea registrationArea8 = new RegistrationArea("6", "光明登记所");
        final RegistrationArea registrationArea9 = new RegistrationArea("7", "坪山登记所");
        registrationAreas.add(registrationArea1);
        registrationAreas.add(registrationArea2);
        registrationAreas.add(registrationArea3);
        registrationAreas.add(registrationArea4);
        registrationAreas.add(registrationArea5);
        registrationAreas.add(registrationArea6);
        registrationAreas.add(registrationArea7);
        registrationAreas.add(registrationArea8);
        registrationAreas.add(registrationArea9);
        filter = new Filter2(registrationAreas);


        mQueue = Volley.newRequestQueue(getActivity());


        stringRequest4 = new StringRequest("http://123.207.61.165/outside/createBook",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ImageRequest imageRequest = null;
                        try {
//
                            JSONObject object = new JSONObject(response);
                            if (object.getString("resultCode").equals("00")) {

                                object = object.getJSONObject("result");
                                String img = object.getString("img");
                                responseCookie = object.getString("responseCookie");

                                imageRequest = new ImageRequest(
                                        "http://123.207.61.165/uploads/verifyCode/createBook/" + img,
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                if (certCodeImg != null) {
                                                    certCodeImg.setBackgroundDrawable(new BitmapDrawable(response));

                                                }
                                            }
                                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.e("adv未接收", error.getMessage(), error);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (mQueue != null) {
                            mQueue.add(imageRequest);
                        }
                        Log.d("loopers", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        rg.setBackgroundColor(0x66000000);
        table();
        mQueue.add(stringRequest3);
        mQueue.add(stringRequest4);


        rg.setOnCheckedChangeListener(this);


        List<String> option1 = new ArrayList<String>();
        option1.add("请选择：");
        option1.add("罗湖区");
        option1.add("福田区");
        option1.add("南山区");
        option1.add("宝安区");
        option1.add("龙岗区");
        option1.add("盐田区");
        option1.add("光明新区");
        option1.add("坪山新区");
        option1.add("龙华新区");
        option1.add("大鹏新区");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option1);
        adapter1.setDropDownViewResource(R.layout.spinner_item2);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0) {
                    bookingSzAreaOid = position + "";
                    szAreaName.setText(adapter.getItem(position));
                    registrationAreas2.clear();
                    option32.clear();
                    String a = adapter.getItem(position).substring(0, 2);
                    if (bussName.equals("抵押权注销登记") || bussName.equals("二手商品房转移登记（二手房买卖）")) {
                        if (a.equals("龙岗")) {
                            registrationAreas2.add(registrationArea6);
                            registrationAreas2.add(registrationArea7);
                        } else if (a.equals("大鹏")) {
                            registrationAreas2.add(registrationArea9);
                        } else {
                            registrationAreas2 = filter.filtered(a);
                        }
                        option32.add("请选择");
                        for (int i = 0; i < registrationAreas2.size(); i++) {
                            option32.add(registrationAreas2.get(i).getRegistrationAreaName());
                        }
                    } else {
                        option32.add("请选择");
                        for (int i = 0; i < registrationAreas.size(); i++) {
                            option32.add(registrationAreas.get(i).getRegistrationAreaName());
                        }
                    }

                    adapter3 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option32);
                    adapter3.setDropDownViewResource(R.layout.spinner_item2);
                    spinner3.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                    spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //获取Spinner控件的适配器
                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();


                            if (position == 0) {
                                registrationAreaName.setText("");
                            } else {
                                if (bussName.equals("抵押权注销登记") || bussName.equals("二手商品房转移登记（二手房买卖）")) {
                                    try {
                                        registrationAreaOid = registrationAreas2.get(position - 1).getRegistrationAreaOid();
                                    } catch (IndexOutOfBoundsException e) {
                                        Toast.makeText(getContext(), "请重写选择房产所在地", Toast.LENGTH_LONG).show();
                                        szAreaName.setText("");
                                    }
                                } else {
                                    registrationAreaOid = registrationAreas.get(position - 1).getRegistrationAreaOid();
                                }
                                registrationAreaName.setText(adapter.getItem(position));
                                rg.setBackgroundColor(0x66000000);
                                table();
                                mQueue.add(stringRequest3);
                            }
                        }

                        //没有选中时的处理
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                }
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1_left:
                        XLType = "2";
                        break;

                    case R.id.rb1_right:
                        XLType = "1";
                        break;
                }
            }
        });


        stringRequest5 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/appointmentTransact", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (rb2_1 != null) {
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response);
                        String resultCode = object.getString("resultCode");
                        String msg = object.getString("msg");

                        toast(msg);

                        if (resultCode.equals("00")) {
                            object = object.getJSONObject("result");

                            Order order = new Order();

                            order.setUid(object.getString("uid"));
                            order.setRealname(object.getString("realname"));
                            order.setIdNum(object.getString("idNum"));
                            order.setMobNum(object.getString("mobNum"));
                            order.setEvent(object.getString("event"));
                            order.setBookTime(object.getString("bookTime"));
                            order.setRegistrationAreaName(object.getString("registrationAreaName"));
                            if (!object.isNull("fzFileNo")) {
                                order.setFzFileNo(object.getString("fzFileNo"));
                            } else {
                                order.setProveType(object.getString("proveType"));
                                order.setProveCode(object.getString("proveCode"));
                                order.setHouseName(object.getString("houseName"));
                            }
                            order.setBookCode(object.getString("bookCode"));
                            order.setState("已预约");

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", order);

                            OrderActivity orderActivity = (OrderActivity) getActivity();
                            orderActivity.orderRightFragment.updata(order);


                            startActivity(OrderDetailsActivity.class, bundle);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mQueue.add(stringRequest4);
                    lock = true;

                    Log.d("预约结果-------", response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                if (phoneNumber != null) {
                    mQueue.add(stringRequest4);
                    toast("预约失败");
                    lock = true;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("registrationAreaOid", registrationAreaOid);
                map.put("registrationAreaName", registrationAreaName.getText().toString());
                map.put("bookingType", bookingType);
                map.put("bookingTypeName", bookingTypeName);
                map.put("bussName", bussName);
                map.put("szItemNo", szItemNo);
                map.put("bookingDateLabel", bookingDateLabel);
                map.put("bookingDateStr", bookingDateStr);
                map.put("workTimeSoltOid", workTimeSoltOid);
                map.put("workTimeSoltName", workTimeSoltName);
                map.put("fzFileNo", fzFileNo.getText().toString());
                map.put("personName", personName.getText().toString());
                map.put("certificateNo", certificateNo.getText().toString());
                map.put("phoneNumber", phoneNumber.getText().toString());
                map.put("proveType", proveType);
                map.put("proveTypeName", proveTypeName.getText().toString());
                map.put("XLType", XLType);
                map.put("proveCode", proveCode.getText().toString());
                map.put("houseName", houseName.getText().toString());
                map.put("bookingSzAreaOid", bookingSzAreaOid);
                map.put("szAreaName", szAreaName.getText().toString());
                map.put("certCode", verificationcodereg.getText().toString());
                map.put("responseCookie", responseCookie);

                String aa = "  uid  = " + User.getInstance().getUid() + " registrationAreaOid   = " + registrationAreaOid +
                        "  registrationAreaName  = " + registrationAreaName.getText().toString() + "   bookingType = " + bookingType +
                        "  bookingTypeName  = " + bookingTypeName + "  bussName  = " + bussName +
                        "  szItemNo  = " + szItemNo + "  bookingDateLabel  = " + bookingDateLabel +
                        "  bookingDateStr  = " + bookingDateStr + " workTimeSoltOid   = " + workTimeSoltOid +
                        " workTimeSoltName   = " + workTimeSoltName + "  fzFileNo  = " + fzFileNo.getText().toString() +
                        "  personName  = " + personName.getText().toString() + "  certificateNo  = " + certificateNo.getText().toString() +
                        "  phoneNumber  = " + phoneNumber.getText().toString() + " proveType   = " + proveType +
                        "   proveTypeName = " + proveTypeName.getText().toString() + "  XLType  = " + XLType +
                        "  proveCode  = " + proveCode.getText().toString() + "  houseName  = " + houseName.getText().toString() +
                        "  bookingSzAreaOid  = " + bookingSzAreaOid + "  szAreaName  = " + szAreaName.getText().toString() +

                        "certCode:  " + verificationcodereg.getText().toString() + "   responseCookie:  " + responseCookie;


                Log.e("发送包==========", aa);


                return map;


            }
        };


        option32.add("请选择");
        for (int i = 0; i < registrationAreas.size(); i++) {
            option32.add(registrationAreas.get(i).getRegistrationAreaName());
        }


        adapter3 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option32);
        adapter3.setDropDownViewResource(R.layout.spinner_item2);
        spinner3.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();


                if (position == 0) {

                } else {
//                    if (bussName.equals("抵押权注销登记") || bussName.equals("二手商品房转移登记（二手房买卖）")) {
//                        registrationAreaOid = registrationAreas2.get(position - 1).getRegistrationAreaOid();
//                    } else {
                    registrationAreaOid = registrationAreas.get(position - 1).getRegistrationAreaOid();
//                    }
                    registrationAreaName.setText(adapter.getItem(position));
                    rg.setBackgroundColor(0x66000000);
                    table();
                    mQueue.add(stringRequest3);
                }
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return view;
    }


    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {// Q: 参数 group 暂时还没搞清什么用途
        switch (checkedId) {
            case R.id.rb1_1:
                select(1, 1);
                break;
            case R.id.rb2_1:
                select(2, 1);
                break;
            case R.id.rb3_1:
                select(3, 1);
                break;
            case R.id.rb4_1:
                select(4, 1);
                break;
            case R.id.rb5_1:
                select(5, 1);
                break;
            case R.id.rb1_2:
                select(1, 2);
                break;
            case R.id.rb2_2:
                select(2, 2);
                break;
            case R.id.rb3_2:
                select(3, 2);
                break;
            case R.id.rb4_2:
                select(4, 2);
                break;
            case R.id.rb5_2:
                select(5, 2);
                break;
            case R.id.rb1_3:
                select(1, 3);
                break;
            case R.id.rb2_3:
                select(2, 3);
                break;
            case R.id.rb3_3:
                select(3, 3);
                break;
            case R.id.rb4_3:
                select(4, 3);
                break;
            case R.id.rb5_3:
                select(5, 3);
                break;
            case R.id.rb1_4:
                select(1, 4);
                break;
            case R.id.rb2_4:
                select(2, 4);
                break;
            case R.id.rb3_4:
                select(3, 4);
                break;
            case R.id.rb4_4:
                select(4, 4);
                break;
            case R.id.rb5_4:
                select(5, 4);
                break;
            case R.id.rb1_5:
                select(1, 5);
                break;
            case R.id.rb2_5:
                select(2, 5);
                break;
            case R.id.rb3_5:
                select(3, 5);
                break;
            case R.id.rb4_5:
                select(4, 5);
                break;
            case R.id.rb5_5:
                select(5, 5);
                break;
            case R.id.rb1_6:
                select(1, 6);
                break;
            case R.id.rb2_6:
                select(2, 6);
                break;
            case R.id.rb3_6:
                select(3, 6);
                break;
            case R.id.rb4_6:
                select(4, 6);
                break;
            case R.id.rb5_6:
                select(5, 6);
                break;
            case R.id.rb1_7:
                select(1, 7);
                break;
            case R.id.rb2_7:
                select(2, 7);
                break;
            case R.id.rb3_7:
                select(3, 7);
                break;
            case R.id.rb4_7:
                select(4, 7);
                break;
            case R.id.rb5_7:
                select(5, 7);
                break;


        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {


        if (registrationAreaName.getText().length() != 0 && personName.getText().length() != 0 &&
                certificateNo.getText().length() != 0 && phoneNumber.getText().length() != 0 &&
                verificationcodereg.getText().length() != 0 && registrationAreaName.getText().length() != 0) {
            if ((proveTypeName.getText().length() != 0 &&
                    proveCode.getText().length() != 0 && houseName.getText().length() != 0 && szAreaName.getText().length() != 0) || fzFileNo.getText().length() != 0) {
                if (workTimeSoltName != null) {

                    if (phoneNumber.getText().length() == 11 && (certificateNo.getText().length() == 18 || certificateNo.getText().length() == 15)) {
                        if (lock) {
                            lock = false;
                            toast("请稍候");
                            mQueue.add(stringRequest5);
                        }
                    } else toast("身份证或手机号不正确");
                } else toast("请选择预约时间");
            } else toast("请填写完整信息");
        } else toast("请填写完整信息");
    }

    @OnClick(R.id.certCode_img)
    public void onView2Clicked() {
        mQueue.add(stringRequest4);
    }


    public void select(int a, int b) {
        bookingDateLabel = bookingDateLabels[a - 1];
        bookingDateStr = bookingDateStrs[a - 1];
        workTimeSoltName = workTimeSoltNames[b - 1];
        workTimeSoltOid = workTimeSoltOids[b - 1];
        Log.e("您选择的是=======", bookingDateLabel + "   " + bookingDateStr + "   " + workTimeSoltName + "   " + workTimeSoltOid);
    }

    @Override
    public void refreshActivity(String bookingType, String bookingTypeName, String bussName, String szItemNo) {

    }

    @OnClick({R.id.im_1, R.id.bussName})
    public void onViewClicked(View view) {
        OrderDalog dalog = new OrderDalog(getContext(), R.style.FullScreenDialog, orderListener);
        dalog.show();

    }


    @OnClick(R.id.button2)
    public void onView3Clicked() {
        if (lock2) {
            lock2 = false;
            rg.setBackgroundColor(0x66000000);
            table();
            mQueue.add(stringRequest3);
        } else {
            toast("请勿频繁刷新");
        }
    }


    public void table() {

        stringRequest = new StringRequest("http://123.207.61.165/outside/getWorkDate?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (phoneNumber != null) {
                            Log.e("======= 123456 =====", "http://123.207.61.165/outside/getWorkDate?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid);


                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("resultCode").equals("00")) {

                                    String jsonStr = object.getString("result");
                                    jsonStr = jsonStr.replace("{", "{\"");
                                    jsonStr = jsonStr.replace(":", "\":\"");
                                    jsonStr = jsonStr.replace(",", "\",\"");
                                    jsonStr = jsonStr.replace("}", "\"}");
                                    jsonStr = jsonStr.replace("}\",\"{", "},{");
                                    jsonStr = "[" + jsonStr + "]";
                                    Log.d("======= 1.1 =====", jsonStr);


                                    lieJson = null;
                                    lieJson = new JSONArray(jsonStr);
                                    lie = lieJson.length();
                                    for (int i = 0; i < lie; i++) {
                                        object = lieJson.getJSONObject(i);

                                        Log.e("======= 工作时间 （已解析） =====", object.getString("workDayLabel"));
                                    }

                                    mQueue.add(stringRequest2);

                                }else {
                                    toast("网络异常");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.d("======= 工作日期 未解析 =====", response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (phoneNumber != null) {
                    lock2 = true;
                    toast("预约时间加载失败");
                    rg.setBackgroundColor(0xffffffff);
                }
            }
        });


        stringRequest2 = new StringRequest("http://123.207.61.165/outside/getWorkTime?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid + "&bookingType=" + bookingType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (phoneNumber != null) {

                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("resultCode").equals("00")) {

                                    String jsonStr = object.getString("result");
                                    jsonStr = jsonStr.replace("{", "{\"");
                                    jsonStr = jsonStr.replace(":", "\":\"");
                                    jsonStr = jsonStr.replace(",", "\",\"");
                                    jsonStr = jsonStr.replace("}", "\"}");
                                    jsonStr = jsonStr.replace("}\",\"{", "},{");
                                    jsonStr = jsonStr.replace("\":\"00", ":00");

                                    jsonStr = jsonStr.replace("\"{\"nanos", "\"{\",\"nanos");
                                    jsonStr = jsonStr.replace("}\"", "");
                                    jsonStr = "[" + jsonStr + "]";
                                    Log.d("======= 工作时间 （已解析） =====", jsonStr);


                                    hangJson = null;
                                    hangJson = new JSONArray(jsonStr);
                                    hang = hangJson.length();


                                    for (int i = 0; i < hang; i++) {
                                        object = hangJson.getJSONObject(i);
                                        LinearLayout linearLayout = (LinearLayout) getView().findViewById(hangIds[i]);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        TextView tv_start = (TextView) getView().findViewById(startTimes[i]);
                                        TextView tv_end = (TextView) getView().findViewById(endTimes[i]);
                                        tv_start.setText(object.getString("startTime"));
                                        tv_end.setText(object.getString("endTime"));
                                        String hangid = object.getString("workTimeSoltOid");

                                        workTimeSoltOids[i] = hangid;
                                        workTimeSoltNames[i] = object.getString("startTime") + "-" + object.getString("endTime");


                                        for (int j = 0; j < lie; j++) {
                                            JSONObject object2 = lieJson.getJSONObject(j);


                                            if (i == 0) {
                                                TextView tv_data = (TextView) getView().findViewById(dataids[j]);
                                                TextView tv_weekday = (TextView) getView().findViewById(weekdays[j]);
                                                String data = object2.getString("workDay").substring(5, 10);
                                                String weekName = object2.getString("weekName");
                                                tv_data.setText(data);
                                                tv_weekday.setText(weekName);
                                                bookingDateLabels[j] = weekName.substring(2);
                                                bookingDateStrs[j] = object2.getString("workDay").substring(0, 10);


                                            }


                                            String lieid = object2.getString("workDay");
                                            lieid = lieid.substring(0, 10);
                                            String key = lieid + "_" + hangid;
                                            RadioButton radioButton = (RadioButton) getView().findViewById(ids[i][j]);
                                            if (valueJson.has(key)) {
                                                key = valueJson.getString(key);
                                                String value = valueJson.getString(key);
                                                if (!value.equals("u672Au653Eu53F7")) {
                                                    radioButton.setText(value);
                                                    radioButton.setClickable(true);
                                                }else {
                                                    radioButton.setText("");
                                                    radioButton.setHint("未放号");
                                                    radioButton.setClickable(false);
                                                }
                                            } else {
                                                radioButton.setText("");
                                                radioButton.setHint("未放号");
                                                radioButton.setClickable(false);
                                            }

                                        }

                                        Date now = new Date(System.currentTimeMillis());
                                        String dateStr = bookingDateStrs[0] + " " + object.getString("startTime");
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                                        Date date = null;
                                        try {
                                            date = format.parse(dateStr);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        Log.e("...............", format.format(date));
                                        int a = date.compareTo(now);
                                        if (a < 0) {
                                            RadioButton radioButton = (RadioButton) getView().findViewById(ids[i][0]);
                                            radioButton.setText("");
                                            radioButton.setHint("已结束");
                                            radioButton.setClickable(false);

                                        }


                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            lock2 = true;
                            rg.setBackgroundColor(0xffffffff);

                            Log.d("======= 工作时间 未解析 =====", response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (phoneNumber != null) {

                    toast("预约时间加载失败");
                    lock2 = true;
                    rg.setBackgroundColor(0xffffffff);
                }
            }
        });


        stringRequest3 = new StringRequest("http://123.207.61.165/outside/getWorkAmount?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid + "&bookingType=" + bookingType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (phoneNumber != null) {

                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("resultCode").equals("00")) {

                                    String jsonStr = object.getString("result");

                                    jsonStr = jsonStr.replace("var s0={};", "");
                                    jsonStr = jsonStr.replace("var ", "");
                                    jsonStr = jsonStr.replace(";", ",");
                                    jsonStr = jsonStr.replace("s0['", "");
                                    jsonStr = jsonStr.replace("']", "");
                                    jsonStr = jsonStr.replace("=", ":");

                                    jsonStr = jsonStr.replace("\\", "");


                                    jsonStr = jsonStr.replace("{", "{\"");
                                    jsonStr = jsonStr.replace(":", "\":\"");
                                    jsonStr = jsonStr.replace(",", "\",\"");
                                    jsonStr = jsonStr.replace("}", "\"}");
                                    jsonStr = jsonStr.replace("}\",\"{", "},{");


                                    jsonStr = jsonStr.replace("\n", "ss");

                                    jsonStr = jsonStr.replace("\"\"", "\"");
                                    jsonStr = "{\"" + jsonStr + "\"}";
                                    jsonStr = jsonStr.replace("\",\" s0)\",\"ss\"", " s0)\":\"ss\"");
                                    Log.d("======= s0—s25 （已解析） =====", jsonStr);


                                    valueJson = new JSONObject(jsonStr);
                                    Log.e("========", valueJson.getString("s24"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mQueue.add(stringRequest);

                            Log.d("======= ！！！！参数！！！！ =====", "szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid + "&bookingType=" + bookingType);
                            Log.d("======= s0—s25 未解析 =====", response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (phoneNumber != null) {
                    toast("预约时间加载失败");
                    lock2 = true;
                    rg.setBackgroundColor(0xffffffff);
                }
            }
        });


    }

}
