package com.example.wang.qke.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.myself.LoginActivity;
import com.example.wang.qke.ui.tools.HouseActivity;
import com.example.wang.qke.ui.tools.OrderActivity;
import com.example.wang.qke.ui.tools.RecordActivity;
import com.example.wang.qke.ui.tools.TransferTallageActivity;
import com.example.wang.qke.ui.tools.WorkActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends BaseFragment {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;

    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        ButterKnife.bind(this, view);

        back.setVisibility(view.INVISIBLE);
        title.setText("权威工具");


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tools_lab1, R.id.tools_lab2, R.id.tools_lab3, R.id.tools_lab4, R.id.tools_lab5, R.id.tools_lab6})
    public void onViewClicked(View view) {
        if (User.getInstance().getStatus()) {
            switch (view.getId()) {
                case R.id.tools_lab1:
                    Bundle bundle = new Bundle();
                    bundle.putString("form", "1");
                    startActivity(HouseActivity.class, bundle);
                    break;
                case R.id.tools_lab2:
                    startActivity(RecordActivity.class, null);
                    break;
                case R.id.tools_lab3:
                    startActivity(WorkActivity.class, null);
                    break;
                case R.id.tools_lab4:
                    startActivity(OrderActivity.class, null);
                    break;
                case R.id.tools_lab5:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), TransferTallageActivity.class);
                    intent.putExtra("tab", "0");
                    startActivity(intent);
                    break;
                case R.id.tools_lab6:
                    break;
            }
        }else {startActivity(LoginActivity.class,null);}
    }
}
