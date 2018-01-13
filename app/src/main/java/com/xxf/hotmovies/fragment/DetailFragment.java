package com.xxf.hotmovies.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xxf.hotmovies.Constants;
import com.xxf.hotmovies.R;
import com.xxf.hotmovies.adapter.DetailTrailerAdapter;
import com.xxf.hotmovies.bean.Movie;
import com.xxf.hotmovies.bean.Trailer;
import com.xxf.hotmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xxf.hotmovies.MainActivity.UPDATA_DATA;

/**
 * Created by dell on 2018/1/3.
 */

public class DetailFragment extends Fragment {

    private View view;

    @BindView(R.id.movie_name_detail) TextView mName;
    @BindView(R.id.image_view_detail) ImageView mImageView;
    @BindView(R.id.date_vote_detail) TextView mDateVote;
    @BindView(R.id.overview_detail) TextView mOverview;
    @BindView(R.id.recycler_view_trailer) RecyclerView mTrailerRecyclerView;
    @BindView(R.id.review) TextView mReviews;
    @BindView(R.id.btn_favourite) Button mFavourite;

    private Movie mMovie;
    private URL reviewUrl = null;
    private URL trailerUrl = null;
    private String reviewJsonResponse;
    private String trailerJsonResponse;

    private List<Trailer> mTrailers = new ArrayList<>();

    private DetailTrailerAdapter mDetailTrailerAdapter;

    public static final int UPDATA_TRAILER = 2;

    private String reviews = "";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATA_DATA:
                    mReviews.setText(reviews);
                    break;
                case UPDATA_TRAILER:
                    mDetailTrailerAdapter.setData(mTrailers);
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_fragment,container,false);
        ButterKnife.bind(this,view);

//        String trailerUrl = "https://api.themoviedb.org/3/movie/"+mMovie.getId()+"/videos?language=en-US&api_key="+ Constants.API.API_KEY;
//        String reviewsUrl = "https://api.themoviedb.org/3/movie/"+mMovie.getId()+"/reviews?language=en-US&api_key="+Constants.API.API_KEY;
////        Log.d("ID", trailerUrl);
//        fetchData(reviewsUrl);
////        Log.d("reviewUrl",reviewsUrl);
//        fetchTrailer(trailerUrl);
//        showDetail();
//        initRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Constants.isTwoPane)
        refresh();
        Log.d("istwo2", String.valueOf(Constants.isTwoPane));
    }

    public void refresh(){
        String trailerUrl = "https://api.themoviedb.org/3/movie/"+mMovie.getId()+"/videos?language=en-US&api_key="+ Constants.API.API_KEY;
        String reviewsUrl = "https://api.themoviedb.org/3/movie/"+mMovie.getId()+"/reviews?language=en-US&api_key="+Constants.API.API_KEY;
//        Log.d("ID", trailerUrl);
        fetchData(reviewsUrl);
//        Log.d("reviewUrl",reviewsUrl);
        fetchTrailer(trailerUrl);
        showDetail();
        initRecyclerView();
    }



    @OnClick(R.id.btn_favourite) void setFavourite(){

        Constants.sMovies.add(mMovie);
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(Constants.sMovies);

        SharedPreferences preferences=getActivity().getSharedPreferences(Constants.SHARED_FAVOURITE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("json", json);
        editor.commit();

        Toast.makeText(getActivity(),"加入收藏成功",Toast.LENGTH_SHORT).show();

    }

    private void initRecyclerView() {

        mDetailTrailerAdapter = new DetailTrailerAdapter(mTrailers);
        mTrailerRecyclerView.setAdapter(mDetailTrailerAdapter);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void fetchTrailer(final String httpUrl) {

        if (isOnline()){
            try {
                trailerUrl = new URL(httpUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        trailerJsonResponse = NetworkUtils.getResponseFromHttpUrl(trailerUrl);
                        Log.d("trailerJsonResponse",trailerJsonResponse);
                        parseTrailerJson(trailerJsonResponse);
                        Message message = new Message();
                        message.what = UPDATA_TRAILER;
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }


    }

    public void getMovie(Movie movie){

        mMovie= movie;
    }

    private void showDetail(){

        mName.setText(mMovie.getTitle());
        Picasso.with(getActivity()).load(mMovie.getPoster_path()).into(mImageView);
        mDateVote.setText(mMovie.getRelease_date()+"\n"+mMovie.getVote_average());
        mOverview.setText(mMovie.getOverview());

    }


    private void fetchData(final String httpUrl) {

        if (isOnline()){
            try {
                reviewUrl = new URL(httpUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("reviewhttpURL",httpUrl);
//                        Log.d("reviewURL", String.valueOf(reviewUrl));
                        reviewJsonResponse = NetworkUtils.getResponseFromHttpUrl(reviewUrl);
//                        Log.d("review",reviewJsonResponse);
                        parseJson(reviewJsonResponse);
                        Message message = new Message();
                        message.what = UPDATA_DATA;
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }

    private void parseJson(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++){
            JSONObject review = results.getJSONObject(i);
            reviews = reviews+review.getString("author")+"  :\n";
//            Log.d("author",review.getString("author"));
            if (i+1 == results.length()){
                reviews = reviews+review.getString("content");
            }else {
                reviews = reviews+review.getString("content")+"\n\n\n";
            }
        }
        Log.d("review",reviews);

    }

    private void parseTrailerJson(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++){
            JSONObject result = results.getJSONObject(i);
            Trailer trailer = new Trailer();
            trailer.setKey(result.getString("key"));
            Log.d("trailer",result.getString("key"));
            mTrailers.add(trailer);
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }



}
