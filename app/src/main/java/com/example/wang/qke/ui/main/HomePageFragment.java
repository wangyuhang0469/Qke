package com.example.wang.qke.ui.main;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wang.qke.R;
import com.example.wang.qke.adapter.MyAdapter;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.Article;
import com.example.wang.qke.classes.ArticleTool;
import com.example.wang.qke.classes.ImageUtil;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.myself.LoginActivity;
import com.example.wang.qke.ui.articles.ArticleActivity;
import com.example.wang.qke.ui.articles.BiblesActivity;
import com.example.wang.qke.ui.articles.InformationActivity;
import com.example.wang.qke.ui.loan.LoanActivity;
import com.example.wang.qke.ui.tools.HouseActivity;
import com.example.wang.qke.ui.tools.OrderActivity;
import com.example.wang.qke.ui.tools.RecordActivity;
import com.example.wang.qke.ui.tools.TransferTallageActivity;
import com.example.wang.qke.ui.tools.WorkActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHousePath;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {


    //统计下载了几张图片
    int n = 0;
    //统计当前viewpager轮播到第几页
    int p = 0;
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.ll_tag)
    LinearLayout ll_tag;
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.ptrFrameLayout)
    PtrFrameLayout mPtrFrame;

    @Bind({R.id.title1 , R.id.title2 , R.id.title3})
    List<TextView> titleList1;
    @Bind({R.id.title4 , R.id.title5 , R.id.title6})
    List<TextView> titleList2;
    @Bind({ R.id.data1 , R.id.data2 ,R.id.data3 })
    List<TextView> dataList1;
    @Bind({ R.id.data4 , R.id.data5 ,R.id.data6 })
    List<TextView> dataList2;
    @Bind({R.id.home_article1_pic,R.id.home_article2_pic,R.id.home_article3_pic})
    List<ImageView> picList1;
    @Bind({R.id.home_article4_pic,R.id.home_article5_pic,R.id.home_article6_pic})
    List<ImageView> picList2;
    private String ids[] = new String[6];



    private StringRequest stringRequest;
    private RequestQueue mQueue;


    //-------------------------------------------------轮播图变量-----------------------------------
    //准备好三张网络图片的地址
    private String imageUrl[] = new String[3];

    private List<ImageView> data;
    //控制图片是否开始轮播的开关,默认关的
    private boolean isStart = false;
    //开始图片轮播的线程
    private MyThread t;
    //存放代表viewpager播到第几张的小圆点
    //存储小圆点的一维数组
    private ImageView tag[];
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    n++;
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ImageView iv = new ImageView(getContext());
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);
                    //把图片添加到集合里
                    data.add(iv);
                    //当接收到第三张图片的时候，设置适配器,
                    if (n == imageUrl.length) {
                        vp.setAdapter(new MyAdapter(data, getContext()));
                        //创建小圆点
                        creatTag();
                        //把开关打开
                        isStart = true;
                        t = new MyThread();
                        //启动轮播图片线程
                        t.start();

                    }
                    break;
                case 1:
                    //接受到的线程发过来的p数字
                    int page = (Integer) msg.obj;
                    vp.setCurrentItem(page);

                    break;
            }

        }

        ;
    };
//-------------------------轮播图变量end-----------------------------------------


//=============================================主线程=====================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);

        loadFromInternet();


        mQueue = Volley.newRequestQueue(getActivity());

        //---------------------------------------get轮播图---------------------------------------------------------

        stringRequest = new StringRequest("http://123.207.61.165/outside/getbanners?tid=2",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("result");
                            for (int i = 0; i < array.length(); i++) {
//                                imageUrl= new String[array.length()];
                                imageUrl[i] = "http://123.207.61.165/uploads/banner/" + array.getJSONObject(i).getString("pic");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        init();               //轮播图执行函数


                        Log.d("loopers", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });







        mQueue.add(stringRequest);

        //------------------------------------下拉菜单------------------------------------------
        List<String> list = new ArrayList<String>();
        list.add("深圳");
        list.add("北京");
        list.add("上海");
        list.add("广州");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter(adapter);



        //------------------------------------下拉菜单end------------------------------------------


        //------------------------------------下拉刷新------------------------------------------

//        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
//        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);


        /**
         * StoreHouse风格的头部实现
         */
//        final StoreHouseHeader header = new StoreHouseHeader(getContext());
//        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);

        /**
         * using a string, support: A-Z 0-9 - .
         * you can add more letters by {@link StoreHousePath#addChar}
         */
//         header.initWithString("Alibaba");


        /**
         * Material Design风格的头部实现
         */
        final MaterialHeader header = new MaterialHeader(getContext());
        header.setPadding(5, PtrLocalDisplay.dp2px(15), 5, 5);//显示相关工具类，用于获取用户屏幕宽度、高度以及屏幕密度。同时提供了dp和px的转化方法。


        /**
         * Rentals Style风格的头部实现
         * 这个需要引入这两个类RentalsSunDrawable.java ; RentalsSunHeaderView.java
         * 在人家git上的daemon中能找到
         */
       /* final RentalsSunHeaderView header = new RentalsSunHeaderView(this);

        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setUp(mPtrFrame);
        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1500);*/


        // mPtrFrame = (PtrFrameLayout) findViewById(R.ids.ptr);
        mPtrFrame.setHeaderView(header);
        // mPtrFrame.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
        mPtrFrame.addPtrUIHandler(header);
        //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
        mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
        mPtrFrame.setPtrHandler(new PtrHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                System.out.println("MainActivity.onRefreshBegin");
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                        mQueue.add(stringRequest);
//                        mQueue.add(advsStringRequest);
//                        mQueue.add(atcStringRequest);
//                        mQueue.add(atcStringRequest2);
                        //mPtrFrame.autoRefresh();//自动刷新
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


        return view;
    }

    private void init() {
        // TODO Auto-generated method stub

        ll_tag.setBackgroundColor(00000000);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //把当前的页数赋值给P
                p = position;
                //得到当前图片的索引,如果图片只有三张，那么只有0，1，2这三种情况
                int currentIndex = (position % imageUrl.length);
                for (int i = 0; i < tag.length; i++) {
                    if (i == currentIndex) {
                        tag[i].setBackgroundResource(R.drawable.circle1);
                    } else {
                        tag[i].setBackgroundResource(R.drawable.circle2);
                    }
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //这个switch语句我注掉了，我觉得这个语句没有问题啊，可是为什么加上以下语句，当用手拉一次viewpager的时候，轮播就停止了，再也恢复不过来了?有人知道吗
                //switch(state){
                //当页面被手指拉动的时候，暂停轮播
                //case ViewPager.SCROLL_STATE_DRAGGING:
                //  isStart=false;
                //  break;
                //当手指拉完松开或者页面自己翻到下一页静止的时候,开始轮播
                //case ViewPager.SCROLL_STATE_IDLE:
                //  isStart=true;

                //  break;
                //
                //}
            }
        });
        //构造一个存储照片的集合
        data = new ArrayList<ImageView>();
        //从网络上把图片下载下来
        for (int i = 0; i < imageUrl.length; i++) {
            getImageFromNet(imageUrl[i]);

        }

    }


    private void getImageFromNet(final String imagePath) {
        // TODO Auto-generated method stub
        new Thread() {
            public void run() {
                try {
                    ImageUtil imageUtil = new ImageUtil(getContext());
                    Bitmap bitmap = imageUtil.readImage(imagePath);
                    if (bitmap != null) {
                        Log.e("轮播图片缓存---", "------------------------------------------");

                    } else {
                        Log.e("轮播图片下载---", "------------------------------------------");
                        URL url = new URL(imagePath);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setConnectTimeout(10 * 1000);
                        InputStream is = con.getInputStream();
                        //把流转换为bitmap
                        bitmap = BitmapFactory.decodeStream(is);
                        ImageUtil imageUtils = new ImageUtil(getContext());
                        imageUtils.saveImage(imagePath, bitmap, ImageUtil.FORMAT_JPEG);
                    }
                    Message message = new Message();
                    message.what = 0;
                    message.obj = bitmap;
                    //把这个bitmap发送到hanlder那里去处理
                    mHandler.sendMessage(message);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }catch (OutOfMemoryError e){
                    e.printStackTrace();
                }

            }

            ;
        }.start();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

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


//    @OnClick(R.ids.home_commit)
//    public void onView2Clicked() {
//        String a = homeEdit.getText().toString();
//
//        if (User.getInstance().getStatus()) {
//            if (TextUtils.isEmpty(homeEdit.getText())) {
//                toast("请输入贷款金额");
//            } else {
//                Intent intent2 = new Intent();
//                intent2.setClass(getActivity(), LoanActivity.class);
//                intent2.putExtra("much", a);
//                startActivity(intent2);
//            }
//        } else {
//            startActivity(LoginActivity.class, null);
//        }
//    }

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
        if (User.getInstance().getStatus()){
            startActivity(LoanActivity.class,null);
        }else {
            startActivity(LoginActivity.class,null);
        }
    }


    //控制图片轮播
    class MyThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while (isStart) {
                Message message = new Message();
                message.what = 1;
                message.obj = p;
                mHandler.sendMessage(message);
                try {
                    //睡眠3秒,在isStart为真的情况下，一直每隔三秒循环
                    Thread.sleep(3300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                p++;
            }
        }
    }

    protected void creatTag() {
        tag = new ImageView[imageUrl.length];
        for (int i = 0; i < imageUrl.length; i++) {

            tag[i] = new ImageView(getContext());
            //第一张图片画的小圆点是白点
            if (i == 0) {
                tag[i].setBackgroundResource(R.drawable.circle1);
            } else {
                //其它的画灰点
                tag[i].setBackgroundResource(R.drawable.circle2);
            }
            //设置上下左右的间隔
            tag[i].setPadding(10, 20, 10, 20);

            tag[i].setLayoutParams(new ViewGroup.LayoutParams(46, 46));
            //添加到viewpager底部的线性布局里面
            ll_tag.addView(tag[i]);
        }

    }

    private void loadFromInternet(){

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
                            List<Article> list = JSON.parseArray(object.getString("result"),Article.class);


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
                                Log.e("==============================================================",article.getId());

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
                            List<Article> list = JSON.parseArray(object.getString("result"),Article.class);


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

                                ids[i+3] = article.getId();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.d("loopers", response);
                    }
                });

    }




}
