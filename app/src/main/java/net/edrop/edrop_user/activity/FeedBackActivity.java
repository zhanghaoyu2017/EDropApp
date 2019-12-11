package net.edrop.edrop_user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/9
 * Time: 14:24
 * TODO：反馈的页面
 */
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener,
        EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    // EmojiconTextView继承自AppCompatTextView，而AppCompatTextView继承自TextView
    private EmojiconEditText mEditEmojicon;
    private EditText etQQ;
    private EditText etPhone;
    private TextView textView;
    private ImageView imageView;
    private Button button;
    private boolean hasClick;
    private String string;
    private String qq;
    private String phone;
    private Intent intent;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        new SystemTransUtil().transform(FeedBackActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_main);
        findViews();
        setLisner();
        setEmojiconFragment(false);
        intent = getIntent();
        if (intent != null) {
            string = intent.getStringExtra("msg");
            textView.setText(string);
        }
    }

    //设置监听器
    private void setLisner() {
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);
        mEditEmojicon.setOnClickListener(this);

    }

    private void findViews() {
        mEditEmojicon = findViewById(R.id.et_feedback);
        etPhone = findViewById(R.id.et_feedback_phone);
        textView = findViewById(R.id.tv_feedback);
        etQQ = findViewById(R.id.et_feedback_qq);
        button = findViewById(R.id.btn_feedback);
        imageView = findViewById(R.id.im_feedback_smile);
        linearLayout = findViewById(R.id.ll_feedback);
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选项选择
            case R.id.ll_feedback:
                intent = new Intent(FeedBackActivity.this, FeedBackOption.class);
                startActivity(intent);
                break;
            case R.id.im_feedback_smile: // 表情按钮
                if (hasClick) {
                    findViewById(R.id.emojicons).setVisibility(View.GONE);
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                    findViewById(R.id.emojicons).setVisibility(View.VISIBLE);
                }
                hasClick = !hasClick;
                break;
            case R.id.et_feedback: // 输入框
                findViewById(R.id.emojicons).setVisibility(View.GONE);
                hasClick = !hasClick;
                break;
            case R.id.btn_feedback:
                Toast.makeText(this, "发送请求！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.im_feedback_index:
                Toast.makeText(this, "图片功能待完善！！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //发送消息的异步类
    private class sendMessage extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //获取控件值
            qq = etQQ.getText().toString();
            phone = etPhone.getText().toString();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL((String) objects[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置Http请求方式 GET,POST,DELETE
                //默认get请求
                connection.setRequestMethod("POST");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }

    @Override
    public void onBackPressed() {
        if (hasClick) {
            findViewById(R.id.emojicons).setVisibility(View.GONE);
            hasClick = !hasClick;
        } else {
            super.onBackPressed();
        }
    }
}
