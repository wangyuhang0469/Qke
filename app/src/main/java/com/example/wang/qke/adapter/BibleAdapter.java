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



public class BibleAdapter extends BaseAdapter {
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




    public BibleAdapter(Context context, List<Article> list) {
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
                convertView = mInflater.inflate(R.layout.bibles_item, null);

            ViewHolder holder = new ViewHolder();
            holder.picName = (ImageView) convertView.findViewById(R.id.picName);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.summary = (TextView) convertView.findViewById(R.id.summary);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.pageView = (TextView) convertView.findViewById(R.id.pageView);

            convertView.setTag(holder);

        }
        final ViewHolder holder2 = (ViewHolder) convertView.getTag();

        final String picName = list.get(position).getPicUrl();
        String imageUrl = "http://123.207.61.165/uploads/article/"+picName;
        String title = list.get(position).getTitle();
        String summary = list.get(position).getSummary();
        String author = list.get(position).getAuthor();
        String pageView = list.get(position).getPageView();

        holder2.title.setText(title);
        holder2.summary.setText(summary);
        holder2.author.setText("作者："+author);
        holder2.pageView.setText("阅读数："+pageView);

        //----------增加tag----------------
        holder2.picName.setTag(picName);

        ImageUtil imageUtil= new ImageUtil(context);
        Bitmap bitmap = imageUtil.readImage(imageUrl);
        if(bitmap!=null){
            Log.e("图片缓存---","------------------------------------------");
            holder2.picName.setBackgroundDrawable(new BitmapDrawable(bitmap));

        }else{

            Log.e("图片下载---"," ------------------------------------------");

            new DownImageTask(new DownImageTask.DownLoadBack() {
                @Override
                public void response(Bitmap bitmap) {

                    String tag = (String) holder2.picName.getTag();

                    if(tag!=null&&tag.equals(picName)){

                        holder2.picName.setBackgroundDrawable(new BitmapDrawable(bitmap));

                    }
                }
            },context).execute(imageUrl);
        }


        return convertView;
    }

    class ViewHolder{
        TextView title,summary,author,pageView;
        ImageView picName;
    }

}
