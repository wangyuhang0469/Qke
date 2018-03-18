package com.example.wang.qke.ui.tools;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.Order;
import com.example.wang.qke.classes.RegistrationArea;
import com.example.wang.qke.classes.SyLinearLayoutManager;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.classes.WorkDate;
import com.example.wang.qke.classes.WorkTime;
import com.example.wang.qke.ui.main.LoadingDalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderLeftFragment extends BaseFragment  {




    @Bind(R.id.certCode_img)
    ImageView certCodeImg;
    @Bind(R.id.bussName)
    TextView tv_bussName;
    @Bind(R.id.spinner3)
    Spinner spinner3;
    @Bind(R.id.szAreaName)
    TextView szAreaName;
    @Bind(R.id.proveTypeName)
    TextView proveTypeName;
    @Bind(R.id.registrationAreaName)
    TextView registrationAreaName;
    @Bind(R.id.ll_003)
    RelativeLayout ll003;
    @Bind(R.id.houseName)
    EditText houseName;
    @Bind(R.id.fzFileNo)
    EditText fzFileNo;
    @Bind(R.id.personName)
    EditText personName;
    @Bind(R.id.phoneNumber)
    EditText phoneNumber;
    @Bind(R.id.certificateNo)
    EditText certificateNo;
    @Bind(R.id.verificationcodereg)
    EditText verificationcodereg;
    @Bind(R.id.proveCode)
    EditText proveCode;
    @Bind(R.id.dateRecyclerView)
    RecyclerView dateRecyclerView;
    @Bind(R.id.timeRecyclerView)
    RecyclerView timeRecyclerView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private RequestQueue mQueue;
    private StringRequest workDateRequest;
    private StringRequest workTimeRequest;
    private StringRequest workAmountRequest;
    private StringRequest verifCodeRequest;
    private StringRequest commitRequest;

    private List<WorkTime> timeList = new ArrayList<>();
    private List<WorkDate> dateList = new ArrayList<>();
    private List<String> numList = new ArrayList<>();
    private String time;
    private String date;
    private String num;
    private MyAdapter myAdapter;
    private DateAdapter dateAdapter;
    private TimeAdapter timeAdapter;
    private int lastPosition = -1;
    private int load = 0;


    private List<String> option = new ArrayList<String>();
    private List<RegistrationArea> registrationAreas = new ArrayList<>();
    private String bookingDateLabel;
    private String bookingDateStr;
    private String workTimeSoltOid;
    private String workTimeSoltName;

    private String responseCookie;
    private String bookingSzAreaOid = "";
    private String proveType = "";
    private String registrationAreaOid = "1";
    private String bookingType = "-1060";
    private String bookingTypeName = "领证业务类";
    private String bussName = "领取不动产权证书及登记证明";
    private String szItemNo = "FZ20150730";
    private String XLType = "2";

    private boolean lock = true;
    private LoadingDalog loadingDalog;




    //=======================================================================主线程===============================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_left, container, false);
        ButterKnife.bind(this, view);


        initData();

        initView();

        initMethod();

        loadTable();
        mQueue.add(verifCodeRequest);


        return view;
    }




    private void  initData(){
        registrationAreas.add(new RegistrationArea("1", "福田登记所"));
        registrationAreas.add(new RegistrationArea("-1000", "南山登记所"));
        registrationAreas.add(new RegistrationArea("-1001", "罗湖盐田登记所"));
        registrationAreas.add( new RegistrationArea("4", "宝安登记所"));
        registrationAreas.add( new RegistrationArea("3", "龙华登记所"));
        registrationAreas.add(new RegistrationArea("2", "龙岗登记所"));
        registrationAreas.add( new RegistrationArea("5", "布吉登记所"));
        registrationAreas.add(new RegistrationArea("6", "光明登记所"));
        registrationAreas.add(new RegistrationArea("7", "坪山登记所"));

        option.add("请选择");
        for (int i = 0; i < registrationAreas.size(); i++) {
            option.add(registrationAreas.get(i).getRegistrationAreaName());
        }
    }

    private void initView(){

        mQueue = Volley.newRequestQueue(getActivity());

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        LinearLayoutManager layoutmanager = new SyLinearLayoutManager(getContext());
        timeRecyclerView.setLayoutManager(layoutmanager);

        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(getContext());
        layoutmanager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        dateRecyclerView.setLayoutManager(layoutmanager2);


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option);
        adapter3.setDropDownViewResource(R.layout.spinner_item2);
        spinner3.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();


                if (position == 0) {

                } else {
                    registrationAreaOid = registrationAreas.get(position - 1).getRegistrationAreaOid();
                    registrationAreaName.setText(adapter.getItem(position));
                    loadTable();
                }
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void  initMethod(){

        verifCodeRequest = new StringRequest("http://123.207.61.165/outside/createBook",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ImageRequest imageRequest = null;
                        try {
//
                            JSONObject object = new JSONObject(response);
                            if (object.getString("resultCode").equals("00")) {

                                object = object.getJSONObject("result");
                                String img = object.getString("img");
                                responseCookie = object.getString("responseCookie");

                                imageRequest = new ImageRequest(
                                        "http://123.207.61.165/uploads/verifyCode/createBook/" + img,
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                if (certCodeImg != null) {
                                                    certCodeImg.setBackgroundDrawable(new BitmapDrawable(response));

                                                }
                                            }
                                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.e("adv未接收", error.getMessage(), error);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (mQueue != null) {
                            mQueue.add(imageRequest);
                        }
                        Log.d("loopers", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });








        commitRequest = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/appointmentTransact", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( spinner3!= null) {
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response);
                        String resultCode = object.getString("resultCode");
                        String msg = object.getString("msg");

                        toast(msg);

                        if (resultCode.equals("00")) {
                            object = object.getJSONObject("result");

                            Order order = new Order();

                            order.setUid(object.getString("uid"));
                            order.setRealname(object.getString("realname"));
                            order.setIdNum(object.getString("idNum"));
                            order.setMobNum(object.getString("mobNum"));
                            order.setEvent(object.getString("event"));
                            order.setBookTime(object.getString("bookTime"));
                            order.setRegistrationAreaName(object.getString("registrationAreaName"));
                            if (!object.isNull("fzFileNo")) {
                                order.setFzFileNo(object.getString("fzFileNo"));
                            } else {
                                order.setProveType(object.getString("proveType"));
                                order.setProveCode(object.getString("proveCode"));
                                order.setHouseName(object.getString("houseName"));
                            }
                            order.setBookCode(object.getString("bookCode"));
                            order.setState("已预约");

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", order);

                            OrderActivity orderActivity = (OrderActivity) getActivity();
                            orderActivity.orderRightFragment.updata(order);


                            startActivity(OrderDetailsActivity.class, bundle);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mQueue.add(verifCodeRequest);
                    lock = true;

                    Log.d("预约结果-------", response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG----------------------------", error.getMessage(), error);
                if (phoneNumber != null) {
                    mQueue.add(verifCodeRequest);
                    toast("预约失败");
                    lock = true;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());
                map.put("registrationAreaOid", registrationAreaOid);
                map.put("registrationAreaName", registrationAreaName.getText().toString());
                map.put("bookingType", bookingType);
                map.put("bookingTypeName", bookingTypeName);
                map.put("bussName", bussName);
                map.put("szItemNo", szItemNo);
                map.put("bookingDateLabel", bookingDateLabel);
                map.put("bookingDateStr", bookingDateStr);
                map.put("workTimeSoltOid", workTimeSoltOid);
                map.put("workTimeSoltName", workTimeSoltName);
                map.put("fzFileNo", fzFileNo.getText().toString());
                map.put("personName", personName.getText().toString());
                map.put("certificateNo", certificateNo.getText().toString());
                map.put("phoneNumber", phoneNumber.getText().toString());
                map.put("proveType", proveType);
                map.put("proveTypeName", proveTypeName.getText().toString());
                map.put("XLType", XLType);
                map.put("proveCode", proveCode.getText().toString());
                map.put("houseName", houseName.getText().toString());
                map.put("bookingSzAreaOid", bookingSzAreaOid);
                map.put("szAreaName", szAreaName.getText().toString());
                map.put("certCode", verificationcodereg.getText().toString());
                map.put("responseCookie", responseCookie);

//                String aa = "  uid  = " + User.getInstance().getUid() + " registrationAreaOid   = " + registrationAreaOid +
//                        "  registrationAreaName  = " + registrationAreaName.getText().toString() + "   bookingType = " + bookingType +
//                        "  bookingTypeName  = " + bookingTypeName + "  bussName  = " + bussName +
//                        "  szItemNo  = " + szItemNo + "  bookingDateLabel  = " + bookingDateLabel +
//                        "  bookingDateStr  = " + bookingDateStr + " workTimeSoltOid   = " + workTimeSoltOid +
//                        " workTimeSoltName   = " + workTimeSoltName + "  fzFileNo  = " + fzFileNo.getText().toString() +
//                        "  personName  = " + personName.getText().toString() + "  certificateNo  = " + certificateNo.getText().toString() +
//                        "  phoneNumber  = " + phoneNumber.getText().toString() + " proveType   = " + proveType +
//                        "   proveTypeName = " + proveTypeName.getText().toString() + "  XLType  = " + XLType +
//                        "  proveCode  = " + proveCode.getText().toString() + "  houseName  = " + houseName.getText().toString() +
//                        "  bookingSzAreaOid  = " + bookingSzAreaOid + "  szAreaName  = " + szAreaName.getText().toString() +
//
//                        "certCode:  " + verificationcodereg.getText().toString() + "   responseCookie:  " + responseCookie;
//
//
//                Log.e("发送包==========", aa);


                return map;


            }
        };

    }



    @OnClick(R.id.btn)
    public void onViewClicked() {


        if (registrationAreaName.getText().length() != 0 && personName.getText().length() != 0 &&
                certificateNo.getText().length() != 0 && phoneNumber.getText().length() != 0 &&
                verificationcodereg.getText().length() != 0 && registrationAreaName.getText().length() != 0) {
            if ((proveTypeName.getText().length() != 0 &&
                    proveCode.getText().length() != 0 && houseName.getText().length() != 0 && szAreaName.getText().length() != 0) || fzFileNo.getText().length() != 0) {
                if (workTimeSoltName != null) {

                    if (phoneNumber.getText().length() == 11 && (certificateNo.getText().length() == 18 || certificateNo.getText().length() == 15)) {
                        if (lock) {
                            lock = false;
                            toast("请稍候");
                            mQueue.add(commitRequest);
                        }
                    } else toast("身份证或手机号不正确");
                } else toast("请选择预约时间");
            } else toast("请填写完整信息");
        } else toast("请填写完整信息");
    }

    @OnClick(R.id.certCode_img)
    public void onView2Clicked() {
        mQueue.add(verifCodeRequest);
    }


    @OnClick(R.id.refresh)
    public void onView3Clicked() {
            loadTable();
    }

    public void loadTable() {

        loadingDalog = new LoadingDalog(getActivity());
        loadingDalog.show();

        workDateRequest = new StringRequest("http://123.207.61.165/outside/getWorkDate?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (phoneNumber != null) {
                            Log.e("======= 123456 =====", "http://123.207.61.165/outside/getWorkDate?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid);


                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("resultCode").equals("00")) {

                                    String jsonStr = object.getString("result");
                                    jsonStr = jsonStr.replace("{", "{\"");
                                    jsonStr = jsonStr.replace(":", "\":\"");
                                    jsonStr = jsonStr.replace(",", "\",\"");
                                    jsonStr = jsonStr.replace("}", "\"}");
                                    jsonStr = jsonStr.replace("}\",\"{", "},{");
                                    jsonStr = "[" + jsonStr + "]";
                                    Log.d("======= 1.1 =====", jsonStr);

                                    date = jsonStr;

                                    mQueue.add(workTimeRequest);

                                } else {
                                    toast("网络异常");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.d("======= 工作日期 未解析 =====", response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (phoneNumber != null) {
                    loadingDalog.dismiss();
                    toast("预约时间加载失败");
                }
            }
        });


        workTimeRequest = new StringRequest("http://123.207.61.165/outside/getWorkTime?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid + "&bookingType=" + bookingType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (phoneNumber != null) {

                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("resultCode").equals("00")) {

                                    String jsonStr = object.getString("result");
                                    jsonStr = jsonStr.replace("{", "{\"");
                                    jsonStr = jsonStr.replace(":", "\":\"");
                                    jsonStr = jsonStr.replace(",", "\",\"");
                                    jsonStr = jsonStr.replace("}", "\"}");
                                    jsonStr = jsonStr.replace("}\",\"{", "},{");
                                    jsonStr = jsonStr.replace("\":\"00", ":00");

                                    jsonStr = jsonStr.replace("\"{\"nanos", "\"{\",\"nanos");
                                    jsonStr = jsonStr.replace("}\"", "");
                                    jsonStr = "[" + jsonStr + "]";
                                    Log.d("======= 工作时间 （已解析） =====", jsonStr);

                                    time = jsonStr;


                                    try {
                                        JSONArray timeArray = new JSONArray(time);
                                        timeList.clear();
                                        for (int i = 0 ; i < timeArray.length() ; i++) {
                                            WorkTime workTime = new WorkTime();
                                            workTime.setTv_startTime(timeArray.getJSONObject(i).getString("startTime"));
                                            workTime.setTv_endTime(timeArray.getJSONObject(i).getString("endTime"));
                                            workTime.setUp_workTimeSoltOid(timeArray.getJSONObject(i).getString("workTimeSoltOid"));
                                            timeList.add(workTime);
                                        }

                                        JSONArray dateArray = new JSONArray(date);
                                        dateList.clear();
                                        for (int j = 0 ; j < dateArray.length() ; j++) {
                                            WorkDate workDate = new WorkDate();
                                            workDate.setTv_weekName(dateArray.getJSONObject(j).getString("weekName"));
                                            workDate.setTv_workDay(dateArray.getJSONObject(j).getString("workDay"));
                                            dateList.add(workDate);
                                        }

                                        JSONObject numJson = new JSONObject(num);
                                        numList.clear();
                                        for (int k = 0 ; k<timeList.size() ; k++){

                                            for (int l = 0 ; l<dateList.size() ; l++){

                                                String a = dateList.get(l).getUp_bookingDateStr() + "_" + timeList.get(k).getUp_workTimeSoltOid();
                                                if (numJson.has(a)){
                                                    String b = numJson.getString(a);
                                                    b = numJson.getString(b);
                                                    numList.add(b);
                                                }else {
                                                    numList.add("未放号");
                                                }

                                            }

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    load++;
                                    if (load == 1){
                                        myAdapter = new MyAdapter(numList);
                                        recyclerView.setAdapter(myAdapter);

                                        dateAdapter = new DateAdapter(dateList);
                                        dateRecyclerView.setAdapter(dateAdapter);

                                        timeAdapter = new TimeAdapter(timeList);
                                        timeRecyclerView.setAdapter(timeAdapter);
                                    }else {
                                        myAdapter.notifyDataSetChanged();
                                        dateAdapter.notifyDataSetChanged();
                                        timeAdapter.notifyDataSetChanged();
                                    }



                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            loadingDalog.dismiss();
                            Log.d("======= 工作时间 未解析 =====", response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (phoneNumber != null) {

                    toast("预约时间加载失败");
                    loadingDalog.dismiss();
                }
            }
        });


        workAmountRequest = new StringRequest("http://123.207.61.165/outside/getWorkAmount?callCount=1&szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid + "&bookingType=" + bookingType,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (phoneNumber != null) {

                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                if (object.getString("resultCode").equals("00")) {

                                    String jsonStr = object.getString("result");

                                    jsonStr = jsonStr.replace("var s0={};", "");
                                    jsonStr = jsonStr.replace("var ", "");
                                    jsonStr = jsonStr.replace(";", ",");
                                    jsonStr = jsonStr.replace("s0['", "");
                                    jsonStr = jsonStr.replace("']", "");
                                    jsonStr = jsonStr.replace("=", ":");
                                    jsonStr = jsonStr.replace("\\", "");
                                    jsonStr = jsonStr.replace("{", "{\"");
                                    jsonStr = jsonStr.replace(":", "\":\"");
                                    jsonStr = jsonStr.replace(",", "\",\"");
                                    jsonStr = jsonStr.replace("}", "\"}");
                                    jsonStr = jsonStr.replace("}\",\"{", "},{");
                                    jsonStr = jsonStr.replace("\n", "ss");
                                    jsonStr = jsonStr.replace("\"\"", "\"");
                                    jsonStr = "{\"" + jsonStr + "\"}";
                                    jsonStr = jsonStr.replace("\",\" s0)\",\"ss\"", " s0)\":\"ss\"");
                                    Log.d("======= s0—s25 （已解析） =====", jsonStr);

                                    num = jsonStr;

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mQueue.add(workDateRequest);

                            Log.d("======= ！！！！参数！！！！ =====", "szItemNo=" + szItemNo + "&registrationAreaOid=" + registrationAreaOid + "&bookingType=" + bookingType);
                            Log.d("======= s0—s25 未解析 =====", response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (phoneNumber != null) {
                    toast("预约时间加载失败");
                    loadingDalog.dismiss();
                }
            }
        });

        mQueue.add(workAmountRequest);


    }

    public void select(int hang, int lie) {

        workTimeSoltName = timeList.get(hang).getUp_workTimeSoltName();
        workTimeSoltOid =timeList.get(hang).getUp_workTimeSoltOid();

        bookingDateLabel = dateList.get(lie).getUp_workDayLabel();
        bookingDateStr = dateList.get(lie).getUp_bookingDateStr();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        private List<String> list;
        private Date now;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
                now = new Date(System.currentTimeMillis());
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            String text = list.get(position);


            String dateStr = dateList.get(0).getUp_bookingDateStr() + " " + timeList.get(position/5).getTv_startTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = null;
            try {
                date = format.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int a = date.compareTo(now);
            if (a < 0 && position%5 == 0) {
                holder.tv.setText("");
                holder.tv.setHint("已结束");
            } else if (text.equals("u672Au653Eu53F7")){
                holder.tv.setText("");
                holder.tv.setHint("未放号");
            }else {
                holder.tv.setText(list.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        lastPosition = position;
                        notifyDataSetChanged();
                        int i = position / 5 ;
                        int j = position % 5;
                        select(i,j);
                    }
                });
            }

            if(position == lastPosition){
                holder.tv.setSelected(true);
            }else{
                holder.tv.setSelected(false);
            }
        }


        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


    }

    public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {


        private List<WorkDate> list;

        public DateAdapter(List<WorkDate> list) {
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_date,weekday;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_date = (TextView) itemView.findViewById(R.id.tv_data);
                weekday = (TextView) itemView.findViewById(R.id.weekday);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tv_date.setText(list.get(position).getTv_weekName());
            holder.weekday.setText(list.get(position).getTv_workDay());
        }


        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


    }

    public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {


        private List<WorkTime> list;


        public TimeAdapter(List<WorkTime> list) {
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView startTime,endTime;

            public ViewHolder(View itemView) {
                super(itemView);
                startTime = (TextView) itemView.findViewById(R.id.startTime);
                endTime = (TextView) itemView.findViewById(R.id.endTime);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list3, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.startTime.setText(list.get(position).getTv_startTime());
            holder.endTime.setText(list.get(position).getTv_endTime());
        }


        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
