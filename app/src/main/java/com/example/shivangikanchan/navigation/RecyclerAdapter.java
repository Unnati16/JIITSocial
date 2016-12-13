package com.example.shivangikanchan.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import static android.media.CamcorderProfile.get;
import static com.example.shivangikanchan.navigation.R.id.button;


/**
 * Created by Shivangi Kanchan on 05-10-2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
       private String[] mDataset;
    private String[] mDataset1;
    private int[] mDataset2;
    private Context context;


    public RecyclerAdapter(Context context, String[] myDataset,String[] myDataset1,int[] myDataset2) {

        this.context = context;
        mDataset = myDataset;
        mDataset2=myDataset2;
        mDataset1 = myDataset1;

    }

    @Override


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
       View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler,parent,false);
      ImageView  button = (ImageView) v.findViewById(R.id.imageView4);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Perform action on click
                addCalendarEvent();
            }

        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void addCalendarEvent(){

        Calendar cal = Calendar.getInstance();
        Intent intent =new Intent(Intent.ACTION_EDIT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "JIIT Social");
        intent.putExtra("description", "Mark Your events");
        intent.putExtra("eventLocation", "Geolocation");
        context.startActivity(intent);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.info.setText(mDataset[position]);
     // final String name=androidversions.get(position).getAndroid_version_name();
       // Log.e("hhh","onbindviewholder");
        holder.info1.setText(mDataset1[position]);
      //Picasso.with(context).load(mDataset2[position]).resize(240, 120).into(holder.iv);
        holder.iv.setImageResource(mDataset2[position]);
      // final int image=androidversions.get(position).getAndroid_image_url();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView info;
         TextView info1;
        ImageView iv;


        public ViewHolder(View itemView){
            super(itemView);
            info=(TextView)itemView.findViewById(R.id.info_text);
            info1=(TextView)itemView.findViewById(R.id.info_text1);
            iv=(ImageView)itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                  opendetail(info.getText().toString(),info1.getText().toString());
                }
            });
        }


    }
    private void opendetail(String name,String data)
    {
        Intent i= new Intent(context,card1.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("DATA",name);
        i.putExtra("Data1",data);
        context.startActivity(i);
    }
}
