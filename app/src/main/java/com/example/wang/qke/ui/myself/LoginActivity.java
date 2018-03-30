package com.example.wang.qke.ui.myself;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.wang.qke.classes.User;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.mobNum)
    EditText mobNum;
    @Bind(R.id.password)
    EditText password;


    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        user=User.getInstance();

        mQueue = Volley.newRequestQueue(this);


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                JSONObject result = null;
                try {
                    object = new JSONObject(response);
                    if (object.getString("resultCode").equals("00")) {
                        result = object.getJSONObject("result");
                        user.setUid(result.getString("uid"));
                        user.setLogin(result.getString("login"));
                        user.setMobNum(result.getString("mobNum"));
                        user.setRole(result.getString("role"));
                        user.setInviteCode(result.getString("inviteCode"));
                        user.setToken(result.getString("token"));
                        user.setStatus(true);
                        SharedPreferences.Editor editor = getSharedPreferences("verifyUser", MODE_PRIVATE).edit();
                        editor.putString("uid", user.getUid());
                        editor.putString("token", user.getToken());
                        editor.commit();
                        finish();
                    }
                    toast(object.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                toast("登录失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("password", password.getText().toString());
                map.put("mobNum", mobNum.getText().toString());
                return map;
            }

        };




    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.login, R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:

                if (!mobNum.getText().toString().equals("")) {
                    if (!password.getText().toString().equals("")){
                        mQueue.add(stringRequest);
                    }else {
                        toast("请输入密码");
                    }
                }else{
                    toast("请输入手机号");
                }

                break;
            case R.id.tv1:
                startActivity(FindPasswordActivity.class, null, false);
                break;
            case R.id.tv2:
                startActivity(RegisterActivity.class, null, false);
                break;
        }
    }

}
