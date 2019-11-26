package net.edrop.edrop_user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.edrop.edrop_user.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private Button btnLogin;
    private Button btnPhoneLogin;
    private EditText edUserName;
    private EditText edPwd;
    public boolean isSelected =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }
    private void initView() {
        usernameWrapper = findViewById(R.id.usernameWrapper);
        passwordWrapper =  findViewById(R.id.passwordWrapper);
        edPwd =  findViewById(R.id.password);
        edUserName = findViewById(R.id.username);
        btnPhoneLogin=findViewById(R.id.btn_phone_login);
        btnLogin =  findViewById(R.id.btn_login);
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
                    btnLogin.setBackgroundColor(Color.parseColor("#666666"));
                } else {
                    btnLogin.setBackgroundColor(Color.parseColor("#32CD32"));
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
        usernameWrapper.setOnClickListener(this);
        passwordWrapper.setOnClickListener(this);
        btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,PhoneLoginActivity.class);
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

    private class LoginTask extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {
            String username = edUserName.getText().toString();
            String password = edPwd.getText().toString();
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
