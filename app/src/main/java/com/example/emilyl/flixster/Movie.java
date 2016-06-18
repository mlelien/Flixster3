package com.example.emilyl.flixster;

import android.content.res.Configuration;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by emilyl on 6/15/16.
 */
public class Movie {
    private String posterPath;
    private String title;
    private String overview;
    private double rating;
    private int popularity;
    private String id;


    public Movie(JSONObject jsonObject, boolean isPortrait) throws JSONException {
        if (isPortrait)
            this.posterPath = jsonObject.getString("poster_path");
        else
            this.posterPath = jsonObject.getString("backdrop_path");
        this.title = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getDouble("vote_average");
        this.popularity = (int) jsonObject.getDouble("popularity");
        this.id = jsonObject.getString("id");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray, boolean isPortrait) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new Movie(jsonArray.getJSONObject(i), isPortrait));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating;}

    public int getPopularity() { return popularity; }

    public String getID() { return id; }
}
