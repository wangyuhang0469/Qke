package com.example.wang.qke.classes;

import java.io.IOException;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.client.ClientProtocolException;

public class DownImageTask extends AsyncTask<String, Void, Bitmap> {

	private DownLoadBack downLoadBack;
	private Context context;
	
	public DownImageTask(DownLoadBack downLoadBack,Context context)
	{
		this.downLoadBack = downLoadBack;
		this.context = context;
	}


	
	@Override
	protected Bitmap doInBackground(String... params) {
		String url = params[0];
		Bitmap bitmap = null;

		if(url!=null)
		{
			try {
				byte[] data = HttpUtil.getJsonString(url);
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				ImageUtil imageUtil =new ImageUtil(context);
				//ͼƬ�������ʱ����ͼƬ�浽��չ��
				imageUtil.saveImage(url, bitmap, ImageUtil.FORMAT_JPEG);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (RuntimeException e){
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		
		downLoadBack.response(result);
	}
	
	public interface DownLoadBack
	{
		public void response(Bitmap bitmap);
	}
}
