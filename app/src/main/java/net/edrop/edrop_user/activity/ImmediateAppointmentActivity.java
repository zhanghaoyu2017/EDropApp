package net.edrop.edrop_user.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
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
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ImmediateAppointmentActivity extends Activity {
    private CityPickerView mPicker = new CityPickerView();
    private TextView tvSelect;
    private TextView tvDateSelect;
    private TextView tvTimeSelect;
    private LinearLayout llCitySelect;
    private LinearLayout llDateSelect;
    private LinearLayout llTimeSelect;
    DateFormat format = DateFormat.getDateTimeInstance();
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(ImmediateAppointmentActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediate_appointment);
        initView();
        setListener();
    }

    private void initView() {
        mPicker.init(this);
        tvSelect = findViewById(R.id.tv_immediate_select);
        tvDateSelect = findViewById(R.id.tv_date_select);
        tvTimeSelect = findViewById(R.id.tv_time_select);
        llCitySelect = findViewById(R.id.ll_immediate_select);
        llDateSelect = findViewById(R.id.ll_date_select);
        llTimeSelect = findViewById(R.id.ll_time_select);

    }

    private void setListener() {

        llCitySelect.setOnClickListener(new MyListener());
        llDateSelect.setOnClickListener(new MyListener());
        llTimeSelect.setOnClickListener(new MyListener());
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
                            tvSelect.setText(province + "\t" + city + "\t\t" + district);
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
                tv.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
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
                        tv.setText(hourOfDay + "时" + minute + "分");
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }
}
