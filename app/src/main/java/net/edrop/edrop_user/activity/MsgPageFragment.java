package net.edrop.edrop_user.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.MsgSwipeAdapter;
import net.edrop.edrop_user.entity.MsgItemBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/25
 * Time: 16:40
 */
public class MsgPageFragment extends Fragment {
    private SmartRefreshLayout refeshLayout;
    private ListView listView;
    private List<MsgItemBean> datas = new ArrayList<>();
    private MsgSwipeAdapter swipeAdapter;
    private View view;
    private static final String SECTION_STRING = "fragment_string";

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
        view = inflater.inflate(R.layout.fragment_msg_page, container, false);
        initView();
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        refeshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新信息栏
                RefreshMsgTask refreshMsgTask = new RefreshMsgTask();
                refreshMsgTask.execute();
            }
        });
    }
    private class RefreshMsgTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //更新视图

            swipeAdapter.notifyDataSetChanged();
            //结束加载更多的动画
            refeshLayout.finishRefresh();
        }
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            MsgItemBean itemBean = new MsgItemBean();
            itemBean.setNickName("昵称 " + (i + 1));
            itemBean.setMsg("Message " + i);
            itemBean.setDate(getDate());
            datas.add(itemBean);
        }
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
        refeshLayout =view.findViewById(R.id.smart_refesh);
        listView = view.findViewById(R.id.lv_main);
        swipeAdapter = new MsgSwipeAdapter(getContext(), R.layout.item_swipe_msg ,datas);
        listView.setAdapter(swipeAdapter);
    }
}
