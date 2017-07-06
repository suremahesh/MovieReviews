package com.example.sadanandk.moviereviews;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/";// "http://api.themoviedb.org/3/movie/popular?api_key=b24e007d75e5c16e171c9d4ffceea3b2";
    String json_string;
    ProgressDialog pd;
    GridView g1;
    PojoImage pj;
    boolean flag;
    ArrayList<PojoImage> arrayList;
    boolean internet;
    BroadcastReceiver b;
    String popular = "popular",top_rated ="top_rated";
    //  private static final String BASE_URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=b24e007d75e5c16e171c9d4ffceea3b2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internet = isOnline();
        pd = new ProgressDialog(this);
        g1 = (GridView) findViewById(R.id.g1);
        if (savedInstanceState != null) {
            int count = savedInstanceState.getInt("count");
            if (savedInstanceState.getBoolean("flag")) {
                g1.setSelection(count);
                sortMoview(top_rated);
            }
            if (!savedInstanceState.getBoolean("flag")) {

                g1.setSelection(count);
                sortMoview(popular);
            }
        } else {
            if (internet) {
                sortMoview(top_rated);
                flag = true;
            } else {
                Toast.makeText(this, "please check connection!!!!", Toast.LENGTH_SHORT).show();
            }
        }
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
                flag = true;
                sortMoview(top_rated);



                return true;
            case R.id.popular:
                flag = false;
                sortMoview(popular);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void sortMoview(String path)
    {

        g1 = (GridView) findViewById(R.id.g1);
        arrayList = new ArrayList<>();
       // new MyAsyncTask().execute();
        getData(path);

        g1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String id1 = arrayList.get(position).getId();

                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("title", arrayList.get(position).getOriginal_title());
                i.putExtra("image", arrayList.get(position).getUrl());
                i.putExtra("rating", arrayList.get(position).getVote_average());
                i.putExtra("releasedate", arrayList.get(position).getRelease_date());
                i.putExtra("overview", arrayList.get(position).getOverview());
                i.putExtra("id", arrayList.get(position).getId());
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

        outState.putBoolean("flag", flag);
        int cout = g1.getFirstVisiblePosition();
        outState.putInt("count", cout);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        flag = savedInstanceState.getBoolean("flag");
    }
    void getData(String path)
    {
        pd.show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL_POPULAR).addConverterFactory(GsonConverterFactory.create()).build();
        retrofit.create(AppConfig.class).getMovieData(path).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                List<Result> record = response.body().getResults();
                for (Result ac : record) {
                    pj = new PojoImage();
                    pj.setUrl(ac.getPosterPath());
                    pj.setId(ac.getId().toString());
                    pj.setOriginal_title(ac.getOriginalTitle());
                    pj.setVote_average(ac.getVoteAverage().toString());

                    pj.setOverview(ac.getOverview());
                    pj.setRelease_date(ac.getReleaseDate());
                    arrayList.add(pj);
                }
                GridViewAdapter ga = new GridViewAdapter(MainActivity.this, R.layout.grid_item, arrayList);
                ga.notifyDataSetChanged();
                g1.invalidateViews();
                g1.setAdapter(ga);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                // Toast.makeText(getApplicationContext()," on failure" +t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(" on failure",t.getMessage());
            }
        });
    }
}

