package com.example.wang.qke.ui.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.example.wang.qke.R;


public class LoadingDalog extends Dialog{

    private Context context;

    public LoadingDalog(Context context) {
        super(context, R.style.FullScreenDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.loading_dalog, null));

    }

}
