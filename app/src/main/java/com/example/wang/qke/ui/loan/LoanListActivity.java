package com.example.wang.qke.ui.loan;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.DurationComparator;
import com.example.wang.qke.classes.ImageUtil;
import com.example.wang.qke.classes.LoanAmountComparator;
import com.example.wang.qke.classes.LoanPlan;
import com.example.wang.qke.classes.LoanRateComparator;
import com.example.wang.qke.classes.RepaymentFilter;
import com.example.wang.qke.classes.SpeedComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoanListActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.loading)
    TextView loading;
//    @Bind(R.id.spinner1)
//    Spinner spinner1;
//    @Bind(R.id.spinner2)
//    Spinner spinner2;
//    @Bind(R.id.sort)
//    TextView sort;
//    @Bind(R.id.filtration)
//    TextView filtration;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rl)
    RelativeLayout rl;

    private RequestQueue mQueue;
    private StringRequest stringRequest;
    private List<LoanPlan> list = new ArrayList<>();
    private List<LoanPlan> list2 = new ArrayList<>();
    private List<LoanPlan> Oldlist = new ArrayList<>();
    private boolean listLock = true;


    private int mTouchShop;//最小滑动距离
    protected float mFirstY;//触摸下去的位置
    protected float mCurrentY;//滑动时Y的位置
    protected int direction;//判断是否上滑或者下滑的标志

    protected boolean mShow;//判断是否执行了上滑动画
    private Animator mAnimator;//动画属性


    private LLAdapter llAdapter;
    private String form;

    private String uid;
    private String loanAmount;
    private String step;
    private String age;
    private String overdueLoan;
    private String loanFrequency;
    private String realname;
    private String sex;
    private String mobNum;
    private String idNum;
    private String occupation;
    private String salaryRange;
    private String isSZPerson;
    private String socialSec;
    private String marriage;
    private String spauseJob;
    private String spauseIncome;
    private String property;

    private String path;
    private boolean lock = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_list);
        ButterKnife.bind(this);

        title.setText("推荐方案");

        Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();

        form = bundle.getString("form");
        if (form.equals("adv")) {
            loanAmount = bundle.getString("loanAmount");
            property = bundle.getString("property");
            age = bundle.getString("age");
            path = "http://123.207.61.165/outside/consult_db";
        } else {
            //接收name值
            uid = bundle.getString("uid");
            loanAmount = bundle.getString("loanAmount");
            step = bundle.getString("step");
            age = bundle.getString("age");
            overdueLoan = bundle.getString("overdueLoan");
            loanFrequency = bundle.getString("loanFrequency");
            realname = bundle.getString("realname");
            sex = bundle.getString("sex");
            mobNum = bundle.getString("mobNum");
            idNum = bundle.getString("idNum");
            occupation = bundle.getString("occupation");
            salaryRange = bundle.getString("salaryRange");
            isSZPerson = bundle.getString("isSZPerson");
            socialSec = bundle.getString("socialSec");
            marriage = bundle.getString("marriage");
            spauseJob = bundle.getString("spauseJob");
            spauseIncome = bundle.getString("spauseIncome");
            property = bundle.getString("property");
            path = "http://123.207.61.165/outside/consult";

        }

        mQueue = Volley.newRequestQueue(this);


        stringRequest = new StringRequest(Request.Method.POST, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {


                    object = new JSONObject(response);
                    String resultCode = object.getString("resultCode");
                    String msg = object.getString("msg");
                    if (resultCode.equals("00")) {
                        JSONArray array = object.getJSONArray("result");
                        int len = array.length();
                        for (int i = 0; i < len; i++) {
                            object = array.getJSONObject(i);

                            LoanPlan loanPlan = new LoanPlan();


                            String id = object.getString("id");
                            String company = object.getString("company");
                            String product = object.getString("product");
                            String canLoan = object.getString("canLoan");
                            String loanRate = object.getString("loanRate");
                            String duration = object.getString("duration");
                            String repayment = object.getString("repayment");
                            String type = object.getString("type");
                            String mortgage = object.getString("mortgage");
                            String pic = object.getString("pic");

                            loanPlan.setId(id);
                            loanPlan.setCompany(company);
                            loanPlan.setProduct(product);
                            loanPlan.setCanLoan(canLoan);
                            loanPlan.setLoanRate(loanRate);
                            loanPlan.setDuration(duration);
                            loanPlan.setRepayment(repayment);
                            loanPlan.setType(type);
                            loanPlan.setMortgage(mortgage);
                            loanPlan.setPic(pic);

                            list.add(loanPlan);
                        }
                    } else {
                        toast(msg);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (listView != null) {

                    Oldlist = list;

                    loading.setText("");
                    llAdapter = new LLAdapter(LoanListActivity.this, list);
                    listView.setAdapter(llAdapter);
                }
                Log.d("json-------------", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (loading != null) {
                    Log.e("TAG----------------------------", error.getMessage(), error);
                    lock = true;
                    loading.setText("加载失败,请检查您的网络及数据");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                if (form.equals("adv")) {
                    map.put("step", "2");
                    map.put("loanAmount", loanAmount);
                    map.put("property", property);
                    map.put("age", age);

                    Log.e("SSSSSSSSSSSSS","loanAmount="+loanAmount+"&property"+property);

                } else {
                    map.put("uid", uid);
                    map.put("loanAmount", loanAmount);
                    map.put("step", step);
                    map.put("age", age);
                    map.put("overdueLoan", overdueLoan);
                    map.put("loanFrequency", loanFrequency);
                    map.put("realname", realname);
                    map.put("sex", sex);
                    map.put("mobNum", mobNum);
                    map.put("idNum", idNum);
                    map.put("occupation", occupation);
                    map.put("salaryRange", salaryRange);
                    map.put("isSZPerson", isSZPerson);
                    map.put("socialSec", socialSec);
                    map.put("marriage", marriage);
                    map.put("spauseJob", spauseJob);
                    map.put("spauseIncome", spauseIncome);
                    map.put("property", property);

                    Log.e("============","?uid="+uid+"&loanAmount="+loanAmount+"&step="+step+
                    "&age="+age+"&overdueLoan="+  overdueLoan  +"&loanFrequency="+  loanFrequency  +"&realname="+realname
                            +"&sex="+ sex   +"&mobNum="+ mobNum   +"&idNum="+ idNum   +"&occupation="+occupation
                            +"&salaryRange="+ salaryRange   +"&isSZPerson="+ isSZPerson   +"&socialSec="+socialSec
                            +"&marriage="+  marriage  +"&spauseJob="+  spauseJob  +"&spauseIncome="+  spauseIncome  +"&property="+  property  );
                }
                return map;
            }
        };


        mQueue.add(stringRequest);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent();
                intent.setClass(LoanListActivity.this, LoanPlanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("loanPlan", list.get(i));
                intent.putExtras(bundle);
                startActivity(intent);

//                Bundle bundle1 = new Bundle();
//                bundle.putSerializable("loanPlan",list.get(i));
//                startActivity(LoanPlanActivity.class,bundle,false);

            }
        });


//        listView.setOnTouchListener(new View.OnTouchListener() {//listview的触摸事件
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        mFirstY = event.getY();//按下时获取位置
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        mCurrentY = event.getY();//得到滑动的位置
//                        if (mCurrentY - mFirstY -20> mTouchShop) {//滑动的位置减去按下的位置大于最小滑动距离  则表示向下滑动
//                            direction = 0;//down
//                        } else if (mFirstY - mCurrentY - 70 > mTouchShop) {//反之向上滑动
//                            direction = 1;//up
//                        }else {
//                            direction = 5;//up
//                        }
//
//                        if (direction == 1) {//判断如果是向上滑动 则执行向上滑动的动画
//                            if (mShow) {//判断动画是否执行了  执行了则改变状态
//                                //执行往上滑动的动画
//                                tolbarAnim(1);
//                                mShow = !mShow;
//                            }
//                        } else if (direction == 0) {//判断如果是向下滑动 则执行向下滑动的动画
//                            if (!mShow) {//判断动画是否执行了  执行了则改变状态
//                                //执行往下滑动的动画
//                                tolbarAnim(0);
//                                mShow = !mShow;
//                            }
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//
//                }
//                return false;
//            }
//        });


        //------------------------------------下拉菜单------------------------------------------
//        List<String> option1 = new ArrayList<String>();
//        option1.add("------请选择------");
//        option1.add("年限");
//        option1.add("利率");
//        option1.add("额度");
//        option1.add("审批速度");
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item1, R.id.tv_spinner, option1);
//        adapter.setDropDownViewResource(R.layout.spinner_item3);
//        spinner1.setAdapter(adapter);
//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //获取Spinner控件的适配器
//                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
//                switch (position) {
//                    case 0:
//                        break;
//                    case 1:
//
//                        Comparator comp = new DurationComparator();
//                        Collections.sort(Oldlist, comp);
//                        Collections.sort(list, comp);
//
//                        llAdapter.notifyDataSetChanged();
//
//
//                        sort.setText(adapter.getItem(position));
//                        break;
//                    case 2:
//                        Comparator comp2 = new LoanRateComparator();
//                        Collections.sort(Oldlist, comp2);
//                        Collections.sort(list, comp2);
//                        llAdapter.notifyDataSetChanged();
//
//                        sort.setText(adapter.getItem(position));
//                        break;
//
//                    case 3:
//                        Comparator comp3 = new LoanAmountComparator();
//                        Collections.sort(list, comp3);
//                        Collections.sort(Oldlist, comp3);
//                        llAdapter.notifyDataSetChanged();
//                        sort.setText(adapter.getItem(position));
//                        break;
//
//                    case 4:
//                        Comparator comp4 = new SpeedComparator();
//                        Collections.sort(list, comp4);
//                        Collections.sort(Oldlist, comp4);
//
//                        llAdapter.notifyDataSetChanged();
//
//                        sort.setText(adapter.getItem(position));
//                        break;
//                }
//
//            }
//
//            //没有选中时的处理
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//
//        List<String> option2 = new ArrayList<String>();
//        option2.add("-------请选择-------");
//        option2.add("先息后本");
//        option2.add("等额本息/本金");
//        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.simple_spinner_item1, R.id.tv_spinner, option2);
//        adapter2.setDropDownViewResource(R.layout.spinner_item3);
//        spinner2.setAdapter(adapter2);
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //获取Spinner控件的适配器
//                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
//                switch (position) {
//                    case 0:
//                        break;
//                    case 1:
//
//
//                        RepaymentFilter repaymentFilter = new RepaymentFilter(Oldlist);
//                        list = repaymentFilter.filtered("先息后本");
//                        llAdapter = new LLAdapter(LoanListActivity.this, list);
//                        listView.setAdapter(llAdapter);
//                        filtration.setText(adapter.getItem(position));
//                        break;
//                    case 2:
//
//
//                        RepaymentFilter repaymentFilter2 = new RepaymentFilter(Oldlist);
//                        list = repaymentFilter2.filtered("等额");
//                        llAdapter = new LLAdapter(LoanListActivity.this, list);
//                        listView.setAdapter(llAdapter);
//                        filtration.setText(adapter.getItem(position));
//                        break;
//                }
//
//            }
//
//            //没有选中时的处理
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        //------------------------------------下拉菜单end------------------------------------------


    }

    @OnClick(R.id.loading)
    public void onViewClicked() {

        if (lock) {
            lock = false;
            mQueue.add(stringRequest);
            loading.setText("加载中...");
        }
    }

    @OnClick(R.id.back)
    public void onView2Clicked() {
        finish();
    }


    public class LLAdapter extends BaseAdapter {
        private List<LoanPlan> list = null;

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


        public LLAdapter(Context context, List<LoanPlan> list) {
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
                        convertView = mInflater.inflate(R.layout.loan_list_item4, null);
                    ViewHolder holder = new ViewHolder();

                    holder.company = (TextView) convertView.findViewById(R.id.company);
                    holder.loanRate = (TextView) convertView.findViewById(R.id.loanRate);
                    holder.canLoan = (TextView) convertView.findViewById(R.id.canLoan);
                    holder.duration = (TextView) convertView.findViewById(R.id.duration);
                    holder.type = (TextView) convertView.findViewById(R.id.type);
                    holder.picName = (ImageView) convertView.findViewById(R.id.logo);

                    convertView.setTag(holder);

            }

            final ViewHolder holder2 = (ViewHolder) convertView.getTag();

            String company = list.get(position).getCompany();
            String loanRate = list.get(position).getLoanRate();
            String canLoan = list.get(position).getCanLoan();
            if (canLoan.length()>4) {
                canLoan = canLoan.substring(0, canLoan.length() - 4);
            }else {
                canLoan = "少于1";
            }
//            String repayment = list.get(position).getRepayment();
            String duration = list.get(position).getDuration();
            String type = list.get(position).getType();

            final String picName = list.get(position).getPic();

            final String imageUrl = "http://123.207.61.165/uploads/loanplans/" + picName;


//            holder2.product.setText(product);
            holder2.company.setText(company);
            holder2.loanRate.setText(loanRate + "%");
            holder2.canLoan.setText(canLoan+"万");
//            holder2.repayment.setText(repayment);
            holder2.duration.setText(duration+"年");
            switch (type){
                case "b_amount":
                    holder2.type.setText("金额最大");
                    holder2.type.setBackgroundResource(R.drawable.tuoyuan_orange);
                    break;
                case "b_rate":
                    holder2.type.setText("利率最优");
                    holder2.type.setBackgroundResource(R.drawable.tuoyuan_blue);
                    break;
                case "b_duration":
                    holder2.type.setText("年限最长");
                    holder2.type.setBackgroundResource(R.drawable.tuoyuan_green);
                    break;
            }

            holder2.picName.setTag(picName);
            ImageUtil imageUtil = new ImageUtil(context);
            Bitmap bitmap = imageUtil.readImage(imageUrl);

            if (bitmap != null) {
                Log.e("图片缓存---", "------------------------------------------");

                holder2.picName.setBackgroundDrawable(new BitmapDrawable(bitmap));

            } else {

                Log.e("图片下载---", "------------------------------------------");

                ImageRequest imageRequest = new ImageRequest(
                        imageUrl,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                String tag = (String) holder2.picName.getTag();
                                if (holder2.picName != null) {

                                    ImageUtil imageUtil = new ImageUtil(context);
                                    try {
                                        String e = picName.substring(picName.length()-3);
                                        if (e.equals("png")) {
                                            imageUtil.saveImage(imageUrl, response, ImageUtil.FORMAT_PNG);
                                        }else {
                                            imageUtil.saveImage(imageUrl, response, ImageUtil.FORMAT_JPEG);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if (tag != null && tag.equals(picName)) {
                                        holder2.picName.setBackgroundDrawable(new BitmapDrawable(response));
                                    }
                                }
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("adv未接收", error.getMessage(), error);
                    }
                });

                mQueue.add(imageRequest);

            }


                return convertView;
            }

            class ViewHolder {
                TextView product, company, loanRate, canLoan, repayment, duration,type;
                ImageView picName;
            }

    }


//    private void tolbarAnim(int flag) {
//        if (mAnimator != null && mAnimator.isRunning()) {//判断动画存在  如果启动了,则先关闭
//            mAnimator.cancel();
//        }
//        if (flag == 0) {
//            mAnimator = ObjectAnimator.ofFloat(rl, "translationY", rl.getTranslationY(), 0);//从当前位置位移到0位置
//        } else {
//            mAnimator = ObjectAnimator.ofFloat(rl, "translationY", rl.getTranslationY(), -rl.getHeight());//从当前位置移动到布局负高度的wiz
//        }
//        mAnimator.start();//执行动画
//
//    }


}
