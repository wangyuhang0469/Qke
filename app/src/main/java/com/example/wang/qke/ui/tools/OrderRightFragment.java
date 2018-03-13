package com.example.wang.qke.ui.tools;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.example.wang.qke.classes.Order;

import com.example.wang.qke.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderRightFragment extends BaseFragment {


    @Bind(R.id.no_mess)
    LinearLayout noMess;
    @Bind(R.id.listView)
    ListView listView;

    private Date now;

    private List<Order> list = new ArrayList<>();
    private OrderAdapter orderAdapter;


    public OrderRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_right, container, false);
        ButterKnife.bind(this, view);

        RequestQueue mQueue = Volley.newRequestQueue(getContext());

        now = new Date(System.currentTimeMillis());

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/appointment_db", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (listView != null) {

                    JSONObject object = null;
                    try {


                        object = new JSONObject(response);
                        String resultCode = object.getString("resultCode");
                        String msg = object.getString("msg");
                        String result = object.getString("result");
                        if (resultCode.equals("00")) {
                            if (result.equals("null")) {
                                noMess.setVisibility(View.VISIBLE);
                            } else {
                                JSONArray array = object.getJSONArray("result");
                                int len = array.length();
                                for (int i = 0; i < len; i++) {
                                    object = array.getJSONObject(i);

                                    Order order = new Order();

                                    order.setUid(object.getString("id"));
                                    order.setUid(object.getString("uid"));
                                    order.setRealname(object.getString("realname"));
                                    order.setBookCode(object.getString("bookCode"));
                                    order.setIdNum(object.getString("idNum"));
                                    order.setMobNum(object.getString("mobNum"));
                                    order.setEvent(object.getString("event"));
                                    order.setBookTime(object.getString("bookTime"));
                                    order.setRegistrationAreaName(object.getString("registrationAreaName"));
                                    order.setState(object.getString("state"));
                                    order.setCreateTime(object.getString("createTime"));
                                    order.setFzFileNo(object.getString("fzFileNo"));
                                    order.setProveType(object.getString("proveType"));
                                    order.setProveCode(object.getString("proveCode"));
                                    order.setHouseName(object.getString("houseName"));


                                    list.add(order);

                                }
                            }
                        } else {
                            toast(msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    orderAdapter = new OrderAdapter(getContext(), list);
                    listView.setAdapter(orderAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);

                            //用Bundle携带数据
                            Bundle bundle = new Bundle();

                            bundle.putSerializable("order", list.get(i));

                            intent.putExtras(bundle);
                            startActivity(intent);


                        }
                    });
                }
                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (noMess != null) {
                    toast("取号记录加载失败");
                    Log.e("TAG----------------------------", error.getMessage(), error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", User.getInstance().getUid());

                return map;
            }
        };


        mQueue.add(stringRequest1);




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }




    public class OrderAdapter extends BaseAdapter {
        private List<Order> list = null;

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


        public OrderAdapter(Context context, List<Order> list) {
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

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.order_item, null);

                ViewHolder holder = new ViewHolder();

                holder.registrationAreaName = (TextView) convertView.findViewById(R.id.registrationAreaName);
                holder.event = (TextView) convertView.findViewById(R.id.event);
                holder.bookTime = (TextView) convertView.findViewById(R.id.bookTime);
                holder.bookCode = (TextView) convertView.findViewById(R.id.bookCode);
                holder.state = (TextView) convertView.findViewById(R.id.state);

                convertView.setTag(holder);

            }
            final ViewHolder holder2 = (ViewHolder) convertView.getTag();



            String registrationAreaName = list.get(position).getRegistrationAreaName();
            String event = list.get(position).getEvent();
            String bookTime = list.get(position).getBookTime();
            String bookTime2 = bookTime.substring(0,bookTime.length()-6);
            String bookCode = list.get(position).getBookCode();
            bookCode = bookCode.substring(bookCode.length()-6);
            String state = list.get(position).getState();



            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = null;
            try {
                date = format.parse(bookTime2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("...............", format.format(date));
            int a = date.compareTo(now);
            if (a < 0) {
                state = "已过期";
            }




            holder2.registrationAreaName.setText("登记点："+registrationAreaName);
            holder2.event.setText("业务类型："+event);
            holder2.bookTime.setText("预约时间："+bookTime);
            holder2.bookCode.setText("流水号后六位："+bookCode);
            holder2.state.setText(state);
            if (state.equals("已取消") || state.equals("已过期")|| state.equals("已受理")) {
                holder2.state.setBackground(getResources().getDrawable(R.drawable.btn_my_hui));
            }else {
                holder2.state.setBackground(getResources().getDrawable(R.drawable.btn_my));
            }


            return convertView;
        }

        class ViewHolder {
            TextView registrationAreaName, event, bookTime, bookCode,state;

        }

    }

    public void updata(Order order){
        list.add(0,order);
        orderAdapter.notifyDataSetChanged();
    }



}
