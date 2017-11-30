package com.xxf.hotmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xxf.hotmovies.bean.Movie;

/**
 * Created by dell on 2017/11/30.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView mName;
    private ImageView mImageView;
    private TextView mDateVote;
    private TextView mOverview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        showDetail();
    }

    private void init(){

        mName = (TextView) findViewById(R.id.movie_name_detail);
        mImageView = (ImageView) findViewById(R.id.image_view_detail);
        mDateVote = (TextView) findViewById(R.id.date_vote_detail);
        mOverview = (TextView) findViewById(R.id.overview_detail);

    }

    private Movie getMovie(){
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("movie");
        return movie;
    }

    private void showDetail(){

        Movie movie = getMovie();
        mName.setText(movie.getTitle());
        Picasso.with(this).load(movie.getPoster_path()).into(mImageView);
        mDateVote.setText(movie.getRelease_date()+"\n"+movie.getVote_average());
        mOverview.setText(movie.getOverview());

    }
}
