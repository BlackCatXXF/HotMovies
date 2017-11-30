package com.xxf.hotmovies;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.xxf.hotmovies.adapter.HomeAdapter;
import com.xxf.hotmovies.bean.Movie;
import com.xxf.hotmovies.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int UPDATA_DATA = 1;

    private RecyclerView mRecyclerView;

    private String jsonResponse;

    private URL url = null;

    private List<Movie> mMovies = new ArrayList<>();

    private HomeAdapter mHomeAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATA_DATA:
                    initRecyclerView();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fetchData(Constants.API.MOVIE_POPULAR);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular_movie:
                fetchData(Constants.API.MOVIE_POPULAR);
                mHomeAdapter.setData(mMovies);
                break;
            case R.id.top_rated_movie:
                fetchData(Constants.API.MOVIE_TOP);
                mHomeAdapter.setData(mMovies);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(){

        mHomeAdapter = new HomeAdapter(mMovies);
        mRecyclerView.setAdapter(mHomeAdapter);
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    private void fetchData(String httpUrl){

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
                    if (mMovies != null){
                        mMovies.clear();
                    }
                    parseJson(jsonResponse);
                    Message message = new Message();
                    message.what = UPDATA_DATA;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void parseJson(String json) throws JSONException {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i=0;i<results.length();i++){
                JSONObject movie = results.getJSONObject(i);
                long id = movie.getLong("id");
                String title = movie.getString("title");
                String poster_path = Constants.API.POSTER_PATH+movie.getString("poster_path");
                String overview = movie.getString("overview");
                Double vote_average = movie.getDouble("vote_average");


            Movie movie1 = new Movie();
            movie1.setId(id);
            movie1.setOverview(overview);
            movie1.setPoster_path(poster_path);
            movie1.setTitle(title);
            movie1.setVote_average(vote_average);
//            Log.d("title",title);
            mMovies.add(movie1);
        }

    }

}
