package com.example.instalike;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.Calendar;

public class FullPostFragment extends Fragment implements View.OnClickListener {
    private ImageView mPhoto,mHeart,mComment;
    private TextView mUsername, mDescription, mNbLike, mNbComment;
    private View view;
    private int mPostID, nbLike,nbComment, mCurrent_user, mUserId_post;
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
        mPostID=getArguments().getInt("POST_ID");
        mCurrent_user=getArguments().getInt("CURRENT_USER");
        mUserId_post=getArguments().getInt("USER_PROFIL");
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
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public void setRecources(){

        postAction=new PostActions(getContext());
        nbComment=postAction.getNbComment(mPostID);
        nbLike=postAction.getNbLike(mPostID);
        post=postAction.getPostFromID(mPostID);

        byte[] outImage=post.getPhoto_path();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        mPhoto.setImageBitmap(theImage);

        mUsername.setText(postAction.getUserName(post.getUser_id()));
        mDescription.setText(post.getDescription());
        mNbLike.setText(String.valueOf(nbLike));
        mNbComment.setText(String.valueOf(nbComment));
        mComment.setOnClickListener(this);
        mHeart.setOnClickListener(this);
        //A VOIR
        mLikeAction= new LikeActions(getContext());
        boolean islike=mLikeAction.postIsLike(mCurrent_user,post.getId());
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


                boolean islike=mLikeAction.postIsLike(mCurrent_user,post.getId());

                if (islike){

                    mLikeAction.removeLikeWithID(mLikeAction.getPostLike(mCurrent_user,post.getId()));
                    mHeart.setImageResource(R.drawable.heart);
                }
                else{
                    Like newlike= new Like();
                    Date now= new Date(Calendar.getInstance().getTime().getTime());

                    newlike.setUser_id(mCurrent_user);
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
        bundle.putInt("CURRENT_USER",mCurrent_user);
        bundle.putInt("USER_PROFIL",mUserId_post);
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }
}