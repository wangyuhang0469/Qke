package com.example.wang.qke.classes;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;


import android.content.Context;
import android.os.AsyncTask;

import android.widget.ListView;


import com.example.wang.qke.adapter.AdsHistoryAdapter;
import com.example.wang.qke.adapter.NewsAdapter;



public class NewsTask extends AsyncTask<String, Void, List<Article>> {


	private Context context;
	private ListView listView;
	private int n;
	
	
	public NewsTask(Context context, ListView listView,int n) {
		super();
		this.context = context;
		this.listView = listView;
		this.n=n;

	}
	@Override
	protected List<Article> doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = params[0];
		List<Article> list = null;
		if(url!=null)
		{
			try {
				byte[] data = HttpUtil.getJsonString(url);
				if (data != null){
				String jsonString = new String(data,"utf-8");
				list = ArticleTool.parseArticle(jsonString);}
				
				
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
		switch (n){
			case 0:
				final NewsAdapter adapter = new NewsAdapter(context, result);
				listView.setAdapter(adapter);

				break;
			case 1:




//				BibleAdapter adapter1 = new BibleAdapter(context, result);
//				listView.setAdapter(adapter1);
				break;
			case 2:
				AdsHistoryAdapter adapter2 = new AdsHistoryAdapter(context, result);
				listView.setAdapter(adapter2);
				break;




		}



	}


}
