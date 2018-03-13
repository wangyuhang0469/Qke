package com.example.wang.qke.ui.tools;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.example.wang.qke.classes.Record;
import com.example.wang.qke.classes.User;

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
public class RecordLeftFragment extends BaseFragment {


    @Bind(R.id.loading)
    TextView loading;
    @Bind(R.id.rg1)
    RadioGroup rg1;
    @Bind(R.id.rg2)
    RadioGroup rg2;
    @Bind(R.id.certNo)
    EditText certNo;
    @Bind(R.id.idCardNo)
    EditText idCardNo;
    @Bind(R.id.epName)
    EditText epName;
    @Bind(R.id.rg3)
    RadioGroup rg3;
    @Bind(R.id.ll_001)
    RelativeLayout ll001;
    @Bind(R.id.btn)
    Button btn;

    public RecordLeftFragment() {
        // Required empty public constructor
    }


    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private boolean lock = true;


    private String queryWay = "FenHu";
    private String certType = "FangDiChanQuan";
    private String certNoYear = "";


    private Record record = new Record();

    private MyCountDownTimer myCountDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_left, container, false);
        ButterKnife.bind(this, view);


        mQueue = Volley.newRequestQueue(getContext());



        //-------------post------------------------------------------------


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/record", new Response.Listener<String>() {
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


//                            String str = object.getString("data");


//                            str = str.replace("&nbsp;", "");
//                            str = str.replace("<li>", "");
//                            str = str.replace("</li>", "");
//                            str = str.replace("<span style=\"font-size:10.5pt;color:red\">", "");
//                            str = str.replace("</span>", "");
//                            str = str.replace("<hr size=\"1\">", "");
//                            str = str.replace("<br>", "");


                            record.setId(object.getString("id"));
                            record.setInqueryWay(object.getString("inqueryWay"));
                            record.setCertType(object.getString("certType"));
                            record.setCertNo(object.getString("certNo"));
                            record.setCertYear(object.getString("certYear"));
                            record.setIdNum(object.getString("idNum"));
                            record.setEpName(object.getString("epName"));
                            record.setCreateTime(object.getString("createTime"));

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("record", record);


                            RecordActivity recordActivity = (RecordActivity) getActivity();
                            recordActivity.recordRightFragment.updateWR(record);


                            startActivity(RecordDetailsActivity.class, bundle);

//                            Log.e("============解析后=========", str);

                        } else if (resultCode.equals("05")){
                            int a = object.getInt("result");
                            myCountDownTimer = new MyCountDownTimer(a*60*1000 , 60000);
                            myCountDownTimer.start();
                        }
                        else {
                            toast(msg);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    lock = true;
                    loading.setVisibility(View.GONE);

                    Log.d("===========解析前==========", response);
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
                map.put("queryWay", queryWay);
                map.put("certType", certType);
                map.put("certNo", certNo.getText().toString());
                map.put("idCardNo", idCardNo.getText().toString());
                map.put("epName", epName.getText().toString());
                map.put("certNoYear", certNoYear);

                Log.e("===========", " queryWay=" + queryWay + " certType=" + certType + " certNo=" + certNo.getText().toString() + " idCardNo=" + idCardNo.getText().toString() + " epName=" + epName.getText().toString() + " certNoYear=" + certNoYear);
                return map;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, //timeout时间
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1_left:
                        queryWay = "FenHu";
                        break;
                    case R.id.rb1_right:
                        queryWay = "FenDong";
                        break;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb2_left:
                        certType = "FangDiChanQuan";
                        ll001.setVisibility(View.GONE);
                        certNoYear = "";
                        break;
                    case R.id.rb2_right:
                        certType = "BuDongChanQuan";
                        ll001.setVisibility(View.VISIBLE);
                        certNoYear = "2015";
                        break;
                }
            }
        });


        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb3_left:
                        certNoYear = "2015";
                        break;
                    case R.id.rb3_middle:
                        certNoYear = "2016";
                        break;
                    case R.id.rb3_right:
                        certNoYear = "2017";
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
        if (certNo.getText().length() != 0) {
            if (epName.getText().length() != 0 || idCardNo.getText().length() != 0) {
                if (lock) {
                    lock = false;
                    loading.setVisibility(View.VISIBLE);
                    mQueue.add(stringRequest);
                }
            } else {
                toast("请填写权利人或身份证号");
            }
        } else {
            toast("请填写房产证号");
        }

    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            if (btn != null) {
                btn.setClickable(false);
                btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_my_hui));

                btn.setText("查询倒计时:"+l / 1000 /60+ "分");
            } else myCountDownTimer.cancel();
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            if (btn != null) {
                btn.setText("查   询");
                //设置可点击
                btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_my));
                btn.setClickable(true);
            } else myCountDownTimer.cancel();
        }
    }


}
