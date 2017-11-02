package com.lipeng.newapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lipeng.myapplication.R;
import com.lipeng.newapp.database.UserDatabase;
import com.lipeng.newapp.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lipeng-ds3 on 2017/10/27.
 * email:  lipeng-ds3@gomeplus.com
 * App登录界面，主要实现账号密码的验证以及之后的跳转
 */

public class LoginPageActivity extends AppCompatActivity implements View.OnClickListener{
    private UserDatabase mDatabase;
    private User mUser;
    private String mAccount;
    private String mPassword;
    private String mAccountFromEditText;
    private String mPasswordFromEditText;

    //控件
    @BindView(R.id.edit_account) EditText mEditAccount;
    @BindView(R.id.edit_password) EditText mEditPassword;
    @BindView(R.id.btn_login)  Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);
        mBtnLogin.setOnClickListener(this);
        getUserFromDB();
    }

    private void getUserFromDB(){//从数据库读取账号密码
        mDatabase = UserDatabase.getInstance(this);
        mUser = mDatabase.getUser().get(0);
        mAccount = mUser.getAccount();
        mPassword = mUser.getPassword();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    private void login(){//对登录操作进行验证
        //获取EditText的账号和密码
        mAccountFromEditText = mEditAccount.getText().toString();
        mPasswordFromEditText = mEditPassword.getText().toString();
        if ((TextUtils.isEmpty(mAccountFromEditText))
                || (TextUtils.isEmpty(mPasswordFromEditText))){//检查EditText内容是否为空
            Toast.makeText(this, "mAccount or mPassword must not be null!",Toast.LENGTH_SHORT ).show();
        }else if (mAccount.equals(mAccountFromEditText) && mPassword.equals(mPasswordFromEditText)){//账号密码都正确则跳转
            Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
            Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "mAccount or mPassword is invalid!",Toast.LENGTH_SHORT).show();
        }
    }
}
