package com.example.wang.qke.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.Record;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordDetailsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.inqueryWay)
    TextView inqueryWay;
    @Bind(R.id.certNo)
    TextView certNo;
    @Bind(R.id.epName)
    TextView epName;
    @Bind(R.id.idNum)
    TextView idNum;
    @Bind(R.id.ll_001)
    LinearLayout ll001;
    @Bind(R.id.ll_002)
    LinearLayout ll002;


    private Record record;
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        ButterKnife.bind(this);

        title.setText("查档详情");

        Intent intent = this.getIntent();
        record = (Record) intent.getSerializableExtra("record");


        String url = "http://123.207.61.165/outside/queryrecord/" + record.getId();

        inqueryWay.setText(record.getInqueryWay());
        certNo.setText(record.getCertNo());

        if (!record.getEpName().equals("")) {
            ll001.setVisibility(View.VISIBLE);
            epName.setText(record.getEpName());
        }
        if (!record.getIdNum().equals("")) {
            ll002.setVisibility(View.VISIBLE);
            idNum.setText(record.getIdNum());
        }


        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient());


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.btn)
    public void onVie2wClicked() {
        finish();
    }


    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
