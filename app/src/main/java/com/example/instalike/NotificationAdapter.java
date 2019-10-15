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

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private NotificationAdapter.OnItemClickListener mListener;

    private List<String> mNotification;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(NotificationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView notif;

        public ViewHolder(View itemView, final NotificationAdapter.OnItemClickListener listener) {
            super(itemView);


            notif = itemView.findViewById(R.id.notification_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
        }
    }


    public NotificationAdapter(List<String> listNotification) {
        mNotification = listNotification;
    }

    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.notification, parent, false);
        // Return a new holder instance
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(contactView, mListener);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        // Set item views based on your views and data model
        if( mNotification.size()!=0){
            String notifyMember = mNotification.get(position);

            viewHolder.notif.setText(notifyMember);
        }

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNotification.size();
    }

}
