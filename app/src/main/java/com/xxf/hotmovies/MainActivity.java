package com.xxf.hotmovies;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    private RecyclerView mRecyclerView;

    private String jsonResponse;

    private URL url = null;

    private List<Movie> mMovies = new ArrayList<>();

    private HomeAdapter mHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        requestData();
        // 下面的这些操作要等网络请求完成之后再去做，jsonResponse 现在的值是 null，因为它还没有被初始化
//        try {
//            parseJson(jsonResponse);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        initRecyclerView();


    }

    private void initRecyclerView(){

        mHomeAdapter = new HomeAdapter(mMovies);
        Log.d("mHomeAdapter",mHomeAdapter.toString());
        mRecyclerView.setAdapter(mHomeAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    private void requestData(){

        Uri uri = Uri.parse(Constants.API.MOVIE_POPULAR);
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);

                    // 这时候 jsonResponse 已经初始化好了，可以开始进行数据解析了
                    parseJson(jsonResponse);
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

        // json 已经解析成 mMovies，可以更新 ui 了
        // 注意哦，因为这个方法是在其它线程调用的，所以现在它不在主线程，我们要在主线程进行 ui 的更新操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
            }
        });
    }

}
