package com.example.shivangikanchan.navigation;

/**
 * Created by Unnati Arora on 18-11-2016.
 */




import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {
    private String[] mDataset;
    private String[] mDataset1;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter1(Context context,String[] myDataset,String[] myDataset1) {
        mDataset = myDataset;
        mDataset1=myDataset1;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent,
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
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView mTextView1;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
            mTextView1=(TextView)v.findViewById(R.id.tv_blah);
            v.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    opendetail(mTextView.getText().toString(),mTextView1.getText().toString());
                }
            });
        }

    }
    private  void opendetail(String name,String comment)
    {
        Intent i= new Intent(context,card4.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("DATA",name);
        i.putExtra("comment",comment);
        context.startActivity(i);
    }
}


