package com.example.wang.qke.ui.loan;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wang.qke.R;
import com.example.wang.qke.base.BaseFragment;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.tools.HouseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Loan2Fragment extends BaseFragment {


    @Bind(R.id.rb1)
    RadioButton rb1;
    @Bind(R.id.rb2)
    RadioButton rb2;
    @Bind(R.id.ll_show)
    LinearLayout llShow;
    @Bind(R.id.property)
    EditText property;
    @Bind(R.id.realname)
    EditText realname;
    @Bind(R.id.sex)
    EditText sex;
    @Bind(R.id.spauseJob)
    TextView spauseJob;
    @Bind(R.id.spauseIncome)
    TextView spauseIncome;
    @Bind(R.id.mobNum)
    EditText mobNum;
    @Bind(R.id.idNum)
    EditText idNum;
    @Bind(R.id.salaryRange)
    TextView salaryRange;
    @Bind(R.id.occupation)
    TextView occupation;
    @Bind(R.id.isSZPerson)
    TextView tv_isSZPerson;
    @Bind(R.id.socialSec)
    TextView tv_socialSec;
    @Bind(R.id.rg_ismarriage)
    RadioGroup rgIsmarriage;
    @Bind(R.id.spinner3)
    Spinner spinner3;
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner4)
    Spinner spinner4;
    @Bind(R.id.spinner5)
    Spinner spinner5;
    @Bind(R.id.spinner6)
    Spinner spinner6;
    @Bind(R.id.spinner7)
    Spinner spinner7;
    @Bind(R.id.loanAmount)
    EditText loanAmount;
    @Bind(R.id.tv9)
    TextView tv9;
    @Bind(R.id.tv10)
    TextView tv10;


    private LoanActivity loanActivity;
    private String marriage = "1";

    private String isSZPerson = "0";
    private String socialSec = "0";


    public Loan2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_loan2, container, false);
        ButterKnife.bind(this, view);

        Drawable drawable = getResources().getDrawable(R.drawable.loan_radio_button);
        drawable.setBounds(0, 0, 46, 46);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb1.setCompoundDrawables(null, null, drawable, null);//只放上面

        Drawable drawable2 = getResources().getDrawable(R.drawable.loan_radio_button);
        drawable2.setBounds(0, 0, 46, 46);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rb2.setCompoundDrawables(null, null, drawable2, null);//只放上面


        final Animation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);

        final Animation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);


        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle.getString("str") != null) {

            String much = bundle.getString("str");
            loanAmount.setText(much);
            tv9.setVisibility(View.VISIBLE);
        }


        rgIsmarriage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb1:
                        llShow.startAnimation(mShowAction);
                        llShow.setVisibility(view.VISIBLE);
                        marriage = "1";
                        break;
                    case R.id.rb2:
                        llShow.startAnimation(mHiddenAction);
                        llShow.setVisibility(view.GONE);
                        marriage = "0";
                        break;
                }
            }
        });


        loanActivity = (LoanActivity) getActivity();


        //------------------------------------下拉菜单------------------------------------------
        List<String> option1 = new ArrayList<String>();
        option1.add("请选择：");
        option1.add("国有企业");
        option1.add("政府企业");
        option1.add("500强企业");
        option1.add("一般企业员工");
        option1.add("个体工商户");
        option1.add("小企业主");
        List<String> option2 = new ArrayList<String>();
        option2.add("请选择：");
        option2.add("50000以上");
        option2.add("25000-50000");
        option2.add("15000-25000");
        option2.add("8000-15000");
        option2.add("3000-8000");
        option2.add("3000以下");

        List<String> option3 = new ArrayList<String>();
        option3.add("请选择：");
        option3.add("是");
        option3.add("否");


        List<String> option4 = new ArrayList<String>();
        option4.add("请选择：");
        option4.add("男");
        option4.add("女");


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option1);
        adapter1.setDropDownViewResource(R.layout.spinner_item2);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    spauseJob.setText(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option2);
        adapter2.setDropDownViewResource(R.layout.spinner_item2);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    spauseIncome.setText(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner3.setAdapter(adapter2);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    salaryRange.setText(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinner4.setAdapter(adapter1);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    occupation.setText(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option3);
        adapter3.setDropDownViewResource(R.layout.spinner_item2);
        spinner5.setAdapter(adapter3);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        isSZPerson = "1";
                        tv_isSZPerson.setText(adapter.getItem(position));
                        break;
                    case 2:
                        isSZPerson = "0";
                        tv_isSZPerson.setText(adapter.getItem(position));
                        break;
                }
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner6.setAdapter(adapter3);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        socialSec = "1";
                        tv_socialSec.setText(adapter.getItem(position));
                        break;
                    case 2:
                        socialSec = "0";
                        tv_socialSec.setText(adapter.getItem(position));
                        break;
                }
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item1, R.id.tv_spinner, option4);
        adapter7.setDropDownViewResource(R.layout.spinner_item2);
        spinner7.setAdapter(adapter7);
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if (position != 0)
                    sex.setText(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //------------------------------------下拉菜单end------------------------------------------


        TextWatcher mTextWatcher = new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (loanAmount.getText().length() == 0) {
                    tv9.setVisibility(View.GONE);
                } else {
                    tv9.setVisibility(View.VISIBLE);
                }

            }
        };


        TextWatcher mTextWatcher2 = new TextWatcher() {
            private CharSequence temp;
            private int editStart;
            private int editEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (property.getText().length() == 0) {
                    tv10.setVisibility(View.GONE);
                } else {
                    tv10.setVisibility(View.VISIBLE);
                }

            }
        };


        loanAmount.addTextChangedListener(mTextWatcher);
        property.addTextChangedListener(mTextWatcher2);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        Intent intent = new Intent(getActivity(), HouseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("form", "2");
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b = data.getExtras(); //data为B中回传的Intent
                String result = b.getString("result");//str即为回传的值
                property.setText(result);
                tv10.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    public void commit() {


        if (loanAmount.getText().length() != 0 && sex.getText().length() != 0 && occupation.getText().length() != 0 && salaryRange.getText().length() != 0 && tv_isSZPerson.getText().length() != 0 && tv_socialSec.getText().length() != 0 && property.getText().length() != 0) {
            Bundle bundle = new Bundle();

            if (!(marriage.equals("1") && spauseJob.getText().length() == 0 && spauseIncome.getText().length() == 0)) {

                if (idNum.getText().length() == 18 || idNum.getText().length() == 15) {
                    if (mobNum.getText().length() == 11) {
                        if (Integer.parseInt(property.getText().toString()) > 9999) {

                            bundle.putString("form", "loan");
                            bundle.putString("uid", User.getInstance().getUid());
                            bundle.putString("loanAmount", loanAmount.getText().toString());
                            bundle.putString("step", "2");
                            bundle.putString("age", loanActivity.age);
                            bundle.putString("overdueLoan", loanActivity.overdueLoan);
                            bundle.putString("loanFrequency", loanActivity.loanFrequency);
                            bundle.putString("realname", realname.getText().toString());
                            bundle.putString("sex", sex.getText().toString());
                            bundle.putString("mobNum", mobNum.getText().toString());
                            bundle.putString("idNum", idNum.getText().toString());
                            bundle.putString("occupation", occupation.getText().toString());
                            bundle.putString("salaryRange", salaryRange.getText().toString());
//                map.put("isSZPerson", isSZPerson.getText().toString());
//                map.put("socialSec", socialSec.getText().toString());
                            bundle.putString("isSZPerson", isSZPerson);
                            bundle.putString("socialSec", socialSec);
                            bundle.putString("marriage", marriage);
                            bundle.putString("spauseJob", spauseJob.getText().toString());
                            bundle.putString("spauseIncome", spauseIncome.getText().toString());
                            bundle.putString("property", property.getText().toString());

                            startActivity(LoanListActivity.class, bundle);
                        }else {
                            toast("房产值不能少于10000");
                        }
                    } else {
                        toast("手机号位数不正确");
                    }
                } else {
                    toast("身份证号不正确");
                }
            } else {
                toast("请输入配偶信息");
            }
        } else {
            toast("请输入完整信息");
        }
    }


}
