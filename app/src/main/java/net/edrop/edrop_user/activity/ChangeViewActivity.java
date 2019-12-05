package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;

public class ChangeViewActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvOk;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_view);
        ivBack=findViewById(R.id.iv_change_back);
        tvOk=findViewById(R.id.tv_change_ok);
        etName=findViewById(R.id.et_change_name);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        etName.setText(name);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMethod();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMethod();
            }
        });
    }

    private void submitMethod() {
        if (etName.getText().toString().equals("")){
            Toast.makeText(ChangeViewActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(ChangeViewActivity.this, FillPersonalInforActivity.class);
            intent.putExtra("nameInfo",etName.getText().toString());
            setResult(90,intent);
            finish();
        }
    }
}
