package net.edrop.edrop_user.callBack;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import net.edrop.edrop_user.entity.Competition;

public class MyItemCallback extends DiffUtil.ItemCallback<Competition> {


    @Override
    public boolean areItemsTheSame(@NonNull Competition oldItem, @NonNull Competition newItem) {
        return TextUtils.equals(oldItem.getQuestion(), newItem.getQuestion());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Competition oldItem, @NonNull Competition newItem) {
        return TextUtils.equals(oldItem.getQuestion(), newItem.getQuestion());
    }
}
