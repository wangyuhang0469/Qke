package com.example.wang.qke.classes;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


public class HttpUtil {
      public static byte[] getJsonString(String path) throws ClientProtocolException, IOException{
    	  byte[] data = null;
  		HttpGet get = new HttpGet(path);
  		HttpClient client = new DefaultHttpClient();

		  HttpParams params = null;
		  params = client.getParams();
		  //set timeout
		  HttpConnectionParams.setConnectionTimeout(params, 5000);
		  HttpConnectionParams.setSoTimeout(params, 35000);


		  HttpResponse response = null;
  		response = client.execute(get);
  		if(response.getStatusLine().getStatusCode()==200)
  		{
  			data = EntityUtils.toByteArray(response.getEntity());
  		}
  		return data;
    	  
      }

	

	
}
