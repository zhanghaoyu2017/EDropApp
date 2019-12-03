package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;
import net.edrop.edrop_user.utils.SystemTransUtil;

public class PersonalCenterManagerActivity extends AppCompatActivity {
    private TextView etUsername;
    private ImageView userImg;
    private ImageView gender;
    private ImageView personalBack;
    private Button cancelLogin;

    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(PersonalCenterManagerActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        initView();
        setListener();
    }

    private void setListener() {
        cancelLogin.setOnClickListener(new MyListener());
        personalBack.setOnClickListener(new MyListener());
    }

    private void initView() {
        cancelLogin=findViewById(R.id.btn_cancel_login);
        personalBack=findViewById(R.id.personal_back);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_cancel_login:
                    SharedPreferencesUtils sharedPreferences2 = new SharedPreferencesUtils(PersonalCenterManagerActivity.this,"loginInfo");
                    sharedPreferences2.removeValues("username");
                    sharedPreferences2.removeValues("password");
                    SharedPreferences.Editor editor2 = sharedPreferences2.getEditor();
                    editor2.putBoolean("isAuto",false);
                    editor2.commit();
                    getLoginExit();
                    Intent intent2 = new Intent(PersonalCenterManagerActivity.this, LoginActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    break;
                case R.id.personal_back:
                    finish();
                    break;

            }
        }
        /**
         * 退出环信登录
         */
        private void getLoginExit(){
            EMClient.getInstance().logout(true, new EMCallBack() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onProgress(int progress, String status) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onError(int code, String message) {
                    // TODO Auto-generated method stub

                }
            });
        }
    }
}
