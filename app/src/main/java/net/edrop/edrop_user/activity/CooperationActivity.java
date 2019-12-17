package net.edrop.edrop_user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/17
 * Time: 14:17
 * TODO：商务合作的Activity
 */
public class CooperationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        new SystemTransUtil().trans(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation);
    }
}
