package com.example.shivangikanchan.navigation;

/**
 * Created by Richa on 30-Nov-16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    private String[] mDataset;
    public String[][] mDataset1;
    public String[][] mDataset2;
    public int[] ll;
    private Context context;
    private int pos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter2(Context context,String[] myDataset,String[][] myDataset1,String[][] myDataset2,int[] len1) {
        mDataset = myDataset;
        mDataset1=myDataset1;
        mDataset2=myDataset2;
        ll=len1;
        this.context=context;
      //  Log.e("unni",myDataset1[0][0]);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
        String op=Integer.toString(position);
        holder.mTextView1.setText(op);
        pos=position;
        String pp=Integer.toString(pos);
        Log.e("position",pp);

        // holder.mTextView1.setText(mDataset1[position][]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView mTextView1;
        public TextView mTextView2;


        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            mTextView1 = (TextView) v.findViewById(R.id.tv_blah);
            // mTextView2 = (TextView) v.findViewById(R.id.tv_len);
            v.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    opendetail(mTextView.getText().toString(),mTextView1.getText().toString());
                }
            });
        }

    }
    private  void opendetail(String name,String pos)
    {   //Log.e("unnati",mDataset1[pos][0]);
        Intent i= new Intent(context,listview.class);
        Bundle mbundle=new Bundle();
        /// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("DATA",name);
        mbundle.putSerializable("names",mDataset1);
        i.putExtras(mbundle);
        i.putExtra("position",pos);
        Bundle b=new Bundle();
        b.putSerializable("posts",mDataset2);
        i.putExtras(b);
        // i.putExtra("length",len);
        // i.putExtra("posts",mDataset2);
        //i.putExtra("Image",image);
        context.startActivity(i);
    }
}


