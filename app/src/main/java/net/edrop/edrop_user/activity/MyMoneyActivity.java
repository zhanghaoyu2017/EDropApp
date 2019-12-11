package net.edrop.edrop_user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

public class MyMoneyActivity extends AppCompatActivity {
    private LinearLayout SaveMoney;
    private LinearLayout GetMoney;
    private LinearLayout One;
    private LinearLayout Two;
    private LinearLayout Three;
    private LinearLayout Four;
    private TextView tvMoney;
    private ImageView ivBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().transform(MyMoneyActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        initView();
        setLinstener();
        initData();
    }

    private void initData() {
        One.setVisibility(View.GONE); // 隐藏
        Two.setVisibility(View.GONE); // 隐藏
        Three.setVisibility(View.GONE); // 隐藏
        Four.setVisibility(View.GONE); // 隐藏
    }

    private void setLinstener() {
        SaveMoney.setOnClickListener(new MyLinstener());
        GetMoney.setOnClickListener(new MyLinstener());
        tvMoney.setOnClickListener(new MyLinstener());
        ivBack.setOnClickListener(new MyLinstener());
    }

    private void initView() {
        SaveMoney=findViewById(R.id.ll_saveMoney);
        GetMoney=findViewById(R.id.ll_getMoney);
        tvMoney=findViewById(R.id.tv_money);
        ivBack=findViewById(R.id.iv_money_back);
        One=findViewById(R.id.ll_money_one);
        Two=findViewById(R.id.ll_money_two);
        Three=findViewById(R.id.ll_money_three);
        Four=findViewById(R.id.ll_money_four);
    }
    private class MyLinstener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_money_back:
                    finish();
                    break;
                case R.id.ll_saveMoney:

                    break;
                case R.id.ll_getMoney:

                    break;
                case R.id.tv_money:

                    break;
            }
        }
    }
}
