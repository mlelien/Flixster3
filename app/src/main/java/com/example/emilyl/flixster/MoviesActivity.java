package com.example.emilyl.flixster;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MoviesAdapter movieAdapter;
    ListView lvMovies;
    //VideoView videoView;

    String baseURL = "https://api.themoviedb.org/3";
    String apiKey = "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movies = new ArrayList<>();
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        //videoView = (VideoView) findViewById(R.id.videoView);
        movieAdapter = new MoviesAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);
        setUpListViewListener();

        // 1. Get the actual movie
        String url = baseURL + "/movie/now_playing" + apiKey;
        String videoURL = "";
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
                    movies.addAll(Movie.fromJSONArray(movieJsonResults, isPortrait));
                    String videoURL = getVideoURL(movies.get(0), baseURL);
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "failure");
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void setUpListViewListener() {
        Log.d("clicked", "clicked");
        lvMovies.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MoviesActivity.this, DetailedActivity.class);
                        Movie currentMovie = movies.get(position);
                        i.putExtra("title", currentMovie.getTitle());
                        i.putExtra("overview", currentMovie.getOverview());
                        i.putExtra("posterPath", currentMovie.getPosterPath());
                        i.putExtra("rating", currentMovie.getRating());
                        i.putExtra("popularity", currentMovie.getPopularity());
                        startActivity(i);
                    }
                });
    }

    private String getVideoURL(Movie movie, String baseURL) {
        String id = movie.getID();
        client.get(baseURL + "/movie/" + id + "/videos" + apiKey, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }
        });
        return "";
    }
}
