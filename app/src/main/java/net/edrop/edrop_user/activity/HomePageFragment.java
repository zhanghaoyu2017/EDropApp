package net.edrop.edrop_user.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.edrop.edrop_user.R;

public class HomePageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_home_page, container, false);
        return newView;
    }
}
