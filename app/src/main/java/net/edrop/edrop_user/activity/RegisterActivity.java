package net.edrop.edrop_user.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import net.edrop.edrop_user.R;

public class RegisterActivity extends Activity {
    private TextView etName;
    private TextView etPsd;
    private TextView etPsd2;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        setLinstener();
    }

    private void setLinstener() {
        btnReg.setOnClickListener(new MyListener());
    }

    private void initData() {

    }

    private void initView() {
        getLoginExit();
        etName=findViewById(R.id.et_regName);
        etPsd=findViewById(R.id.et_regPsd);
        etPsd2=findViewById(R.id.et_regPsd2);
        btnReg=findViewById(R.id.btn_reg);
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_reg:
                    if (etPsd.getText().toString().equals(etPsd2.getText().toString())){
                        regUser();
                    }else {
                        Toast.makeText(RegisterActivity.this, "密码不一致，请检查", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    /**
     * 注册用户（同步需注意）
     */
    private void regUser(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    EMClient.getInstance().createAccount(etName.getText().toString().trim(), etPsd.getText().toString().trim());//同步方法
                    subscriber.onNext("注册成功,请登录");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    subscriber.onNext("注册失败错误码："+e.getErrorCode());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
    }

    /**
     * 退出登录
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
