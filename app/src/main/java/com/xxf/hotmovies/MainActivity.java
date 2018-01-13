package com.xxf.hotmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxf.hotmovies.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    public static final int UPDATA_DATA = 1;

//    private RecyclerView mRecyclerView;
//
//    private TextView mErrorMessage;

//    private String jsonResponse;
//
//    private URL url = null;
//
//    private List<Movie> mMovies = new ArrayList<>();
//
////    private HomeAdapter mHomeAdapter;
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case UPDATA_DATA:
//                    mHomeAdapter.setData(mMovies);
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Constants.isTwoPane == false){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_layout,new MainFragment())
                    .addToBackStack(null)
                    .commit();
        }


//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);
//        fetchData(Constants.API.MOVIE_POPULAR);
//
//        initRecyclerView();

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sort, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.popular_movie:
//                fetchData(Constants.API.MOVIE_POPULAR);
//                break;
//            case R.id.top_rated_movie:
//                fetchData(Constants.API.MOVIE_TOP);
//                break;
//            case R.id.favourite_movie:
//                getFavouriteData();
//                mHomeAdapter.setData(mMovies);
//                //重新设置adapter来显示收藏电影
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void getFavouriteData(){
//
//        SharedPreferences preferences=getSharedPreferences(Constants.SHARED_FAVOURITE, Context.MODE_PRIVATE);
//        String json = preferences.getString("json","");
//        if (json != ""){
//            Gson gson = new Gson();
//            mMovies = gson.fromJson(json, new TypeToken<List<Movie>>(){}.getType());
//        }else {
//            Log.d("json","获取的本地json为空");
//        }
//
//    }
//
////    private void initRecyclerView() {
////
////        mHomeAdapter = new HomeAdapter(mMovies,this);
////        mRecyclerView.setAdapter(mHomeAdapter);
//////        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
////        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
////
////    }
//
//    private void fetchData(String httpUrl) {
//
//        if (isOnline()){
//            try {
//                url = new URL(httpUrl);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
//                        if (mMovies != null) {
//                            mMovies.clear();
//                        }
//                        parseJson(jsonResponse);
//                        Message message = new Message();
//                        message.what = UPDATA_DATA;
//                        mHandler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//        }else {
////            mRecyclerView.setVisibility(View.GONE);
////            mErrorMessage.setVisibility(View.VISIBLE);
//        }
//
//
//    }
//
//    private void parseJson(String json) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(json);
//        JSONArray results = jsonObject.getJSONArray("results");
//        for (int i = 0; i < results.length(); i++) {
//            JSONObject movie = results.getJSONObject(i);
//            long id = movie.getLong("id");
//            String title = movie.getString("title");
//            String poster_path = Constants.API.POSTER_PATH + movie.getString("poster_path");
//            String overview = movie.getString("overview");
//            Double vote_average = movie.getDouble("vote_average");
//            String release_date = movie.getString("release_date");
//
//
//            Movie movie1 = new Movie();
//            movie1.setId(id);
//            movie1.setOverview(overview);
//            movie1.setPoster_path(poster_path);
//            movie1.setTitle(title);
//            movie1.setVote_average(vote_average);
//            movie1.setRelease_date(release_date);
//            mMovies.add(movie1);
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
