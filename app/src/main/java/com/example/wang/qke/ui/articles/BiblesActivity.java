package com.example.wang.qke.ui.articles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.Article;
import com.example.wang.qke.classes.ArticleTool;
import com.example.wang.qke.classes.HttpUtil;
import com.example.wang.qke.classes.PullableRecyclerView;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiblesActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.loading)
    TextView loading;
    @Bind(R.id.recycleView)
    PullableRecyclerView recycleView;
    @Bind(R.id.PullToRefreshLayout)
    PullToRefreshLayout pullToRefreshLayout;
    private String path = "http://123.207.61.165/outside/getarticles?from=1&type=宝典&page=";

    private int cpageNum = 0;


    private List<Article> list;

    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibles);
        ButterKnife.bind(this);

        title.setText("宝典");

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutmanager);

        new infoTask().execute(path + cpageNum);

        initPullToRresh();

    }

    private void initPullToRresh() {
        pullToRefreshLayout.setCanRefresh(false);

        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
            }

            @Override
            public void loadMore() {
                new infoTask().execute(path + cpageNum);
            }
        });
    }


    public class infoTask extends AsyncTask<String, Void, List<Article>> {


        @Override
        protected List<Article> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = params[0];
            List<Article> list = null;
            if (url != null) {
                try {
                    byte[] data = HttpUtil.getJsonString(url);
                    String jsonString = new String(data, "utf-8");
                    list = ArticleTool.parseArticle(jsonString);

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Article> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            cpageNum++;
            if (cpageNum == 1) {
                list = result;
                myAdapter = new MyAdapter(list);
                recycleView.setAdapter(myAdapter);
                loading.setText("");
            } else {

                if (result == null) {
                    toast("加载失败");
                } else if (result.size() != 0) {
                    list.addAll(result);
                    myAdapter.notifyDataSetChanged();
                } else {
                    toast("已是最后一页");
                    pullToRefreshLayout.setCanLoadMore(false);
                }
            }

            pullToRefreshLayout.finishLoadMore();

        }
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bibles_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.item_title.setText(list.get(position).getTitle());


            Glide.with(BiblesActivity.this)
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
                    startActivity(ArticleActivity.class, bundle, false);
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
