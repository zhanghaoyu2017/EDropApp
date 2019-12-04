package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import net.edrop.edrop_user.R;

public class TestPhoneNumActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testphonenum);

        Intent intent = new Intent(TestPhoneNumActivity.this,Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
