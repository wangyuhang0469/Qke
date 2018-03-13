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
import com.example.wang.qke.classes.TransferTallage;
import com.example.wang.qke.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferTallageRightFragment extends BaseFragment {


    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.no_mess)
    LinearLayout noMess;

    private List<TransferTallage> list = new ArrayList<>();
    private TTAdapter ttAdapter;


    public TransferTallageRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_tallage_right, container, false);
        ButterKnife.bind(this, view);


        RequestQueue mQueue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/ransfer_db", new Response.Listener<String>() {
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

                                TransferTallage transferTallage = new TransferTallage();

                                String owner = object.getString("owner");
                                String zslx = object.getString("zslx");

                                transferTallage.setOwner(owner);
                                transferTallage.setZslx(zslx);
                                if (owner.equals("个人")) {
                                    transferTallage.setIdNo(object.getString("idNum"));
                                } else {
                                    transferTallage.setOwnerName(object.getString("ownerName"));
                                }
                                if (!zslx.equals("房地产权证书")) {
                                    transferTallage.setSelectBdc(object.getString("selectBdc"));
                                }
                                transferTallage.setId(object.getString("id"));
                                transferTallage.setCertNo(object.getString("certNum"));
                                transferTallage.setDesc(object.getString("description"));
                                transferTallage.setUnitPrice(object.getString("unitPrice"));
                                double d=object.getDouble("housePrice");
                                String housePrice=new DecimalFormat("0.00").format(d);
                                transferTallage.setHousePrice(housePrice);
                                transferTallage.setBuildArea(object.getString("buildArea"));
                                transferTallage.setCreateTime(object.getString("createTime"));
                                String hasTax = object.getString("hasTax");
                                transferTallage.setHasTax(hasTax);

                                if (hasTax.equals("1")) {
                                    transferTallage.setValueAddedTax(object.getString("valueAddedTax"));
                                    transferTallage.setMaintenanceTax(object.getString("maintenanceTax"));
                                    transferTallage.setEducationSurtax(object.getString("educationSurtax"));
                                    transferTallage.setLocalEducationSurtax(object.getString("localEducationSurtax"));
                                    transferTallage.setStampDuty(object.getString("stampDuty"));
                                    transferTallage.setPersonalTaxFerry(object.getString("personalTaxFerry"));
                                    transferTallage.setPersonalTaxCheck(object.getString("personalTaxCheck"));
                                    transferTallage.setTransactionTax(object.getString("transactionTax"));
                                    transferTallage.setProcedureTax(object.getString("procedureTax"));
                                    transferTallage.setAppliqueExpense(object.getString("appliqueExpense"));
                                    transferTallage.setCheckExpense(object.getString("checkExpense"));
                                }

                                list.add(transferTallage);


                            }
                        }
                    } else {
                        toast(msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    ttAdapter = new TTAdapter(getContext(), list);
                    listView.setAdapter(ttAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(getActivity(), TransferTallageDetailsActivity.class);

                            //用Bundle携带数据
                            Bundle bundle = new Bundle();

                            bundle.putSerializable("transferTallage", list.get(i));

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
                    toast("税费记录加载失败");
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


    public class TTAdapter extends BaseAdapter {
        private List<TransferTallage> list = null;

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


        public TTAdapter(Context context, List<TransferTallage> list) {
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
                convertView = mInflater.inflate(R.layout.transfertallage_item, null);

                ViewHolder holder = new ViewHolder();

                holder.shen_or_dan = (TextView) convertView.findViewById(R.id.shen_or_dan);
                holder.idNo_ownerName = (TextView) convertView.findViewById(R.id.idNo_ownerName);
                holder.certNo = (TextView) convertView.findViewById(R.id.certNo);
                holder.createTime = (TextView) convertView.findViewById(R.id.createTime);

                convertView.setTag(holder);

            }
            final ViewHolder holder2 = (ViewHolder) convertView.getTag();

            String idNo_ownerName = null;
            if (list.get(position).getIdNo() != null) {
                idNo_ownerName = list.get(position).getIdNo();
            } else {
                holder2.shen_or_dan.setText("单位：");
                idNo_ownerName = list.get(position).getOwnerName();
            }
            String certNo = list.get(position).getCertNo();
            String createTime = list.get(position).getCreateTime();


            holder2.idNo_ownerName.setText(idNo_ownerName);
            holder2.certNo.setText(certNo);
            holder2.createTime.setText(createTime);


            return convertView;
        }

        class ViewHolder {
            TextView shen_or_dan, idNo_ownerName, certNo, createTime;

        }

    }


}
