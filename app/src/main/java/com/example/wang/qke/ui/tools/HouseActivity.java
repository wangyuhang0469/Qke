package com.example.wang.qke.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.view.WindowManager;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.adapter.FragmentAdapter;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.ToolHouse;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;


    private Bundle bundle;
    private Intent intent;
    public String form ="";


    private String[] titles = new String[]{"发起询价  ", "  询价记录"};
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    private HouseRightFragment houseRightFragment= new HouseRightFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        ButterKnife.bind(this);

        title.setText("房价查询");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        intent = this.getIntent();
        bundle = intent.getExtras();
        //接收name值
        if (bundle != null) {
            form = bundle.getString("form");
        }




        mTitles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mTitles.add(titles[i]);
        }


        mFragments = new ArrayList<>();
        mFragments.add(new HouseLeftFragment());
        mFragments.add(houseRightFragment);
//        mFragments = new ArrayList<>();
//        for (int i = 0; i < mTitles.size(); i++) {
//            mFragments.add(SearchRecordFragment.newInstance(i));
//
//        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来





    }

    public void getData(String data){
        bundle.putString("result",data);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();
    }

    public void updataRF(ToolHouse toolHouse){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        houseRightFragment.updata(toolHouse);

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


}
