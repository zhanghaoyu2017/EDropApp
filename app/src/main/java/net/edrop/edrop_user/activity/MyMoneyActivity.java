package net.edrop.edrop_user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

public class MyMoneyActivity extends AppCompatActivity {
    private LinearLayout Kongbai;
    private LinearLayout SaveMoney;
    private LinearLayout GetMoney;
    private LinearLayout One;
    private LinearLayout Two;
    private LinearLayout Three;
    private LinearLayout Four;
    private TextView tvMoney;
    private ImageView ivBack;
    private Button btnGoSaveMoney;
    private Button btnGoGetMoney;
    private RadioGroup rgGoSaveMoney = null;
    private RadioGroup rgGoGetMoney = null;

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
        Two.setVisibility(View.GONE);
        Three.setVisibility(View.GONE);
        Four.setVisibility(View.GONE);
    }

    private void setLinstener() {
        SaveMoney.setOnClickListener(new MyLinstener());
        GetMoney.setOnClickListener(new MyLinstener());
        ivBack.setOnClickListener(new MyLinstener());
        btnGoGetMoney.setOnClickListener(new MyLinstener());
        btnGoSaveMoney.setOnClickListener(new MyLinstener());
        Kongbai.setOnClickListener(new MyLinstener());
    }

    private void initView() {
        rgGoGetMoney=findViewById(R.id.rg_go_get_money);
        rgGoSaveMoney=findViewById(R.id.rg_go_save_money);
        SaveMoney=findViewById(R.id.ll_saveMoney);
        GetMoney=findViewById(R.id.ll_getMoney);
        tvMoney=findViewById(R.id.tv_money);
        ivBack=findViewById(R.id.iv_money_back);
        One=findViewById(R.id.ll_money_one);
        Two=findViewById(R.id.ll_money_two);
        Three=findViewById(R.id.ll_money_three);
        Four=findViewById(R.id.ll_money_four);
        btnGoGetMoney=findViewById(R.id.btn_go_get_money);
        btnGoSaveMoney=findViewById(R.id.btn_go_save_money);
        Kongbai=findViewById(R.id.ll_kongbai);
    }
    private class MyLinstener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_money_back:
                    finish();
                    break;
                case R.id.ll_saveMoney:
                    One.setVisibility(View.VISIBLE);
                    Two.setVisibility(View.VISIBLE);
                    Three.setVisibility(View.GONE);
                    Four.setVisibility(View.GONE);
                    break;
                case R.id.ll_getMoney:
                    Three.setVisibility(View.VISIBLE);
                    Four.setVisibility(View.VISIBLE);
                    One.setVisibility(View.GONE);
                    Two.setVisibility(View.GONE);
                    break;
                case R.id.btn_go_get_money:
                    int money=0;
                    switch (rgGoGetMoney.getCheckedRadioButtonId()) {
                        case R.id.rb_4:
                            money = 10;
                            break;
                        case R.id.rb_5:
                            money=20;
                            break;
                        case R.id.rb_6:
                            money=30;
                            break;
                    }
                    if (Double.valueOf(tvMoney.getText().toString())<money){
                        Toast.makeText(MyMoneyActivity.this,"余额不足，禁止提款",Toast.LENGTH_SHORT).show();
                    }else {
                        tvMoney.setText(Double.valueOf(tvMoney.getText().toString())-money+"");
                        Toast.makeText(MyMoneyActivity.this,"提款成功",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_go_save_money:
                    int money1=0;
                    switch (rgGoSaveMoney.getCheckedRadioButtonId()) {
                        case R.id.rb_1:
                            money1 = 10;
                            break;
                        case R.id.rb_2:
                            money1=20;
                            break;
                        case R.id.rb_3:
                            money1=30;
                            break;
                    }
                    tvMoney.setText(Double.valueOf(tvMoney.getText().toString())+money1+"");
                    Toast.makeText(MyMoneyActivity.this,"充值成功",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_kongbai:
                    One.setVisibility(View.GONE); // 隐藏
                    Two.setVisibility(View.GONE);
                    Three.setVisibility(View.GONE);
                    Four.setVisibility(View.GONE);
                    break;
            }
        }
    }
}
