package net.edrop.edrop_user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.WaitLoadingView;

public class LoadingActivity extends AppCompatActivity {
    private WaitLoadingView loadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingView=findViewById(R.id.loading);
        loadingView.start();
    }
}
