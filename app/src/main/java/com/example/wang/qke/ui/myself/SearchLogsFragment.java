package com.example.wang.qke.ui.myself;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import android.widget.Toast;

import com.example.wang.qke.R;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchLogsFragment extends Fragment {


    public static SearchRecordFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        SearchRecordFragment fragment = new SearchRecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logs, null);
        ButterKnife.bind(this, view);

        SwitchButton mSwitchButton = (SwitchButton) view.findViewById(R.id.switchButton);
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String s;
                if (b)
                    s = "选中";
                else
                    s = "未选";

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}