package com.example.wang.qke.ui.main;

import android.graphics.Color;
import android.os.Bundle;

import com.example.wang.qke.R;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        //使用addSlide方法增加页面
            addSlide(SlideFragment.newInstance(R.layout.fragment_splansh1));
            addSlide(SlideFragment.newInstance(R.layout.fragment_splansh2));
            addSlide(SlideFragment.newInstance(R.layout.fragment_splansh3));
        //设置指示器颜色
            setColorDoneText(Color.parseColor("#313131"));
            setIndicatorColor(Color.parseColor("#555555"),Color.parseColor("#aaaaaa"));
        //将默认底部样式改为透明
            setSeparatorColor(getResources().getColor(R.color.touming));
        //初始化按钮
            showStatusBar(false);
            showSkipButton(false);
            setDoneText("立即体验");
    }
//  启动Activity
    private void startMain(){
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
        startMain();
    }

    @Override
    public void onSlideChanged() {
    }

    @Override
    public void onNextPressed() {
    }

}
