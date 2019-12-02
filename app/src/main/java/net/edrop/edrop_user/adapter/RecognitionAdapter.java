package net.edrop.edrop_user.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.Recognition;

import java.util.List;


/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/2
 * Time: 15:04
 * TODO:给拍照识别结果设置布局
 */
public class RecognitionAdapter extends BaseAdapter {
    // 原始数据
    private List<Recognition> dataSource = null;
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
    public RecognitionAdapter(Context context,
                              List<Recognition> dataSource,
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(item_layout_id, null);
            viewHolder = new ViewHolder();
            viewHolder.imgId = convertView.findViewById(R.id.img_recognition);
            viewHolder.text = convertView.findViewById(R.id.tv_recognition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Recognition recognition = dataSource.get(position);
        viewHolder.text.setText(recognition.getText());
        viewHolder.imgId.setImageResource(recognition.getImgId());

        return convertView;
    }

    private class ViewHolder {
        public ImageView imgId;
        public TextView text;
    }
}
