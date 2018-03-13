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
import com.example.wang.qke.classes.ToolHouse;
import com.example.wang.qke.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseRightFragment extends BaseFragment {


    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.no_mess)
    LinearLayout noMess;

    private List<ToolHouse> list = new ArrayList<>();


    private RequestQueue mQueue;

    private MyAdapter myAdapter;

    public HouseRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_house_right, container, false);
        ButterKnife.bind(this, view);

        RequestQueue mQueue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/inquery_db", new Response.Listener<String>() {
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

                        }else {
                            JSONArray array = object.getJSONArray("result");
                            int len = array.length();

                                for (int i = 0; i < len; i++) {
                                    object = array.getJSONObject(i);

                                    String name = object.getString("name");
                                    String taxTotal = object.getString("taxTotal");
                                    String loanKeep = object.getString("loanKeep");
                                    String averagePrice = object.getString("averagePrice");
                                    String amount = object.getString("totalPrice");
                                    String buildingArea = object.getString("buildArea");
                                    String time = object.getString("createTime");
                                    String transactionPrice = object.getString("transactionPrice");
                                    String houseYear = object.getString("houseYear");
                                    String registerPrice = object.getString("registerPrice");
                                    String owner = object.getString("owner");

                                    ToolHouse toolHouse = new ToolHouse();

                                    toolHouse.setName(name);
                                    toolHouse.setTaxTotal(taxTotal);
                                    toolHouse.setLoanKeep(loanKeep);
                                    toolHouse.setAveragePrice(averagePrice);
                                    toolHouse.setAmount(amount);
                                    toolHouse.setBuildingArea(buildingArea);
                                    toolHouse.setTime(time);
                                    toolHouse.setTransactionPrice(transactionPrice);
                                    toolHouse.setHouseYear(houseYear);
                                    toolHouse.setRegisterPrice(registerPrice);
                                    toolHouse.setOwner(owner);

                                    list.add(toolHouse);


                                }

                        }
                    }else {toast(msg);}
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                    myAdapter = new MyAdapter(getContext(), list);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(getActivity(), HouseDetailsActivity.class);

                            //用Bundle携带数据
                            Bundle bundle = new Bundle();

                            bundle.putString("name", list.get(i).getName());

                            bundle.putString("buildingArea", list.get(i).getBuildingArea());

                            bundle.putString("averagePrice", list.get(i).getAveragePrice());
                            bundle.putString("amount", list.get(i).getAmount());
                            bundle.putString("time", list.get(i).getTime());

                            if (list.get(i).getRegisterPrice() != null) {
                                if (!list.get(i).getRegisterPrice().equals("null")) {

                                    bundle.putString("taxTotal", list.get(i).getTaxTotal());
                                    bundle.putString("loanKeep", list.get(i).getLoanKeep());

                                    bundle.putString("houseYear", list.get(i).getHouseYear());
                                    bundle.putString("registerPrice", list.get(i).getRegisterPrice());
                                    bundle.putString("owner", list.get(i).getOwner());

                                    if (!list.get(i).getTransactionPrice().equals("null") && !list.get(i).getTransactionPrice().equals(""))
                                        bundle.putString("transactionPrice", list.get(i).getTransactionPrice());
                                }
                            }

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
                    toast("询价记录加载失败");
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

    public class MyAdapter extends BaseAdapter {
        private List<ToolHouse> list = null;

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


        public MyAdapter(Context context, List<ToolHouse> list) {
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
                convertView = mInflater.inflate(R.layout.tools_house_item, null);

                ViewHolder holder = new ViewHolder();

                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.amount = (TextView) convertView.findViewById(R.id.amount);
                holder.time = (TextView) convertView.findViewById(R.id.time);

                convertView.setTag(holder);

            }
            final ViewHolder holder2 = (ViewHolder) convertView.getTag();


            String title = list.get(position).getName();
            String amount = list.get(position).getAmount();
            String time = list.get(position).getTime();


            holder2.name.setText(title);
            holder2.amount.setText(amount);
            holder2.time.setText(time);


            return convertView;
        }

        class ViewHolder {
            TextView name, amount, time;

        }

    }


    public void updata(ToolHouse toolHouse) {
        list.add(0,toolHouse);
        myAdapter.notifyDataSetChanged();
        noMess.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
