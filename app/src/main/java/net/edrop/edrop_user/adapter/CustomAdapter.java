package net.edrop.edrop_user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.Competition;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<Competition> dataSource = null;
    private List hisAnswerLists=null;
    private Context context = null;
    private int item_layout_id;

    public CustomAdapter(List<Competition> dataSource, List hisAnswerLists, Context context, int item_layout_id) {
        this.dataSource = dataSource;
        this.hisAnswerLists = hisAnswerLists;
        this.context = context;
        this.item_layout_id = item_layout_id;
    }
//    public CustomAdapter(List<Bean> dataSource,Context context, int item_layout_id) {
//        this.dataSource = dataSource;
//        this.context = context;
//        this.item_layout_id = item_layout_id;
//    }


    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        if (null != dataSource) {
            return dataSource.get(i);
        }
        return null;
    }


    @Override
    public long getItemId(int i) {
        return dataSource.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        Log.e("myanswer",""+hisAnswerLists.toString());
        Log.e("datasorce",""+dataSource.toString());
        if (view == null) {
            view = LayoutInflater.from(context).inflate(item_layout_id, null);
            viewHolder = new ViewHolder();
            viewHolder.quesId = view.findViewById(R.id.tv_quesId);
            viewHolder.question = view.findViewById(R.id.tv_question);
            viewHolder.corrAnswer = view.findViewById(R.id.tv_correctAnswer);
            viewHolder.youAnswer = view.findViewById(R.id.tv_yourAnswer);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.quesId.setText(i+1+"");
        viewHolder.question.setText(dataSource.get(i).getQuestion());
        viewHolder.corrAnswer.setText(dataSource.get(i).getTypeId()+"");
        viewHolder.youAnswer.setText(hisAnswerLists.get(i).toString());
        return view;
    }

    private class ViewHolder {
        private TextView quesId;
        private TextView corrAnswer;
        private TextView question;
        private TextView youAnswer;
    }
}
