package com.example.wang.qke.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ParseTool {
     public static List<News> parseNews(String json) throws JSONException{
		
    	 List<News> list = new ArrayList<News>();
    	 
    	 JSONObject object = new JSONObject(json);
    	
    		object = object.getJSONObject("paramz"); 
    		JSONArray array = object.getJSONArray("feeds");
				int len = array.length();
    		for(int i = 0 ; i <len; i ++){
    			object = array.getJSONObject(i);
    		
    			object = object.getJSONObject("data");
    			String subject = object.getString("subject");
    			String summary = object.getString("summary");
    			String cover = object.getString("cover");
    			
    			String changed = object.getString("changed");
    			
    			News news = new News();
    			news.setSubject(subject);
    			news.setCover(cover);
    			news.setChanged(changed);
    			news.setSummary(summary);
    			
    			list.add(news);
    		
 		}
    	 System.out.println("++++++++++++++++++++++++++++++++++++"+list);
    	 return list;
    	 
     }
}
