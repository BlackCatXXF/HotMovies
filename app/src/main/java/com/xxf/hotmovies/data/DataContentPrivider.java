package com.xxf.hotmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.xxf.hotmovies.data.MovieContract.MovieEntry.HOT_TABLE_NAME;
import static com.xxf.hotmovies.data.MovieContract.MovieEntry.POPULAR_TABLE_NAME;
import static com.xxf.hotmovies.data.MovieContract.MovieEntry.TABLE_NAME;

/**
 * Created by dell on 2018/1/15.
 */

public class DataContentPrivider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    public static final int POPULARS = 200;
    public static final int POPULARS_WITH_ID = 201;

    public static final int HOTS = 300;
    public static final int HOTS_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_POPULAR, POPULARS);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_POPULAR + "/#", POPULARS_WITH_ID);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_HOT, HOTS);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_HOT + "/#", HOTS_WITH_ID);

        return uriMatcher;
    }

    private DBHelpher mDBHelpher;


    @Override
    public boolean onCreate() {

        Context context = getContext();
        mDBHelpher = new DBHelpher(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mDBHelpher.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case MOVIES:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case POPULARS:
                retCursor = db.query(POPULAR_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case HOTS:
                retCursor = db.query(HOT_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = mDBHelpher.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                long id = db.insert(TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case POPULARS:
                long id2 = db.insert(POPULAR_TABLE_NAME, null, contentValues);
                if ( id2 > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.POPULAR_CONTENT_URI, id2);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case HOTS:
                long id3 = db.insert(HOT_TABLE_NAME, null, contentValues);
                if ( id3 > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.TOP_CONTENT_URI, id3);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelpher.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int tasksDeleted;

        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case MOVIES_WITH_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
