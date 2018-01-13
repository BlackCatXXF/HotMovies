package com.xxf.hotmovies;

import com.xxf.hotmovies.bean.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/11/26.
 * 后台接口及常量
 */

public class Constants {

    public static long  MOVIE_ID = 0;

    public static String YOUTUBE_KEY = "";

    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v="+YOUTUBE_KEY+"&list=PLHPTxTxtC0ial7mOT-Srrguvokjvlcbg7";

    public static final String SHARED_FAVOURITE = "favourite";

    public static List<Movie> sMovies = new ArrayList<>();

    public static boolean isTwoPane;

    public class API{

        public static final String API_KEY="";

        public static final String MOVIE_POPULAR = "http://api.themoviedb.org/3/movie/popular?language=zh&api_key="+API_KEY;

        public static final String MOVIE_TOP ="http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key="+API_KEY;

        public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";

//        public static  String MOVIE_TRAILER = "https://api.themoviedb.org/3/movie/"+MOVIE_ID+"/videos?language=en-US&api_key="+API_KEY;
//
//        public static  String MOVIE_REVIEW = "https://api.themoviedb.org/3/movie/"+MOVIE_ID+"/reviews?language=en-US&api_key="+API_KEY;
    }

}
