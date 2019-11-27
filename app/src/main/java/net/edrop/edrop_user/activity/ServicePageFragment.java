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
 * Time: 16:39
 */
public class ServicePageFragment extends Fragment {
    private static final String SECTION_STRING = "fragment_string";

    public static ServicePageFragment newInstance(String sectionNumber) {
        ServicePageFragment fragment = new ServicePageFragment();
        Bundle args = new Bundle();
        args.putString(SECTION_STRING, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_service_page, container, false);
        return newView;
    }
}
