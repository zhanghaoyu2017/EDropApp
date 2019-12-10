package net.edrop.edrop_user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.CustomAdapter;
import net.edrop.edrop_user.entity.Competition;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.util.ArrayList;
import java.util.List;

public class Answer3Activity extends AppCompatActivity {
    private List<Competition> list2 = new ArrayList<>();
    private List listHisAnswer = new ArrayList<>();
    private CustomAdapter customAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().transform(Answer3Activity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer3);
        initdata();
        findViews();

    }

    private void initdata() {
        list2 = (ArrayList<Competition>) getIntent().getSerializableExtra("lists");
        listHisAnswer = (List) getIntent().getSerializableExtra("listHis");
    }

    private void findViews() {
        listView = findViewById(R.id.list_itemViewId);
        customAdapter = new CustomAdapter(list2, listHisAnswer, this, R.layout.listview_item);
        listView.setAdapter(customAdapter);
    }
}
