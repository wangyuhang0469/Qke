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
import com.example.wang.qke.classes.Record;
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
public class RecordRightFragment extends BaseFragment {


    @Bind(R.id.no_mess)
    LinearLayout noMess;
    @Bind(R.id.listView)
    ListView listView;

    private List<Record> list = new ArrayList<>();
    private RecordAdapter recordAdapter;

    public RecordRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_right, container, false);
        ButterKnife.bind(this, view);


        RequestQueue mQueue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/record_db", new Response.Listener<String>() {
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
                            if (result.equals("null")){
                                noMess.setVisibility(View.VISIBLE);
                            }else {
                                JSONArray array = object.getJSONArray("result");
                                int len = array.length();
                                for (int i = 0; i < len; i++) {
                                    object = array.getJSONObject(i);

                                    Record record = new Record();

                                    record.setUid(object.getString("uid"));
                                    record.setId(object.getString("id"));
                                    record.setInqueryWay(object.getString("inqueryWay"));
                                    record.setCertType(object.getString("certType"));
                                    record.setCertNo(object.getString("certNo"));
                                    record.setCertYear(object.getString("certYear"));
                                    record.setIdNum(object.getString("idNum"));
                                    record.setEpName(object.getString("epName"));
                                    record.setCreateTime(object.getString("createTime"));

                                    list.add(record);

                                }
                            }
                        } else {
                            toast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    recordAdapter = new RecordAdapter(getContext(), list);
                    listView.setAdapter(recordAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(getActivity(), RecordDetailsActivity.class);

                            //用Bundle携带数据
                            Bundle bundle = new Bundle();

                            bundle.putSerializable("record", list.get(i));

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
                    toast("查档加载失败");
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


    public class RecordAdapter extends BaseAdapter {
        private List<Record> list = null;

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


        public RecordAdapter(Context context, List<Record> list) {
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
                convertView = mInflater.inflate(R.layout.record_left_item, null);

                ViewHolder holder = new ViewHolder();

                holder.idNum = (TextView) convertView.findViewById(R.id.idNum);
                holder.certNo = (TextView) convertView.findViewById(R.id.certNo);
                holder.createTime = (TextView) convertView.findViewById(R.id.createTime);

                convertView.setTag(holder);

            }
            final ViewHolder holder2 = (ViewHolder) convertView.getTag();
//
//
            String epName = list.get(position).getEpName();
            String idNum = list.get(position).getIdNum();
            String certNo = list.get(position).getCertNo();
            String createTime = list.get(position).getCreateTime();

//
            if (epName.equals("")) {
                holder2.idNum.setText("权利人身份证：" + idNum);
            }else {
                holder2.idNum.setText("权利人：" + epName);

            }
            holder2.certNo.setText("房产证号：" + certNo);
            holder2.createTime.setText("时间：" + createTime);


            return convertView;
        }

        class ViewHolder {
            TextView idNum, certNo, createTime;

        }

    }



    public void updateWR(Record record) {
        list.add(0,record);
        recordAdapter.notifyDataSetChanged();
        noMess.setVisibility(View.GONE);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
