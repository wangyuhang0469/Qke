package com.example.wang.qke.ui.tools;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkDetailsActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.docNum)
    TextView tv_docNum;
    @Bind(R.id.appNum)
    TextView tv_appNum;
    @Bind(R.id.event)
    TextView tv_event;
    @Bind(R.id.acceptDate)
    TextView tv_acceptDate;
    @Bind(R.id.responseDate)
    TextView tv_responseDate;
    @Bind(R.id.time)
    TextView tv_time;
    @Bind(R.id.state)
    TextView tv_state;


    private String uid;
    private String docNum;
    private String appNum;
    private String event;
    private String acceptDate;
    private String responseDate;
    private String state;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);
        ButterKnife.bind(this);

        title.setText("办文详情");

        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getString("uid");
        docNum = bundle.getString("docNum");
        appNum = bundle.getString("appNum");
        event = bundle.getString("event");
        acceptDate = bundle.getString("acceptDate");
        responseDate = bundle.getString("responseDate");
        state = bundle.getString("state");
        time = bundle.getString("time");

        tv_docNum.setText(docNum);
        tv_appNum.setText(appNum);
        tv_event.setText(event);
        tv_acceptDate.setText(acceptDate);
        tv_responseDate.setText(responseDate);
        tv_state.setText(state);
        tv_time.setText(time);



    }


    @OnClick({R.id.back, R.id.btn})
    public void onViewClicked(View view) {
        finish();
    }
}
