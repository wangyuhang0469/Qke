package com.example.wang.qke.ui.loan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.LoanPlan;
import com.example.wang.qke.classes.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Loan3Fragment extends BaseFragment {


    @Bind(R.id.age)
    EditText age;
    @Bind(R.id.loanFrequency)
    EditText loanFrequency;
    @Bind(R.id.overdueLoan)
    EditText overdueLoan;

    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private LoanActivity loanActivity;
    private List<LoanPlan> list = new ArrayList<>();


    public Loan3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan3, container, false);
        ButterKnife.bind(this, view);

        mQueue = Volley.newRequestQueue(getContext());

        loanActivity = (LoanActivity)getActivity();

        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/consult", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {


                    object = new JSONObject(response);
                    String a= object.getString("resultCode");
                    String b= object.getString("msg");
                    if (a.equals("00")){
                        loanActivity.next2();
                        loanActivity.uid =User.getInstance().getUid();
                        loanActivity.age = age.getText().toString();
                        loanActivity.overdueLoan = overdueLoan.getText().toString();
                        loanActivity.loanFrequency = loanFrequency.getText().toString();
                    }
                    toast(b);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loanActivity.lock = true;
                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                loanActivity.lock = true;
                toast("发送失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
//                map.put("loanAmount",loanActivity.loanAmount );
                map.put("step", "1");
                map.put("age", age.getText().toString());
                map.put("overdueLoan", overdueLoan.getText().toString());
                map.put("loanFrequency", loanFrequency.getText().toString());
                return map;
            }
        };








        return view;
    }

    public void addRequestQueue(){
        if (age.getText().length()!=0 && overdueLoan.getText().length()!=0 && loanFrequency.getText().length()!=0 ) {
            mQueue.add(stringRequest);
        }else {
            loanActivity.lock = true;
            toast("请填写完整信息");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
