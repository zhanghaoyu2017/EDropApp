package net.edrop.edrop_user.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.edrop.edrop_user.R;

public class PersonalCenterManagerActivity extends AppCompatActivity {

    private TextView et_username;
    private ImageView tv_gender;
    private ImageView iv_head;
    private int gender = 1;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        et_username=findViewById(R.id.btn_changeUsername);
        tv_gender = findViewById(R.id.btn_changegender);
        iv_head = findViewById(R.id.btn_ChangeHeadImg);





    }


    public void onPersonalClick(View view) {
        switch (view.getId()){
            case R.id.btn_changegender:
                //直接改变
                gender = 2;

                break;

            case R.id.btn_ChangeHeadImg:
                //从相册选择图片上传

                break;

            case R.id.btn_changeUsername:
                //更改姓名

                break;
            case R.id.personal_back:
                //返回

                break;

        }
    }
}
