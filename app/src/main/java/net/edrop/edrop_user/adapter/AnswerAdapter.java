package net.edrop.edrop_user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.entity.Competition;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter {
    private List<Competition> datasource;
    private Context context;

    public AnswerAdapter(Context mContext) {
        this.context = mContext;
    }

    public List<Competition> getData() {
        return datasource;
    }

    public void setData(List<Competition> datasource) {
        this.datasource = new ArrayList<>(datasource);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_view, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        Competition bean = datasource.get(i);
        myViewHolder.tv.setText(bean.getQuestion());
    }

    @Override
    public int getItemCount() {
        return datasource.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
