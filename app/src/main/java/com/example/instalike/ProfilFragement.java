package com.example.instalike;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;

import java.util.ArrayList;

public class ProfilFragement extends Fragment  implements View.OnClickListener{
    View view;
    Button mFollow;
    TextView mPost,mFollowers,mAbonnement;

    private HashtagAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;
    private ArrayList<Comment> mListComment;
    private int mUser_id;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        mFollow=view.findViewById(R.id.profil_follow_btn);
        mPost=view.findViewById(R.id.profil_post);
        mFollowers=view.findViewById(R.id.profil_followers);
        mAbonnement=view.findViewById(R.id.profil_abonnement);

        mFollow.setOnClickListener(this);
        mUser_id=getArguments().getInt("ID_USER");
        createList();
        buildRecycleView();
        return view;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.profil_follow_btn:
                String isFollow=mFollow.getText().toString();
                if (isFollow=="Follow"){
                    mFollow.setText("S'abonner");
                    int nbFollowers=Integer.parseInt(mFollowers.getText().toString());
                    nbFollowers--;
                    mFollowers.setText(String.valueOf(nbFollowers));

                }
                else{
                    int nbFollowers=Integer.parseInt(mFollowers.getText().toString());
                    nbFollowers++;
                    mFollowers.setText(String.valueOf(nbFollowers));
                    mFollow.setText("Follow");

                }

        }
    }
    public void changeItem(int position, String text){
        //Posts.get(position).changeDescription("clicked");
        mAdapter.notifyItemChanged(position);
    }
    public void changeActivity(int position){
        Fragment selectedFragment= null;
        Bundle bundle= new Bundle();
        // A MODIFIER PAS DYNAMIQUE
        bundle.putInt("POST",Posts.get(position).getId());
        selectedFragment=new FullPostFragment();
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
        /*createComment(position);

        bundle.putString("USERNAME",Posts.get(position).getmUserName());
        bundle.putString("DESCRIPTION",Posts.get(position).getmDescription());
        bundle.putInt("IMAGE",Posts.get(position).getmImagePosts());
        bundle.putInt("ISLIKE",Posts.get(position).getmColorLike());
        bundle.putString("NBLIKE",Posts.get(position).getmLike());
        bundle.putInt("NBCOMMENT",Posts.get(position).getmListComment().size());
        // ESSAYER DE PASSER L'OBJET COMMENT
        // bundle.putString("COMMENT",Posts.get(position).getmListComment());
        selectedFragment=new FullPostFragment();
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();*/
    }
    public void createComment(int position){
       /* mListComment=new ArrayList<Comment>();
        mListComment.add(new Comment("vincent",R.drawable.paysage2, "superbePhoto"));
        mListComment.add(new Comment("paul",R.drawable.paysage3, "nice"));
        mListComment.add(new Comment("Thomas",R.drawable.paysage4, "super à visiter"));
        Posts.get(position).setmListComment(mListComment);
        System.out.println(Posts.get(position).getmListComment());*/
    }
    public void createList(){
        /*Posts =new ArrayList<Post>();
        Posts.add(new Post("ichiban japan", "super voyage à tokyo",R.drawable.paysage2,"120"));
        Posts.add(new Post("VincentJouanne", "i love BJJ",R.drawable.paysage3,"110"));
        Posts.add(new Post("Florent Brassac", "t'as dead ça chacal",R.drawable.paysage4,"105"));
        Posts.add(new Post("PaullBoveyron", "Je suis une locomotive",R.drawable.paysage5,"23"));*/
        Posts =new ArrayList<com.example.instalike.db.Post>();
        PostActions postAction=new PostActions(getContext());
        Posts=postAction.getAllPost(mUser_id);

    }
    public void buildRecycleView(){

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);

        mRecyclerView=view.findViewById(R.id.fragment_profil_listPost);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.scheduleLayoutAnimation();


        mLayoutManager=new LinearLayoutManager(getActivity());
        mAdapter = new HashtagAdapter(Posts);

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
