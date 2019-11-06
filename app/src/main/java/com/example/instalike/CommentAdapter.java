package com.example.instalike;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPics;
        public TextView mPseudo, mComment;
        public Button mPublish;

        public ViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);

            mPics=itemView.findViewById(R.id.comment_pp);
            mPseudo=itemView.findViewById(R.id.Comment_pseudo);
            mComment=itemView.findViewById(R.id.comment_commentary);
            mPublish=itemView.findViewById(R.id.fragment_comment_publish_btn);
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

        }
    }

    private List<Comment> mListComment;

    public CommentAdapter(List<Comment> comments) {
        mListComment=comments;
    }

    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.comment, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView,mListener);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
       Comment comments = mListComment.get(position);
        byte[] outImage=comments.getimagepp();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        viewHolder.mPics.setImageBitmap(image);
        viewHolder.mPseudo.setText(comments.getmUserName());
        viewHolder.mComment.setText(comments.getmComment());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mListComment.size();
    }
}
