package com.example.instalike;

import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.Follow;
import com.example.instalike.db.FollowActions;
import com.example.instalike.db.Notify;
import com.example.instalike.db.NotifyActions;
import com.example.instalike.db.User;
import com.example.instalike.db.UserActions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class NotificationFragment extends Fragment {

    private NotificationAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> notifications;
    private ArrayList<Notify> listNotification;
    private UserActions userActions;
    private NotifyActions notifyActions;
    private ArrayList<Integer> usersNotifier;
    private Boolean isFollow;


    private View view;
    private int mCurrentUser;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        mCurrentUser=getArguments().getInt("CURRENT_USER");
        notifications=new ArrayList<String>();

        createList();
        listNotification=notifyActions.getAllNotificationOfUser(mCurrentUser);

        buildRecycleView();

        return view;
    }

    public void changeItem(int position){
        Fragment selectedFragment= null;
        Bundle bundle= new Bundle();
        bundle.putInt("USER_PROFIL",listNotification.get(position).getUser_id());
        bundle.putInt("CURRENT_USER",mCurrentUser);

        switch(listNotification.get(position).getEvent()){
            case "follow":
                selectedFragment=new ProfilFragement();

                break;
            case "like":
                bundle.putInt("POST_ID",listNotification.get(position).getPost_id());
                selectedFragment=new FullPostFragment();


                break;
            case "comment":
                bundle.putInt("POST_ID",listNotification.get(position).getPost_id());
                selectedFragment=new CommentFragment();

                break;

        }
        notifyActions.removeNotificationById(listNotification.get(position).getId());
        notifications.remove(position);
        mAdapter.notifyDataSetChanged();

        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();


    }

    public void createList(){
        usersNotifier= new ArrayList<Integer>();
        notifyActions= new NotifyActions(getContext());
        usersNotifier=notifyActions.getAllUserNotifier(mCurrentUser);
        notifyActions.close();
        userActions= new UserActions(getContext());
        ArrayList<String> nameUserNotifier=new ArrayList<String>();
        User user= new User();
        for (int i=0;i<usersNotifier.size();i++){
            user=userActions.getUserWithID(usersNotifier.get(i));
            nameUserNotifier.add(user.getPseudeo());
        }

        notifications=notifyActions.getNotification(mCurrentUser,nameUserNotifier);
    }

    public void follow(int position){
        User user;
        user=userActions.getUserWithID(listNotification.get(position).getUser_id());
        FollowActions followActions= new FollowActions(getContext());
        isFollow=followActions.isFollowed(mCurrentUser,user.getId());
        Follow follow;
        if (!isFollow){
            Date now = new Date(Calendar.getInstance().getTime().getTime());

            follow=new Follow(mCurrentUser,user.getId(),now.toString());
            followActions.insertFollow(follow);
            followActions.close();

            notifyActions=new NotifyActions(getContext());

            Notify notif= new Notify(mCurrentUser,user.getId(),-1,"follow",now.toString());
            notifyActions.insertNotification(notif);
            notifyActions.close();
            Toast.makeText(getContext(),"vous suivez maintenant cette personne",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getContext(),"vous suivez déjà cette personne",Toast.LENGTH_SHORT).show();
        }
        changeItem(position);
    }
    public void buildRecycleView(){

        mRecyclerView=view.findViewById(R.id.ListRecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter = new NotificationAdapter(notifications);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position);
            }
            public void onFollowBack(int position){
                follow(position);
            }
        });
    }

}
