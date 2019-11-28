package net.edrop.edrop_user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.edrop.edrop_user.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: sifannnn
 * Date: 2019/11/27
 * Time: 21:06
 */
//设置服务显示的item，方便以后维护项目。
public class ServiceAdapter extends BaseAdapter {
    // 原始数据
    private List<Map<String, Object>> dataSource = null;
    // 上下文环境
    private Context context;
    // item对应的布局文件
    private int item_layout_id;

    /**
     * 构造器，完成初始化
     *
     * @param context        上下文环境
     * @param dataSource     原始数据
     * @param item_layout_id item对应的布局文件
     */
    public ServiceAdapter(Context context,
                          List<Map<String, Object>> dataSource,
                          int item_layout_id) {
        this.context = context;
        this.dataSource = dataSource;
        this.item_layout_id = item_layout_id;

    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            //布局填充器，根据布局文件生成相应的VIew
            LayoutInflater inflater = LayoutInflater.from(context);
            // 使用布局填充器根据布局文件资源ID生成View视图对象
            convertView = inflater.inflate(item_layout_id, null);
        }

        //设置item的值和右箭头图标
        final TextView textView = convertView.findViewById(R.id.tv_service_title);
        final ImageView imageView = convertView.findViewById(R.id.img_service);
        Map<String, Object> map = dataSource.get(position);
        textView.setText(map.get("text").toString());
        imageView.setImageResource((int) map.get("img"));

        return convertView;
    }
}
