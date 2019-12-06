package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.Constant;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;
import net.edrop.edrop_user.utils.SystemTransUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.edrop.edrop_user.utils.Constant.REGISTER_FAIL;

public class ChangeViewActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvOk;
    private EditText etName;
    private OkHttpClient okHttpClient;
    private int userId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 66) {//用户名
                Toast.makeText(ChangeViewActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeViewActivity.this, FillPersonalInforActivity.class);
                intent.putExtra("nameInfo",etName.getText().toString());
                setResult(90,intent);
                finish();
            } else if (msg.what == 67) {//手机号
                Toast.makeText(ChangeViewActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeViewActivity.this, FillPersonalInforActivity.class);
                intent.putExtra("nameInfo",etName.getText().toString());
                setResult(90,intent);
                finish();
            }else if (msg.what ==REGISTER_FAIL){//失败
                Toast.makeText(ChangeViewActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(ChangeViewActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_view);

        SharedPreferencesUtils loginInfo = new SharedPreferencesUtils(ChangeViewActivity.this, "loginInfo");
        userId = loginInfo.getInt("userId");

        ivBack=findViewById(R.id.iv_change_back);
        tvOk=findViewById(R.id.tv_change_ok);
        etName=findViewById(R.id.et_change_name);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        final String state1 = intent.getStringExtra("state");
        etName.setText(name);
        okHttpClient = new OkHttpClient();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state1.equals("name")) {
                    submitMethodByName();
                }else if (state1.equals("phone")) {
                    submitMethodByPhone();
                }
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state1.equals("name")) {
                    submitMethodByName();
                }else if (state1.equals("phone")) {
                    submitMethodByPhone();
                }
            }
        });
    }

    private void submitMethodByPhone() {
        if (!(etName.getText().toString().length()==11)&&isNumber(etName.getText().toString())){
            Toast.makeText(ChangeViewActivity.this,"号码填写错误",Toast.LENGTH_SHORT).show();
        }else {
            FormBody formBody = new FormBody.Builder()
                    .add("id", userId+"")
                    .add("phone", etName.getText().toString())
                    .build();
            Request request = new Request.Builder()
                    .url(Constant.BASE_URL + "registerByUserName")
                    .post(formBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    int state = 0;
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        state = jsonObject.getInt("state");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if (state == REGISTER_SUCCESS) {
//                        Message msg = new Message();
//                        msg.obj = "更改成功";
//                        msg.what = 67 ;
//                        mHandler.sendMessage(msg);
//                    } else {
//                        Message msg = new Message();
//                        msg.obj = "该手机号已被绑定，请更换";
//                        msg.what = REGISTER_FAIL;
//                        mHandler.sendMessage(msg);
//                    }
                }
            });
        }
    }

    private boolean isNumber(String phone) {
        StringBuffer stringBuffer = new StringBuffer(phone);
        String arr = "0123456789";
        for (int i = 0; i < stringBuffer.length(); i++) {
            if (!arr.contains(String.valueOf(stringBuffer.charAt(i)))){
                return false;
            }
        }
        return true;
    }

    private void submitMethodByName() {
        if (etName.getText().toString().equals("")){
            Toast.makeText(ChangeViewActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else {
            FormBody formBody = new FormBody.Builder()
                    .add("id", userId+"")
                    .add("username", etName.getText().toString())
                    .build();
            Request request = new Request.Builder()
                    .url(Constant.BASE_URL + "registerByUserName")
                    .post(formBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    int state = 0;
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        state = jsonObject.getInt("state");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if (state == REGISTER_SUCCESS) {
//                        Message msg = new Message();
//                        msg.obj = "更改成功";
//                        msg.what = 66 ;
//                        mHandler.sendMessage(msg);
//                    } else {
//                        Message msg = new Message();
//                        msg.obj = "该用户名已被注册，请更换";
//                        msg.what = REGISTER_FAIL;
//                        mHandler.sendMessage(msg);
//                    }
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(ChangeViewActivity.this,FillPersonalInforActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
