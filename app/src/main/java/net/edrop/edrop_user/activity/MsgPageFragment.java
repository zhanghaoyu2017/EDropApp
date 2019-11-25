package net.edrop.edrop_user.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.edrop.edrop_user.R;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/25
 * Time: 16:40
 */
public class MsgPageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_msg_page, container, false);
        return newView;
    }
}
