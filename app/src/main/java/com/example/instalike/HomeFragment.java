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

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private PostsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;
    private View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        createList();
        buildRecycleView();
        return view;
    }

    public void changeItem(int position, String text){
        Posts.get(position).changeDescription("clicked");
        mAdapter.notifyItemChanged(position);
    }
    public void increaseLike(int position){
        int currentLike=Integer.parseInt(Posts.get(position).getmLike());
        if(Posts.get(position).isIslike()){
            currentLike--;
            Posts.get(position).setIslike(false);
        }
        else{
            currentLike++;
            Posts.get(position).setIslike(true);

        }
        Posts.get(position).setmLike(String.valueOf(currentLike));
        if (Posts.get(position).getmColorLike()==R.drawable.heart)
            Posts.get(position).setmColorLike(R.drawable.redheart);
        else
            Posts.get(position).setmColorLike(R.drawable.heart);
        mAdapter.notifyItemChanged(position);

    }

    public void changeActivityToComment(){
        Fragment selectedFragment= null;
        selectedFragment=new CommentFragment();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }

    public void createList(){
        Posts =new ArrayList<Post>();
        Posts.add(new Post("ichiban japan", "super voyage à tokyo",R.drawable.paysage2,"120"));
        Posts.add(new Post("VincentJouanne", "i love BJJ",R.drawable.paysage3,"110"));
        Posts.add(new Post("Florent Brassac", "t'as dead ça chacal",R.drawable.paysage4,"105"));
        Posts.add(new Post("PaullBoveyron", "Je suis une locomotive",R.drawable.paysage5,"23"));
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
            public void onCommentClick(int position){changeActivityToComment();}
        });
    }
}
