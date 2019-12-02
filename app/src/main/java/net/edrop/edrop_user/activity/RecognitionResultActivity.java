package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import net.edrop.edrop_user.R;

import java.io.File;

public class RecognitionResultActivity extends Activity {
    private ImageView imgPhoto;
//    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition_result);
        initViews();
        Intent intent = getIntent();
        byte buf[] = intent.getByteArrayExtra("photo_bmp");
        Bitmap  photo_bmp = BitmapFactory.decodeByteArray(buf, 0, buf.length);
        imgPhoto.setImageBitmap(photo_bmp);
        String json = intent.getStringExtra("json");
//        textView.setText(json);
    }
    private void initViews() {
        imgPhoto = findViewById(R.id.iv_photo);
//        textView =findViewById(R.id.tv_json);

    }

}
