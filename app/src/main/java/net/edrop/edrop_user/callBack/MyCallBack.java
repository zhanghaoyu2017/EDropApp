package net.edrop.edrop_user.callBack;

import android.support.v7.util.DiffUtil;

import net.edrop.edrop_user.entity.Competition;

import java.util.List;

public class MyCallBack extends DiffUtil.Callback {
    private List<Competition> old_data, new_data;

    public MyCallBack(List<Competition> old_data, List<Competition> new_data) {
        this.old_data = old_data;
        this.new_data = new_data;
    }

    @Override
    public int getOldListSize() {
        return old_data.size();
    }

    @Override
    public int getNewListSize() {
        return new_data.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return old_data.get(oldItemPosition).getQuestion() == new_data.get(newItemPosition).getQuestion();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return old_data.get(oldItemPosition).getQuestion().equals( new_data.get(newItemPosition).getQuestion());
    }
}
