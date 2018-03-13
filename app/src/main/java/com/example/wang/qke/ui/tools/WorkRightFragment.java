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
import com.example.wang.qke.classes.User;
import com.example.wang.qke.classes.Workdoc;

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
public class WorkRightFragment extends BaseFragment {


    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.no_mess)
    LinearLayout noMess;

    private List<Workdoc> list = new ArrayList<>();
    private WorkAdapter workAdapter;

    public WorkRightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_right, container, false);
        ButterKnife.bind(this, view);


        RequestQueue mQueue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/workdoc_db", new Response.Listener<String>() {
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

                                    Workdoc workdoc = new Workdoc();

                                    workdoc.setUid(object.getString("uid"));
                                    workdoc.setDocNum(object.getString("docNum"));
                                    workdoc.setAppNum(object.getString("appNum"));
                                    workdoc.setEvent(object.getString("event"));
                                    workdoc.setAcceptDate(object.getString("acceptDate"));
                                    workdoc.setResponseDate(object.getString("responseDate"));
                                    workdoc.setState(object.getString("state"));
                                    workdoc.setTime(object.getString("createTime"));

                                    list.add(workdoc);

                            }
                        }
                    } else {
                        toast(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                    workAdapter = new WorkAdapter(getContext(), list);
                    listView.setAdapter(workAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(getActivity(), WorkDetailsActivity.class);

                            //用Bundle携带数据
                            Bundle bundle = new Bundle();

                            bundle.putString("uid", list.get(i).getUid());
                            bundle.putString("docNum", list.get(i).getDocNum());
                            bundle.putString("appNum", list.get(i).getAppNum());
                            bundle.putString("event", list.get(i).getEvent());
                            bundle.putString("acceptDate", list.get(i).getAcceptDate());
                            bundle.putString("responseDate", list.get(i).getResponseDate());
                            bundle.putString("state", list.get(i).getState());
                            bundle.putString("time", list.get(i).getTime());


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
                    toast("办公记录加载失败");
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

    public class WorkAdapter extends BaseAdapter {
        private List<Workdoc> list = null;

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


        public WorkAdapter(Context context, List<Workdoc> list) {
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
                convertView = mInflater.inflate(R.layout.work_item, null);

                ViewHolder holder = new ViewHolder();

                holder.docNum = (TextView) convertView.findViewById(R.id.docNum);
                holder.state = (TextView) convertView.findViewById(R.id.state);
                holder.time = (TextView) convertView.findViewById(R.id.time);

                convertView.setTag(holder);

            }
            final ViewHolder holder2 = (ViewHolder) convertView.getTag();


            String docNum = list.get(position).getDocNum();
            String state = list.get(position).getState();
            String time = list.get(position).getTime();


            holder2.docNum.setText("办文编号：" + docNum);
            holder2.state.setText("办理状态：" + state);
            holder2.time.setText("查询时间：" + time);


            return convertView;
        }

        class ViewHolder {
            TextView docNum, state, time;

        }

    }


    public void updateWR(Workdoc workdoc) {
        list.add(0,workdoc);
        workAdapter.notifyDataSetChanged();
        noMess.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
