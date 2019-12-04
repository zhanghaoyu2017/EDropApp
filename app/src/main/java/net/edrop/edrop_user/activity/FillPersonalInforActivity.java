package net.edrop.edrop_user.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.utils.SystemTransUtil;

import java.util.ArrayList;
import java.util.List;

public class FillPersonalInforActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<String>();//页卡标题集合
    private View view1, view2 ,view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    //性别
    private RadioGroup rgSex = null;
    private RadioButton rbBoy=null;
    private RadioButton rbGirl=null;
    private RadioButton rbSecret=null;
    //三级联动
    private CityPickerView mPicker = new CityPickerView();
    private TextView tvSelect;
    private TextView tvChange;
    private TextView tvDetailAddress;
    private Button btnUpdata;
    private String strSex = null;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new SystemTransUtil().trans(FillPersonalInforActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_personal_infor);
        mPicker.init(this);
        initView();

        view1 = mInflater.inflate(R.layout.item_fill_base_info, null);
        tvSelect = (TextView) view1.findViewById(R.id.tv_select);
        tvChange = (TextView) view1.findViewById(R.id.tv_change);
        tvDetailAddress = (TextView) view1.findViewById(R.id.tv_detail_address);
        btnUpdata = (Button) view1.findViewById(R.id.btn_update);
        rgSex = view1.findViewById(R.id.rg_sex);
        rbBoy = view1.findViewById(R.id.rb_boy);
        rbGirl = view1.findViewById(R.id.rb_girl);
        rbSecret = view1.findViewById(R.id.rb_secret);
        view2 = mInflater.inflate(R.layout.item_fill_img_info, null);
        view3 = mInflater.inflate(R.layout.item_fill_img_info, null);
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
    }

    private void setLinstener() {
        tvSelect.setOnClickListener(new MyLinsener());
        tvChange.setOnClickListener(new MyLinsener());
        tvDetailAddress.setOnClickListener(new MyLinsener());
        btnUpdata.setOnClickListener(new MyLinsener());
    }

    private class MyLinsener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_select:
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
                            //省份province
                            //城市city
                            //地区district
                            tvSelect.setText(province+"\t"+city+"\t"+district);
                            address=province+"-"+city+"-"+district;
//                            Toast.makeText(FillPersonalInforActivity.this,province+"-"+city+"-"+district,Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancel() {
                            ToastUtils.showLongToast(FillPersonalInforActivity.this, "已取消");
                        }
                    });
                    //显示
                    mPicker.showCityPicker();
                    break;
                case R.id.tv_change:
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
                    Toast.makeText(FillPersonalInforActivity.this,
                            strSex+"\t"+address+"\t"+tvDetailAddress.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mInflater = LayoutInflater.from(this);
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
}
