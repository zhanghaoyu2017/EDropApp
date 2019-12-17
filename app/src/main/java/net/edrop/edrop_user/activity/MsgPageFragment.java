package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.MsgSwipeAdapter;
import net.edrop.edrop_user.entity.MsgItemBean;
import net.edrop.edrop_user.utils.Constant;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/25
 * Time: 16:40
 */
public class MsgPageFragment extends Fragment {
    private LinearLayout llKong;
    private OkHttpClient okHttpClient;
    private SmartRefreshLayout refeshLayout;
    private ListView listView;
    private List<MsgItemBean> datas = new ArrayList<>();
    private MsgSwipeAdapter swipeAdapter;
    private View myView;
    private int userId;
    private int employeeId;
    private String userName;
    private String employeeName;
    private static final String SECTION_STRING = "fragment_string";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                String json = (String) msg.obj;
                Log.e("json",json);
            }
        }
    };

    public static MsgPageFragment newInstance(String sectionNumber) {
        MsgPageFragment fragment = new MsgPageFragment();
        Bundle args = new Bundle();
        args.putString(SECTION_STRING, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_msg_page, container, false);
        initView();
        initData();
        setListener();
        return myView;
    }

    private void setListener() {
        llKong.setOnClickListener(new MyLinstener());
        refeshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                //刷新信息栏
                FormBody formBody = new FormBody.Builder()
                        .add("employeeId", "")
                        .add("userId", userId + "")
                        .build();
                Request request = new Request.Builder()
                        .url(Constant.BASE_URL + "getOrderById")
                        .post(formBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        refreshLayout.finishRefresh();//结束加载更多的动画
                        Message message = new Message();
                        message.what = 1;
                        message.obj = string;
                        handler.sendMessage(message);
                    }
                });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(myView.getContext(), ChatViewActivity.class);
                intent.putExtra("userId","ls");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            listView.smoothScrollToPosition(pos);
        } else {
            listView.setSelection(pos);
        }
    }


    private class MyLinstener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ll_kong:
                    swipeAdapter.closeAllItems();
                    break;
            }
        }
    }

    private void initData() {
        SharedPreferencesUtils loginInfo = new SharedPreferencesUtils(myView.getContext(), "loginInfo");
        userId = loginInfo.getInt("userId");
//        for (int i = 0; i < 2; i++) {
//            MsgItemBean itemBean = new MsgItemBean();
//            itemBean.setNickName("昵称 " + (i + 1));
//            itemBean.setMsg("Message " + i);
//            itemBean.setDate(getDate());
//            datas.add(itemBean);
//        }
    }

    private String getDate() {
        String hour;
        String minute;
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        if (c.get(Calendar.HOUR_OF_DAY)<10){
            hour="0"+c.get(Calendar.HOUR_OF_DAY);
        }else {
            hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        }
        if (c.get(Calendar.MINUTE)<10){
            minute="0"+c.get(Calendar.MINUTE);
        }else {
            minute = String.valueOf(c.get(Calendar.MINUTE));
        }
        int second = c.get(Calendar.SECOND);
        return hour+":"+minute;
    }

    private void initView() {
        //获取智能刷新布局
        llKong=myView.findViewById(R.id.ll_kong);
        refeshLayout =myView.findViewById(R.id.smart_refesh);
        listView = myView.findViewById(R.id.lv_main);
        swipeAdapter = new MsgSwipeAdapter(getContext(), R.layout.item_swipe_msg ,datas);
        listView.setAdapter(swipeAdapter);
        okHttpClient = new OkHttpClient();
    }
}
