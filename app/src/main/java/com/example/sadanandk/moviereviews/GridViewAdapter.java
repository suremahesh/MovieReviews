package com.example.sadanandk.moviereviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sadanandk on 6/27/2017.
 */

public class GridViewAdapter extends BaseAdapter
{
    Context context;
    int xml_file;
    ArrayList<PojoImage> pj;
    GridViewAdapter(Context context, int xml_file,ArrayList<PojoImage> pj)
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

        ImageView iv = (ImageView) v.findViewById(R.id.i1);
        TextView tv = (TextView) v.findViewById(R.id.movie_tital);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+pj.get(position).getUrl()).into(iv);
        tv.setText(pj.get(position).getId());
      //  Toast.makeText(context, ""+pj.size(), Toast.LENGTH_SHORT).show();
        // http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        return v;
    }
}
