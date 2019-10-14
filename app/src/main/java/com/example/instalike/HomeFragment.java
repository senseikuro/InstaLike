package com.example.instalike;

import android.os.Bundle;
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
import com.example.instalike.db.PostActions;

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

    private PostActions postActions;
    private LikeActions likeActions;
    private FollowActions followActions;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        mCurrent_User=getArguments().getInt("CURRENT_USER");
        mUser_id=getArguments().getInt("USER_PROFIL");

        createList();
        buildRecycleView();
        return view;
    }

    public void changeItem(int position, String text){
        mAdapter.notifyItemChanged(position);
    }
    public void increaseLike(int position){

        boolean islike=likeActions.postIsLike(mCurrent_User,postAbonnement.get(position).getId());

        if (islike){
            likeActions.removeLikeWithID(likeActions.getPostLike(mCurrent_User,postAbonnement.get(position).getId()));
            Posts.get(position).setmLike(String.valueOf(postActions.getNbLike(postAbonnement.get(position).getId())));
            Posts.get(position).setmColorLike(R.drawable.heart);
        }
        else{
            Like newlike= new Like();
            Date now= new Date(Calendar.getInstance().getTime().getTime());

            newlike.setUser_id(mCurrent_User);
            newlike.setPost_id(postAbonnement.get(position).getId());
            newlike.setDate(now);
            likeActions.insertLike(newlike);
            Posts.get(position).setmLike(String.valueOf(postActions.getNbLike(postAbonnement.get(position).getId())));
            Posts.get(position).setmColorLike(R.drawable.redheart);
            likeActions.close();
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
        selectedFragment=new CommentFragment();
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }

    public void createList(){

        followActions= new FollowActions(getContext());
        ArrayList<Integer> idAbonnement= new ArrayList<Integer>();
        idAbonnement=followActions.getAllAbonnement(mCurrent_User);
        followActions.close();
        System.out.println("j'ai les follows");
        postActions= new PostActions(getContext());
        postAbonnement= new ArrayList<com.example.instalike.db.Post>();
        postAbonnement=postActions.getActuality(idAbonnement);
        System.out.println("j'ai les actualités");

        Posts =new ArrayList<Post>();
        Post tempPost=new Post();
        for (int i=0;i<postAbonnement.size();i++){
            tempPost.setUserName(postActions.getUserName(postAbonnement.get(i).getUser_id()));
            tempPost.setDescription(postAbonnement.get(i).getDescription());
            postActions.close();
            likeActions=new LikeActions(getContext());
            tempPost.setIslike(likeActions.postIsLike(mCurrent_User,postAbonnement.get(i).getId()));
            if (tempPost.isIslike()){
                tempPost.setmColorLike(R.drawable.redheart);
            }
            else{
                tempPost.setmColorLike(R.drawable.heart);
            }
            likeActions.close();
            tempPost.setmLike(String.valueOf(postActions.getNbLike(postAbonnement.get(i).getId())));

            tempPost.setImagePosts(postAbonnement.get(i).getPhoto_path());
            Posts.add(tempPost);

        }


    }
    public void buildRecycleView(){
        mRecyclerView=view.findViewById(R.id.ListRecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());
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
        });
    }
}
