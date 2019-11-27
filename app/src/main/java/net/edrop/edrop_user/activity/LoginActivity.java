package net.edrop.edrop_user.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import net.edrop.edrop_user.QQbase.Config;
import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.QQUser;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private Button btnLogin;
    private Button btnPhoneLogin;
    private EditText edUserName;
    private EditText edPwd;
    public boolean isSelected =false;
    private ImageView qqLogin;
    //qq
    private Tencent mTencent;
    private UserInfo userInfo;
    private static BaseUiListener listener = null;
    private String QQ_uid;//qq_openid
    private SharedPreferencesUtils sharedPreferences;

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
        setContentView(R.layout.activity_login);
        sharedPreferences = new SharedPreferencesUtils(LoginActivity.this,"loginInfo");
        isAuto();
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        listener = new BaseUiListener();
        mTencent = Tencent.createInstance(Config.QQ_LOGIN_APP_ID, this.getApplicationContext());
        initView();
    }

    //判断是否是第一次登陆
    private void isAuto() {
        try {
            boolean isAuto = sharedPreferences.getBoolean("isAuto");
            if (isAuto){
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
    }

    private void initView() {
        usernameWrapper = findViewById(R.id.usernameWrapper);
        passwordWrapper =  findViewById(R.id.passwordWrapper);
        edPwd =  findViewById(R.id.password);
        edUserName = findViewById(R.id.username);
        btnPhoneLogin=findViewById(R.id.btn_phone_login);
        btnLogin =  findViewById(R.id.btn_login);
        qqLogin=findViewById(R.id.qq_name);
        usernameWrapper.setHint("请输入用户名");
        passwordWrapper.setHint("请输入密码");
        edPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validatePassword(edPwd.getText().toString())) {
                    passwordWrapper.setError("请输入6位数有效的密码哦!");
                    btnLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_login_gray_backgroung));
                } else {
                    btnLogin.setBackgroundDrawable( getResources().getDrawable(R.drawable.btn_login_backgroung));
                    isSelected=true;
                    passwordWrapper.setErrorEnabled(false);
                    hideKeyboard();
                }
            }
        });
        setListener();
    }

    private void setListener() {
        btnLogin.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        usernameWrapper.setOnClickListener(this);
        passwordWrapper.setOnClickListener(this);
        btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,PhoneLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (isSelected) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                }else {
                    Toast.makeText(LoginActivity.this, "请检查用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.qq_name:
                Log.e("qq","开始QQ登录..");
                if (!mTencent.isSessionValid()) {
                    //注销登录 mTencent.logout(this);
                    mTencent.login(LoginActivity.this, "all", listener);
                }
                break;
        }
    }

    /***
     * 隐藏键盘
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 自动弹出软 键盘
     */
    public static void showSoftkeyboard(final EditText etID, final Context mContext) {
        etID.post(new Runnable() {
            @Override
            public void run() {
                etID.requestFocus(etID.getText().length());
                InputMethodManager imm = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etID, 0);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
    }

    /***
     * 校验密码
     * @param password
     * @return
     */
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    private class BaseUiListener  implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.e("qq","授权:"+o.toString());
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(o.toString());
                initOpenidAndToken(jsonObject);
                updateUserInfo();
            }catch (org.json.JSONException e) {
                e.printStackTrace();
            }
        }

        private void updateUserInfo() {
            if (mTencent != null && mTencent.isSessionValid()) {
                IUiListener listener = new IUiListener() {
                    @Override
                    public void onError(UiError e) {
                    }
                    @Override
                    public void onComplete(final Object response) {
                        Message msg = new Message();
                        msg.obj = response;
                        Log.e("qq","................"+response.toString());
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    }
                    @Override
                    public void onCancel() {
                        Log.e("qq","登录取消..");
                    }
                };
                userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(listener);
            }
        }

        private void initOpenidAndToken(org.json.JSONObject jsonObject) {
            try {
                String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
                String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
                if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                        && !TextUtils.isEmpty(openId)) {
                    mTencent.setAccessToken(token, expires);
                    mTencent.setOpenId(openId);
                    QQ_uid = openId;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    com.alibaba.fastjson.JSONObject response = com.alibaba.fastjson.JSONObject.parseObject(String.valueOf(msg.obj));
                    Log.e("qq","UserInfo:"+ JSON.toJSONString(response));
                    QQUser user= com.alibaba.fastjson.JSONObject.parseObject(response.toJSONString(),QQUser.class);
                    if (user!=null) {
                        Log.e("qq","userInfo:昵称："+user.getNickname()+"  性别:"+user.getGender()+"  地址："+user.getProvince()+user.getCity());
                        Log.e("qq","头像路径："+user.getFigureurl_qq_2());
//                        Glide.with(QQLoginActivity.this).load(user.getFigureurl_qq_2()).into(ivHead);
                    }
                }
            }
        };

        @Override
        public void onError(UiError e) {
            Log.e("qq","onError:code:" + e.errorCode +
                    ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
            Log.e("qq","onCancel");
        }

    }

    /**
     * 用户名密码登录
     */
    private class LoginTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            String username = edUserName.getText().toString();
            String password = edPwd.getText().toString();

            //将数据存入数据库里
            SharedPreferences.Editor editor = sharedPreferences.getEditor();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putBoolean("isAuto",true);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            return null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent intent = new Intent();// 创建Intent对象
            intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
            intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
            startActivity(intent);// 将Intent传递给Activity
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
