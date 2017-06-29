package com.example.sadanandk.moviereviews;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
   String BASE_URL_POPULAR ="https://api.themoviedb.org/3/movie/top_rated?api_key=b24e007d75e5c16e171c9d4ffceea3b2";// "http://api.themoviedb.org/3/movie/popular?api_key=b24e007d75e5c16e171c9d4ffceea3b2";
    String json_string;
    ProgressDialog pd;
    GridView g1;
    PojoImage pj;
    boolean flag;
    ArrayList<PojoImage> arrayList;
     boolean internet;
    BroadcastReceiver b;
    //  private static final String BASE_URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=b24e007d75e5c16e171c9d4ffceea3b2";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         internet=isOnline();



        pd = new ProgressDialog(this);

        if(savedInstanceState!=null) {
            if (savedInstanceState.getBoolean("flag") == true) {
                BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/top_rated?api_key=b24e007d75e5c16e171c9d4ffceea3b2";
               // checkinternetconn();
                sortMoview();
// Toast.makeText(this, "" + savedInstanceState.getBoolean("flag"), Toast.LENGTH_SHORT).show();
// Toast.makeText(this, "state is not null", Toast.LENGTH_SHORT).show();
            }
            if (savedInstanceState.getBoolean("flag") == false) {
// Toast.makeText(this, "" + savedInstanceState.getBoolean("flag"), Toast.LENGTH_SHORT).show();
// Toast.makeText(this, "state is not null", Toast.LENGTH_SHORT).show();
              //  checkinternetconn();
                BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=b24e007d75e5c16e171c9d4ffceea3b2";

                sortMoview();
            }
        }
        else
        {


            if(internet) {


                sortMoview();
                flag = true;
            }
            else
            {
                Toast.makeText(this, "please check connection!!!!", Toast.LENGTH_SHORT).show();
            }


        }

      //  g1 = (GridView) findViewById(R.id.g1);
      //  arrayList = new ArrayList<>();
        /*System.out.println("0000000");


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_POPULAR).addConverterFactory(GsonConverterFactory.create()).build();
        System.out.println("1111111111");
        *//*retrofit.create(AppConfig.class).getData().enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                System.out.println("2222222222222");

                MovieDetails record = response.body();

                Toast.makeText(getApplicationContext(), record.getPage() + "", Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(), record.getEmployeeList() + "", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), record.getResults().get(0).getTitle() + "", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });*//*
        AppConfig appconfih=retrofit.create(AppConfig.class);

        Call<MovieDetails> ma  =appconfih.getMovieData();

        System.out.println("2222222222222222");
        ma.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response)
            {
                System.out.println("3333333333333333");
                //Toast.makeText(MainActivity.this, ""+response.body().getPage(), Toast.LENGTH_SHORT).show();

                MovieDetails record = response.body();

                System.out.println("5555555555555"+record.getTotalPages());


            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                System.out.println("444444444444444");

            }
        });*/


        /*new MyAsyncTask().execute();
        g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //String id1 = arrayList.get(position).getId();

                Intent i=new Intent(MainActivity.this,DetailsActivity.class);
                i.putExtra("title",arrayList.get(position).getOriginal_title());
                i.putExtra("image",arrayList.get(position).getUrl());
                i.putExtra("rating",arrayList.get(position).getVote_average());
                i.putExtra("releasedate",arrayList.get(position).getRelease_date());
                i.putExtra("overview",arrayList.get(position).getOverview());
                startActivity(i);

            }
        });*/




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.top_rated:
                flag=true;

/*
                g1 = (GridView) findViewById(R.id.g1);
                arrayList = new ArrayList<>();
*/

                BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/top_rated?api_key=b24e007d75e5c16e171c9d4ffceea3b2";
                sortMoview();
                /*new MyAsyncTask().execute();
                g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        //String id1 = arrayList.get(position).getId();

                        Intent i=new Intent(MainActivity.this,DetailsActivity.class);
                        i.putExtra("title",arrayList.get(position).getOriginal_title());
                        i.putExtra("image",arrayList.get(position).getUrl());
                        i.putExtra("rating",arrayList.get(position).getVote_average());
                        i.putExtra("releasedate",arrayList.get(position).getRelease_date());
                        i.putExtra("overview",arrayList.get(position).getOverview());
                        startActivity(i);

                    }
                });*/



                return true;
            case R.id.popular:
                flag=false;
/*
                g1 = (GridView) findViewById(R.id.g1);
                arrayList = new ArrayList<>();
*/




                BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=b24e007d75e5c16e171c9d4ffceea3b2";
                sortMoview();

              /*  new MyAsyncTask().execute();
                g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        //String id1 = arrayList.get(position).getId();

                        Intent i=new Intent(MainActivity.this,DetailsActivity.class);
                        i.putExtra("title",arrayList.get(position).getOriginal_title());
                        i.putExtra("image",arrayList.get(position).getUrl());
                        i.putExtra("rating",arrayList.get(position).getVote_average());
                        i.putExtra("releasedate",arrayList.get(position).getRelease_date());
                        i.putExtra("overview",arrayList.get(position).getOverview());
                        startActivity(i);

                    }
                });*/


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class MyAsyncTask extends AsyncTask<String, String, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("fecthing data from server");
            pd.setTitle("loading");

            pd.show();


        }

        @Override
        protected String doInBackground(String... params) {
            URL url1 = null;
            try {

                    url1 = new URL(BASE_URL_POPULAR);

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
                    String poster = js1.getString("poster_path");
                    String id = js1.getString("id");
                    String original_title = js1.getString("original_title");
                    String vote_average = js1.getString("vote_average");
                    String release_date = js1.getString("release_date");
                    String overview = js1.getString("overview");


                    pj = new PojoImage();
                    pj.setUrl(poster);
                    pj.setId(id);
                    pj.setOriginal_title(original_title);
                    pj.setVote_average(vote_average);

                    pj.setOverview(overview);
                    pj.setRelease_date(release_date);


                    arrayList.add(pj);


                }

                GridViewAdapter ga = new GridViewAdapter(MainActivity.this, R.layout.grid_item, arrayList);
                ga.notifyDataSetChanged();
                g1.invalidateViews();
                g1.setAdapter(ga);

                //  Toast.makeText(MainActivity.this, ""+js.getString("total_results"), Toast.LENGTH_SHORT).show();
               // Toast.makeText(MainActivity.this, "" + js.getString("total_pages"), Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public  void sortMoview()
    {
        g1 = (GridView) findViewById(R.id.g1);
        arrayList = new ArrayList<>();

        new MyAsyncTask().execute();
        g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //String id1 = arrayList.get(position).getId();

                Intent i=new Intent(MainActivity.this,DetailsActivity.class);
                i.putExtra("title",arrayList.get(position).getOriginal_title());
                i.putExtra("image",arrayList.get(position).getUrl());
                i.putExtra("rating",arrayList.get(position).getVote_average());
                i.putExtra("releasedate",arrayList.get(position).getRelease_date());
                i.putExtra("overview",arrayList.get(position).getOverview());
                i.putExtra("id",arrayList.get(position).getId());
                startActivity(i);

            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("flag",flag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        flag = savedInstanceState.getBoolean("flag");
    }
    /*public void checkinternetconn()
    {
        IntentFilter i=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        b=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};

                 *//*if(ConnectivityReceiver.isnetworkavilable(context,type))
                 {
                     return;
                 }else
                 {
                     Toast.makeText(context, "no internet", Toast.LENGTH_SHORT).show();
                 }*//*


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
                        Toast.makeText(context, "nointernet", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
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
    }*/
}


