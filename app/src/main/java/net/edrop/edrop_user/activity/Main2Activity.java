package net.edrop.edrop_user.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import net.edrop.edrop_user.R;

public class Main2Activity extends AppCompatActivity {
    private FragmentTabHost tabHost = null;
    private String [] tabStrArr = {"主页", "服务", "消息","社区"};
    //类信息，通过类信息可以创建对象，对象之中包含视图，用来填充fragment
    private Class[] fragmentArr = {HomePageFragment.class, ServicePageFragment.class, MsgPageFragment.class,CommunityPageFragment.class};
    private int [] imageNormalArr = {R.drawable.home_normal, R.drawable.service_normal, R.drawable.message_normal,R.drawable.community_normal};
    private int[] imageSelectArr= {R.drawable.home_select, R.drawable.service_select, R.drawable.message_select,R.drawable.community_select};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initTabHost();
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
        imageView.setImageResource(imageNormalArr[i]);

        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(tabStrArr[i]);
        return view;
    }
}
