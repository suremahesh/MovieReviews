package com.example.sadanandk.moviereviews;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

     ProgressDialog pd;
     String json_string;
    ArrayList<PojoReview> al;
    ListView lvreview;
    String id;
    LinearLayout ll2;

    BroadcastReceiver b;
     ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        ll2= (LinearLayout) findViewById(R.id.ll2);

        lvreview= (ListView) findViewById(R.id.lv_review);
        image=(ImageView)findViewById(R.id.image_not);
        Intent i=getIntent();
        Bundle b=i.getExtras();
       id= b.getString("id");
      //  Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        al=new ArrayList<>();
        checkinternetconn();
        new MyAsynctask1().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

    class MyAsynctask1 extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pd = new ProgressDialog(ReviewActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("fecthing data from server");
            pd.setTitle("loading");

            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            URL url1;
            try {
                url1 = new URL("https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=7a2a50af24de2babb36f18505f377efb");

                URLConnection con = url1.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.connect();

                InputStream is = http.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                json_string = br.readLine();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return json_string;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject js = new JSONObject(s);
                JSONArray ja = js.getJSONArray("results");
                String totalresults = js.getString("total_results");

                if(totalresults.equals("0"))
                {
//                    Toast.makeText(ReviewActivity.this, "No Revweis Available", Toast.LENGTH_SHORT).show();
                    image.setVisibility(View.VISIBLE);




                }

                else {
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject js1 = ja.getJSONObject(i);
                        String content = js1.getString("content");
                        String author = js1.getString("author");


                        PojoReview p = new PojoReview();

                        p.setAuthor(author);
                        p.setReview(content);
                        al.add(p);


                    }
                    ReviewAdapter r = new ReviewAdapter(ReviewActivity.this, R.layout.reviewitem, al);
                    lvreview.setAdapter(r);

                }


            }catch (JSONException e) {
                e.printStackTrace();

            }

        }





    }
    public void checkinternetconn()
    {
        IntentFilter i=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        b=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};

                 /*if(ConnectivityReceiver.isnetworkavilable(context,type))
                 {
                     return;
                 }else
                 {
                     Toast.makeText(context, "no internet", Toast.LENGTH_SHORT).show();
                 }*/


                ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                for(int typnetwork : type)
                {
                    NetworkInfo ni=cm.getNetworkInfo(typnetwork);
                    if(ni!=null && ni.getState()== NetworkInfo.State.CONNECTED)
                    {
                        return;
                    }
                    else
                    {
                       // Toast.makeText(context, "nointernet", Toast.LENGTH_SHORT).show();

                        pd.dismiss();
                        Snackbar.make(ll2,"No Internet..",Snackbar.LENGTH_LONG).show();
                    }


                }

            }
        };
        registerReceiver(b,i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(b);
    }


}
