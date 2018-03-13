package com.example.wang.qke.ui.tools;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.classes.Workdoc;

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
public class WorkLeftFragment extends BaseFragment {

    ;
    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.loading)
    TextView loading;

    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private boolean lock = true;

    private Workdoc workdoc = new Workdoc();

    public WorkLeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_left, container, false);
        ButterKnife.bind(this, view);

        mQueue = Volley.newRequestQueue(getContext());
        //-------------post------------------------------------------------


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/workdoc", new Response.Listener<String>() {
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


                            workdoc.setUid(object.getString("uid"));
                            workdoc.setDocNum(object.getString("docNum"));
                            workdoc.setAppNum(object.getString("appNum"));
                            workdoc.setEvent(object.getString("event"));
                            workdoc.setAcceptDate(object.getString("acceptDate"));
                            workdoc.setResponseDate(object.getString("responseDate"));
                            workdoc.setState(object.getString("state"));
                            workdoc.setTime(object.getString("time"));

                            Bundle bundle = new Bundle();
                            bundle.putString("uid", workdoc.getUid());
                            bundle.putString("docNum", workdoc.getDocNum());
                            bundle.putString("appNum", workdoc.getAppNum());
                            bundle.putString("event", workdoc.getEvent());
                            bundle.putString("acceptDate", workdoc.getAcceptDate());
                            bundle.putString("responseDate", workdoc.getResponseDate());
                            bundle.putString("state", workdoc.getState());
                            bundle.putString("time", workdoc.getTime());


                            WorkActivity workActivity = (WorkActivity) getActivity();
                            workActivity.workRightFragment.updateWR(workdoc);


                            startActivity(WorkDetailsActivity.class, bundle);


                        } else {
                            toast(msg);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    lock = true;
                    loading.setVisibility(View.GONE);

                    Log.d("json-------------", response);
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
                map.put("searchNo", edit.getText().toString());
                return map;
            }
        };


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (edit.getText().length()!=0) {
            if (lock) {
                lock = false;
                loading.setVisibility(View.VISIBLE);
                mQueue.add(stringRequest);
            }
        }else {toast("请填写办公编号");}
    }

    @OnClick(R.id.call)
    public void onView1Clicked() {
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        Uri data = Uri.parse("tel:" + "135xxxxxxxx");
//        intent.setData(data);
//        startActivity(intent);
    }
}
