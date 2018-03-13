package com.example.wang.qke.ui.myself;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPasswordActivity extends BaseActivity {

    @Bind(R.id.mobNum)
    EditText mobNum;
    @Bind(R.id.verifyCode)
    EditText verifyCode;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.code)
    Button code;

    private boolean lock = true;

    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private StringRequest stringRequest2;

    private MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);

        // ----------------------------请求队列----------------------------------

        mQueue = Volley.newRequestQueue(this);


//-------------获取验证码post------------------------------------------------


        stringRequest2 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/forgetpassword", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {


                    object = new JSONObject(response);
                    String b = object.getString("msg");
                    toast(b);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                toast("发送失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("verify", "1");
                map.put("mobNum", mobNum.getText().toString());
                return map;
            }
        };

//------------------------------更新post请求----------------------

        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/forgetpassword", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String resultCode = object.getString("resultCode");
                    String b = object.getString("msg");
                    toast(b);
                    if (resultCode.equals("00"))
                        finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                lock = true;
                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                lock = true;
                toast("发送失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("verify", "0");
                map.put("mobNum", mobNum.getText().toString());
                map.put("verifyCode", verifyCode.getText().toString());
                map.put("new_password", newPassword.getText().toString());
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

        int a = newPassword.getText().toString().length();
        if(a >= 8 && a <= 16) {
            if (lock) {
                lock = false;
                mQueue.add(stringRequest);
            }
        }else {toast("密码不符合规范");}



//        LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.find_alert, null);
//        final EditText et = (EditText) ll.findViewById(R.id.alert_editText);
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(ll);
//        builder.show();

    }

    @OnClick(R.id.code)
    public void onView3Clicked() {
        myCountDownTimer.start();
        mQueue.add(stringRequest2);
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            if (code != null) {
                code.setClickable(false);
                code.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_my_hui));

                code.setText(l / 1000 + "s");
            } else myCountDownTimer.cancel();
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            if (code != null) {
                code.setText("重新获取");
                //设置可点击
                code.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_my));
                code.setClickable(true);
            } else myCountDownTimer.cancel();
        }
    }

}
