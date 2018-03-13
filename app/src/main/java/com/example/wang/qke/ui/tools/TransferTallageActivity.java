package com.example.wang.qke.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.adapter.FragmentAdapter;
import com.example.wang.qke.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransferTallageActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private String[] titles = new String[]{"查过户价  ","计算税费", "  税费记录"};
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private int tab;
    private TransferTallageRightFragment transferTallageRightFragment = new TransferTallageRightFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);

        getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        Intent intent = this.getIntent();
        String t = intent.getStringExtra("tab");
        int tab = Integer.parseInt(t);

        title.setText("过户税费");

        mTitles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mTitles.add(titles[i]);
        }

        TallageFragment tallageFragment = new TallageFragment();
        mFragments = new ArrayList<>();
        mFragments.add(new TransferFragment());
        mFragments.add(tallageFragment);
        mFragments.add(transferTallageRightFragment);
//        mFragments = new ArrayList<>();
//        for (int i = 0; i < mTitles.size(); i++) {
//            mFragments.add(SearchRecordFragment.newInstance(i));
//
//        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来

        mViewPager.setCurrentItem(tab);
        mTabLayout.getTabAt(tab).select();

//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        ft.replace(R.id.loan_content, tallageFragment).commit();


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
