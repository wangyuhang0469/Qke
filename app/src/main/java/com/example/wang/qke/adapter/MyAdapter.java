package com.example.wang.qke.adapter;

/**
 * Created by wang on 2017/7/17.
 */


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;


import java.util.List;

public class MyAdapter extends PagerAdapter {
    private List<ImageView> data;
    Context context;
    public MyAdapter(List<ImageView> data, Context context) {
        this.data=data;
        this.context=context;
    }

    @Override
    public int getCount() {
        //返回一个无穷大的值，
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0==arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //注意，这里什么也不做!!!

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView image=data.get(position%data.size());
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp=image.getParent();
        if(vp!=null){
            ViewGroup vg=(ViewGroup) vp;
            vg.removeView(image);
        }
        image.setOnClickListener(new OnClickListener() {

            int a = position%data.size();
            @Override
            public void onClick(View v) {
//                switch (a){
//                    case 0:
//                        Intent i = new Intent(context,LoginActivity.class);
//                        context.startActivity(i,null);
////                        WebView webView = new WebView(context);
////                        Uri uri = Uri.parse("https://news.sina.cn/2017-07-25/detail-ifyihmmm8508757.d.html?cre=tianyi&mod=whome&loc=1&r=25&doct=0&rfunc=71&none&tr=25&vt=4&pos=108&wm=4007");
////                        webView.loadUrl("https://news.sina.cn/2017-07-25/detail-ifyihmmm8508757.d.html?cre=tianyi&mod=whome&loc=1&r=25&doct=0&rfunc=71&none&tr=25&vt=4&pos=108&wm=4007");
////                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 4:
//                        Toast.makeText(context, "5", Toast.LENGTH_SHORT).show();
//                        break;
//                }

            }
        });
        container.addView(data.get(position%data.size()));
        return data.get(position%data.size());
    }



}