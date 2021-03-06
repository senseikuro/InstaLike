package com.example.instalike;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.Post;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.ViewHolder>{
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onPicsClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPics;

        public ViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);


            mPics=(ImageView)itemView.findViewById(R.id.Hashtag_post);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!= null){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
            mPics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!= null){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onPicsClick(position);
                    }
                }
            });
        }
    }

    private List<com.example.instalike.db.Post> mPosts;

    public HashtagAdapter(List<com.example.instalike.db.Post> Posts) {
        mPosts = Posts;
    }

    public HashtagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.hashtag, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView,mListener);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(HashtagAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Post postmember = mPosts.get(position);
        System.out.println(mPosts.size());
        // Set item views based on your views and data model
        byte[] outImage=postmember.getPhoto_path();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        viewHolder.mPics.setImageBitmap(theImage);

    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
