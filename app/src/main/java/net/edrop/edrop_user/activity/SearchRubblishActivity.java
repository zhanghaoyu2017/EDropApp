package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.HotSearchAdapter;
import net.edrop.edrop_user.adapter.SearchAdapter;
import net.edrop.edrop_user.entity.HotItem;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.edrop.edrop_user.utils.Constant.BASE_URL;

public class SearchRubblishActivity extends AppCompatActivity {
    private Window window;
    private boolean lightStatusBar=false;
    //搜索框控件
    private SearchView searchView;
    private AutoCompleteTextView mAutoCompleteTextView;//搜索输入框
    private ImageView mDeleteButton;//搜索框中的删除按钮
    private RecyclerView searchRes;//搜索的结果
    private List<String> findList=new ArrayList<>();//部分结果
    private List<String> allList=new ArrayList<>();//所有结果
    private SearchAdapter searchAdapter;
    //历史记录
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout container;
    private String item[] = new String[]{"London", "Bangkok", "Paris", "Dubai", "Istanbul", "New York"};
    private List<String> history = new ArrayList<>();
    private ImageView ivDelete;
    //热搜榜
    private List<HotItem> hotItems=new ArrayList<>();
    private ListView hotItemListView;
    private HotSearchAdapter hotSearchAdapter;
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

            //将状态栏文字颜色改为黑色
            lightStatusBar=true;
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (lightStatusBar) {
                ui |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体的颜色为黑色
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体颜色为白色
            }
            decor.setSystemUiVisibility(ui);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rubblish);
        initView();
        initData();
        bindHZSWData();
        setListener();
    }

    private void initView() {
        //搜索框
        searchView=findViewById(R.id.view_search);
        mAutoCompleteTextView=searchView.findViewById(R.id.search_src_text);
        mDeleteButton=searchView.findViewById(R.id.search_close_btn);
        searchRes=findViewById(R.id.search_result);
        //历史记录：横向布局
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        container = (LinearLayout) findViewById(R.id.horizontalScrollViewItemContainer);
        ivDelete=findViewById(R.id.iv_delete);
        //热搜榜
        hotItemListView=findViewById(R.id.lv_hot_search);
        hotSearchAdapter=new HotSearchAdapter(SearchRubblishActivity.this,R.layout.item_hot_search,hotItems);
        hotItemListView.setAdapter(hotSearchAdapter);
        okHttpClient=new OkHttpClient();
    }

    private void initData() {
        Collections.addAll(history, item);
        allList.add("我是程序员");
        allList.add("我是程序员哈哈哈");
        allList.add("我是人");
        allList.add("我不是人");

        hotItems.add(new HotItem("1","康师傅矿泉水"));
        hotItems.add(new HotItem("2","康师傅矿泉水"));

        searchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        //1:回车2:前往3:搜索4:发送5:下一項6:完成
        searchView.setImeOptions(2);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
//        mSearchView.setInputType(1);//设置输入类型
//        mSearchView.setMaxWidth(200);//设置最大宽度
        searchView.setQueryHint("查找分类");//设置查询提示字符串
//        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
        //设置SearchView下划线透明
        setUnderLinetransparent(searchView);
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

    /**设置搜索文本监听**/
    private void setListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query)) {
                    findList.clear();
                    searchRes.setAdapter(searchAdapter);
                } else {
                    findList.clear();
                    OkHttpQuery(query);
                    if (findList.size() == 0) {
                        Toast.makeText(SearchRubblishActivity.this, "查找失败，推荐使用模糊查询", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SearchRubblishActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                        searchAdapter = new SearchAdapter(findList);
                        searchRes.setAdapter(searchAdapter);
                    }
                    searchView.setQuery("", false);//设置初始值
                    searchView.clearFocus();//收起键盘
//                searchView.onActionViewCollapsed();//收起SearchView视图
                }
                return true;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)) {
                    findList.clear();
                    searchRes.setAdapter(searchAdapter);
                } else {
                    findList.clear();
                    OkHttpQuery(newText);
//                    for (int i = 0; i < allList.size(); i++) {
//                        String string = allList.get(i);
//                        if (string.contains(newText)) {
//                            findList.add(string);
//                        }
//                    }
                    searchRes.setLayoutManager(new LinearLayoutManager(SearchRubblishActivity.this));
                    searchAdapter = new SearchAdapter(findList);
                    searchAdapter.notifyDataSetChanged();
                    searchRes.setAdapter(searchAdapter);
                }
                return true;
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history.clear();
                Toast.makeText(SearchRubblishActivity.this,"删除按钮点击了",Toast.LENGTH_SHORT).show();
            }
        });

        hotItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchRubblishActivity.this,id+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询垃圾种类
     * @param query
     */
    private void OkHttpQuery( String query){
        Request request = new Request.Builder().url(BASE_URL + "searchRubbishByName?name="+query).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("test", response.body().string());
            }
        });

    }
    /**历史记录的绑定**/
    private void bindHZSWData() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, 100);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20, 10, 20, 10);

        for (int i = 0; i < history.size(); i++) {
            final Button button = new Button(this);
            button.setText(history.get(i));
            button.setTextSize(10);
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_search_gray_background));
            button.setLayoutParams(layoutParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SearchRubblishActivity.this,button.getText().toString(),Toast.LENGTH_SHORT).show();
//                    performItemClick(view);
                }
            });
            container.addView(button);
            container.invalidate();
        }
    }

    private void performItemClick(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;

        int scrollX = (view.getLeft() - (screenWidth / 2)) + (view.getWidth() / 2);

        //smooth scrolling horizontalScrollView
        horizontalScrollView.smoothScrollTo(scrollX, 0);

        String s = "CenterLocked Item: "+((TextView)view).getText();
    }
}
