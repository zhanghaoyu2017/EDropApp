package net.edrop.edrop_user.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

public class RubbishDesc02Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(RubbishDesc02Activity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish_desc02);
    }
}
