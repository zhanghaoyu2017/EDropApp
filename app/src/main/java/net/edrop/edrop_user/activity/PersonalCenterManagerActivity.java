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

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;

public class PersonalCenterManagerActivity extends AppCompatActivity {
    private Window window;
    private boolean lightStatusBar=false;
    private TextView etUsername;
    private ImageView userImg;
    private ImageView gender;
    private ImageView personalBack;
    private Button cancelLogin;

    protected void onCreate(Bundle savedInstanceState) {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            //将状态栏文字颜色改为黑色
            lightStatusBar=true;
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (lightStatusBar) {
                ui |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体的颜色为黑色
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体颜色为白色
            }
            decor.setSystemUiVisibility(ui);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
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
                    Intent intent2 = new Intent(PersonalCenterManagerActivity.this, LoginActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    break;
                case R.id.personal_back:
                    finish();
                    break;

            }
        }
    }
}
