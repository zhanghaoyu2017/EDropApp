package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.CacheClear;
import net.edrop.edrop_user.entity.MyDialog;

import java.io.File;


/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/10
 * Time: 20:30
 * TODO：清除应用缓存的主页面
 */
public class ClearCacheActivity extends AppCompatActivity {
    private final int UPDATE_TEXT = 326520;
    private ImageView imageView;
    private RelativeLayout layout;
    private TextView textView;
    private Intent intent;
    private String string;
    private MyDialog mMyDialog;
    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleancache_main);
        findViews();
        initData();
        initEvent();
        Toast.makeText(ClearCacheActivity.this, getCacheDir() + "", Toast.LENGTH_SHORT).show();
    }


    /**
     * Created: sifannnn
     * TODO：计算当前的缓存大小
     */
    private String getDataSize() {
        long fileSize = 0;
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();
        fileSize += CacheClear.getDirSize(filesDir);
        fileSize += CacheClear.getDirSize(cacheDir);
        String formatSize = CacheClear.getFormatSize(fileSize);
        return formatSize;
    }

    /**
     * Created: sifannnn
     * TODO：获取当前缓存所占的控件大小
     */
    private void initData() {
        string = getDataSize();
        textView.setText(string.equals("0.0Byte") ? "" : string);
    }


    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    string = (String) msg.obj;
                    textView.setText(string.equals("0.0Byte") ? "" : string);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * Created: sifannnn
     * TODO：找到控件对象
     */
    private void findViews() {
        imageView = findViewById(R.id.iv_cleancache_back);
        layout = findViewById(R.id.rl_clean_cache);
        textView = findViewById(R.id.tv_clean_cache);
    }

    /**
     * Created: sifannnn
     * TODO：绑定监听器
     */
    private void initEvent() {
        imageView.setOnClickListener(new MyListener());
        layout.setOnClickListener(new MyListener());
    }

    /**
     * Created: sifannnn
     * TODO：设置监听处理方法
     */
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_cleancache_back://返回
                    intent = new Intent(ClearCacheActivity.this, SettingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.rl_clean_cache://清除缓存
                    show();
                    break;
            }
        }
    }

    /**
     * Created: sifannnn
     * TODO：自定义Dialog并绑定监听事件
     */
    private void show() {
        View view = getLayoutInflater().inflate(R.layout.setting_dialog, null);
        mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setCancelable(true);
        mMyDialog.show();
        mMyDialog.getWindow().findViewById(R.id.tv_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyDialog.dismiss();
                Toast.makeText(ClearCacheActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
            }
        });
        mMyDialog.getWindow().findViewById(R.id.tv_dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheClear.cleanInternalCache(getApplicationContext());
                Message msg = new Message();
                msg.what = UPDATE_TEXT;
                msg.obj = "0.0Byte";
                handle.sendMessage(msg);
                mMyDialog.dismiss();
                Toast.makeText(ClearCacheActivity.this, "清除完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
