package com.example.sadanandk.moviereviews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

     ProgressDialog pd;
     String json_string;
    ArrayList<PojoReview> al;
    ListView lvreview;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        lvreview= (ListView) findViewById(R.id.lv_review);
        Intent i=getIntent();
        Bundle b=i.getExtras();
       id= b.getString("id");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        al=new ArrayList<>();
        new MyAsynctask1().execute();
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
            URL url1 = null;
            try {
                url1 = new URL("https://api.themoviedb.org/3/movie/297762/reviews?api_key=7a2a50af24de2babb36f18505f377efb");

                URLConnection con = url1.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.connect();

                InputStream is = http.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                json_string = br.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject js1 = ja.getJSONObject(i);
                    String content = js1.getString("content");
                    String author = js1.getString("author");

                    PojoReview p=new PojoReview();

                    p.setAuthor(author);
                    p.setReview(content);
                    al.add(p);


                }
                ReviewAdapter r=new ReviewAdapter(ReviewActivity.this,R.layout.reviewitem,al);
                lvreview.setAdapter(r);


            }catch (JSONException e) {
                e.printStackTrace();

            }

        }



    }
}
