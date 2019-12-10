package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.ShowOrderAdapter;
import net.edrop.edrop_user.entity.Order;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.util.List;

public class ShowOrders extends Activity {
    private SmartRefreshLayout refreshLayout;
    private List<Order> orderList = null;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(ShowOrders.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);



        findView();
        json2objectList();
        setMyAdapter();
    }

    private void findView() {

        refreshLayout = findViewById(R.id.smart_layout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        listView = findViewById(R.id.lv_showOrders);

    }
    private void setMyAdapter() {
        ShowOrderAdapter adapter = new ShowOrderAdapter(orderList,ShowOrders.this,R.layout.item_showorders_layout);
        listView.setAdapter(adapter);
    }
    private void json2objectList() {
        Intent intent = getIntent();
        String orderjson = intent.getStringExtra("orderjson");
        Log.e("test", orderjson);
        orderList= new Gson().fromJson(orderjson, new TypeToken<List<Order>>(){}.getType());
    }

}
