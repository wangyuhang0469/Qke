package com.example.wang.qke.ui.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.classes.Community;
import com.example.wang.qke.classes.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ChooseHouseDalog extends Dialog implements MyListener{



    protected Context mContext;
    public ImageView icon;

    private View customView;

    private MyListener mMyListener;
    private String keyword;

    private ListView listView;
    private TextView title;
    private List<Community> communityList = new ArrayList<>();
    private ChooseAdapter chooseAdapter;


    private String construction_id="";
    private String building_id="";
    private String HouseId="";
    private String construction_name;
    private String building_name;
    private String House_name;

    private boolean lock = true;


    public ChooseHouseDalog(Context context, int style,String keyword,MyListener mMyListener) {
        super(context, R.style.FullScreenDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.activity_choose_house_dalog, null);
        this.keyword=keyword;
        this.mMyListener = mMyListener;

        icon =(ImageView) customView.findViewById(R.id.back);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //=====================================主线程============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);

        final User user = User.getInstance();
        listView =(ListView)findViewById(R.id.listView);
        title =(TextView) findViewById(R.id.title);


        final RequestQueue mQueue = Volley.newRequestQueue(mContext);


        //-------------第三轮post------------------------------------------------------------------------


        final StringRequest stringRequest3 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/inquery", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                title.setText("请选择房号");

                JSONObject object = null;
                communityList.clear();
                try {


                    object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result");
                    int len = array.length();
                    for(int i = 0 ; i <len; i ++){
                        object = array.getJSONObject(i);

                        String id = object.getString("HouseId");
                        String name = object.getString("HouseName");

                        Community c = new Community();
                        c.setCommunityId(id);
                        c.setCommunityName(name);

                        communityList.add(c);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                chooseAdapter.notifyDataSetChanged();
                lock = true;

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (lock) {
                            HouseId = communityList.get(i).getCommunityId();
                            House_name =communityList.get(i).getCommunityName();
                            String result = construction_name+"|"+building_name+"|"+House_name;
                            refreshActivity(result, construction_id, building_id, HouseId);
                            ChooseHouseDalog.this.dismiss();
                        }else {
                            Toast.makeText(mContext, "请勿重复点击", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                lock=true;
                title.setText("加载失败");
                Log.e("TAG----------------------------", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", user.getUid());
                map.put("token", user.getToken());
                map.put("step", "3");
                map.put("building_id", building_id);
                return map;
            }
        };




//-------------第二轮post------------------------------------------------------------------------


        final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/inquery", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                title.setText("请选择楼栋");
                JSONObject object = null;
                communityList.clear();
                try {


                    object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("result");
                    int len = array.length();
                    for(int i = 0 ; i <len; i ++){
                        object = array.getJSONObject(i);

                        String id = object.getString("BuildingId");
                        String name = object.getString("BuildingName");

                        Community c = new Community();
                        c.setCommunityId(id);
                        c.setCommunityName(name);


                        communityList.add(c);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                ChooseAdapter chooseAdapter = new ChooseAdapter(getContext(),communityList);
//                listView.setAdapter(chooseAdapter);

                chooseAdapter.notifyDataSetChanged();
                lock = true;

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (lock) {
                            lock = false;
                            building_id = communityList.get(i).getCommunityId();
                            building_name = communityList.get(i).getCommunityName();
                            title.setText("加载中");
                            mQueue.add(stringRequest3);
                        }else {
                            Toast.makeText(mContext, "请勿重复点击", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                lock=true;
                title.setText("加载失败");

                Log.e("TAG----------------------------", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", user.getUid());
                map.put("token", user.getToken());
                map.put("step", "2");
                map.put("construction_id", construction_id);
                return map;
            }
        };







//-------------第一轮post---------------------------------------------------------------


        final StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://123.207.61.165/outside/inquery", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                JSONObject object = null;
                try {


                    object = new JSONObject(response);
                    String resultCode = object.getString("resultCode");
                    String msg = object.getString("msg");
                    String result = object.getString("result");
                    if (resultCode.equals("00")) {
                        if (!result.equals("-1")) {
                            title.setText("请选择物业");
                            JSONArray array = object.getJSONArray("result");
                            int len = array.length();
                            for (int i = 0; i < len; i++) {
                                object = array.getJSONObject(i);

                                String id = object.getString("ConstructionId");
                                String name = object.getString("ConstructionName");

                                Community c = new Community();
                                c.setCommunityId(id);
                                c.setCommunityName(name);

                                communityList.add(c);

                            }
                        }else {title.setText("未找到数据");}
                    }else { title.setText(msg);}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                chooseAdapter = new ChooseAdapter(getContext(),communityList);
                listView.setAdapter(chooseAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (lock) {
                            lock = false;
                            construction_id = communityList.get(i).getCommunityId();
                            construction_name = communityList.get(i).getCommunityName();
                            title.setText("加载中");
                            mQueue.add(stringRequest2);
                        }else {
                            Toast.makeText(mContext, "请勿重复点击", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                title.setText("加载失败");
                Log.e("TAG----------------------------", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", user.getUid());
                map.put("token", user.getToken());
                map.put("step", "1");
                map.put("keyword", keyword);

                Log.e("dalog", "uid: " + user.getUid() + "token: " + user.getToken() + "step : 1" +"keyword: " + keyword);
                return map;
            }
        };




        mQueue.add(stringRequest1);


//        listView.setOnClickListener();



        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseHouseDalog.this.dismiss();

            }
        });
    }

    //=====================================主线程END============================================


    public class ChooseAdapter extends BaseAdapter {
        private List<Community> list = null;

        private Context context =null;




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


        public ChooseAdapter(Context context, List<Community> list) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            int count = 0 ;
            if(list!=null){
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

            if(convertView==null){
                convertView = mInflater.inflate(R.layout.choose_house_item, null);

                ViewHolder holder = new ViewHolder();

                holder.title = (TextView) convertView.findViewById(R.id.tv);

                convertView.setTag(holder);

            }
            final ViewHolder holder2 = (ViewHolder) convertView.getTag();


            String title = list.get(position).getCommunityName();


            holder2.title.setText(title);



            return convertView;
        }

        class ViewHolder{
            TextView title;

        }

    }






    @Override
    public void refreshActivity(String text,String id1,String id2,String id3) {
        mMyListener.refreshActivity(text,id1,id2,id3);
    }
}
