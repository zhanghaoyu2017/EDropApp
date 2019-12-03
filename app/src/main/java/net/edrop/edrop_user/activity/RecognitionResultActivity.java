package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.RecognitionAdapter;
import net.edrop.edrop_user.adapter.ServiceAdapter;
import net.edrop.edrop_user.entity.Recognition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecognitionResultActivity extends Activity {
    private ImageView imgPhoto;
    private List<Recognition> dataSource;
    private ListView listView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition_result);
//        initData();
        initViews();
//
//        setMyAdapter();
        Intent intent = getIntent();
        byte buf[] = intent.getByteArrayExtra("photo_bmp");
        Bitmap photo_bmp = BitmapFactory.decodeByteArray(buf, 0, buf.length);
        imgPhoto.setImageBitmap(photo_bmp);
        String json = intent.getStringExtra("json");
        textView.setText(json);
    }

    /*设置adapter*/
    private void setMyAdapter() {
        RecognitionAdapter adapter = new RecognitionAdapter(
                RecognitionResultActivity.this,
                dataSource,
                R.layout.activity_recognition_result_item
        );
        listView.setAdapter(adapter);
    }


    /*初始化数据设置adapter*/
    private void initData() {
        String[] arr = {"废鼠标", "废鼠标垫", "塑料瓶", "玻璃杯"};
        dataSource = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            Recognition recognition = new Recognition();
            recognition.setText(arr[i]);
            recognition.setImgId(R.drawable.magnifier);
            dataSource.add(recognition);
        }
    }

    private void initViews() {
        imgPhoto = findViewById(R.id.iv_photo);
//        listView = findViewById(R.id.lv_recognition);
        textView =findViewById(R.id.tv_json);

    }

}
