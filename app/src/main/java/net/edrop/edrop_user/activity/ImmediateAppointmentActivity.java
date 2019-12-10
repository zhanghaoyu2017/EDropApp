package net.edrop.edrop_user.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.Constant;
import net.edrop.edrop_user.utils.SharedPreferencesUtils;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImmediateAppointmentActivity extends Activity {
    private CityPickerView mPicker = new CityPickerView();
    private TextView tvSelect;
    private TextView tvDateSelect;
    private TextView tvTimeSelect;
    private LinearLayout llCitySelect;
    private LinearLayout llDateSelect;
    private LinearLayout llTimeSelect;
    private EditText etRealName;
    private EditText etPhoneNum;
    private EditText etAddressDetail;
    private Button btnOrder;
    private OkHttpClient okHttpClient;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(ImmediateAppointmentActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate_appointment);
        initView();
        setListener();

    }

    private void initData() {
        SharedPreferencesUtils loginInfo = new SharedPreferencesUtils(ImmediateAppointmentActivity.this, "loginInfo");
        int userId = loginInfo.getInt("userId");
        String realname = etRealName.getText().toString();
        String phone = etPhoneNum.getText().toString();
        String address = tvSelect.getText().toString() + etAddressDetail.getText().toString();
        String reserveTime = tvDateSelect.getText().toString() + " " + tvTimeSelect.getText().toString();

        Log.e("qqqqqqqqqq", userId + "===" + realname + "===" + phone + "===" + address + "===" + reserveTime);

    }

    private void initView() {
        mPicker.init(this);
        tvSelect = findViewById(R.id.tv_immediate_select);
        tvDateSelect = findViewById(R.id.tv_date_select);
        tvTimeSelect = findViewById(R.id.tv_time_select);
        llCitySelect = findViewById(R.id.ll_immediate_select);
        llDateSelect = findViewById(R.id.ll_date_select);
        llTimeSelect = findViewById(R.id.ll_time_select);
        etRealName = findViewById(R.id.et_realName);
        etPhoneNum = findViewById(R.id.et_phone);
        etAddressDetail = findViewById(R.id.et_detail_address);
        btnOrder = findViewById(R.id.btn_order);
        okHttpClient = new OkHttpClient();
    }

    private void setListener() {

        llCitySelect.setOnClickListener(new MyListener());
        llDateSelect.setOnClickListener(new MyListener());
        llTimeSelect.setOnClickListener(new MyListener());
        btnOrder.setOnClickListener(new MyListener());
    }

    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_immediate_select:
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
                            tvSelect.setText(province + "" + city + "" + district);
                        }

                        @Override
                        public void onCancel() {
                            ToastUtils.showLongToast(ImmediateAppointmentActivity.this, "已取消");
                        }
                    });
                    //显示
                    mPicker.showCityPicker();
                    break;
                case R.id.ll_date_select:
                    showDatePickerDialog(ImmediateAppointmentActivity.this, R.style.MyDatePickerDialogTheme, tvDateSelect, calendar);
                    break;
                case R.id.ll_time_select:
                    showTimePickerDialog(ImmediateAppointmentActivity.this, R.style.MyDatePickerDialogTheme, tvTimeSelect, calendar);
                    break;
                case R.id.btn_order:
                    initData();
                    break;
            }
        }
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
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog(activity, themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(hourOfDay + ":" + minute);
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }

    /**
     * 通过okhttp传输数据
     *
     * @param userId
     * @param realName
     * @param phone
     * @param address
     * @param reserveTime
     */
    private void sendOrderByOkHttp(String userId, String realName, String phone, String address, String reserveTime) {
        FormBody formBody = new FormBody.Builder()
                .add("userId", userId + "")
                .add("realName", realName)
                .add("phone", phone)
                .add("address", address)
                .add("reserveTime", reserveTime.toString())
                .build();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "generateOrder")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
