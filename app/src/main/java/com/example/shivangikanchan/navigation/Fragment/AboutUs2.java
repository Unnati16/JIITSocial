package com.example.shivangikanchan.navigation.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivangikanchan.navigation.R;

/**
 * Created by Shivangi Kanchan on 15-10-2016.
 */

public class AboutUs2 extends Fragment {
    public AboutUs2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us2, container, false);
    }
}
