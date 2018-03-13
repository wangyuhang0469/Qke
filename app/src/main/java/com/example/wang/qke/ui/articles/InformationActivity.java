package com.example.wang.qke.ui.articles;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.adapter.NewsAdapter;
import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.Article;
import com.example.wang.qke.classes.ArticleTool;
import com.example.wang.qke.classes.HttpUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InformationActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.loading)
    TextView loading;
    private ListView listView;
    private String path = "http://123.207.61.165/outside/getarticles?from=1&type=资讯&page=";


    //最多有几页

    private boolean havaNext = true;
    //用来判断是否加载完成
    private boolean loadfinish = true;
    private View v;

    private List<Article> list;

    private NewsAdapter newsAdapter;

    private int cpageNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);

        title.setText("资讯");

        listView = (ListView) findViewById(R.id.listView1);

        v = this.getLayoutInflater().inflate(R.layout.listview_foot, null);


        new infoTask(this, listView, 0).execute(path + "0"); //这里执行了异步任务的方法，并传入路径。


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, final int totalItemCount) {


                //得到listview最后一项的id
                int lastItemId = listView.getLastVisiblePosition();
                //判断用户是否滑动到最后一项，因为索引值从零开始所以要加上1
                if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == listView.getHeight()) {

                        if (havaNext && loadfinish) {

                            loadfinish = false;


                            if (totalItemCount > 0) {
                                //判断当前页是否超过最大页，以及上一页的数据是否加载完成


//                                //添加页脚视图
//                                listView.addFooterView(v);


                                cpageNum++;


                                new infoTask(InformationActivity.this, listView, 1).execute(path + cpageNum);


                            }
                        }

                    }

                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //这里我使用了一个点击ListView的监听，这儿一般是点击后就会跳转并显示该条目的详情，在这片文章中我还完善。

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //得到listview最后一项的id
                int lastItemId = listView.getLastVisiblePosition();

                if (position != lastItemId) {

                    Intent intent = new Intent(InformationActivity.this, ArticleActivity.class);

                    //用Bundle携带数据
                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.get(position).getId());
                    bundle.putString("title", list.get(position).getTitle());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }


    public class infoTask extends AsyncTask<String, Void, List<Article>> {


        private Context context;
        private ListView listView;
        private int n;


        public infoTask(Context context, ListView listView, int n) {
            super();
            this.context = context;
            this.listView = listView;
            this.n = n;
        }

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

            switch (n) {
                case 0:
                    list = result;
                    newsAdapter = new NewsAdapter(InformationActivity.this, list);
                    //添加listview的脚跟视图，这个方法必须在listview.setAdapter()方法之前，否则无法显示视图
                    if (list != null) {
                        listView.addFooterView(v);
                    }
                    //添加数据
                    listView.setAdapter(newsAdapter);

                    loading.setText("");
                    break;


                case 1:

                    if (result == null) {
                        toast("加载失败");
                    } else if (result.size() != 0) {

                        list.addAll(result);

                        newsAdapter.notifyDataSetChanged();
                        if (listView.getFooterViewsCount() != 0) {
//                                        listView.removeFooterView(v);
                        }

                        loadfinish = true;
                    } else {
                        havaNext = false;

                        View v2 = getLayoutInflater().inflate(R.layout.listview_foot_end, null);
                        listView.removeFooterView(v);

                        listView.addFooterView(v2);
                    }


                    break;
            }


        }


    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
