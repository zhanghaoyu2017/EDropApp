package net.edrop.edrop_user.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.FragmentIndexAdapter;
import net.edrop.edrop_user.utils.MyViewPager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static net.edrop.edrop_user.utils.Constant.BASE_URL;

public class Main2Activity extends AppCompatActivity {
    //自定义tabhost属性
    private FragmentIndexAdapter mFragmentIndexAdapter;
    private List<Fragment> mFragments;
    private MyViewPager index_vp_fragment_list_top;
    private RelativeLayout index_rl_contain;
    private ImageView index_bottom_bar_home_image;
    private LinearLayout index_bottom_bar_home;
    private ImageView index_bottom_bar_dynamic_state_image;
    private LinearLayout index_bottom_bar_dynamic_state;
    private ImageView index_bottom_bar_integral_image;
    private LinearLayout index_bottom_bar_integral;
    private ImageView index_bottom_bar_me_image;
    private LinearLayout index_bottom_bar_me;
    private FrameLayout index_fl_bottom_bar;
    private ImageView index_bottom_bar_scan;
    private TextView navTitle;

    private ImageView nav_userImg;
    private DrawerLayout mDrawerLayout;
    private MainMenuLeftFragment leftMenuFragment;
    private ImageView imgSweep;
    private long waitTime = 2000;
    private long touchTime = 0;
    private final int REQUEST_CAMERA = 1;//请求相机权限的请求码
    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //初始化控件
        initViews();
        //初始化数据
        initData();
        //初始化控件的点击事件
        initEvent();
    }

    private void initViews() {
        navTitle = (TextView) findViewById(R.id.nav_title);
        index_vp_fragment_list_top = (MyViewPager) findViewById(R.id.index_vp_fragment_list_top);
        index_bottom_bar_home_image = (ImageView) findViewById(R.id.index_bottom_bar_home_image);
        index_bottom_bar_home = (LinearLayout) findViewById(R.id.index_bottom_bar_home);
        index_bottom_bar_dynamic_state_image = (ImageView) findViewById(R.id.index_bottom_bar_dynamic_state_image);
        index_bottom_bar_dynamic_state = (LinearLayout) findViewById(R.id.index_bottom_bar_dynamic_state);
        index_bottom_bar_integral_image = (ImageView) findViewById(R.id.index_bottom_bar_integral_image);
        index_bottom_bar_integral = (LinearLayout) findViewById(R.id.index_bottom_bar_integral);
        index_bottom_bar_me_image = (ImageView) findViewById(R.id.index_bottom_bar_me_image);
        index_bottom_bar_me = (LinearLayout) findViewById(R.id.index_bottom_bar_me);
        index_fl_bottom_bar = (FrameLayout) findViewById(R.id.index_fl_bottom_bar);
        index_bottom_bar_scan = (ImageView) findViewById(R.id.index_bottom_bar_scan);
        index_rl_contain = (RelativeLayout) findViewById(R.id.index_rl_contain);

        nav_userImg = (ImageView) findViewById(R.id.nav_user);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        //关闭手势滑动：DrawerLayout.LOCK_MODE_LOCKED_CLOSED（Gravity.LEFT：代表左侧的）
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        leftMenuFragment = (MainMenuLeftFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_leftmenu);
        imgSweep = findViewById(R.id.top_sweep);
        okHttpClient=new OkHttpClient();
    }

    private void initData() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(HomePageFragment.newInstance("主页"));
        mFragments.add(ServicePageFragment.newInstance("代扔"));
        mFragments.add(MsgPageFragment.newInstance("消息"));
        mFragments.add(CommunityPageFragment.newInstance("社区"));
        initIndexFragmentAdapter();
    }

    private void initIndexFragmentAdapter() {
        mFragmentIndexAdapter = new FragmentIndexAdapter(this.getSupportFragmentManager(), mFragments);
        index_vp_fragment_list_top.setAdapter(mFragmentIndexAdapter);
        index_bottom_bar_home.setSelected(true);
        index_vp_fragment_list_top.setCurrentItem(0);
        index_vp_fragment_list_top.setOffscreenPageLimit(3);
        index_vp_fragment_list_top.addOnPageChangeListener(new TabOnPageChangeListener());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                //让Toast的显示时间和等待时间相同
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            } else {
                //启动一个意图,回到桌面
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
                intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
                startActivity(intent);
//                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initEvent() {
        index_bottom_bar_home.setOnClickListener(new TabOnClickListener(0));
        index_bottom_bar_dynamic_state.setOnClickListener(new TabOnClickListener(1));
        index_bottom_bar_integral.setOnClickListener(new TabOnClickListener(2));
        index_bottom_bar_me.setOnClickListener(new TabOnClickListener(3));
        index_bottom_bar_scan.setOnClickListener(new TabOnClickListener(4));

        //用户图标的点击事件
        nav_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLeftMenu();
            }
        });
        imgSweep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // 动态申请权限");
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(Main2Activity.this,
                            android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    } else {
                        //说明已经获取到摄像头权限了
                        Log.i("Main2Activity", "已经获取了权限");
                        Intent intent1 = new Intent();
                        intent1.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent1, 100);
                    }
                } else {
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    Intent intent1 = new Intent();
                    intent1.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1, 100);

                }
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
     * Bottom_Bar的点击事件
     */
    public class TabOnClickListener implements View.OnClickListener {

        private int index = 0;

        public TabOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            if (index == 4) {
                // 跳转到Scan界面
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(Main2Activity.this,
                            android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    } else {
                        //说明已经获取到摄像头权限了
                        Log.i("Main2Activity", "已经获取了权限");
                        Intent intent1 = new Intent();
                        intent1.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent1, 100);
                    }
                } else {
                    //这个说明系统版本在6.0之下，不需要动态获取权限。
                    Intent intent1 = new Intent();
                    intent1.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1, 100);

                }
            } else {
                //选择某一页
                if (index == 0) {
                    navTitle.setText("主页");
                } else if (index == 1) {
                    navTitle.setText("代扔服务");
                } else if (index == 2) {
                    navTitle.setText("消息");
                }else {
                    navTitle.setText("社区动态");
                }
                index_vp_fragment_list_top.setCurrentItem(index, false);
            }
        }

    }

    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {
        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            resetTextView();
            switch (position) {
                case 0:
                    index_bottom_bar_home.setSelected(true);
                    break;
                case 1:
                    index_bottom_bar_dynamic_state.setSelected(true);
                    break;
                case 2:
                    index_bottom_bar_integral.setSelected(true);
                    break;
                case 3:
                    index_bottom_bar_me.setSelected(true);
                    break;
            }
        }
    }

    /**
     * 重置所有TextView的字体颜色
     */
    private void resetTextView() {
        index_bottom_bar_home.setSelected(false);
        index_bottom_bar_dynamic_state.setSelected(false);
        index_bottom_bar_integral.setSelected(false);
        index_bottom_bar_me.setSelected(false);
    }

    /**
     * 打开左侧的侧边栏
     */
    public void OpenLeftMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        //打开手势滑动：DrawerLayout.LOCK_MODE_UNLOCKED（Gravity.LEFT：代表左侧的）
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }

    //拍照成功回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ImageView imageView = findViewById(R.id.iv);
//        if (requestCode == 100 && resultCode == RESULT_OK){
        Log.e("拍照测试", "拍照成功");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");//获取拍取的照片

        //BitMap转成文件
       File f = convertBitmapToFile(bitmap);
       postFile(f);
    }
    private File convertBitmapToFile(Bitmap bitmap) {
        File f=null;

        try {
            // create a file to write bitmap data
            f = new File(Main2Activity.this.getCacheDir(), "portrait");
            f.createNewFile();

            // convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

        } catch (Exception e) {

        }
        return f;
    }
    //通过okhttp传图片给服务器
    private void postFile(File f) {
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/octet-stream"),f);
        Request.Builder builder = new Request.Builder();
        Request request = builder.post(requestBody).url( BASE_URL+ "indentify").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            Intent intent1 = new Intent();
            intent1.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            Log.e("权限获取成功", "onRequestPermissionsResult: ");
            startActivityForResult(intent1, 100);
        }
    }

    //设置一个接口，调试手势
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

}
