package com.example.wang.qke.ui.myself;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import com.example.wang.qke.R;


import butterknife.ButterKnife;

/**
 * Created by lijuan on 2016/8/23.
 */
public class SearchRecordFragment extends Fragment {



    public static SearchRecordFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", 'A' + index);
        SearchRecordFragment fragment = new SearchRecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);

        ButterKnife.bind(this, view);

        View v1 = (View)view.findViewById(R.id.rd1);
        View v2 = (View)view.findViewById(R.id.rd2);
        RadioButton btn1Left = (RadioButton) v1.findViewById(R.id.left);
        RadioButton btn1Right = (RadioButton) v1.findViewById(R.id.right);
        RadioButton btn2Left = (RadioButton) v2.findViewById(R.id.left);
        RadioButton btn2Right = (RadioButton) v2.findViewById(R.id.right);
        btn1Left.setText("原地产权");
        btn1Right.setText("不动产权");
        btn2Left.setText("  分户  ");
        btn2Right.setText("  分栋  ");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}