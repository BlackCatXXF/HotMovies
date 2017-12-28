package com.xxf.hotmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xxf.hotmovies.bean.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/11/30.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_name_detail) TextView mName;
    @BindView(R.id.image_view_detail) ImageView mImageView;
    @BindView(R.id.date_vote_detail) TextView mDateVote;
    @BindView(R.id.overview_detail) TextView mOverview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        showDetail();
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
