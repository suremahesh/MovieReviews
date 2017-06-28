package com.example.sadanandk.moviereviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sadanandk on 6/27/2017.
 */

public interface AppConfig {
    @POST("top_rated?api_key=b24e007d75e5c16e171c9d4ffceea3b2")
    Call<MovieDetails> getMovieData();
}
