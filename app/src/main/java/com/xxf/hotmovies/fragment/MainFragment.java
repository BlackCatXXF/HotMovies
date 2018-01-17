package com.xxf.hotmovies.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxf.hotmovies.Constants;
import com.xxf.hotmovies.R;
import com.xxf.hotmovies.adapter.HomeAdapter;
import com.xxf.hotmovies.bean.Movie;
import com.xxf.hotmovies.data.MovieContract;
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

import static com.xxf.hotmovies.Constants.FAVOURITE_LIST;
import static com.xxf.hotmovies.Constants.ORTHER_LIST;
import static com.xxf.hotmovies.MainActivity.UPDATA_DATA;

/**
 * Created by dell on 2018/1/3.
 */

public class MainFragment extends Fragment {


    public boolean isTwoPane;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessage;

    private String jsonResponse;

    private URL url = null;

    private List<Movie> mMovies = new ArrayList<>();

    public HomeAdapter mHomeAdapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATA_DATA:
                    mHomeAdapter.setData(mMovies,ORTHER_LIST);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_frag,container,false);
        ButterKnife.bind(this,view);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("list",Context.MODE_PRIVATE);
        sharedPreferences = preferences;
        editor = preferences.edit();

        setHasOptionsMenu(true);


        initRecyclerView();
        int list_order = preferences.getInt("list",1);
        if (list_order == 1){
            if (isOnline()){
                fetchData(Constants.API.MOVIE_POPULAR);
            }else {
                offlinePopular();
            }

        }else if (list_order == 2){
            if (isOnline()){
                fetchData(Constants.API.MOVIE_TOP);
            }else {
                offlineTop();
            }

        }else {
            getFavouriteData();
            mHomeAdapter.setData(mMovies,FAVOURITE_LIST);
        }
        return view;
    }

    private void offlinePopular(){
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.POPULAR_CONTENT_URI,null,null,null,null);
        if (cursor != null){
            if (mMovies != null){
                mMovies.clear();
            }
            while (cursor.moveToNext()){
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATA)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                String vote = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE));
                movie.setVote_average(vote);


                mMovies.add(movie);
            }
            mHomeAdapter.setData(mMovies,ORTHER_LIST);
        }else {
            Log.d("cursor","getFavouriteData()的cursor为空");
        }
    }

    private void offlineTop(){
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.TOP_CONTENT_URI,null,null,null,null);
        if (cursor != null){

            if (mMovies!= null){
                mMovies.clear();
            }
            while (cursor.moveToNext()){
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATA)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                String vote = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE));
                movie.setVote_average(vote);

                mMovies.add(movie);
            }
            mHomeAdapter.setData(mMovies,ORTHER_LIST);
        }else {
            Log.d("cursor","getFavouriteData()的cursor为空");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().findViewById(R.id.detail_layout) != null){
            isTwoPane = true;//双页模式
            Constants.isTwoPane = isTwoPane;
        }else {
            isTwoPane = false;//单页模式
            Constants.isTwoPane = isTwoPane;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sort,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_movie:
                if (isOnline()){
                    fetchData(Constants.API.MOVIE_POPULAR);
                }else {
                    offlinePopular();
                }
                editor.putInt("list",1);
                editor.commit();
                break;
            case R.id.top_rated_movie:
                if (isOnline()){
                    fetchData(Constants.API.MOVIE_TOP);
                }else {
                    offlineTop();
                }
                editor.putInt("list",2);
                editor.commit();
                break;
            case R.id.favourite_movie:
                getFavouriteData();
                mHomeAdapter.setData(mMovies,FAVOURITE_LIST);

                editor.putInt("list",3);
                editor.commit();
                //重新设置adapter来显示收藏电影
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getFavouriteData(){

//        SharedPreferences preferences=getActivity().getSharedPreferences(Constants.SHARED_FAVOURITE, Context.MODE_PRIVATE);
//        String json = preferences.getString("json","");
//        if (json != ""){
//            Gson gson = new Gson();
//            mMovies = gson.fromJson(json, new TypeToken<List<Movie>>(){}.getType());
//        }else {
//            Log.d("json","获取的本地json为空");
//        }
        if (mMovies != null){
            mMovies.clear();
        }
        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,null,null,null);
        if (cursor != null){

            while (cursor.moveToNext()){
                Movie movie = new Movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATA)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
                String vote = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE));
                movie.setVote_average(vote);

                mMovies.add(movie);
            }
        }else {
            Log.d("cursor","getFavouriteData()的cursor为空");
        }

    }

    private void initRecyclerView() {

        mHomeAdapter = new HomeAdapter(mMovies,getActivity());
        mRecyclerView.setAdapter(mHomeAdapter);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    private void fetchData(final String httpUrl) {

        if (isOnline()){
            try {
                url = new URL(httpUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                        if (mMovies != null) {
                            mMovies.clear();
                        }
                        if (httpUrl == Constants.API.MOVIE_POPULAR){
                            parseJson(jsonResponse,Constants.API.MOVIE_POPULAR);
                        }else {
                            parseJson(jsonResponse,Constants.API.MOVIE_TOP);
                        }

                        Message message = new Message();
                        message.what = UPDATA_DATA;
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }else {
            mRecyclerView.setVisibility(View.GONE);
            mErrorMessage.setVisibility(View.VISIBLE);
        }


    }

    private void parseJson(String json,String type) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            long id = movie.getLong("id");
            String title = movie.getString("title");
            String poster_path = Constants.API.POSTER_PATH + movie.getString("poster_path");
            String overview = movie.getString("overview");
            String vote_average = movie.getDouble("vote_average")+"";
            String release_date = movie.getString("release_date");


            Movie movie1 = new Movie();
            movie1.setId(id);
            movie1.setOverview(overview);
            movie1.setPoster_path(poster_path);
            movie1.setTitle(title);
            movie1.setVote_average(vote_average);
            movie1.setRelease_date(release_date);
            mMovies.add(movie1);

            ContentValues contentValues = new ContentValues();
            //缓存进数据库
            if (type == Constants.API.MOVIE_POPULAR){
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME,title);
                contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE,poster_path);
                contentValues.put(MovieContract.MovieEntry.COLUMN_DATA,release_date);
                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE,vote_average);
                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,overview);
                getActivity().getContentResolver().insert(MovieContract.MovieEntry.POPULAR_CONTENT_URI,contentValues);
            }else if (type == Constants.API.MOVIE_TOP){
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME,title);
                contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE,poster_path);
                contentValues.put(MovieContract.MovieEntry.COLUMN_DATA,release_date);
                contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE,vote_average);
                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,overview);
                getActivity().getContentResolver().insert(MovieContract.MovieEntry.TOP_CONTENT_URI,contentValues);
            }


        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }



}
