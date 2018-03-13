package com.example.wang.qke.ui.myself;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.ImageUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InviteFriendActivity extends BaseActivity {
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tv)
    TextView tv;
//    private String[] titles = new String[]{"立即查档", "查档记录"};
//    private TabLayout mTabLayout;
//    private ViewPager mViewPager;
//    private FragmentAdapter adapter;
//    //ViewPage选项卡页面列表
//    private List<Fragment> mFragments;
//    private List<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final RequestQueue mQueue = Volley.newRequestQueue(this);


        final InviDalog dalog = new InviDalog(this, R.style.FullScreenDialog);
        dalog.show();
        dalog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dalog.dismiss();
            }
        });


        StringRequest gifStringRequest = new StringRequest("http://123.207.61.165/outside/getbanners?tid=4",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String url = "";
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("result");
                            String gifName = array.getJSONObject(0).getString("pic");
                            url = "http://123.207.61.165/uploads/banner/" + gifName;

//                            Glide.with(InviteFriendActivity.this).load(url).centerCrop().crossFade().into(img);

                            ImageUtil imageUtil = new ImageUtil(InviteFriendActivity.this);
                            Bitmap bitmap = imageUtil.readImage(url);
                            if (bitmap != null) {
                                Log.e("资讯图片缓存---", "------------------------------------------");
                                img.setImageBitmap(bitmap);
                            } else {


                                final String finalUrl = url;
                                ImageRequest imageRequest = new ImageRequest(
                                        url,
                                        new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap response) {

                                                ImageUtil imageUtils = new ImageUtil(InviteFriendActivity.this);
                                                try {
                                                    imageUtils.saveImage(finalUrl, response, ImageUtil.FORMAT_JPEG);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                if (img != null) {
                                                    img.setImageBitmap(response);

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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d("loopers", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (tv!=null) {
                    tv.setText("加载失败");
                }
            }
        });


        mQueue.add(gifStringRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }


}