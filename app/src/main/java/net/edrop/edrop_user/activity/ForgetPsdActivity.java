package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mob.MobSDK;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPsdActivity extends AppCompatActivity {
    private String APPKEY = "2d3bde91c4a25";
    private String APPSECRET = "0f4a5150f9707ef7423d60cf7aaf3ae8";
    private int i = 60;//倒计时显示   可以手动更改
    private EditText etPhone;
    private EditText etPhoneCode;
    private EditText etNewPsd;
    private EditText etNewPsd2;
    private Button btnOK;
    private Button btnGetCode;
    private ImageView ivBack;
    private LinearLayout llpsd1;
    private LinearLayout llpsd2;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btnGetCode.setText("重新发送(" + i + ")");

            } else if (msg.what == -8) {
                btnGetCode.setText("获取验证码");
                btnGetCode.setBackgroundResource(R.drawable.btn_login_green_background);
                btnGetCode.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.e("test", "1");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(),"提交验证码成功",Toast.LENGTH_SHORT).show();
                        Log.e("test", "2");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码，请及时接收登录",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPsdActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(ForgetPsdActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        llpsd1.setVisibility(View.GONE); // 隐藏
        llpsd2.setVisibility(View.GONE); // 隐藏
    }

    private void setListener() {
        btnOK.setOnClickListener(new MyListener());
        btnGetCode.setOnClickListener(new MyListener());
        ivBack.setOnClickListener(new MyListener());
    }

    private void initView() {
        btnGetCode=findViewById(R.id.btn_forget_phone_code);
        llpsd1=findViewById(R.id.ll_psd1);
        llpsd2=findViewById(R.id.ll_psd2);
        etPhone=findViewById(R.id.et_forget_phone);
        etPhoneCode=findViewById(R.id.et_forget_code);
        etNewPsd=findViewById(R.id.et_forget_newPsd);
        etNewPsd2=findViewById(R.id.et_forget_newPsd2);
        ivBack=findViewById(R.id.iv_psd_back);
        btnOK=findViewById(R.id.btn_forget_ok);
        // 启动短信验证sdk
        MobSDK.init(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }
    private class MyListener implements View.OnClickListener{
        String phoneNums = "";
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_forget_phone_code:
                    llpsd1.setVisibility(View.VISIBLE); // 显示
                    llpsd2.setVisibility(View.VISIBLE); // 显示
                    // 1. 判断手机号是不是11位并且看格式是否合理
                    phoneNums=etPhone.getText().toString().trim();
                    if (!judgePhoneNums(phoneNums)) {
                        return;
                    } // 2. 通过sdk发送短信验证
                    SMSSDK.getVerificationCode("86", phoneNums);

                    // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                    btnGetCode.setBackgroundResource(R.drawable.btn_login_gray_background);
                    btnGetCode.setClickable(false);
                    btnGetCode.setText("重新发送(" + i + ")");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-9);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-8);
                        }
                    }).start();
                    break;
                case R.id.btn_forget_ok:
                    SMSSDK.submitVerificationCode("86", phoneNums, etPhoneCode.getText().toString().trim());
                    break;
                case R.id.iv_psd_back:
                    finish();
                    break;
            }
        }
    }
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }
    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        //反注册回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
