package com.example.wang.qke.ui.articles;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView tv_title;
    private WebView webview;

    private String id;
    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);



        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        id = bundle.getString("id");
        title = bundle.getString("title");
        url = "http://123.207.61.165/outside/article/" + id;

        tv_title.setText(title);


        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(url);
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient());


        RequestQueue mQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest( "http://123.207.61.165/outside/add_pageView?id="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
            }
        });

        mQueue.add(stringRequest);


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
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
