package com.example.wang.qke.ui.myself;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
import com.example.wang.qke.classes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RePasswordActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.o_password)
    EditText oPassword;
    @Bind(R.id.n_password1)
    EditText nPassword1;
    @Bind(R.id.n_password2)
    EditText nPassword2;

    private StringRequest stringRequest;
    private RequestQueue mQueue;

    private boolean lock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
        ButterKnife.bind(this);

        title.setText("修改密码");


        // ----------------------------请求队列----------------------------------

        mQueue = Volley.newRequestQueue(this);


//-------------post------------------------------------------------


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/update_profile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String b = object.getString("msg");
                    toast(b);

                    if (object.getString("resultCode").equals("00")){
                        User.getInstance().clear();

                        startActivity(LoginActivity.class,null,true);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                toast("修改失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("login", "");
                map.put("o_password",oPassword.getText().toString());
                map.put("n_password",nPassword2.getText().toString());

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

        if (oPassword.getText().length() != 0 && nPassword1.getText().length() !=0 && nPassword2.getText().length() != 0) {
            String a = nPassword1.getText().toString();
            String b = nPassword2.getText().toString();
            if (a.equals(b)) {
                if (a.length() >= 8 && a.length() <= 16) {
                    if (lock) {
                        lock = false;
                        mQueue.add(stringRequest);
                    }
                } else {
                    toast("密码不符合规范");
                }
            } else {
                toast("两次输入密码不一致");
            }
        }else {toast("请填写完整信息");}
    }




}
