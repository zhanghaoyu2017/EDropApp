package net.edrop.edrop_user.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.MyPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager vpGuiding;
    private MyPagerAdapter myPagerAdapter;
    private ArrayList<View> viewArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpGuiding = findViewById(R.id.main_vpGuiding);

        viewPagerNormalLookLike();
    }

    //默认效果的
    public void viewPagerNormalLookLike() {
        //List集合赋值，用于给适配器传参数
        viewArrayList = new ArrayList<View>();
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        viewArrayList.add(layoutInflater.inflate(R.layout.item_viewpaper_one, null, false));
        viewArrayList.add(layoutInflater.inflate(R.layout.item_viewpaper_two, null, false));
        viewArrayList.add(layoutInflater.inflate(R.layout.item_viewpaper_three, null, false));

        //适配器赋值
        myPagerAdapter = new MyPagerAdapter(viewArrayList,this);
        //绑定数据适配器
        vpGuiding.setAdapter(myPagerAdapter);
    }

}
