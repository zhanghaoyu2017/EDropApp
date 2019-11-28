package net.edrop.edrop_user.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import net.edrop.edrop_user.MyApplication;
import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.ImageInfo;
import net.edrop.edrop_user.utils.ImageCarousel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements TabHost.TabContentFactory, GestureDetector.OnGestureListener {
    private Activity activity;
    private View view;
    private ImageView nav_userImg;
    //搜索框控件
    private SearchView searchView;
    private AutoCompleteTextView mAutoCompleteTextView;//搜索输入框
    private ImageView mDeleteButton;//搜索框中的删除按钮
    // 图片轮播控件
    private ViewPager mViewPager;
    private TextView mTvPagerTitle;
    private LinearLayout mLineLayoutDot;
    private ImageCarousel imageCarousel;
    private List<View> dots;//小点
    // 图片数据，包括图片标题、图片链接、数据、点击要打开的网站（点击打开的网页或一些提示指令）
    private List<ImageInfo> imageInfoList;
    private CustomClickListener customClickListener = new CustomClickListener();
    //定义手势检测器实例
    private GestureDetector detector;

    private Main2Activity main2Activity;
    private LinearLayout recyclable;
    private LinearLayout hazardous;
    private LinearLayout housefood;
    private LinearLayout residoual;
    private static final String SECTION_STRING = "fragment_string";

    public static HomePageFragment newInstance(String sectionNumber) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(SECTION_STRING, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        //创建手势检测器
        detector = new GestureDetector(getActivity(),this);
        Main2Activity.MyOnTouchListener myOnTouchListener = new Main2Activity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                boolean result = detector.onTouchEvent(ev);
                return result;
            }
        };
        main2Activity=(Main2Activity)getActivity();
        ((Main2Activity) getActivity()).registerMyOnTouchListener(myOnTouchListener);

        initView();
        initData();
        initEvent();
        setListener();
        imageStart();
        return view;
    }

    @Nullable
    @Override
    //在Fragment中直接使用getContext方法容易产生空指针异常，覆写getContext方法
    public Context getContext() {
        activity = getActivity();
        if (activity == null) {
            return MyApplication.getInstance();
        }
        return activity;
    }

    private void initView(){
        nav_userImg=view.findViewById(R.id.nav_user);
        //搜索框
        searchView=view.findViewById(R.id.view_search);
        mAutoCompleteTextView=searchView.findViewById(R.id.search_src_text);
        mDeleteButton=searchView.findViewById(R.id.search_close_btn);
        //轮播图
        mViewPager =view.findViewById(R.id.viewPager);
        mTvPagerTitle = view.findViewById(R.id.tv_pager_title);
        mLineLayoutDot = view.findViewById(R.id.lineLayout_dot);
        recyclable=view.findViewById(R.id.ll_rubbish_recyclable);
        hazardous=view.findViewById(R.id.ll_rubbish_hazardous);
        housefood=view.findViewById(R.id.ll_rubbish_housefood);
        residoual =view.findViewById(R.id.ll_rubbish_residoual);
        Fresco.initialize(getContext());
    }

    private void initData(){
        searchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        //1:回车
        //2:前往
        //3:搜索
        //4:发送
        //5:下一項
        //6:完成
        searchView.setImeOptions(2);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
//        mSearchView.setInputType(1);//设置输入类型
//        mSearchView.setMaxWidth(200);//设置最大宽度
        searchView.setQueryHint("查找分类");//设置查询提示字符串
//        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
        //设置SearchView下划线透明
        setUnderLinetransparent(searchView);
    }

    private void setListener(){
        // 设置搜索文本监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("=query=",query);
                searchView.setQuery("", false);
                searchView.clearFocus();//收起键盘
                searchView.onActionViewCollapsed();//收起SearchView视图
                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("=====newText=",newText);
                return false;
            }

        });
        //可回收垃圾介绍
        recyclable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RubbishDesc01Activity.class);
                startActivity(intent);
            }
        });
        //有害垃圾介绍
        hazardous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RubbishDesc03Activity.class);
                startActivity(intent);
            }
        });
        //湿垃圾介绍
        housefood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RubbishDesc04Activity.class);
                startActivity(intent);
            }
        });
        //干垃圾介绍
        residoual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RubbishDesc02Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View createTabContent(String tag) {
        return null;
    }

    private class CustomClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }

    /**设置SearchView下划线透明**/
    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*---------------------------------------------设置图片轮播-------------------------------------------------*/
    //初始化事件
    private void initEvent() {
        imageInfoList = new ArrayList<>();
        imageInfoList.add(new ImageInfo(1, "图片1，啦啦啦啦", "", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574784053918&di=f58933482d84cefcfc063e8833cdb2d6&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fa%2F5879b4884d8e1.jpg", "http://www.cnblogs.com/luhuan/"));
        imageInfoList.add(new ImageInfo(1, "图片2，啦啦啦啦", "", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574784053918&di=88033b9a50f2bb5d763517435d047223&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F3%2F59c86223661fc.jpg", "http://www.cnblogs.com/luhuan/"));
        imageInfoList.add(new ImageInfo(1, "图片3，啦啦啦啦", "", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574784053917&di=7f551a19ac7867bcc5a9fe0749547f7f&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F3%2F5423b8a480e35.jpg", "http://www.cnblogs.com/luhuan/"));
        imageInfoList.add(new ImageInfo(1, "图片4，啦啦啦啦", "仅展示", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574784053917&di=3af840e99adc4a6be5c74c6a22e6d298&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2%2F58fec381a6036.jpg", ""));
        imageInfoList.add(new ImageInfo(1, "图片5，啦啦啦啦", "仅展示", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574784053917&di=506193046c67fc511a7bfea446b783a0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0166e45c396cf8a8012090db10f36f.jpg", ""));
    }

    private void imageStart() {
        int[] imgaeIds = new int[]{R.id.pager_image1, R.id.pager_image2, R.id.pager_image3, R.id.pager_image4, R.id.pager_image5, R.id.pager_image6, R.id.pager_image7, R.id.pager_image8};
        String[] titles = new String[imageInfoList.size()];
        List<SimpleDraweeView> simpleDraweeViewList = new ArrayList<>();

        for (int i = 0; i < imageInfoList.size(); i++) {
            titles[i] = imageInfoList.get(i).getTitle();
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
            simpleDraweeView.setAspectRatio(1.78f);
            // 设置一张默认的图片
            GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(this.getResources())
                    .setPlaceholderImage(ContextCompat.getDrawable(getContext(),
                            R.drawable.defult),
                            ScalingUtils.ScaleType.CENTER_CROP)
                    .build();
            simpleDraweeView.setHierarchy(hierarchy);
            simpleDraweeView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

            //加载高分辨率图片;
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageInfoList.get(i).getImage()))
                    .setResizeOptions(new ResizeOptions(1280, 720))
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                    .setLowResImageRequest(ImageRequest.fromUri(Uri.parse(listItemBean.test_pic_low))) //在加载高分辨率图片之前加载低分辨率图片
                    .setImageRequest(imageRequest)
                    .setOldController(simpleDraweeView.getController())
                    .build();
            simpleDraweeView.setController(controller);

            simpleDraweeView.setId(imgaeIds[i]);//给view设置id
            simpleDraweeView.setTag(imageInfoList.get(i));
            simpleDraweeView.setOnClickListener(new CustomClickListener());
            titles[i] = imageInfoList.get(i).getTitle();
            simpleDraweeViewList.add(simpleDraweeView);

        }

        dots = addDots(mLineLayoutDot,
                fromResToDrawable(getContext(), R.drawable.ic_dot_focused),
                simpleDraweeViewList.size());
        imageCarousel = new ImageCarousel(getContext(), mViewPager, mTvPagerTitle, dots, 5000);
        imageCarousel.init(simpleDraweeViewList, titles).startAutoPlay();
        imageCarousel.start();
    }

    /**
     * 动态添加一个点
     *
     * @param linearLayout 添加到LinearLayout布局
     * @param backgount    设置
     * @return 小点的Id
     */
    private int addDot(final LinearLayout linearLayout, Drawable backgount) {
        final View dot = new View(getContext());
        LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.width = 16;
        dotParams.height = 16;
        dotParams.setMargins(4, 0, 4, 0);
        dot.setLayoutParams(dotParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dot.setBackground(backgount);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dot.setId(View.generateViewId());
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayout.addView(dot);
            }
        });

        return dot.getId();
    }

    /**
     * 资源图片转Drawable
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return 返回Drawable图像
     */
    public static Drawable fromResToDrawable(Context context, int resId) {
        return ContextCompat.getDrawable(context, resId);
        //return context.getResources().getDrawable(resId);
    }

    /**
     * 添加多个轮播小点到横向线性布局
     *
     * @param linearLayout 线性横向布局
     * @param backgount    小点资源图标
     * @param number       数量
     * @return 返回小点View集合
     */
    private List<View> addDots(final LinearLayout linearLayout, Drawable backgount, int number) {
        List<View> dots = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int dotId = addDot(linearLayout, backgount);
            dots.add(view.findViewById(dotId));
        }
        return dots;
    }

    /*----------------------------手势滑动-------------------------------------*/
    public void flingLeft() {//自定义方法：处理向左滑动事件
        main2Activity.OpenLeftMenu();
    }

    public void flingRight() {//自定义方法：处理向右滑动事件

    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (e1.getX() - e2.getX() < -300) {
                flingLeft();
                return true;
            } else if (e1.getX() - e2.getX() > 300) {
                flingRight();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) { }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) { }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

}
