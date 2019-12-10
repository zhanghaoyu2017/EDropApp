package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.Problems;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class GrabageQuestionsActivity extends AppCompatActivity {
    private ArrayList<Problems> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().transform(GrabageQuestionsActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabage_questions);
    }
    public void buttonOnclicked(View view) {
        lists.clear();
        for (int i = 0; i < 10; i++) {
            Problems bean = new Problems(i, 2, "我是第" + i);
            lists.add(bean);
        }
        Intent intent = new Intent(GrabageQuestionsActivity.this, Answer2Activity.class);
        intent.putExtra("lists", (Serializable) lists);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }
}
