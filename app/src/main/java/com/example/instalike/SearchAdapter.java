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

import com.example.instalike.db.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private OnItemClickListener mListener;
    //private List<Post> mPosts;
    //private List<Post> mPostsFull;
    private List<User> mUsers;
    private List<User> mUsersFull;

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


    public SearchAdapter(List<User> users) {
        mUsers = users;
        mUsersFull= new ArrayList<>(mUsers);// copy de la liste
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
        User userMember = mUsers.get(position);

        // Set item views based on your views and data model
        viewHolder.mPics.setImageResource(R.drawable.paysage3);
        viewHolder.mPseudo.setText(userMember.getPseudeo());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public Filter getFilter(){
        return mUsersFilter;
    }
    private Filter mUsersFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length()==0){// shw all the results
                filteredList.addAll(mUsersFull);
            }else{
                String filter= constraint.toString().toLowerCase().trim();

                for (User user: mUsersFull){
                    if(user.getPseudeo().toLowerCase().contains(filter)){
                        filteredList.add(user);
                    }
                }
            }

            FilterResults result= new FilterResults();
            result.values=filteredList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUsers.clear();
            mUsers.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
