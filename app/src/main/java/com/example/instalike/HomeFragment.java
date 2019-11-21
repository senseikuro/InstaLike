package com.example.instalike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.FollowActions;
import com.example.instalike.db.Like;
import com.example.instalike.db.LikeActions;
import com.example.instalike.db.Notify;
import com.example.instalike.db.NotifyActions;
import com.example.instalike.db.PostActions;
import com.example.instalike.db.UserActions;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private PostsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;
    private View view;

    private int mUser_id, mCurrent_User;
    private int mPostID, nbLike,nbComment;
    private ArrayList<com.example.instalike.db.Post> postAbonnement;

    private NotifyActions notifyActions;
    private PostActions postActions;
    private LikeActions likeActions;
    private FollowActions followActions;
    private InputStream inputStream;
    private boolean doubleTap;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        mCurrent_User=getArguments().getInt("CURRENT_USER");
        mUser_id=getArguments().getInt("USER_PROFIL");
        Posts =new ArrayList<Post>();
        doubleTap=false;
        try {
            createList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        buildRecycleView();
        return view;
    }

    public void changeItem(int position, String text){
        mAdapter.notifyItemChanged(position);
    }
    public void increaseLike(int position){

        boolean islike=likeActions.postIsLike(mCurrent_User,postAbonnement.get(position).getId());
        Date now= new Date(Calendar.getInstance().getTime().getTime());

        if (islike){
            likeActions.removeLikeWithID(likeActions.getPostLike(mCurrent_User,postAbonnement.get(position).getId()));
            Posts.get(position).setmLike(String.valueOf(postActions.getNbLike(postAbonnement.get(position).getId())));
            Posts.get(position).setmColorLike(R.drawable.heart);
        }
        else{
            Like newlike= new Like();

            newlike.setUser_id(mCurrent_User);
            newlike.setPost_id(postAbonnement.get(position).getId());
            newlike.setDate(now);
            likeActions.insertLike(newlike);
            Posts.get(position).setmLike(String.valueOf(postActions.getNbLike(postAbonnement.get(position).getId())));
            Posts.get(position).setmColorLike(R.drawable.redheart);
            likeActions.close();
            notifyActions=new NotifyActions(getContext());

            Notify notif= new Notify(mCurrent_User,postAbonnement.get(position).getUser_id(),postAbonnement.get(position).getId(),"like",now);
            notifyActions.insertNotification(notif);
            notifyActions.close();
        }
        mAdapter.notifyItemChanged(position);

    }

    public void changeActivityToProfil(int position){
        Fragment selectedFragment= null;
        selectedFragment=new ProfilFragement();
        Bundle bundle= new Bundle();
        bundle.putInt("CURRENT_USER",mCurrent_User);
        bundle.putInt("USER_PROFIL",postAbonnement.get(position).getUser_id());
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();


    }
    public void changeActivityToComment(int position){
        Fragment selectedFragment= null;
        Bundle bundle= new Bundle();
        // A MODIFIER PAS DYNAMIQUE
        bundle.putInt("POST_ID",postAbonnement.get(position).getId());
        bundle.putInt("CURRENT_USER",mCurrent_User);
        bundle.putInt("USER_PROFIL",mUser_id);

        selectedFragment=new CommentFragment();
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }

    public void createList() throws FileNotFoundException {

        followActions= new FollowActions(getContext());
        ArrayList<Integer> idAbonnement= new ArrayList<Integer>();
        idAbonnement=followActions.getAllAbonnement(mCurrent_User);
        followActions.close();
        postActions= new PostActions(getContext());
        postAbonnement= new ArrayList<com.example.instalike.db.Post>();
        postAbonnement=postActions.getActuality(idAbonnement);
        boolean islike=false;



        for (int i=0;i<postAbonnement.size();i++){
            String userName=postActions.getUserName(postAbonnement.get(i).getUser_id());
            String description=postAbonnement.get(i).getDescription();

            postActions.close();
            likeActions=new LikeActions(getContext());
            islike=likeActions.postIsLike(mCurrent_User,postAbonnement.get(i).getId());
            int heartimage;
            if (islike){
                 heartimage=R.drawable.redheart;
            }
            else{
                 heartimage=R.drawable.heart;
            }
            likeActions.close();
            String nblike=String.valueOf(postActions.getNbLike(postAbonnement.get(i).getId()));
            UserActions userActions= new UserActions(getContext());
            String datepost=postAbonnement.get(i).getDate();
            Posts.add(new Post(userName,description,postAbonnement.get(i).getPhoto_path(),userActions.getUserPP(postAbonnement.get(i).getUser_id()),nblike,heartimage,islike,datepost));
        }

    }
    public void buildRecycleView(){
        mRecyclerView=view.findViewById(R.id.ListRecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
        //System.out.println(Posts.get(0).getmDescription());
        //System.out.println(Posts.get(1).getmDescription());

        mAdapter = new PostsAdapter(Posts);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"clicked");
            }

            @Override
            public void onLikeClick(int position) {
                increaseLike(position);
            }
            public void onCommentClick(int position){changeActivityToComment(position);}
            public void onProfilClick(int position){changeActivityToProfil(position);}
            public void onDoubleTapImage(int position){
                if(doubleTap){
                    increaseLike(position);
                }
                else{
                    doubleTap=true;
                    Handler event= new Handler();
                    event.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleTap=false;
                        }
                    },500);
                }
            }
        });
    }
}
