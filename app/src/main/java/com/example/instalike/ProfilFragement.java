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

import com.example.instalike.db.Follow;
import com.example.instalike.db.FollowActions;
import com.example.instalike.db.Notify;
import com.example.instalike.db.NotifyActions;
import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfilFragement extends Fragment  implements View.OnClickListener{
    View view;
    Button mFollow;
    TextView mPost,mFollowers,mAbonnement;

    private HashtagAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;
    private ArrayList<Comment> mListComment;
    private int mUser_id, mCurrent_User;
    private FollowActions followActions;
    private PostActions postActions;
    private NotifyActions notifyActions;
    private Follow follow;
    private boolean isFollow;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        mFollow=view.findViewById(R.id.profil_follow_btn);
        mPost=view.findViewById(R.id.profil_post);
        mFollowers=view.findViewById(R.id.profil_followers);
        mAbonnement=view.findViewById(R.id.profil_abonnement);

        mFollow.setOnClickListener(this);
        mFollowers.setOnClickListener(this);
        mAbonnement.setOnClickListener(this);
        mCurrent_User=getArguments().getInt("CURRENT_USER");
        mUser_id=getArguments().getInt("USER_PROFIL");
        System.out.println(mUser_id);
        followActions= new FollowActions(view.getContext());
        if (mUser_id == mCurrent_User){ 
            mFollow.setVisibility(view.INVISIBLE);
        }
        else{
            isFollow=followActions.isFollowed(mCurrent_User,mUser_id);


            if (isFollow){
                mFollow.setText("Follow");
            }
            else {
                mFollow.setText("S'abonner");
            }

        }
        int nbFollow=followActions.getNbFollow(mUser_id);
        int nbAbonnement=followActions.getNbAbonnement(mUser_id);
        mFollowers.setText(String.valueOf(nbFollow));
        mAbonnement.setText(String.valueOf(nbAbonnement));
        followActions.close();


        postActions=new PostActions(view.getContext());

        mPost.setText(String.valueOf(postActions.getnbPost(mUser_id)));
        createList();
        buildRecycleView();


        return view;
    }
    @Override
    public void onClick(View v) {
        Fragment selectedFragment= null;
        selectedFragment=new FollowFragment();
        Bundle bundle= new Bundle();
        bundle.putInt("CURRENT_USER",mCurrent_User);
        bundle.putInt("USER_PROFIL",mUser_id);
        switch(v.getId()){
            case R.id.profil_follow_btn:
                isFollow=followActions.isFollowed(mCurrent_User,mUser_id);

                if (isFollow){
                    followActions.removeFollowWithID(followActions.getFollow(mCurrent_User,mUser_id));
                    int nbFollowers=Integer.parseInt(mFollowers.getText().toString());
                    int nbFollow=followActions.getNbFollow(mUser_id);
                    int nbAbonnement=followActions.getNbAbonnement(mUser_id);
                    mFollowers.setText(String.valueOf(nbFollow));
                    mAbonnement.setText(String.valueOf(nbAbonnement));
                    mFollow.setText("S'abonner");
                    followActions.close();


                }
                else{
                    Date now = new Date(Calendar.getInstance().getTime().getTime());
                    System.out.println("user_profil"+mUser_id+" user current"+mCurrent_User);

                    follow=new Follow(mCurrent_User,mUser_id,now);
                    followActions.insertFollow(follow);
                    int nbFollowers=Integer.parseInt(mFollowers.getText().toString());
                    int nbFollow=followActions.getNbFollow(mUser_id);
                    int nbAbonnement=followActions.getNbAbonnement(mUser_id);
                    mFollowers.setText(String.valueOf(nbFollow));
                    mAbonnement.setText(String.valueOf(nbAbonnement));
                    mFollow.setText("Follow");
                    followActions.close();

                    notifyActions=new NotifyActions(getContext());

                    Notify notif= new Notify(mCurrent_User,mUser_id,-1,"follow",now);
                    notifyActions.insertNotification(notif);
                    notifyActions.close();
                }

                break;

            case R.id.profil_abonnement:

                bundle.putString("CHOICE","abonnement");
                selectedFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                        selectedFragment).addToBackStack(null).commit();
                break;
            case R.id.profil_followers:

                bundle.putString("CHOICE","followers");
                selectedFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                        selectedFragment).addToBackStack(null).commit();
                break;

        }
    }
    public void changeItem(int position, String text){
        //Posts.get(position).changeDescription("clicked");
        mAdapter.notifyItemChanged(position);
    }
    public void changeActivity(int position){
        Fragment selectedFragment= null;
        Bundle bundle= new Bundle();
        bundle.putInt("POST_ID",Posts.get(position).getId());
        bundle.putInt("USER_PROFIL",mUser_id);
        bundle.putInt("CURRENT_USER",mCurrent_User);
        selectedFragment=new FullPostFragment();
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();

    }

    public void createList(){

        Posts =new ArrayList<Post>();
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
