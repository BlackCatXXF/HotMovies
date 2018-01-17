package com.xxf.hotmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xxf.hotmovies.data.MovieContract.MovieEntry;

/**
 * Created by dell on 2018/1/15.
 */

public class DBHelpher extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDb.db";

    private static final int VERSION = 2;

    public DBHelpher(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE = "CREATE TABLE "  + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_NAME         + " TEXT, "+
                MovieEntry.COLUMN_IMAGE       + " TEXT, "+
                MovieEntry.COLUMN_DATA        +" TEXT, "+
                MovieEntry.COLUMN_VOTE        +" TEXT, "+
                MovieEntry.COLUMN_TIME        +" TEXT, "+
                MovieEntry.COLUMN_OVERVIEW        +" TEXT );";

        String CREATE_POPULAR_TABLE = "CREATE TABLE "  + MovieEntry.POPULAR_TABLE_NAME + " (" +
                MovieEntry._ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_NAME         + " TEXT, "+
                MovieEntry.COLUMN_IMAGE       + " TEXT, "+
                MovieEntry.COLUMN_DATA        +" TEXT, "+
                MovieEntry.COLUMN_VOTE        +" TEXT, "+
                MovieEntry.COLUMN_TIME        +" TEXT, "+
                MovieEntry.COLUMN_OVERVIEW        +" TEXT );";

        String CREATE_HOT_TABLE = "CREATE TABLE "  + MovieEntry.HOT_TABLE_NAME + " (" +
                MovieEntry._ID                + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_NAME         + " TEXT, "+
                MovieEntry.COLUMN_IMAGE       + " TEXT, "+
                MovieEntry.COLUMN_DATA        +" TEXT, "+
                MovieEntry.COLUMN_VOTE        +" TEXT, "+
                MovieEntry.COLUMN_TIME        +" TEXT, "+
                MovieEntry.COLUMN_OVERVIEW        +" TEXT );";

        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_POPULAR_TABLE);
        sqLiteDatabase.execSQL(CREATE_HOT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
