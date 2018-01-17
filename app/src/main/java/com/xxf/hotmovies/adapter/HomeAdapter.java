package com.xxf.hotmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xxf.hotmovies.Constants;
import com.xxf.hotmovies.R;
import com.xxf.hotmovies.bean.Movie;
import com.xxf.hotmovies.data.MovieContract;
import com.xxf.hotmovies.fragment.DetailFragment;

import java.util.List;

/**
 * Created by dell on 2017/11/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;

    private Activity mActivity;

    public int mSource;

    public int position;

    public HomeAdapter(List<Movie> movies,Activity activity){
        mMovies = movies;
        mActivity = activity;


//        detailFragment = (DetailFragment) mActivity.getFragmentManager().findFragmentById(R.id.detail_fragmeent);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = holder.getLayoutPosition();
                Movie movie = mMovies.get(position);
                if (Constants.isTwoPane){

                    DetailFragment detailFragment = (DetailFragment) mActivity.getFragmentManager().findFragmentById(R.id.detail_fragmeent);
                    detailFragment.getMovie(movie);
                    Log.d("movieId", String.valueOf(movie.getId()));
                    if (mSource != Constants.FAVOURITE_LIST){
                        detailFragment.setCancelHide();
                    }else {
                        Cursor cursor = mActivity.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,"name = ?",new String[]{movie.getTitle()},null);
                        cursor.moveToFirst();
                        String id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
                        detailFragment.deleteId(id);
                        detailFragment.setCancelShow();
                    }
                    detailFragment.refresh();
                }else {

                    DetailFragment detailFragment = new DetailFragment();
                    detailFragment.getMovie(movie);
                    detailFragment.putIsHideCancel(mSource);

                    if (mSource == Constants.FAVOURITE_LIST){
                        Cursor cursor = mActivity.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,"name = ?",new String[]{movie.getTitle()},null);
                        cursor.moveToFirst();
                        String id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
                        detailFragment.deleteId(id);
                    }
                    mActivity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,detailFragment)
                .addToBackStack(null)
                .commit();

                }

            }
        });
        return holder;
    }

    public void setData(List<Movie> data,int Source){
        mMovies = data;
        mSource = Source;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(mContext).load(mMovies.get(position).getPoster_path()).placeholder(R.mipmap.ic_launcher).into(holder.mImageView);


    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }

}
