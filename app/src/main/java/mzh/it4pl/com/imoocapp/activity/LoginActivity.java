package mzh.it4pl.com.imoocapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.activity.base.BaseActivity;
import mzh.it4pl.com.imoocapp.manager.UserManager;
import mzh.it4pl.com.imoocapp.module.user.User;
import mzh.it4pl.com.imoocapp.module.user.UserContent;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/8 17:10
 * 类描述:登陆Activity
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    /**
     * UI
     */
    private EditText mUserNameView;
    private EditText mPasswordView;
    private TextView mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mUserNameView = findViewById(R.id.associate_email_input);
        mPasswordView = findViewById(R.id.login_input_password);
        mLoginView = findViewById(R.id.login_button);
        mLoginView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                login();
                break;
            default:
                break;
        }
    }

    //发送登陆请求
    private void login() {
        String userName = mUserNameView.getText().toString().trim();
        String passWord = mPasswordView.getText().toString().trim();

        //拿到用户信息   模拟登录成功
        User user = new User();
        UserContent userContent = new UserContent();
        userContent.mobile = "908391541@qq.com";
        userContent.name = "代号卧底";
        userContent.photoUrl = "http://e.hiphotos.baidu.com/image/pic/item/f7246b600c338744e7762fb6580fd9f9d62aa04c.jpg";
        userContent.platform = "服务器";
        userContent.userId = "1";
        userContent.tick = "神，生于人心，死于人性";
        user.data = userContent;

        //通过UserManager来管理用户信息
        UserManager.getUserManager().setUser(user);

        //发送登录广播
        sendLoginBroadcast();

        /*RequestCenter.login(userName, passWord, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                //拿到用户信息
                User user = (User) responseObj;
                //通过UserManager来管理用户信息
                UserManager.getUserManager().setUser(user);

                //发送登录广播
                sendLoginBroadcast();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });*/
    }

    /**
     * 自定义登录广播
     */
    public static final String LOGIN_ACTION = "com.imooc.action.LOGIN_ACTION";

    //发送我们的登录广播
    public void sendLoginBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(
                LOGIN_ACTION));

        finish();
    }
}
