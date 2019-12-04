package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;

public class MainMenuLeftFragment extends Fragment {
    private View myView;
    private ImageView userImg;
    private TextView userName;
    private TextView myMoney;
    private TextView myAddress;
    private TextView myOrder;
    private TextView inviteFriends;
    private TextView businessCooperation;
    private TextView aboutEDrop;
    private TextView setting;
    private TextView feedback;
    private MyListener myListener;
    private Intent intent;

    //重写
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home_left_menu, container, false);
        return myView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件以及设置
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化监听事件
        initEvent();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        userImg = getActivity().findViewById(R.id.iv_userImg);
        userName = getActivity().findViewById(R.id.tv_userName);
        myMoney = getActivity().findViewById(R.id.myMoney);
        myAddress = getActivity().findViewById(R.id.myAddress);
        myOrder = getActivity().findViewById(R.id.myOrder);
        inviteFriends = getActivity().findViewById(R.id.inviteFriends);
        businessCooperation = getActivity().findViewById(R.id.businessCooperation);
        aboutEDrop = getActivity().findViewById(R.id.aboutEDrop);
        setting = getActivity().findViewById(R.id.setting);
        feedback = getActivity().findViewById(R.id.feedback);
    }

    /**
     * 初始化默认数据【这个需要在activity中执行，原因是：在布局文件中通过<fragment>的方式引用Fragment，打开Activity的时候，Fragment的生命周期函数均执行了】
     * 那么就存在一个问题，初始化fragment中的数据，可能会在activity数据初始化之前执行
     */
    public void setDefaultDatas() {
        //修改首页左边数据
        SharedPreferencesUtils sharedPreferences = new SharedPreferencesUtils(getContext(), "loginInfo");
        String username = sharedPreferences.getString("username", "");
        userName.setText(username);
    }

    /**
     * 初始化监听事件
     */
    private void initEvent() {
        myListener = new MyListener();
        userName.setOnClickListener(myListener);
        userImg.setOnClickListener(myListener);
        myMoney.setOnClickListener(myListener);
        myAddress.setOnClickListener(myListener);
        myOrder.setOnClickListener(myListener);
        inviteFriends.setOnClickListener(myListener);
        businessCooperation.setOnClickListener(myListener);
        aboutEDrop.setOnClickListener(myListener);
        setting.setOnClickListener(myListener);
        feedback.setOnClickListener(myListener);
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_userName:
                    intent = new Intent(getActivity(), PersonalCenterManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                    break;
                case R.id.myMoney:
                    Toast.makeText(getActivity(), myMoney.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myAddress:
                    Toast.makeText(getActivity(), myAddress.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myOrder:
                    Toast.makeText(getActivity(), myOrder.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.inviteFriends:
                    Toast.makeText(getActivity(), inviteFriends.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.businessCooperation:
                    Toast.makeText(getActivity(), businessCooperation.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.aboutEDrop:
                    //跳转到详细介绍页面
                    intent = new Intent(getActivity(), AboutEDrop.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivity(intent);
                    break;
                case R.id.setting:
                    Toast.makeText(getActivity(), setting.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.feedback:
                    Toast.makeText(getActivity(), feedback.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
