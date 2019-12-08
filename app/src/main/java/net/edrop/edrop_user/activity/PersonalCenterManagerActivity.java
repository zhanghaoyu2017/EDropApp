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
    private TextView tvUsername;
    private TextView tvPhone;
    private TextView tvAddress;
    private ImageView userImg;
    private ImageView ivGender;
    private ImageView personalBack;
    private Button cancelLogin;
    private TextView tvEditInfo;

    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(PersonalCenterManagerActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        initView();
        setListener();
        initData();
    }

    private void initData() {
        SharedPreferencesUtils sharedPreferences = new SharedPreferencesUtils(PersonalCenterManagerActivity.this,"loginInfo");
        tvUsername.setText(sharedPreferences.getString("username",""));
        tvPhone.setText(sharedPreferences.getString("phone",""));
        tvAddress.setText(sharedPreferences.getString("address",""));
        String gender = sharedPreferences.getString("gender", "");
        switch (gender){
            case "boy":
                ivGender.setImageDrawable(getResources().getDrawable(R.drawable.gender_boy));
                break;
            case "girl":
                ivGender.setImageDrawable(getResources().getDrawable(R.drawable.gender_girl));
                break;
            default:
                break;
        }
    }

    private void setListener() {
        cancelLogin.setOnClickListener(new MyListener());
        personalBack.setOnClickListener(new MyListener());
        tvEditInfo.setOnClickListener(new MyListener());
    }

    private void initView() {
        ivGender=findViewById(R.id.change_gender);
        tvAddress=findViewById(R.id.change_address);
        tvPhone=findViewById(R.id.change_phone);
        tvUsername=findViewById(R.id.change_username);
        tvEditInfo=findViewById(R.id.tv_edit_info);
        cancelLogin=findViewById(R.id.btn_cancel_login);
        personalBack=findViewById(R.id.personal_back);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_cancel_login:
                    SharedPreferencesUtils sharedPreferences = new SharedPreferencesUtils(PersonalCenterManagerActivity.this,"loginInfo");
                    sharedPreferences.removeValues("username");
                    sharedPreferences.removeValues("password");
                    SharedPreferences.Editor editor2 = sharedPreferences.getEditor();
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
                case R.id.tv_edit_info:
                    Intent intent = new Intent(PersonalCenterManagerActivity.this, FillPersonalInforActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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
