package com.example.wang.qke.ui.tools;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

public class RecordActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private String[] titles = new String[]{"立即查档  ", "  查档记录"};
    private FragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    public RecordRightFragment recordRightFragment = new RecordRightFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        title.setText("查档");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        mFragments.add(new RecordLeftFragment());
        mFragments.add(recordRightFragment);
//        mFragments = new ArrayList<>();
//        for (int i = 0; i < mTitles.size(); i++) {
//            mFragments.add(SearchRecordFragment.newInstance(i));
//
//        }
        adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来



    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
