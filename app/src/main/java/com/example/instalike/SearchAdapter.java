package com.example.instalike;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private OnItemClickListener mListener;
    private List<Post> mPosts;
    private List<Post> mPostsFull;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPics;
        public TextView mPseudo;

        public ViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);


            mPics=itemView.findViewById(R.id.search_PP);
            mPseudo=itemView.findViewById(R.id.search_pseudo);

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


    public SearchAdapter(List<Post> Posts) {
        mPosts = Posts;
        mPostsFull= new ArrayList<>(mPosts);// copy de la liste
    }

    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.search, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView,mListener);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Post postmember = mPosts.get(position);

        // Set item views based on your views and data model
        viewHolder.mPics.setImageResource(postmember.getmImagePosts());
        viewHolder.mPseudo.setText(postmember.getmUserName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public Filter getFilter(){
        return mPostsFilter;
    }
    private Filter mPostsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Post> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length()==0){// shw all the results
                filteredList.addAll(mPostsFull);
            }else{
                String filter= constraint.toString().toLowerCase().trim();

                for (Post post: mPostsFull){
                    if(post.getmUserName().toLowerCase().contains(filter)){
                        filteredList.add(post);
                    }
                }
            }

            FilterResults result= new FilterResults();
            result.values=filteredList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPosts.clear();
            mPosts.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
