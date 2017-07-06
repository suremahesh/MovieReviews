package com.example.sadanandk.moviereviews;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by maheshs on 6/29/2017.
 */

public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.MyViewHolder> {
    Context context;
    int card_view;
    ArrayList<PojoImage> al;

    RecyclerMainAdapter(Context context, int card_view, ArrayList<PojoImage> al)
    {
        this.context = context;
        this.card_view = card_view;
        this.al = al;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
       // TextView tv_merch,tv_date,tv_amount;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.i1);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(card_view,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Drawable d = ContextCompat.getDrawable(context, R.drawable.second);
      //  holder.iv.setImageResource(Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+al.get(position).getUrl()));
//holder.tv_date.setText(al.get(position).getDate());
        //  holder.tv_date.setText(al.get(position).getDate());

        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize==Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + al.get(position).getUrl()).into(holder.iv);
        }
        else
        {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + al.get(position).getUrl()).into(holder.iv);
        }


    }

    @Override
    public int getItemCount() {
        return al.size();
    }



}
