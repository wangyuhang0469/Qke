package com.example.wang.qke.ui.articles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.Article;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class InformationActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.loading)
    TextView loading;

    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.PullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;


    private List<Article> list;
    private MyAdapter myAdapter;


    private int cpageNum = 0;

    private GetBuilder getBuilder;
    private StringCallback StringCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information2);
        ButterKnife.bind(this);

        title.setText("资讯");

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutmanager);


        initOKHttp();

        getBuilder.addParams("page", cpageNum + "").build().execute(StringCallback);

        initPullToRresh();


    }

    private void initOKHttp(){
        getBuilder = OkHttpUtils.get()
                .url("http://123.207.61.165/outside/getarticles")
                .addParams("from", "1")
                .addParams("type", "资讯")
                .addParams("page", cpageNum + "");


        StringCallback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                pullToRefreshLayout.finishLoadMore();
                toast("加载失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                cpageNum++;
                if (cpageNum == 1) {
                    try {
                        JSONObject object = new JSONObject(response);
                        list = JSON.parseArray(object.getString("result"),Article.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    myAdapter = new MyAdapter(list);
                    recycleView.setAdapter(myAdapter);
                }else {
                    List<Article> addList = null;
                    try {
                        JSONObject object = new JSONObject(response);
                        addList = JSON.parseArray(object.getString("result"),Article.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (addList == null) {
                        toast("加载失败");
                    } else if (addList.size() != 0) {
                        list.addAll(addList);
                        myAdapter.notifyDataSetChanged();
                    } else {
                        pullToRefreshLayout.setCanLoadMore(false);
                        toast("已是最后一页");
                    }
                }
                pullToRefreshLayout.finishLoadMore();
            }
        };

    }

    private void initPullToRresh(){
        pullToRefreshLayout.setCanRefresh(false);

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
            }

            @Override
            public void loadMore() {
                getBuilder.addParams("page", cpageNum + "").build().execute(StringCallback);
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        private List<Article> list;

        public MyAdapter(List<Article> list) {
            this.list = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView item_title;
            ImageView item_picName;

            public ViewHolder(View itemView) {
                super(itemView);
                item_picName = (ImageView) itemView.findViewById(R.id.picName);
                item_title = (TextView) itemView.findViewById(R.id.title);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = null;
            if (viewType == 0) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_top, parent, false);
            } else {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_listview_item, parent, false);
            }
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.item_title.setText(list.get(position).getTitle());


            Glide.with(InformationActivity.this)
                    .load(list.get(position).getPicUrl())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                holder.item_picName.setBackground(new BitmapDrawable(resource));   //设置背景
                            }
                        }
                    });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.get(position).getId());
                    bundle.putString("title", list.get(position).getTitle());
                    startActivity(ArticleActivity.class , bundle ,false);
                }
            });


        }




        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
