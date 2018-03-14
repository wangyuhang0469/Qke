package com.example.wang.qke.ui.main;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.Article;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.articles.ArticleActivity;
import com.example.wang.qke.ui.articles.BiblesActivity;
import com.example.wang.qke.ui.articles.InformationActivity;
import com.example.wang.qke.ui.loan.LoanActivity;
import com.example.wang.qke.ui.myself.LoginActivity;
import com.example.wang.qke.ui.tools.HouseActivity;
import com.example.wang.qke.ui.tools.OrderActivity;
import com.example.wang.qke.ui.tools.RecordActivity;
import com.example.wang.qke.ui.tools.TransferTallageActivity;
import com.example.wang.qke.ui.tools.WorkActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import okhttp3.Call;

public class HomePageFragment extends BaseFragment {


    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.ptrFrameLayout)
    PtrFrameLayout mPtrFrame;

    @Bind({R.id.title1, R.id.title2, R.id.title3})
    List<TextView> titleList1;
    @Bind({R.id.title4, R.id.title5, R.id.title6})
    List<TextView> titleList2;
    @Bind({R.id.data1, R.id.data2, R.id.data3})
    List<TextView> dataList1;
    @Bind({R.id.data4, R.id.data5, R.id.data6})
    List<TextView> dataList2;
    @Bind({R.id.home_article1_pic, R.id.home_article2_pic, R.id.home_article3_pic})
    List<ImageView> picList1;
    @Bind({R.id.home_article4_pic, R.id.home_article5_pic, R.id.home_article6_pic})
    List<ImageView> picList2;
    @Bind(R.id.banner)
    Banner banner;
    private String ids[] = new String[6];




//=============================================主线程=====================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);

        loadFromInternet();

        initRefresh();


        List<String> list = new ArrayList<String>();
        list.add("深圳");
        list.add("北京");
        list.add("上海");
        list.add("广州");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item2, R.id.tv_spinner, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(adapter);


        return view;
    }


    // 网络加载轮播图、资讯、宝典
    private void loadFromInternet() {

        //---------------------------------------get轮播图---------------------------------------------------------

        OkHttpUtils.get()
                .url("http://123.207.61.165/outside/getbanners?tid=2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<String> imageList = new ArrayList<String>();

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("result");
                            for (int i = 0; i < array.length(); i++) {
                                imageList.add("http://123.207.61.165/uploads/banner/" + array.getJSONObject(i).getString("pic"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        log(imageList.toString());
                        banner.setImages(imageList).setImageLoader(new GlideImageLoader()).start();

                    }
                });

        //---------------------------------------get 资讯---------------------------------------------------------

        OkHttpUtils.get()
                .url("http://123.207.61.165/outside/getarticles?from=0&type=资讯")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject object = new JSONObject(response);
                            List<Article> list = JSON.parseArray(object.getString("result"), Article.class);


                            for (int i = 0; i < list.size(); i++) {

                                Article article = list.get(i);

                                titleList1.get(i).setText(article.getTitle());
                                dataList1.get(i).setText(article.getTime());
                                final int finalI = i;
                                Glide.with(getContext())
                                        .load(article.getPicUrl())
                                        .asBitmap()
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    picList1.get(finalI).setBackground(new BitmapDrawable(resource));   //设置背景
                                                }
                                            }
                                        });

                                ids[i] = article.getId();
                                Log.e("==============================================================", article.getId());

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.d("loopers", response);
                    }
                });


        //---------------------------------------get 宝典---------------------------------------------------------


        OkHttpUtils.get()
                .url("http://123.207.61.165/outside/getarticles?from=0&type=宝典")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject object = new JSONObject(response);
                            List<Article> list = JSON.parseArray(object.getString("result"), Article.class);


                            for (int i = 0; i < list.size(); i++) {

                                Article article = list.get(i);

                                titleList2.get(i).setText(article.getTitle());
                                dataList2.get(i).setText(article.getTime());
                                final int finalI = i;
                                Glide.with(getContext())
                                        .load(article.getPicUrl())
                                        .asBitmap()
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                    picList2.get(finalI).setBackground(new BitmapDrawable(resource));   //设置背景
                                                }
                                            }
                                        });

                                ids[i + 3] = article.getId();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.d("loopers", response);
                    }
                });

    }

    //初始化下拉刷新
    private void initRefresh(){
        //------------------------------------下拉刷新------------------------------------------

        final MaterialHeader header = new MaterialHeader(getContext());
        header.setPadding(5, PtrLocalDisplay.dp2px(15), 5, 5);//显示相关工具类，用于获取用户屏幕宽度、高度以及屏幕密度。同时提供了dp和px的转化方法。



        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setPtrHandler(new PtrHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                System.out.println("MainActivity.onRefreshBegin");
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        loadFromInternet();
                    }
                }, 1800);

            }

            /**
             * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                System.out.println("MainActivity.checkCanDoRefresh");
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                // return true;
            }
        });
    }


    //以下是各种点击事件
    @OnClick({R.id.home_lab1, R.id.home_lab2, R.id.home_lab3, R.id.home_lab4, R.id.home_lab5})
    public void onViewClicked(View view) {
        if (User.getInstance().getStatus()) {
            switch (view.getId()) {
                case R.id.home_lab1:
                    startActivity(HouseActivity.class, null);
                    break;
                case R.id.home_lab2:
                    startActivity(RecordActivity.class, null);
                    break;
                case R.id.home_lab3:
                    startActivity(WorkActivity.class, null);
                    break;
                case R.id.home_lab4:
                    startActivity(OrderActivity.class, null);
                    break;
                case R.id.home_lab5:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), TransferTallageActivity.class);
                    intent.putExtra("tab", "0");
                    startActivity(intent);
                    break;
//                case R.ids.home_lab6:
//
//                    break;
            }
        } else {
            startActivity(LoginActivity.class, null);
        }
    }


    @OnClick({R.id.information_bar, R.id.bible_bar})
    public void onView3Clicked(View view) {
        switch (view.getId()) {
            case R.id.information_bar:
                startActivity(InformationActivity.class, null);
                break;
            case R.id.bible_bar:
                startActivity(BiblesActivity.class, null);
                break;
        }
    }

    @OnClick({R.id.home_article1, R.id.home_article2, R.id.home_article3, R.id.home_article4, R.id.home_article5, R.id.home_article6})
    public void onAtcClicked(View view) {
        Intent intent = new Intent(getContext(), ArticleActivity.class);

        //用Bundle携带数据
        Bundle bundle = new Bundle();


        switch (view.getId()) {
            case R.id.home_article1:
                bundle.putString("id", ids[0]);
                bundle.putString("title", titleList1.get(0).getText().toString());
                break;
            case R.id.home_article2:
                bundle.putString("id", ids[1]);
                bundle.putString("title", titleList1.get(1).getText().toString());
                break;
            case R.id.home_article3:
                bundle.putString("id", ids[2]);
                bundle.putString("title", titleList1.get(2).getText().toString());
                break;
            case R.id.home_article4:
                bundle.putString("id", ids[3]);
                bundle.putString("title", titleList2.get(0).getText().toString());
                break;
            case R.id.home_article5:
                bundle.putString("id", ids[4]);
                bundle.putString("title", titleList2.get(1).getText().toString());
                break;
            case R.id.home_article6:
                bundle.putString("id", ids[5]);
                bundle.putString("title", titleList2.get(2).getText().toString());
                break;
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.home_icon)
    public void onViewClicked() {
        if (User.getInstance().getStatus()) {
            startActivity(LoanActivity.class, null);
        } else {
            startActivity(LoginActivity.class, null);
        }
    }


    //Banner需定义的图片加载类
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load( path).into(imageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
