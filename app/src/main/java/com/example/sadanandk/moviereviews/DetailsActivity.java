package com.example.sadanandk.moviereviews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class DetailsActivity extends AppCompatActivity {

    TextView title;
    ImageView image_detail;
    TextView releasedate;
    TextView rating;
    TextView overview;
    ListView lv;

    ProgressDialog pd;
    String json_string;
    String movie_id;
    ArrayList<PojoVideo> arrayListVideo;

    //this is code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);


        title = (TextView) findViewById(R.id.title_detail);
        lv = (ListView) findViewById(R.id.lv);
        image_detail = (ImageView) findViewById(R.id.image_detail);

        rating = (TextView) findViewById(R.id.rating);
        releasedate = (TextView) findViewById(R.id.releasedate);
        overview = (TextView) findViewById(R.id.overview);

        arrayListVideo = new ArrayList<>();


        Intent i = getIntent();
        Bundle b = i.getExtras();
        title.setText(b.getString("title"));
        rating.setText(b.getString("rating"));
        releasedate.setText(b.getString("releasedate"));
        overview.setText(b.getString("overview"));
        movie_id = b.getString("id");

        Log.e("asdhfjasd", movie_id);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + b.getString("image")).into(image_detail);

        System.out.println("sasa111111111");

        new MYAsyncTask().execute();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(DetailsActivity.this, "you click "+position, Toast.LENGTH_SHORT).show();
                //Toast.makeText(DetailsActivity.this, ""+arrayListVideo.get(position).getKey(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + arrayListVideo.get(position).getKey())));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reviewmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.revies:

                Intent i = new Intent(this, ReviewActivity.class);
                i.putExtra("id",movie_id);
                startActivity(i);
                return true;



        }
        return super.onOptionsItemSelected(item);

    }

        class MYAsyncTask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                System.out.println("sas222222222222");

                super.onPreExecute();
                pd = new ProgressDialog(DetailsActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("fecthing data from server");
                pd.setTitle("loading");

                pd.show();
            }

            @Override
            protected String doInBackground(String... params) {
                URL url1 = null;
                try {
                    url1 = new URL("https://api.themoviedb.org/3/movie/" + movie_id + "/videos?api_key=7a2a50af24de2babb36f18505f377efb");

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
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pd.dismiss();
                try {
                    JSONObject js = new JSONObject(s);
                    JSONArray ja = js.getJSONArray("results");

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject js1 = ja.getJSONObject(i);
                        String key1 = js1.getString("key");
                        System.out.println("this is key" + key1);
                        String type1 = js1.getString("type");
                        PojoVideo pv = new PojoVideo();
                        pv.setKey(key1);
                        pv.setType(type1);
                        arrayListVideo.add(pv);
                    }

                    VideoAdapter va = new VideoAdapter(DetailsActivity.this, R.layout.video_item, arrayListVideo);
                    lv.setAdapter(va);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

}

