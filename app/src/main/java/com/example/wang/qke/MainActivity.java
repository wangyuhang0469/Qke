package com.example.wang.qke;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wang.qke.base.BaseActivity;
import com.example.wang.qke.classes.User;
import com.example.wang.qke.ui.main.AdvisoryFragment;
import com.example.wang.qke.ui.main.HomePageFragment;
import com.example.wang.qke.ui.main.IntroActivity;
import com.example.wang.qke.ui.myself.LoginActivity;
import com.example.wang.qke.ui.main.MyselfFragment;
import com.example.wang.qke.ui.main.ToolsFragment;
import com.example.wang.qke.ui.loan.LoanActivity;
import com.example.wang.qke.ui.main.WelcomeActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_homepgae)
    LinearLayout tabHomepgae;
    @Bind(R.id.tab_tools)
    LinearLayout tabTools;
    @Bind(R.id.tab_advisory)
    LinearLayout tabAdvisory;
    @Bind(R.id.tab_myself)
    LinearLayout tabMyself;

    private LinearLayout[] mTabs;
    private HomePageFragment homePageFragment;
    private ToolsFragment toolsFragment;
    private AdvisoryFragment advisoryFragment;
    private MyselfFragment myselfFragment;
    private Fragment[] fragments;
    public int index;
    private int currentTabIndex;
    private long exitTime = 0;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFirstStart();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        user = User.getInstance();


        verifyUser();

    }

    @Override
    protected void initView() {
        super.initView();
        mTabs = new LinearLayout[4];
        mTabs[0] = tabHomepgae;
        mTabs[1] = tabTools;
        mTabs[2] = tabAdvisory;
        mTabs[3] = tabMyself;
        mTabs[0].setSelected(true);

        homePageFragment = new HomePageFragment();
        toolsFragment = new ToolsFragment();
        advisoryFragment = new AdvisoryFragment();
        myselfFragment = new MyselfFragment();
        fragments = new Fragment[]{homePageFragment, toolsFragment, advisoryFragment, myselfFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homePageFragment)
                .add(R.id.fragment_container, toolsFragment)
                .add(R.id.fragment_container, advisoryFragment)
                .add(R.id.fragment_container, myselfFragment)
                .hide(myselfFragment).hide(advisoryFragment).hide(toolsFragment)
                .show(homePageFragment).commit();
    }


    public void onTabSelect(int index) {
        if (user.getStatus() || index == 0 || index == 1) {
            if (currentTabIndex != index) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(fragments[currentTabIndex]);
                if (!fragments[index].isAdded()) {
                    trx.add(R.id.fragment_container, fragments[index]);
                }
                trx.show(fragments[index]).commit();
            }

            mTabs[currentTabIndex].setSelected(false);
            mTabs[index].setSelected(true);
            currentTabIndex = index;
        } else {
            startActivity(LoginActivity.class, null, false);
        }
    }

    @OnClick({R.id.tab_homepgae, R.id.tab_tools, R.id.tab_advisory, R.id.tab_myself, R.id.imageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_homepgae:
                index = 0;
                onTabSelect(index);
                break;
            case R.id.tab_tools:
                index = 1;
                onTabSelect(index);
                break;
            case R.id.tab_advisory:
                index = 2;
                onTabSelect(index);
                break;
            case R.id.tab_myself:
                index = 3;
                onTabSelect(index);
                break;
            case R.id.imageView:
                if (user.getStatus()){
                    startActivity(LoanActivity.class, null, false);
                } else {
                    startActivity(LoginActivity.class, null, false);
                }
                break;
        }
    }

    // 进行用户token验证实现免登录
    private void verifyUser() {

        SharedPreferences read = getSharedPreferences("verifyUser", MODE_PRIVATE);
        final String lastUid = read.getString("uid", "");
        final String lastToken = read.getString("token", "");

        if (!lastUid.equals(""))
            OkHttpUtils.post()
                    .url("http://123.207.61.165/outside/get_profile")
                    .addParams("uid", lastUid)
                    .addParams("token", lastToken)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            SharedPreferences.Editor editor = getSharedPreferences("lock", MODE_PRIVATE).edit();
                            editor.putString("token", "");
                            editor.putString("uid", "");
                            editor.commit();
                            toast("登录已失效");
                        }

                        @Override
                        public void onResponse(String response, int id) {

                            JSONObject object = null;
                            JSONObject result = null;

                            try {

                                object = new JSONObject(response);

                                if (object.getString("resultCode").equals("00")) {

                                    result = object.getJSONObject("result");
                                    user.setUid(result.getString("uid"));
                                    user.setLogin(result.getString("login"));
                                    user.setMobNum(result.getString("mobNum"));
                                    user.setRole(result.getString("role"));
                                    user.setInviteCode(result.getString("inviteCode"));
                                    user.setToken(result.getString("token"));
                                    user.setStatus(true);


                                    SharedPreferences.Editor editor = getSharedPreferences("verifyUser", MODE_PRIVATE).edit();
                                    editor.putString("uid", user.getUid());
                                    editor.putString("token", user.getToken());
                                    editor.commit();
                                } else {
                                    toast(object.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.d("json-------------", response);


                        }
                    });

    }

    // 判断是否第一次启动来决定跳转不同的欢迎界面
    private void isFirstStart() {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

        if (isFirstStart) {

            startActivity(IntroActivity.class, null, false);

            SharedPreferences.Editor e = getPrefs.edit();
            e.putBoolean("firstStart", false);
            e.apply();
        } else {
            startActivity(WelcomeActivity.class, null, false);
        }
    }

    //按两次返回键退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
