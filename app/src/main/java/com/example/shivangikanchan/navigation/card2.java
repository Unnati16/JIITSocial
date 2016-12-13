package com.example.shivangikanchan.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.shivangikanchan.navigation.R.drawable.img;

/**
 * Created by Shivangi Kanchan on 18-11-2016.
 */

public class card2 extends AppCompatActivity {
    TextView nametxt,descr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card2);
        nametxt = (TextView) findViewById(R.id.info_text1);
        //img=(ImageView)findViewById(R.id.imageView1);
        descr = (TextView) findViewById(R.id.info_text12);
        Intent i = this.getIntent();
        String name = i.getExtras().getString("DATA");
        String desc = i.getExtras().getString("DESC");
        // int image=i.getExtras().getInt("Image");

        nametxt.setText(name);
        descr.setText(desc);
    }
}