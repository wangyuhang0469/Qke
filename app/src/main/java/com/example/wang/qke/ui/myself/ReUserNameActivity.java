package com.example.wang.qke.ui.myself;

import android.os.Bundle;
import android.os.Message;
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

import static com.example.wang.qke.ui.main.MyselfFragment.h2;


public class ReUserNameActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.login)
    EditText login;

    private StringRequest stringRequest;
    private RequestQueue mQueue;
    private boolean lock = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_user_name);
        ButterKnife.bind(this);
        title.setText("修改用户名");
        login.setHint(User.getInstance().getLogin());

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
                        Thread thread = new Thread(){
                            public void run(){
                                User.getInstance().setLogin(login.getText().toString());
                                Message msg2 = h2.obtainMessage();
                                    msg2.what = 1;
                                h2.sendMessage(msg2);
                                finish();
                            }
                        };
                        thread.start();
                    }

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
                toast("修改失败");
                lock = true;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("login", login.getText().toString());
                map.put("password","");

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
        if (login.getText().length() != 0) {
            if (lock) {
                lock = false;
                mQueue.add(stringRequest);
            }
        }else toast("请输入新用户名");
    }
}
