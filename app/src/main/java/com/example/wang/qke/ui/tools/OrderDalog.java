package com.example.wang.qke.ui.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.wang.qke.R;


/**
 * Created by wang on 2017/8/16.
 */

public class OrderDalog extends Dialog implements OrderListener{



    protected Context mContext;
    public ImageView icon;

    private View customView;

    private OrderListener orderListener;


    private TextView title;


    private String bookingType;
    private String bookingTypeName;
    private String bussName;
    private String szItemNo;



    public OrderDalog(Context context, int style,OrderListener orderListener) {
        super(context, R.style.FullScreenDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.order_dalog, null);

        this.orderListener = orderListener;

        icon =(ImageView) customView.findViewById(R.id.back);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //=====================================主线程============================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);


        title =(TextView) findViewById(R.id.title);


        final TextView tv1 =(TextView) findViewById(R.id.tv1);
        TextView tv2 =(TextView) findViewById(R.id.tv2);
        TextView tv3 =(TextView) findViewById(R.id.tv3);
        TextView tv4 =(TextView) findViewById(R.id.tv4);
        TextView tv5 =(TextView) findViewById(R.id.tv5);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingType = "-1060";
                bookingTypeName = "领证业务类";
                szItemNo = "FZ20150730";
                bussName = "领取不动产权证书及登记证明";
                refreshActivity(bookingType,bookingTypeName,bussName,szItemNo);
                OrderDalog.this.dismiss();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingType = "2";
                bookingTypeName = "房地产现楼抵押业务类";
                szItemNo = "30004200169555062213440300";
                bussName = "一般抵押权首次登记-现售（现楼抵押）";
                refreshActivity(bookingType,bookingTypeName,bussName,szItemNo);
                OrderDalog.this.dismiss();
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingType = "2";
                bookingTypeName = "房地产现楼抵押业务类";
                szItemNo = "30041100769555062213440300";
                bussName = "最高额抵押权首次登记";
                refreshActivity(bookingType,bookingTypeName,bussName,szItemNo);
                OrderDalog.this.dismiss();
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingType = "-1033";
                bookingTypeName = "房地产注销抵押业务类";
                szItemNo = "30156000169555062213440300";
                bussName = "抵押权注销登记";
                refreshActivity(bookingType,bookingTypeName,bussName,szItemNo);
                OrderDalog.this.dismiss();
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookingType = "1";
                bookingTypeName = "二手商品房转移业务类";
                szItemNo = "30128300369555062213440300";
                bussName = "二手商品房转移登记（二手房买卖）";
                refreshActivity(bookingType,bookingTypeName,bussName,szItemNo);
                OrderDalog.this.dismiss();
            }
        });


        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDalog.this.dismiss();

            }
        });
    }

    //=====================================主线程END============================================




    @Override
    public void refreshActivity(String a,String b,String c,String d) {
        orderListener.refreshActivity(a,b,c,d);
    }
}
