package net.edrop.edrop_user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/4
 * Time: 15:19
 * TODO：设置关于EDrop的详细介绍
 */
public class AboutEDropActivity extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        new SystemTransUtil().trans(AboutEDropActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_edrop);

    }
}
