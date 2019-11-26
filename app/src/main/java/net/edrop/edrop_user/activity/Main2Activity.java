package net.edrop.edrop_user.activity;

import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import net.edrop.edrop_user.R;

public class Main2Activity extends AppCompatActivity {
    private FragmentTabHost tabHost = null;
    private String [] tabStrArr = {"主页", "服务", "消息","社区"};
    private Class[] fragmentArr = {HomePageFragment.class, ServicePageFragment.class, MsgPageFragment.class,CommunityPageFragment.class};
    private int [] imageArr = {R.drawable.tab_home_channel, R.drawable.tab_service_channel, R.drawable.tab_msg_channel,R.drawable.tab_community_channel};
    private ImageView nav_userImg;
    private DrawerLayout mDrawerLayout;
    private MainMenuLeftFragment leftMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initTabHost();
        //初始化控件
        initViews();
        //初始化数据
        initData();
        //初始化控件的点击事件
        initEvent();
    }

    // 完成FragmentTabHost的初始化
    private void initTabHost() {
        // 1 获取获取FragmentTabHost控件对象控件对象
        tabHost = findViewById(android.R.id.tabhost);
        // 2 初始化获取FragmentTabHost控件对象
        tabHost.setup(this,
                getSupportFragmentManager(), //全局的FragmentManager，用来管理Fragment
                android.R.id.tabcontent);

        // 3 创建TabSpec并添加到FragmentTabHost中
        for(int i=0; i<fragmentArr.length; ++i) {
            TabHost.TabSpec tabSpec
                    = tabHost.newTabSpec(tabStrArr[i])
                    .setIndicator(getTabSpecView(i));
            tabHost.addTab(tabSpec, fragmentArr[i], null);
        }
    }

    // 创建TabSpec显示的View
    private View getTabSpecView(int i) {
        View view = getLayoutInflater().inflate(R.layout.item_main_layout, null);
        ImageView imageView = view.findViewById(R.id.iv_image);
        imageView.setImageResource(imageArr[i]);

        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(tabStrArr[i]);
        return view;
    }

    private void initViews() {
        nav_userImg = (ImageView) findViewById(R.id.nav_user);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        //关闭手势滑动：DrawerLayout.LOCK_MODE_LOCKED_CLOSED（Gravity.LEFT：代表左侧的）
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        leftMenuFragment = (MainMenuLeftFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_leftmenu);
    }

    private void initData() {

    }

    private void initEvent() {
        //用户图标的点击事件
        nav_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLeftMenu();
            }
        });

        //侧边栏的事件监听
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置-0），STATE_DRAGGING（拖拽-1），STATE_SETTLING（固定-2）中之一。
             */
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * slideOffset 表示 滑动的幅度(0-1)
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.e("滑动的距离", "slideOffset=" + slideOffset);//0.0 -- 0.56 -- 1.0

                View mContent = mDrawerLayout.getChildAt(0);//内容区域view
                View mMenu = drawerView;

                float scale = 1 - slideOffset;

                if (drawerView.getTag().equals("LEFT")) {//左侧的侧边栏动画效果
                    //设置左侧区域的透明度0.6f + 0.4f * (0.0 ... 1.0)【也就是打开的时候透明度从0.6f ... 1.0f，关闭的时候反之】
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * slideOffset);
                    //移动内容区域：左侧侧边栏宽度 * (0.0 ... 1.0)【也就是打开的时候，内容区域移动从0 ... 左侧侧边栏宽度】
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * slideOffset);
                    mContent.invalidate();//重绘view

                } else {//右侧的侧边栏动画效果
                    //移动内容区域：-右侧侧边栏宽度 * (0.0 ... 1.0)【也就是打开的时候，内容区域移动从-0 ... -左侧侧边栏宽度】
                    ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                    mContent.invalidate();
                }

            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView.getTag().equals("LEFT")) {//如果感觉显示有延迟的话，可以放到nav_userImg的点击事件监听中执行
                    leftMenuFragment.setDefaultDatas();//打开的时候初始化默认数据【比如：请求网络，获取数据】
                }
            }

            /**
             * 当一个抽屉被完全关闭的时候被调用
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                //关闭手势滑动：DrawerLayout.LOCK_MODE_LOCKED_CLOSED（Gravity.LEFT：代表左侧的）
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
            }
        });
    }

    /**
     * 打开左侧的侧边栏
     */
    public void OpenLeftMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        //打开手势滑动：DrawerLayout.LOCK_MODE_UNLOCKED（Gravity.LEFT：代表左侧的）
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }
}
