package com.example.wang.qke.ui.main;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.myself.InviteFragment;
import com.example.wang.qke.ui.myself.PersonalFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFragment extends Fragment implements View.OnClickListener {


//    @Bind(R.id.back)
//    ImageView back;
//    @Bind(R.id.title)
//    TextView title;
    @Bind(R.id.personal)
    TextView personal;
    @Bind(R.id.invite)
    TextView invite;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.role0)
    ImageView role0;
    @Bind(R.id.role1)
    ImageView role1;
    @Bind(R.id.login)
    TextView login;
    @Bind(R.id.avatar)
    ImageView avatar;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private PersonalFragment personalFragment = new PersonalFragment();
    private InviteFragment inviteFragment = new InviteFragment();


    public static Handler h2;


    public MyselfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_myself, container, false);
        ButterKnife.bind(this, view);
//        初始化标题栏
//        title.setText("个人中心");
//        back.setVisibility(view.GONE);

//        personalFragment = new PersonalFragment();
//        inviteFragment = new InviteFragment();


        personal.setOnClickListener(this);
        invite.setOnClickListener(this);

        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.content, personalFragment).commit();
        personal.setSelected(true);


        h2 = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        login.setText("未登录");
                        role0.setVisibility(View.GONE);
                        role1.setVisibility(View.GONE);
                        break;
                    case 1:
                        login.setText(User.getInstance().getLogin());
                        if (User.getInstance().getRole().equals("0")) {
//                            role0.setVisibility(View.VISIBLE);
                            avatar.setImageResource(R.drawable.putongyonghu);
                        } else {
//                            role1.setVisibility(View.VISIBLE);
                            avatar.setImageResource(R.drawable.tongye);
                        }
                        break;
                }
            }
        };


        return view;
    }

    @Override
    public void onClick(View view) {

        ft = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.personal:
                ft.replace(R.id.content, personalFragment);
                personal.setSelected(true);
                invite.setSelected(false);

                break;
            case R.id.invite:
                ft.replace(R.id.content, inviteFragment);
                personal.setSelected(false);
                invite.setSelected(true);
                inviteFragment.loadinv();
                break;

            default:
                break;
        }
        ft.commit();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }

}
