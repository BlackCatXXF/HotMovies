package com.xxf.hotmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dell on 2018/1/16.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.xxf.hotmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_POPULAR = "popular";
    public static final String PATH_HOT = "hot";
    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final Uri POPULAR_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();
        public static final Uri TOP_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_HOT).build();

        public static final String TABLE_NAME = "movies";
        public static final String POPULAR_TABLE_NAME = "popular";
        public static final String HOT_TABLE_NAME = "hot";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_OVERVIEW = "overview";

    }


}
