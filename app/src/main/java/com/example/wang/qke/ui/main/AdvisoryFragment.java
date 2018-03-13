package com.example.wang.qke.ui.main;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.AdvHistory;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.loan.LoanListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHousePath;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvisoryFragment extends BaseFragment {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listView1)
    ListView listView;
    @Bind(R.id.loading)
    TextView loading;
    @Bind(R.id.ptrFrameLayout)
    PtrFrameLayout mPtrFrame;
    @Bind(R.id.no_mess)
    LinearLayout noMess;
    private RequestQueue mQueue;
    private StringRequest stringRequest;

    private View v;
    private View v2;
    private Handler handler;
    private List<AdvHistory> list = new ArrayList<>();
    private AdvAdapter advAdapter;

    private boolean havaNext = true;
    //用来判断是否加载完成
    private boolean loadfinish = true;
    private int cpageNum = 0;

    private boolean lock = true;
    private boolean refresh = true;

    public AdvisoryFragment() {
        // Required empty public constructor
    }

    public static Handler h3;







    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advisory, container, false);
        ButterKnife.bind(this, view);

        title.setText("咨询历史");
        back.setVisibility(View.GONE);

        v = this.getLayoutInflater(null).inflate(R.layout.listview2_foot, null);

        mQueue = Volley.newRequestQueue(getContext());


        stringRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/consult_db", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                String resultCode = null;
                String result = null;
                try {


                    object = new JSONObject(response);
                    resultCode = object.getString("resultCode");
                    result = object.getString("result");
                    String msg = object.getString("msg");
                    if (resultCode.equals("00")) {
                        if (result.equals("null")){
                            noMess.setVisibility(View.VISIBLE);
                        }else {
                            JSONArray array = object.getJSONArray("result");
                            int len = array.length();
                            for (int i = 0; i < len; i++) {
                                object = array.getJSONObject(i);

                                AdvHistory advHistory = new AdvHistory();


                                String id = object.getString("id");
                                String uid = object.getString("uid");
                                String realname = object.getString("realname");
                                String sex = object.getString("sex");
                                String age = object.getString("age");
                                String mobNum = object.getString("mobNum");
                                String idNum = object.getString("idNum");
                                String occupation = object.getString("occupation");
                                String salaryRange = object.getString("salaryRange");
                                String isSZPerson = object.getString("isSZPerson");
                                String socialSec = object.getString("socialSec");
                                String marriage = object.getString("marriage");
                                String spauseJob = object.getString("spauseJob");
                                String spauseIncome = object.getString("spauseIncome");
                                String loanAmount = object.getString("loanAmount");
                                String property = object.getString("property");
                                String loanFrequency = object.getString("loanFrequency");
                                String overdueLoan = object.getString("overdueLoan");
                                String createTime = object.getString("createTime");


                                advHistory.setId(id);
                                advHistory.setUid(uid);
                                advHistory.setRealname(realname);
                                advHistory.setSex(sex);
                                advHistory.setAge(age);
                                advHistory.setMobNum(mobNum);
                                advHistory.setIdNum(idNum);
                                advHistory.setOccupation(occupation);
                                advHistory.setSalaryRange(salaryRange);
                                advHistory.setIsSZPerson(isSZPerson);
                                advHistory.setSocialSec(socialSec);
                                advHistory.setMarriage(marriage);
                                advHistory.setSpauseJob(spauseJob);
                                advHistory.setSpauseIncome(spauseIncome);
                                advHistory.setLoanAmount(loanAmount);
                                advHistory.setProperty(property);
                                advHistory.setLoanFrequency(loanFrequency);
                                advHistory.setOverdueLoan(overdueLoan);
                                advHistory.setCreateTime(createTime);


                                list.add(advHistory);
//
                            }
                        }
                        loading.setText("");

                    } else {
                        loading.setText(msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (refresh) {
                    refresh = false;

                    if (!havaNext && !result.equals("null")){
                        listView.removeFooterView(v2);
                        listView.addFooterView(v);
                    }
                    if (advAdapter != null) {
                        advAdapter.notifyDataSetChanged();
                        loadfinish = true;
                        if (list.size() > 9) {

                        } else {
                            havaNext = false;
                        }
                    } else {
                        advAdapter = new AdvAdapter(getContext(), list);
                        //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
                        if (list.size() > 9) {
                            listView.addFooterView(v);
                        } else {
                            havaNext = false;
                        }
                        listView.setAdapter(advAdapter);
                    }


                } else {

                    if (cpageNum == 0) {
                        advAdapter = new AdvAdapter(getContext(), list);
                        //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
                        if (list.size() > 9) {
                            listView.addFooterView(v);
                        } else {
                            havaNext = false;
                        }

                        listView.setAdapter(advAdapter);
                    } else {
                        if (resultCode.equals("00")) {

                            if (!result.equals("null")) {

                                advAdapter.notifyDataSetChanged();
                                if (listView.getFooterViewsCount() != 0) {
//                                        listView.removeFooterView(v);
                                }

                                loadfinish = true;
                            } else {
                                havaNext = false;

                                v2 = getLayoutInflater(null).inflate(R.layout.listview2_foot_end, null);
                                listView.removeFooterView(v);
                                noMess.setVisibility(View.GONE);
                                listView.addFooterView(v2);
                            }
                        }
                    }
                }
                lock = true;
                Log.d("json---adv-------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loadfinish = true;

                Log.e("TAG----------------------------", error.getMessage(), error);
                lock = true;
                loading.setText("加载失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("step", "1");
                map.put("uid", User.getInstance().getUid());
                map.put("page", String.valueOf(cpageNum));

                return map;
            }
        };


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, final int totalItemCount) {


                //判断用户是否滑动到最后一项，因为索引值从零开始所以要加上1
                if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == listView.getHeight()) {

                        if (havaNext && loadfinish) {

                            loadfinish = false;


                            if (totalItemCount > 0) {
                                //判断当前页是否超过最大页，以及上一页的数据是否加载完成


//                                //添加页脚视图
//                                listView.addFooterView(v);

                                cpageNum++;

                                mQueue.add(stringRequest);


                            }
                        }

                    }

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //这里我使用了一个点击ListView的监听，这儿一般是点击后就会跳转并显示该条目的详情，在这片文章中我还完善。

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //得到listview最后一项的id
                int lastItemId = listView.getLastVisiblePosition();


                if (position != lastItemId || list.size() <= 9) {

                    Intent intent = new Intent(getContext(), LoanListActivity.class);

                    //用Bundle携带数据
                    Bundle bundle = new Bundle();
                    bundle.putString("form", "adv");
                    bundle.putString("loanAmount", list.get(position).getLoanAmount());
                    bundle.putString("property", list.get(position).getProperty());
                    bundle.putString("age", list.get(position).getAge());

                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            }
        });






















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


        // mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr);
        mPtrFrame.setHeaderView(header);
        // mPtrFrame.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
        mPtrFrame.addPtrUIHandler(header);
        //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
        //mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
        mPtrFrame.setPtrHandler(new PtrHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                System.out.println("MainActivity.onRefreshBegin");
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();


                        list.clear();
                        refresh = true;
                        havaNext = true;
                        cpageNum = 0;
                        mQueue.add(stringRequest);
                        noMess.setVisibility(View.GONE);

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





        h3 = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case 1:

                        list.clear();
                        refresh = true;
                        havaNext = true;
                        cpageNum = 0;
                        mQueue.add(stringRequest);
                        noMess.setVisibility(View.GONE);

                        break;
                }
            }
        };









        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public class AdvAdapter extends BaseAdapter {
        private List<AdvHistory> list = null;

        private Context context = null;


        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }

        }

        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return 2;
        }


        public AdvAdapter(Context context, List<AdvHistory> list) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            int count = 0;
            if (list != null) {
                count = list.size();
            }
            return count;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            ViewHolder holder = null;

            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.adv_item, null);


                holder = new ViewHolder();

                holder.realname = (TextView) convertView.findViewById(R.id.realname);
                holder.property = (TextView) convertView.findViewById(R.id.property);
                holder.mobNum = (TextView) convertView.findViewById(R.id.mobNum);
                holder.loanAmount = (TextView) convertView.findViewById(R.id.loanAmount);
                holder.createTime = (TextView) convertView.findViewById(R.id.createTime);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            String realname = list.get(position).getRealname();
            String property = list.get(position).getProperty();
            String mobNum = list.get(position).getMobNum();
            String loanAmount = list.get(position).getLoanAmount();
            String createTime = list.get(position).getCreateTime();


            holder.realname.setText(realname);
            holder.property.setText("房产值：" + property);
            holder.mobNum.setText("手机:" + mobNum);
            holder.loanAmount.setText("¥" + loanAmount + "万元");
            holder.createTime.setText(createTime);


            return convertView;
        }

        class ViewHolder {
            TextView realname, property, mobNum, createTime, loanAmount;

        }

    }

    @OnClick(R.id.loading)
    public void onViewClicked() {

        if (lock) {
            lock = false;
            mQueue.add(stringRequest);
            loading.setText("加载中...");
        }
    }

    public void run() {
        mQueue.add(stringRequest);
        loading.setText("加载中...");

    }
}
