package net.edrop.edrop_user.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.adapter.MyPagerAdapter;
import net.edrop.edrop_user.utils.Constant;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;
import net.edrop.edrop_user.utils.SystemTransUtil;
import net.edrop.edrop_user.utils.getPhotoFromPhotoAlbum;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

import static net.edrop.edrop_user.utils.Constant.BASE_FAIL;
import static net.edrop.edrop_user.utils.Constant.BASE_SUCCESS;
import static net.edrop.edrop_user.utils.Constant.IMG_FAIL;
import static net.edrop.edrop_user.utils.Constant.IMG_SUCCESS;
import static net.edrop.edrop_user.utils.Constant.PSD_FAIL;
import static net.edrop.edrop_user.utils.Constant.PSD_SUCCESS;
import static net.edrop.edrop_user.utils.Constant.UPDATE_USER_FAIL;
import static net.edrop.edrop_user.utils.Constant.UPDATE_USER_SUCCESS;

public class FillPersonalInforActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<String>();//页卡标题集合
    private View view1, view2, view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    //性别
    private RadioGroup rgSex = null;
    private RadioButton rbBoy = null;
    private RadioButton rbGirl = null;
    private RadioButton rbSecret = null;

    //三级联动
    private CityPickerView mPicker = new CityPickerView();
    private TextView tvSelect;
    private TextView tvChangeName;
    private TextView tvChangePhone;
    private TextView tvDetailAddress;
    private Button btnUpdata;
    private String strSex;
    private TextView tvUserName;
    //照片
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button btnSelectImg;
    private ImageView ivHeadImg;
    private Button btnSave;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;
    private String photoPath;
    private MultipartBody.Builder multipartBody = new MultipartBody.Builder();
    //popupWindow
    private PopupWindow popupWindow = null;
    private View popupView = null;
    //新密码
    private EditText etNewPsd;
    private EditText etNewPsd2;
    private Button btnOk;
    private String newPsd = "";

    private OkHttpClient okHttpClient;
    private int userId;
    private Message msg = new Message();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PSD_SUCCESS) {
                Toast.makeText(FillPersonalInforActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            } else if (msg.what == PSD_FAIL) {
                Toast.makeText(FillPersonalInforActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            } else if (msg.what == BASE_SUCCESS) {
                Toast.makeText(FillPersonalInforActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            } else if (msg.what == BASE_FAIL) {
                Toast.makeText(FillPersonalInforActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            } else if (msg.what == IMG_SUCCESS) {
                Toast.makeText(FillPersonalInforActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            } else if (msg.what == IMG_FAIL) {
                Toast.makeText(FillPersonalInforActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(FillPersonalInforActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_personal_infor);
        mPicker.init(this);
        //1.创建OkHttpClient对象
        okHttpClient = new OkHttpClient();
        initView();

        view1 = mInflater.inflate(R.layout.item_fill_base_info, null);
        tvChangePhone = view1.findViewById(R.id.tv_phone_select);
        tvUserName = view1.findViewById(R.id.tv_base_name);
        tvSelect = (TextView) view1.findViewById(R.id.tv_select);
        tvChangeName = (TextView) view1.findViewById(R.id.tv_change_name);
        tvDetailAddress = (TextView) view1.findViewById(R.id.tv_detail_address);
        btnUpdata = (Button) view1.findViewById(R.id.btn_update);
        rgSex = view1.findViewById(R.id.rg_sex);
        rbBoy = view1.findViewById(R.id.rb_boy);
        rbGirl = view1.findViewById(R.id.rb_girl);
        rbSecret = view1.findViewById(R.id.rb_secret);

        view2 = mInflater.inflate(R.layout.item_fill_img_info, null);
        btnSelectImg = view2.findViewById(R.id.btn_select_img);
        ivHeadImg = view2.findViewById(R.id.iv_head_img);
        btnSave = view2.findViewById(R.id.btnSave);
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        getPermission();
        GridLayout grid_view= view2.findViewById(R.id.grid_view);
        view3 = mInflater.inflate(R.layout.item_fill_psd_info, null);
        etNewPsd = view3.findViewById(R.id.et_newPsd);
        etNewPsd2 = view3.findViewById(R.id.et_newPsd2);
        btnOk = view3.findViewById(R.id.btn_new_ok);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        //添加页卡标题
        mTitleList.add("基本信息");
        mTitleList.add("头像信息");
        mTitleList.add("密码修改");
        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        setLinstener();
        initDate();
    }

    private void initDate() {
        SharedPreferencesUtils loginInfo = new SharedPreferencesUtils(FillPersonalInforActivity.this, "loginInfo");
        String username = loginInfo.getString("username", "");
        String phone = loginInfo.getString("phone", "");
        String gender = loginInfo.getString("gender", "");
        String address = loginInfo.getString("address", "");
        String detailAddress = loginInfo.getString("detailAddress", "");
        tvDetailAddress.setText(detailAddress);
        userId = loginInfo.getInt("userId");
        tvSelect.setText(address);
        tvUserName.setText(username);
        String str="****";
        if (!phone.equals("")) {
            StringBuffer sb = new StringBuffer(phone);
            sb.replace(3, 7, str);
            tvChangePhone.setText(sb.toString());
        }
        switch (gender) {
            case "boy":
                rbGirl.setChecked(false);
                rbSecret.setChecked(false);
                break;
            case "girl":
                rbBoy.setChecked(false);
                rbSecret.setChecked(false);
                break;
            case "secret":
                rbGirl.setChecked(false);
                rbBoy.setChecked(false);
                break;
            default:
                break;
        }
    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
//            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(FillPersonalInforActivity.this, "net.edrop.edrop_user.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        FillPersonalInforActivity.this.startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }

    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//相机拍照
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.e("拍照返回图片路径:", photoPath);
            RequestOptions options = new RequestOptions().centerCrop();
            Glide.with(this).load(photoPath).apply(options).into(ivHeadImg);

        } else if (requestCode == 2 && resultCode == RESULT_OK) {//相册选择
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            RequestOptions options = new RequestOptions().centerCrop();
            Glide.with(FillPersonalInforActivity.this).load(photoPath).apply(options).into(ivHeadImg);
        } else if (requestCode == 88 && resultCode == 90) {//用户名
            String nameInfo = "zs";
            nameInfo = data.getStringExtra("nameInfo");
            tvUserName.setText(nameInfo);
        }else if (requestCode == 77 && resultCode == 90) {//手机号
            String nameInfo = "";
            nameInfo = data.getStringExtra("nameInfo");
            String str="****";
            if (!nameInfo.equals("")) {
                StringBuffer sb = new StringBuffer(nameInfo);
                sb.replace(3, 7, str);
                tvChangePhone.setText(sb.toString());
            }
            tvChangePhone.setText(nameInfo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setLinstener() {
        btnSave.setOnClickListener(new MyLinsener());
        btnOk.setOnClickListener(new MyLinsener());
        btnSelectImg.setOnClickListener(new MyLinsener());
        tvSelect.setOnClickListener(new MyLinsener());
        tvChangeName.setOnClickListener(new MyLinsener());
        tvDetailAddress.setOnClickListener(new MyLinsener());
        btnUpdata.setOnClickListener(new MyLinsener());
    }

    private class MyLinsener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_select:
                    hideKeyboard();
                    CityConfig cityConfig = new CityConfig.Builder()
                            .title("选择城市")//标题
                            .titleTextSize(18)//标题文字大小
                            .titleTextColor("#585858")//标题文字颜 色
                            .titleBackgroundColor("#E9E9E9")//标题栏背景色
                            .confirTextColor("#585858")//确认按钮文字颜色
                            .confirmText("确认")//确认按钮文字
                            .confirmTextSize(16)//确认按钮文字大小
                            .cancelTextColor("#585858")//取消按钮文字颜色
                            .cancelText("取消")//取消按钮文字
                            .cancelTextSize(16)//取消按钮文字大小
                            .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                            .showBackground(true)//是否显示半透明背景
                            .visibleItemsCount(7)//显示item的数量
                            .province("河北省")//默认显示的省份
                            .city("石家庄市")//默认显示省份下面的城市
                            .district("裕华区")//默认显示省市下面的区县数据
                            .provinceCyclic(true)//省份滚轮是否可以循环滚动
                            .cityCyclic(true)//城市滚轮是否可以循环滚动
                            .districtCyclic(true)//区县滚轮是否循环滚动
                            .setCustomItemLayout(R.layout.activity_main)//自定义item的布局
                            .drawShadows(false)//滚轮不显示模糊效果
                            .setLineColor("#1DC850")//中间横线的颜色
                            .setLineHeigh(5)//中间横线的高度
                            .setShowGAT(true)//是否显示港澳台数据，默认不显示
                            .build();
                    mPicker.setConfig(cityConfig);
                    //监听选择点击事件及返回结果
                    mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                        @Override
                        public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                            //省份province-城市city-地区district
                            tvSelect.setText(province + "\t" + city + "\t" + district);
                        }

                        @Override
                        public void onCancel() {
                            ToastUtils.showLongToast(FillPersonalInforActivity.this, "已取消");
                        }
                    });
                    //显示
                    mPicker.showCityPicker();
                    break;
                case R.id.tv_change_name://用户名
                    Intent intent = new Intent();
                    intent.setClass(FillPersonalInforActivity.this, ChangeViewActivity.class);
                    intent.putExtra("name", tvUserName.getText().toString());
                    intent.putExtra("state","name");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 88);
                    break;
                case R.id.tv_phone_select://手机号
                    Intent intent1 = new Intent();
                    intent1.setClass(FillPersonalInforActivity.this, ChangeViewActivity.class);
                    intent1.putExtra("name", tvChangePhone.getText().toString());
                    intent1.putExtra("state","phone");
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent1, 77);
                    break;
                case R.id.tv_detail_address:
                    tvDetailAddress.getText().toString();
                    break;
                case R.id.btn_update:
                    switch (rgSex.getCheckedRadioButtonId()) {
                        case R.id.rb_boy:
                            strSex = "boy";
                            rbGirl.setChecked(false);
                            rbSecret.setChecked(false);
                            break;
                        case R.id.rb_girl:
                            strSex = "girl";
                            rbBoy.setChecked(false);
                            rbSecret.setChecked(false);
                            break;
                        case R.id.rb_secret:
                            strSex = "secret";
                            rbGirl.setChecked(false);
                            rbBoy.setChecked(false);
                            break;
                    }
                    postFormData();//发送form表单数据
                    break;
                case R.id.btn_select_img:
                    // 显示PopupWindow
                    if (popupWindow == null || !popupWindow.isShowing())
                        showPopupWindow();
                    break;
                case R.id.btn_new_ok:
                    //确认新密码
                    newPsd = etNewPsd.getText().toString();
                    String newPsd2 = etNewPsd2.getText().toString();
                    if (newPsd.equals(newPsd2)) {
                        postFormPsd();
                    } else {
                        Toast.makeText(FillPersonalInforActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnSave:
                    postFormImg();
                    break;
            }
        }
    }

    private void postFormImg() {
        File file = new File(photoPath);
        if (file.exists()) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", userId + "")
                    .addFormDataPart("imgFile", file.getName(), fileBody)
                    .build();
            Request request = new Request.Builder()
                    .url(Constant.BASE_URL + "addUserInfo")
                    .post(requestBody)
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
                    if (string.equals(UPDATE_USER_SUCCESS + "")) {
                        msg.obj = "密码更新完成";
                        msg.what = IMG_SUCCESS;
                        mHandler.sendMessage(msg);
                    } else if (string.equals(UPDATE_USER_FAIL + "")) {
                        msg.obj = "密码更新失败";
                        msg.what = IMG_FAIL;
                        mHandler.sendMessage(msg);
                    }
                }
            });
        }
    }

    private void postFormPsd() {
        //创建FormBody对象
        FormBody formBody = new FormBody.Builder()
                .add("id", userId + "")
                .add("password", newPsd)
                .build();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "addUserInfo")
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
                int state = 0;
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    state = jsonObject.getInt("state");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (state == UPDATE_USER_SUCCESS) {
                    msg.obj = "密码更新完成";
                    msg.what = PSD_SUCCESS;
                    mHandler.sendMessage(msg);
                } else if (string.equals(UPDATE_USER_FAIL + "")) {
                    msg.obj = "服务器错误";
                    msg.what = PSD_FAIL;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    private void postFormData() {
        //创建FormBody对象
        FormBody formBody = new FormBody.Builder()
                .add("id", userId + "")
                .add("username", tvUserName.getText().toString())
                .add("phone", tvChangePhone.getText().toString())
                .add("gender", strSex)
                .add("address", tvSelect.getText().toString())
                .add("detailAddress", tvDetailAddress.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "addUserInfo")
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
                int state = 0;
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    state = jsonObject.getInt("state");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (state == UPDATE_USER_SUCCESS) {
                    msg.obj = "基本信息更新完成";
                    msg.what = BASE_SUCCESS;
                    mHandler.sendMessage(msg);
                } else if (string.equals(UPDATE_USER_FAIL + "")) {
                    msg.obj = "服务器错误";
                    msg.what = BASE_FAIL;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    private void showPopupWindow() {
        // 创建popupWindow对象
        setBackgroundAlpha(0.5f, this);
        popupWindow = new PopupWindow();
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 通过布局填充器创建View
        popupView = getLayoutInflater().inflate(R.layout.item_popupwindow_photo, null);
        // 设置PopupWindow显示的内容视图
        popupWindow.setContentView(popupView);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否相应点击事件
        popupWindow.setTouchable(true);
        popupView.setFocusable(true);
        View view_list = View.inflate(this, R.layout.item_popupwindow_photo, null);
        popupWindow.setOnDismissListener(new popupDismissListener());
        popupWindow.showAtLocation(view_list.findViewById(R.id.popup_photo), Gravity.BOTTOM, 0, 0);

        // 获取按钮并添加监听器
        Button btnAlbum = popupView.findViewById(R.id.btn_album);
        Button btnCamera = popupView.findViewById(R.id.btn_camera);
        Button btnCancel = popupView.findViewById(R.id.btn_cancel_popup);
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPhotoAlbum();
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goCamera();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private class popupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            setBackgroundAlpha(1f, FillPersonalInforActivity.this);
        }
    }

    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mInflater = LayoutInflater.from(this);
    }

    /***
     * 隐藏键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * ViewPager适配器
     **/
    private class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(FillPersonalInforActivity.this,Main2Activity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
