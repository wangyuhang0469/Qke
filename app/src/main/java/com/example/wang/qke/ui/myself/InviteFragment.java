package com.example.wang.qke.ui.myself;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.classes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteFragment extends Fragment {


    @Bind(R.id.fareTotal)
    TextView fareTotal;
    @Bind(R.id.reward)
    TextView reward;
    @Bind(R.id.needed)
    TextView needed;

    private StringRequest stringRequest;
    private RequestQueue mQueue;

    public InviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite, container, false);
        ButterKnife.bind(this, view);



//-------------post------------------------------------------------
        mQueue = Volley.newRequestQueue(getContext());


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/getreward", new Response.Listener<String>() {
//            stringRequest = new StringRequest( "123.207.61.165/outside/getreward", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String b = object.getString("msg");


                    if (object.getString("resultCode").equals("00")){
                        if (b.equals("没有奖励记录")){
                            if (fareTotal != null) {
                                fareTotal.setText("0");
                                reward.setText("0");
                                needed.setText("0");
                            }
                        }else {
                            String string = object.getString("result");
                            string = string.replace("[", "");
                            string = string.replace("]", "");
                            object = new JSONObject(string);
                            if (fareTotal != null) {
                                fareTotal.setText(object.getString("fareTotal"));
                                reward.setText(object.getString("reward"));
                                needed.setText(object.getString("needed"));
                            }
                        }

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

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                Log.e("===","uid="+User.getInstance().getUid());

                map.put("uid", User.getInstance().getUid());


                return map;
            }
        };



        mQueue.add(stringRequest);



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void loadinv(){


    }
}
