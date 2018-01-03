package com.xxf.hotmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xxf.hotmovies.Constants;
import com.xxf.hotmovies.DetailActivity;
import com.xxf.hotmovies.R;
import com.xxf.hotmovies.bean.Movie;
import com.xxf.hotmovies.fragment.DetailFragment;

import java.util.List;

/**
 * Created by dell on 2017/11/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;

    private Activity mActivity;

    public HomeAdapter(List<Movie> movies,Activity activity){
        mMovies = movies;
        mActivity = activity;
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
                int position = holder.getLayoutPosition();
                Movie movie = mMovies.get(position);
                if (Constants.isTwoPane){
                    DetailFragment detailFragment = (DetailFragment) mActivity.getFragmentManager().findFragmentById(R.id.detail_fragmeent);
                    detailFragment.getMovie(movie);
                    detailFragment.refresh();
                }else {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("movie",movie);
                    mContext.startActivity(intent);
                }

            }
        });
        return holder;
    }

    public void setData(List<Movie> data){
        mMovies = data;
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
