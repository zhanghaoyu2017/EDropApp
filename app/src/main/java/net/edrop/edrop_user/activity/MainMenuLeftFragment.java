package net.edrop.edrop_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.User;
import net.edrop.edrop_user.utils.Constant;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.edrop.edrop_user.utils.Constant.BASE_URL;

public class MainMenuLeftFragment extends Fragment {
    private View myView;
    private ImageView userSex;
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
    private OkHttpClient okHttpClient;
    private SharedPreferencesUtils sharedPreferences;
    private Intent intent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 888) {
                RequestOptions options = new RequestOptions().centerCrop();
                Glide.with(myView.getContext())
                        .load(msg.obj)
                        .apply(options)
                        .into(userImg);
            }else if (msg.what==1){
                Intent intent = new Intent(myView.getContext(), ShowOrders.class);
                String str = (String) msg.obj;
                intent.putExtra("orderjson", str);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

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
        userImg = myView.findViewById(R.id.iv_userImg);
        userName = myView.findViewById(R.id.tv_userName);
        myMoney = myView.findViewById(R.id.myMoney);
        myAddress = myView.findViewById(R.id.myAddress);
        myOrder = myView.findViewById(R.id.myOrder);
        inviteFriends = myView.findViewById(R.id.inviteFriends);
        businessCooperation = myView.findViewById(R.id.businessCooperation);
        aboutEDrop = myView.findViewById(R.id.aboutEDrop);
        setting = myView.findViewById(R.id.setting);
        feedback = myView.findViewById(R.id.feedback);
        userSex = myView.findViewById(R.id.iv_head_img_main);
    }

    /**
     * 初始化默认数据【这个需要在activity中执行，原因是：在布局文件中通过<fragment>的方式引用Fragment，打开Activity的时候，Fragment的生命周期函数均执行了】
     * 那么就存在一个问题，初始化fragment中的数据，可能会在activity数据初始化之前执行
     */
    public void setDefaultDatas() {
        //修改首页左边数据
        sharedPreferences = new SharedPreferencesUtils(myView.getContext(), "loginInfo");
        String username = sharedPreferences.getString("username", "");
        String gender = sharedPreferences.getString("gender", "");
        userName.setText(username);
        switch (gender) {
            case "boy":
                userSex.setImageDrawable(getResources().getDrawable(R.drawable.gender_boy));
                break;
            case "girl":
                userSex.setImageDrawable(getResources().getDrawable(R.drawable.gender_girl));
                break;
            case "secret":
                userSex.setImageDrawable(getResources().getDrawable(R.drawable.gender_secret));
            default:
                break;
        }
        okHttpClient = new OkHttpClient();
        getImg();
    }

    private void getImg() {
        //2.创建Request对象
        Request request = new Request.Builder().url(BASE_URL + "getUserInfoById?id=" + sharedPreferences.getInt("userId")).build();
        //3.创建Call对象
        final Call call = okHttpClient.newCall(request);

        //4.发送请求 获得响应数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();//打印异常信息
            }

            //请求成功时回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                String imgPath = "";
                String imgName = "";
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    imgPath = jsonObject.getString("imgpath");
                    imgName = jsonObject.getString("imgname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 888;
                message.obj = BASE_URL.substring(0,BASE_URL.length()-1)+imgPath +"/"+ imgName;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 初始化监听事件
     */
    private void initEvent() {
        userName.setOnClickListener(new MyListener());
        userImg.setOnClickListener(new MyListener());
        myMoney.setOnClickListener(new MyListener());
        myAddress.setOnClickListener(new MyListener());
        myOrder.setOnClickListener(new MyListener());
        inviteFriends.setOnClickListener(new MyListener());
        businessCooperation.setOnClickListener(new MyListener());
        aboutEDrop.setOnClickListener(new MyListener());
        setting.setOnClickListener(new MyListener());
        feedback.setOnClickListener(new MyListener());
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_userName:
                    intent = new Intent(getContext(), PersonalCenterManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.myMoney:
                    Toast.makeText(getActivity(), myMoney.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myAddress:
                    Toast.makeText(getActivity(), myAddress.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myOrder:
                    SharedPreferencesUtils loginInfo = new SharedPreferencesUtils(myView.getContext(), "loginInfo");
                    int userId = loginInfo.getInt("userId");
                    FormBody formBody = new FormBody.Builder()
                            .add("userId", userId + "").build();
                    Request request = new Request.Builder()
                            .url(Constant.BASE_URL + "getOrderById")
                            .post(formBody)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            Message message = new Message();
                            message.what = 1;
                            message.obj = string;
                            handler.sendMessage(message);
                        }
                    });

                    break;
                case R.id.inviteFriends:
                    Toast.makeText(getActivity(), inviteFriends.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.businessCooperation:
                    Toast.makeText(getActivity(), businessCooperation.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.aboutEDrop:
                    //跳转到详细介绍页面
                    intent = new Intent(getContext(), AboutEDropActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    Toast.makeText(getActivity(), setting.getText().toString(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.feedback:
                    Toast.makeText(getActivity(), feedback.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent = new Intent(getContext(), FeedBackActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
        }
    }
}
