package com.example.emilyl.flixster;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);

        ImageView poster = (ImageView) findViewById(R.id.ivMovieImage);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        TextView overview = (TextView) findViewById(R.id.tvOverview);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Picasso.with(this).load(getIntent().getStringExtra("posterPath")).transform(new RoundedCornersTransformation(10, 10)).fit().centerCrop()
                .into(poster);
        title.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));
        float rating = (float) getIntent().getDoubleExtra("rating", 0.0);
        ratingBar.setRating(rating);
        progressBar.setProgress(getIntent().getIntExtra("popularity", 0));
    }

}
