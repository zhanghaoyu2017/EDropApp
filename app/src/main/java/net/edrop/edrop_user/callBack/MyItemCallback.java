package net.edrop.edrop_user.callBack;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import net.edrop.edrop_user.entity.Problems;

public class MyItemCallback extends DiffUtil.ItemCallback<Problems> {


    @Override
    public boolean areItemsTheSame(@NonNull Problems oldItem, @NonNull Problems newItem) {
        return TextUtils.equals(oldItem.getQuestion(), newItem.getQuestion());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Problems oldItem, @NonNull Problems newItem) {
        return TextUtils.equals(oldItem.getQuestion(), newItem.getQuestion());
    }
}
