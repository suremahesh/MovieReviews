package com.example.sadanandk.moviereviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by maheshs on 6/28/2017.
 */

public class ReviewAdapter extends BaseAdapter
{
    Context context;
    int xml_file;
    ArrayList<PojoReview> pj;
    ReviewAdapter(Context context, int xml_file,ArrayList<PojoReview> pj)
    {
        this.context = context;
        this.xml_file = xml_file;
        this.pj = pj;
    }
    @Override
    public int getCount() {
        return pj.size();
    }

    @Override
    public Object getItem(int position) {
        return pj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater li=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=li.inflate(xml_file,parent,false);


        //  Toast.makeText(context, ""+pj.size(), Toast.LENGTH_SHORT).show();
        // http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        TextView review=(TextView)v.findViewById(R.id.t1);
        TextView author=(TextView)v.findViewById(R.id.t2);

        review.setText(pj.get(position).getReview());
        author.setText(pj.get(position).getAuthor());

        return v;
    }
}
