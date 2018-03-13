package com.example.wang.qke.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.wang.qke.R;
import com.example.wang.qke.classes.Article;
import com.example.wang.qke.classes.DownImageTask;
import com.example.wang.qke.classes.ImageUtil;





public class NewsAdapter extends BaseAdapter {
    private List<Article> list = null;

	private Context context =null;
	//private ViewHolder holder =null;



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




	public NewsAdapter(Context context, List<Article> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		int count = 0 ; 
		if(list!=null){
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

		if(convertView==null){
			if (position == 0)
			{
//				convertView = LayoutInflater.from(context).inflate(R.layout.news_top, parent,false);
				convertView = mInflater.inflate(R.layout.news_top, null);

			}else {
//			convertView = LayoutInflater.from(context).inflate(R.layout.article_listview_item, parent,false);
				convertView = mInflater.inflate(R.layout.article_listview_item, null);
			}
			ViewHolder holder = new ViewHolder();
			holder.picName = (ImageView) convertView.findViewById(R.id.picName);
			holder.title = (TextView) convertView.findViewById(R.id.title);

			convertView.setTag(holder);

		}
        final ViewHolder holder2 = (ViewHolder) convertView.getTag();
		
		final String picName = list.get(position).getPicUrl();
		String imageUrl = "http://123.207.61.165/uploads/article/"+picName;

		String title = list.get(position).getTitle();


		holder2.title.setText(title);

		holder2.picName.setTag(picName);
		ImageUtil imageUtil= new ImageUtil(context);
		Bitmap bitmap = imageUtil.readImage(imageUrl);

		if(bitmap!=null){

			holder2.picName.setBackgroundDrawable(new BitmapDrawable(bitmap));

		}else{



			new DownImageTask(new DownImageTask.DownLoadBack() {
				@Override
				public void response(Bitmap bitmap) {

					String tag = (String) holder2.picName.getTag();
//					Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
					if(tag!=null&&tag.equals(picName)){
						holder2.picName.setBackgroundDrawable(new BitmapDrawable(bitmap));

					}
				}
			},context).execute(imageUrl);
		}
		
		
		return convertView;
	}
	
   class ViewHolder{
	   TextView title;
	   ImageView picName;
   }

}
