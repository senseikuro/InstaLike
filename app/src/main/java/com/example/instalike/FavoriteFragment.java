package com.example.instalike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.LikeActions;
import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment{
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private HashtagAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> posts;
    private View view;
    private int mCurrentUser;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite,container,false);
        mCurrentUser=getArguments().getInt("CURRENT_USER");
        createList();
        buildRecycleView();

        return view;
    }

    public void changeItem(int position, String text){
        mAdapter.notifyItemChanged(position);
    }

    public void createList(){

        ArrayList<Integer> postID=new ArrayList<Integer>();
        LikeActions likeActions= new LikeActions(getContext());
        postID=likeActions.getAllPostLike(mCurrentUser);
        likeActions.close();
        posts =new ArrayList<com.example.instalike.db.Post>();
        PostActions postActions= new PostActions(getContext());
        for (int i=0;i<postID.size();i++){
            posts.add(postActions.getPostFromID(postID.get(i)));
        }

    }

    public void changeActivity(int position){
        Fragment selectedFragment= null;
        Bundle bundle= new Bundle();

        bundle.putInt("POST_ID",posts.get(position).getId());
        bundle.putInt("CURRENT_USER",mCurrentUser);
        bundle.putInt("USER_PROFIL",posts.get(position).getUser_id());


        selectedFragment=new FullPostFragment();
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }

    public void buildRecycleView(){

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);

        mRecyclerView=view.findViewById(R.id.ListHashtagView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();


        mLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter = new HashtagAdapter(posts);

        // en forme de grill
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL,false);


        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HashtagAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"clicked");
            }
            public void onPicsClick(int position){ changeActivity(position);}

        });
    }
}