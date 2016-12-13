package com.example.shivangikanchan.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class card1 extends AppCompatActivity {
     TextView nametxt;
    TextView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card1);
        nametxt=(TextView)findViewById(R.id.imageView1);
        img=(TextView)findViewById(R.id.info_text1);
        Intent i=this.getIntent();
        String name=i.getExtras().getString("DATA");
        String image=i.getExtras().getString("Data1");

        nametxt.setText(name);
        img.setText(image);
    }
}
