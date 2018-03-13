package com.example.wang.qke.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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



public class AdsHistoryAdapter extends BaseAdapter {
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




    public AdsHistoryAdapter(Context context, List<Article> list) {
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
            convertView = mInflater.inflate(R.layout.ads_history_item, null);

            ViewHolder holder = new ViewHolder();
            holder.cover = (ImageView) convertView.findViewById(R.id.image);
            holder.subject = (TextView) convertView.findViewById(R.id.subject);
            holder.summary = (TextView) convertView.findViewById(R.id.summary);
            holder.changed = (TextView) convertView.findViewById(R.id.changed);

            convertView.setTag(holder);

        }
        final ViewHolder holder2 = (ViewHolder) convertView.getTag();

        final String cover = list.get(position).getPicUrl();
        String subject = list.get(position).getTitle();
        String summary = list.get(position).getSummary();
        String changed = list.get(position).getAuthor();
        String imageUrl = "http://123.207.61.165/uploads/article/"+cover;

        holder2.subject.setText(subject);
        holder2.summary.setText(summary);
        holder2.changed.setText(changed);
        holder2.cover.setTag(cover);
        ImageUtil imageUtil= new ImageUtil(context);
        Bitmap bitmap = imageUtil.readImage(imageUrl);
        if(bitmap!=null){
            Log.e("read suee---","read suee------------------------------------------");
            holder2.cover.setImageBitmap(bitmap);
        }else{

            Log.e("not read---"," not  read ------------------------------------------");

            new DownImageTask(new DownImageTask.DownLoadBack() {
                @Override
                public void response(Bitmap bitmap) {

                    String tag = (String) holder2.cover.getTag();
//					Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
                    if(tag!=null&&tag.equals(cover)){
                        holder2.cover.setImageBitmap(bitmap);

                    }
                }
            },context).execute(imageUrl);
        }


        return convertView;
    }

    class ViewHolder{
        TextView subject,changed,summary;
        ImageView cover;
    }

}
