package com.xxf.hotmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xxf.hotmovies.Constants;
import com.xxf.hotmovies.R;
import com.xxf.hotmovies.bean.Trailer;

import java.util.List;

/**
 * Created by dell on 2018/1/2.
 */

public class DetailTrailerAdapter extends RecyclerView.Adapter<DetailTrailerAdapter.ViewHolder> {

    private Context mContext;

    private List<Trailer> mTrailers;

    public DetailTrailerAdapter(List<Trailer> trailers){
        mTrailers = trailers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_trailer,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getLayoutPosition();
                Constants.YOUTUBE_KEY = mTrailers.get(position).getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.YOUTUBE_URL));
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    public void setData(List<Trailer> data){
        mTrailers = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int trailerNum = position + 1;
        holder.mTextView.setText("Trailer"+trailerNum);

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton mImageButton;

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageButton = itemView.findViewById(R.id.image_button_trailer);
            mTextView = itemView.findViewById(R.id.tv_trailer);

        }
    }

}
