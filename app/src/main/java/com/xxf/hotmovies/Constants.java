package com.xxf.hotmovies;

/**
 * Created by dell on 2017/11/26.
 * 后台接口及常量
 */

public class Constants {

    class API{

        public static final String API_KEY="";

        public static final String MOVIE_POPULAR = "http://api.themoviedb.org/3/movie/popular?language=zh&api_key="+API_KEY;

        public static final String MOVIE_TOP ="http://api.themoviedb.org/3/movie/top_rated?language=zh&api_key="+API_KEY;

        public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";
    }

}
