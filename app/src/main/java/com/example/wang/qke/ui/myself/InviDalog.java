package com.example.wang.qke.ui.myself;


import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.TextView;


import com.example.wang.qke.R;
import com.example.wang.qke.classes.User;

public class InviDalog extends Dialog {
    protected static int default_width = WindowManager.LayoutParams.WRAP_CONTENT; // 默认宽度
    protected static int default_height = WindowManager.LayoutParams.WRAP_CONTENT;// 默认高度
    public static int TYPE_TWO_BT = 2;
    public static int TYPE_NO_BT = 0;
    protected Context mContext;
    private View.OnClickListener listener;
    private View customView;
    //	@Bind(R.id.icon)

    public ImageView icon;


    public InviDalog(Context context, int style) {
        super(context, R.style.FullScreenDialog);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.activity_invi_dalog, null);
        icon =(ImageView) customView.findViewById(R.id.icon);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);

        TextView textView = (TextView)findViewById(R.id.code);
        textView.setText(User.getInstance().getInviteCode());



    }

    public InviDalog setClickListener(View.OnClickListener listener) {
        this.listener = listener;
        icon.setOnClickListener(listener);

        return this;
    }



}
