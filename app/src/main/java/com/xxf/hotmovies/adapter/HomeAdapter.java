package com.xxf.hotmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xxf.hotmovies.R;
import com.xxf.hotmovies.bean.Movie;

import java.util.List;

/**
 * Created by dell on 2017/11/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;

    public HomeAdapter(List<Movie> movies){
        mMovies = movies;
        Log.d("1",mMovies.toString());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);
        return new ViewHolder(view);
    }

    public void setData(List<Movie> data){
        mMovies = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        Picasso.with(mContext).load(mMovies.get(position).getPoster_path()).into(holder.mImageView);

        Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(holder.mImageView);

        Log.d("Picasso","Picasso");

    }

    @Override
    public int getItemCount() {
        Log.d("size",mMovies.size()+"");
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
