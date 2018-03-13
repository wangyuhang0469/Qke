package com.example.wang.qke.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.wang.qke.classes.Order;
import com.example.wang.qke.classes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.realname)
    TextView realname;
    @Bind(R.id.idNum)
    TextView idNum;
    @Bind(R.id.bookTime)
    TextView bookTime;
    @Bind(R.id.bookCode)
    TextView bookCode;
    @Bind(R.id.event)
    TextView event;
    @Bind(R.id.fzFileNo)
    TextView fzFileNo;
    @Bind(R.id.registrationAreaName)
    TextView registrationAreaName;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.ll_002)
    LinearLayout ll002;
    @Bind(R.id.ll_001)
    LinearLayout ll001;
    @Bind(R.id.houseName)
    TextView houseName;
    @Bind(R.id.proveType)
    TextView proveType;
    @Bind(R.id.proveCode)
    TextView proveCode;
    @Bind(R.id.state)
    TextView state;

    private Order order;
    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private boolean lock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        title.setText("预约详情");

        Intent intent = this.getIntent();
        order = (Order) intent.getSerializableExtra("order");

        realname.setText(order.getRealname());
        idNum.setText(order.getIdNum());
        bookTime.setText(order.getBookTime());
        bookCode.setText(order.getBookCode());
        event.setText(order.getEvent());
        registrationAreaName.setText(order.getRegistrationAreaName());
        state.setText(order.getState());


        if (order.getFzFileNo() != null && !order.getFzFileNo().equals("null") && !order.getFzFileNo().equals("")) {
            fzFileNo.setText(order.getFzFileNo());
        } else {
            ll001.setVisibility(View.VISIBLE);
            ll002.setVisibility(View.GONE);
            houseName.setText("房地产名称："+order.getHouseName());
            proveType.setText("权属证明类型："+order.getProveType());
            proveCode.setText("权属证明编号："+order.getProveCode());
        }


        if (order.getState().equals("已取消") || order.getState().equals("已受理"))
            btn.setVisibility(View.GONE);


        mQueue = Volley.newRequestQueue(this);

        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/cancelReservation", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (fzFileNo != null) {
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response);
                        String resultCode = object.getString("resultCode");
                        String msg = object.getString("msg");

                        toast(msg);

                        if (resultCode.equals("00")) {
                            order.setState("已取消");
                            btn.setVisibility(View.GONE);
                            state.setText("已取消");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    lock = true;

                    Log.d("取消结果-------", response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                toast("请求失败");
                lock = true;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("phoneNumber", order.getMobNum());
                map.put("certificateNo", order.getIdNum());
                map.put("bookingCode", order.getBookCode());


                return map;


            }
        };


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.btn)
    public void onView2Clicked() {
        if (lock) {
            lock = false;
            mQueue.add(stringRequest);
        }
    }
}
