package net.edrop.edrop_user.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.edrop.edrop_user.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/29
 * Time: 17:13
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private List<String> list;
    private View inflate;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }
    public SearchAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.content.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
