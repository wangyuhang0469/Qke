package com.example.wang.qke.classes;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/8/3.
 */
public class ArticleTool {
//    private static int pageNum;

    public static List<Article> parseArticle(String json) throws JSONException {

        List<Article> list = new ArrayList<Article>();

        JSONObject object = new JSONObject(json);
//        if (object.getInt("total_page") != 0) {
//            pageNum = object.getInt("total_page");
//        }

        JSONArray array = object.getJSONArray("result");
        int len = array.length();
        for(int i = 0 ; i <len; i ++){
            object = array.getJSONObject(i);

            String id = object.getString("id");
            String title = object.getString("title");
            String summary = object.getString("summary");
            String author = object.getString("author");
            String pageView = object.getString("pageView");
            String pic = object.getString("pic");
            String time = object.getString("time");



            Article article = new Article();
            article.setId(id);
            article.setTitle(title);
            article.setSummary(summary);
            article.setAuthor(author);
            article.setPageView(pageView);
            article.setPic(pic);
            article.setTime(time);


            list.add(article);

        }
        System.out.println("++++++++++++++++++++++++++++++++++++"+list);
        return list;

    }
}
