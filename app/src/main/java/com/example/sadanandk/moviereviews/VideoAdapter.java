package com.example.sadanandk.moviereviews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sadanandk on 6/28/2017.
 */

public class VideoAdapter extends BaseAdapter {
    Context context;
    int xml_view;
    ArrayList<PojoVideo> arrayList1;

    VideoAdapter(Context context , int xml_view, ArrayList<PojoVideo> arrayList1)
    {
        this.context = context;
        this.xml_view = xml_view;
        this.arrayList1 = arrayList1;
    }
    @Override
    public int getCount() {
        return arrayList1.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater li=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=li.inflate(xml_view,parent,false);

        TextView tv = (TextView) v.findViewById(R.id.name);

        tv.setText(arrayList1.get(position).getType());
        Log.e("size",""+arrayList1.size());

        //  Toast.makeText(context, ""+pj.size(), Toast.LENGTH_SHORT).show();
        // http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        return v;
    }
}
