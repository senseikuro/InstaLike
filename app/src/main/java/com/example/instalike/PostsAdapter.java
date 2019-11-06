package com.example.instalike;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.UserActions;

import java.io.ByteArrayInputStream;
import java.util.List;

    public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{
        private OnItemClickListener mListener;
        public interface OnItemClickListener {
            void onItemClick(int position);
            void onLikeClick(int position);
            void onCommentClick(int position);
            void onProfilClick(int position);
        }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
     public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mLike,mDescription,mUserName;
        public ImageView mPicsPP,mPicsPost,mheartpic,mCommentary;

        public ViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);

            mLike=(TextView)itemView.findViewById(R.id.post_nb_like);
            mDescription=(TextView)itemView.findViewById(R.id.post_description);
            mUserName=(TextView)itemView.findViewById(R.id.post_name_user);
            mPicsPP=(ImageView)itemView.findViewById(R.id.post_Image_pp);
            mPicsPost=(ImageView)itemView.findViewById(R.id.post_image_post);
            mheartpic=itemView.findViewById(R.id.post_like);
            mCommentary=itemView.findViewById(R.id.post_commentary);


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
            mheartpic.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    if(listener!= null){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onLikeClick(position);
                    }
                }
            });
            mCommentary.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    if(listener!= null){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onCommentClick(position);
                    }
                }
            });
            mPicsPP.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    if(listener!= null){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onProfilClick(position);
                    }
                }
            });
            mUserName.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    if(listener!= null){
                        int position=getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onProfilClick(position);
                    }
                }
            });
        }
     }

    private List<Post> mPosts;

    public PostsAdapter(List<Post> Posts) {
        mPosts = Posts;
    }

    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.post, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView,mListener);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PostsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Post postmember = mPosts.get(position);

        // Set item views based on your views and data model
        viewHolder.mUserName.setText(postmember.getmUserName());
        viewHolder.mDescription.setText(postmember.getmDescription());
        viewHolder.mLike.setText(postmember.getmLike());
        byte[] outImage=postmember.getmImagePosts();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap image = BitmapFactory.decodeStream(imageStream);

        viewHolder.mPicsPost.setImageBitmap(image);
        outImage=postmember.getMpp();
        imageStream = new ByteArrayInputStream(outImage);
        image = BitmapFactory.decodeStream(imageStream);
        viewHolder.mPicsPP.setImageBitmap(image);

        viewHolder.mheartpic.setImageResource(postmember.getmColorLike());


    }
    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
