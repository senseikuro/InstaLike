package com.example.instalike;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.Like;
import com.example.instalike.db.LikeActions;
import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;

import java.sql.Date;
import java.util.Calendar;

public class FullPostFragment extends Fragment implements View.OnClickListener {
    private ImageView mPhoto,mHeart,mComment;
    private TextView mUsername, mDescription, mNbLike, mNbComment;
    private View view;
    private int mPostID, nbLike,nbComment;
    private LikeActions mLikeAction;
    private Post post;
    private PostActions postAction;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_page,container,false);

        /*username=getArguments().getString("USERNAME");
        description=getArguments().getString("DESCRIPTION");
        nbLike=getArguments().getString("NBLIKE");
        imagePost=getArguments().getInt("IMAGE");
        postisLike=getArguments().getInt("ISLIKE",0);
        nbComment=getArguments().getInt("NBCOMMENT",0);*/
        mPostID=getArguments().getInt("POST");

        mPhoto=view.findViewById(R.id.post_page_image);
        mHeart=view.findViewById(R.id.post_page_heart);
        mComment=view.findViewById(R.id.post_page_comment);
        
        mUsername=view.findViewById(R.id.post_page_pseudo);
        mDescription=view.findViewById(R.id.post_page_description);
        mNbLike=view.findViewById(R.id.post_page_nbLike);
        mNbComment=view.findViewById(R.id.post_page_nbComment);


        setRecources();



        return view;
    }
    public void setRecources(){

        postAction=new PostActions(getContext());
        nbComment=postAction.getNbComment(mPostID);
        nbLike=postAction.getNbLike(mPostID);
        post=postAction.getPostFromID(mPostID);


        mPhoto.setImageResource(post.getPhoto_path());
        mUsername.setText(postAction.getUserName(post.getUser_id()));
        mDescription.setText(post.getDescription());
        mNbLike.setText(String.valueOf(nbLike));
        mNbComment.setText(String.valueOf(nbComment));
        mComment.setOnClickListener(this);
        mHeart.setOnClickListener(this);
        //A VOIR
        mLikeAction= new LikeActions(getContext());
        boolean islike=mLikeAction.postIsLike(post.getUser_id(),post.getId());
        if (islike){
            mHeart.setImageResource(R.drawable.redheart);
        }
        else{
            mHeart.setImageResource(R.drawable.heart);
        }

        mLikeAction.close();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_page_heart:


                boolean islike=mLikeAction.postIsLike(post.getUser_id(),post.getId());

                if (islike){

                    mLikeAction.removeLikeWithID(mLikeAction.getPostLike(post.getUser_id(),post.getId()));
                    mHeart.setImageResource(R.drawable.heart);
                }
                else{
                    Like newlike= new Like();
                    Date now= new Date(Calendar.getInstance().getTime().getTime());

                    newlike.setUser_id(post.getUser_id());
                    newlike.setPost_id(post.getId());
                    newlike.setDate(now);
                    mLikeAction.insertLike(newlike);
                    mHeart.setImageResource(R.drawable.redheart);
                    mLikeAction.close();
                }


                mNbLike.setText(String.valueOf(postAction.getNbLike(mPostID)));
                break;
            case R.id.post_page_comment:
                changeActivityToComment();
                break;
        }

    }
    public void changeActivityToComment(){
        Fragment selectedFragment= null;
        selectedFragment=new CommentFragment();
        Bundle bundle= new Bundle();
        bundle.putInt("POST_ID",mPostID);
        bundle.putInt("Current_User",post.getUser_id());
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }
}