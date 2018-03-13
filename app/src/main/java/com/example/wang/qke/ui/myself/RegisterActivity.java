package com.example.wang.qke.ui.myself;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.rb1)
    RadioButton rb1;
    @Bind(R.id.rb2)
    RadioButton rb2;
    @Bind(R.id.login)
    EditText login;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.company)
    EditText company;
    @Bind(R.id.inviteCodeWho)
    EditText inviteCodeWho;
    @Bind(R.id.mobNum)
    EditText mobNum;
    @Bind(R.id.verifyCode)
    TextView verifyCode;
    @Bind(R.id.role)
    RadioGroup role;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.ll_line)
    LinearLayout llLine;
    @Bind(R.id.code)
    Button code;

    private StringRequest stringRequest;
    private StringRequest stringRequest2;
    private RequestQueue mQueue;
    private String mRole = "0";
    private boolean lock = true;


    private  MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Drawable drawable = getResources().getDrawable(R.drawable.loan_radio_button);
        drawable.setBounds(0, 0, 46, 46);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb1.setCompoundDrawables(drawable, null, null, null);//只放上面

        Drawable drawable2 = getResources().getDrawable(R.drawable.loan_radio_button);
        drawable2.setBounds(0, 0, 46, 46);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb2.setCompoundDrawables(drawable2, null, null, null);//只放上面

//-------------------------------定义动画------------------------------
        final Animation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(400);

        final Animation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);


        // ----------------------------请求队列----------------------------------

        mQueue = Volley.newRequestQueue(this);

        //-------------注册post------------------------------------------------
        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/register", new Response.Listener<String>() {
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


                Log.d("json-------------", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                toast("注册失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("verifyCode", verifyCode.getText().toString());
                map.put("mobNum", mobNum.getText().toString());
                map.put("role", mRole);
                map.put("login", login.getText().toString());
                map.put("password", password.getText().toString());
                map.put("inviteCode", inviteCodeWho.getText().toString());
                map.put("company", company.getText().toString());

                return map;
            }
        };


        //-------------获取验证码post------------------------------------------------


        stringRequest2 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String resultCode = object.getString("resultCode");
                    String b = object.getString("msg");
                    toast(b);

                    if (!resultCode.equals("00")){
                        myCountDownTimer.cancel();
                        myCountDownTimer.onFinish();
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
                lock = true;
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


        role.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1:
                        mRole = "0";
                        ll.startAnimation(mHiddenAction);
                        ll.setVisibility(View.GONE);
                        llLine.startAnimation(mHiddenAction);
                        llLine.setVisibility(View.GONE);
                        break;
                    case R.id.rb2:
                        mRole = "1";
                        ll.startAnimation(mShowAction);
                        ll.setVisibility(View.VISIBLE);
                        llLine.startAnimation(mShowAction);
                        llLine.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


    }

    @OnClick({R.id.back, R.id.tologin})
    public void onViewClicked(View view) {
        finish();

    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        int a = password.getText().toString().length();
        if(a >= 8 && a <= 16) {
            if (lock) {
                lock = false;
                mQueue.add(stringRequest);
            }
        }else {toast("密码不符合规范");}
    }

    @OnClick(R.id.code)
    public void onView2Clicked() {
        myCountDownTimer.start();
        mQueue.add(stringRequest2);

    }

    //复写倒计时
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
            }else myCountDownTimer.cancel();
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
            }else myCountDownTimer.cancel();
        }
    }






}
