package com.example.wang.qke.ui.myself;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    @Bind(R.id.mobNum)
    TextView mobNum;
    @Bind(R.id.inviteCode)
    TextView inviteCode;
    @Bind(R.id.outLog)
    TextView outLog;

    private User user;

    public static Handler h;
    private RequestQueue mQueue;
    private StringRequest stringRequest;


    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);

        user = User.getInstance();

        h = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        mobNum.setText("");
                        inviteCode.setText("");
                        break;
                    case 1:
                        mobNum.setText(user.getMobNum());
                        inviteCode.setText(user.getInviteCode());
                        outLog.setText("退出登录");
                        break;
                }
            }
        };

        mobNum.setText(user.getMobNum());
        inviteCode.setText(user.getInviteCode());


        mQueue = Volley.newRequestQueue(getContext());

        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/logout", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
//                toast("请求失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                Log.e("uid", String.valueOf(Integer.valueOf(user.getUid())));
                map.put("uid", String.valueOf(Integer.valueOf(user.getUid())));
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

    @OnClick({R.id.reUserName, R.id.phone, R.id.rePassword, R.id.inviteFriend, R.id.invCodeBar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reUserName:
                startActivity(ReUserNameActivity.class, null);
                break;
            case R.id.phone:
                break;
            case R.id.rePassword:
                startActivity(RePasswordActivity.class, null);
                break;
            case R.id.inviteFriend:
                startActivity(InviteFriendActivity.class, null);
                break;
            case R.id.invCodeBar:
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(inviteCode.getText());
                toast("邀请码已复制");
                break;
        }
    }

    @OnClick(R.id.outLog)
    public void onViewClicked() {

        if (User.getInstance().getStatus()) {
            mQueue.add(stringRequest);

            SharedPreferences.Editor editor = getActivity().getSharedPreferences("lock", MODE_PRIVATE).edit();
            editor.putString("token", " ");
            editor.putString("uid", " ");
            editor.commit();

            toast("退出成功");
            outLog.setText("登  录");
            user.clear();

        }else {
            startActivity(LoginActivity.class,null);

        }

    }
}
