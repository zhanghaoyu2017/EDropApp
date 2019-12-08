package net.edrop.edrop_user.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import net.edrop.edrop_user.activity.LoginActivity;
import net.edrop.edrop_user.activity.Main2Activity;
import net.edrop.edrop_user.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.edrop.edrop_user.utils.Constant.BASE_URL;
import static net.edrop.edrop_user.utils.Constant.LOGIN_SUCCESS;

/**
 * 通过get请求，动态获取用户的账户信息
 * Created by mysterious
 * User: mysterious
 * Date: 2019/12/8
 * Time: 18:32
 */
public class GetUserInfoUtil {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Context context;
    private static String userinfo;
    private SharedPreferencesUtils sharedPreferences = new SharedPreferencesUtils(context, "loginInfo");

    private String getUserAllInfo(final String str) {
        int userId = sharedPreferences.getInt("userId");
        //2.创建Request对象
        Request request = new Request.Builder().url(BASE_URL + "loginByUserId?id=" + userId).build();
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
                String responseJson = response.body().string();
                Log.e("response", responseJson);
                try {
                    JSONObject jsonObject = new JSONObject(responseJson);
                    int state = jsonObject.getInt("state");
                    if (state == LOGIN_SUCCESS) {
                        //登录成功
                        String userJson = jsonObject.getString("user");
                        User user = new Gson().fromJson(userJson, User.class);
                        String address = user.getAddress();
                        String detailAddress = user.getDetailAddress();
                        String gender = user.getGender();
                        String imgname = user.getImgname();
                        String phone = user.getPhone();
                        String imgpath = user.getImgpath();
                        String username = user.getUsername();
                        String qq = user.getQq();
                        switch (str) {
                            case "address":
                                userinfo = address;
                                break;
                            case "detailAddress":
                                userinfo = detailAddress;
                                break;
                            case "gender":
                                userinfo = gender;
                                break;
                            case "imgname":
                                userinfo = imgname;
                                break;
                            case "phone":
                                userinfo = phone;
                                break;
                            case "imgpath":
                                userinfo = imgpath;
                                break;
                            case "username":
                                userinfo = username;
                                break;
                            case "qq":
                                userinfo = qq;
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return userinfo;
    }

    public String getUserAddress() {
        return getUserAllInfo("address");
    }

    public String getUserGender() {
        return getUserAllInfo("gender");
    }

    public String getUserDetailAddress() {
        return getUserAllInfo("detailAddress");
    }

    public String getUserImgname() {
        return getUserAllInfo("imgname");
    }

    public String getUserPhone() {
        return getUserAllInfo("phone");
    }

    public String getUserUsername() {
        return getUserAllInfo("username");
    }

    public String getUserImgpath() {
        return getUserAllInfo("imgpath");
    }
}
