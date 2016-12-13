package com.example.shivangikanchan.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.shivangikanchan.navigation.Fragment.Subscribed;

/**
 * Created by Shivangi Kanchan on 03-12-2016.
 */

public class Subscribed1 extends AppCompatActivity {
    android.support.v4.app.FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed1);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new Subscribed()).commit();
    }
}
