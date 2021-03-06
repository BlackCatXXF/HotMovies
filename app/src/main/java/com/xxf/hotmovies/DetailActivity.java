package com.xxf.hotmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dell on 2017/11/30.
 */

public class DetailActivity extends AppCompatActivity {

//    @BindView(R.id.movie_name_detail) TextView mName;
//    @BindView(R.id.image_view_detail) ImageView mImageView;
//    @BindView(R.id.date_vote_detail) TextView mDateVote;
//    @BindView(R.id.overview_detail) TextView mOverview;
//    @BindView(R.id.recycler_view_trailer) RecyclerView mTrailerRecyclerView;
//    @BindView(R.id.review) TextView mReviews;
//    @BindView(R.id.btn_favourite) Button mFavourite;
//
//    private Movie mMovie;
//    private URL reviewUrl = null;
//    private URL trailerUrl = null;
//    private String reviewJsonResponse;
//    private String trailerJsonResponse;
//
//    private List<Trailer> mTrailers = new ArrayList<>();
//
//    private DetailTrailerAdapter mDetailTrailerAdapter;
//
//    public static final int UPDATA_TRAILER = 2;
//
//    private String reviews = "";
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case UPDATA_DATA:
//                    mReviews.setText(reviews);
//                    break;
//                case UPDATA_TRAILER:
//                    mDetailTrailerAdapter.setData(mTrailers);
//                    break;
//
//            }
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        ButterKnife.bind(this);
//
//
//        getMovie();
//        String trailerUrl = "https://api.themoviedb.org/3/movie/"+mMovie.getId()+"/videos?language=en-US&api_key="+Constants.API.API_KEY;
//        String reviewsUrl = "https://api.themoviedb.org/3/movie/"+mMovie.getId()+"/reviews?language=en-US&api_key="+Constants.API.API_KEY;
//        Log.d("ID", trailerUrl);
//        fetchData(reviewsUrl);
//        Log.d("reviewUrl",reviewsUrl);
//        fetchTrailer(trailerUrl);
//        showDetail();
//        initRecyclerView();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("destory","destory");
//    }
//
//    @OnClick(R.id.btn_favourite) void setFavourite(){
//
//        Constants.sMovies.add(mMovie);
//        String json = "";
//        Gson gson = new Gson();
//        json = gson.toJson(Constants.sMovies);
//
//        SharedPreferences preferences=getSharedPreferences(Constants.SHARED_FAVOURITE,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putString("json", json);
//        editor.commit();
//
//        Toast.makeText(this,"加入收藏成功",Toast.LENGTH_SHORT).show();
//
//    }
//
//    private void initRecyclerView() {
//
//        mDetailTrailerAdapter = new DetailTrailerAdapter(mTrailers);
//        mTrailerRecyclerView.setAdapter(mDetailTrailerAdapter);
////        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
//        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    }
//
//    private void fetchTrailer(final String httpUrl) {
//
//        if (isOnline()){
//            try {
//                trailerUrl = new URL(httpUrl);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        trailerJsonResponse = NetworkUtils.getResponseFromHttpUrl(trailerUrl);
//                        Log.d("trailerJsonResponse",trailerJsonResponse);
//                        parseTrailerJson(trailerJsonResponse);
//                        Message message = new Message();
//                        message.what = UPDATA_TRAILER;
//                        mHandler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//        }
//
//
//    }
//
//    private void getMovie(){
//        Intent intent = getIntent();
//        mMovie= (Movie) intent.getSerializableExtra("movie");
//    }
//
//    private void showDetail(){
//
//        mName.setText(mMovie.getTitle());
//        Picasso.with(this).load(mMovie.getPoster_path()).into(mImageView);
//        mDateVote.setText(mMovie.getRelease_date()+"\n"+mMovie.getVote_average());
//        mOverview.setText(mMovie.getOverview());
//
//    }
//
//
//    private void fetchData(final String httpUrl) {
//
//        if (isOnline()){
//            try {
//                reviewUrl = new URL(httpUrl);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Log.d("reviewhttpURL",httpUrl);
////                        Log.d("reviewURL", String.valueOf(reviewUrl));
//                        reviewJsonResponse = NetworkUtils.getResponseFromHttpUrl(reviewUrl);
////                        Log.d("review",reviewJsonResponse);
//                        parseJson(reviewJsonResponse);
//                        Message message = new Message();
//                        message.what = UPDATA_DATA;
//                        mHandler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//        }
//
//    }
//
//    private void parseJson(String json) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray results = jsonObject.getJSONArray("results");
//        for (int i = 0; i < results.length(); i++){
//            JSONObject review = results.getJSONObject(i);
//            reviews = reviews+review.getString("author")+"  :\n";
////            Log.d("author",review.getString("author"));
//            if (i+1 == results.length()){
//                reviews = reviews+review.getString("content");
//            }else {
//                reviews = reviews+review.getString("content")+"\n\n\n";
//            }
//        }
//        Log.d("review",reviews);
//
//    }
//
//    private void parseTrailerJson(String json) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray results = jsonObject.getJSONArray("results");
//        for (int i = 0; i < results.length(); i++){
//            JSONObject result = results.getJSONObject(i);
//            Trailer trailer = new Trailer();
//            trailer.setKey(result.getString("key"));
//            Log.d("trailer",result.getString("key"));
//            mTrailers.add(trailer);
//        }
//
//    }
//
//    public boolean isOnline() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnected();
//    }

}
