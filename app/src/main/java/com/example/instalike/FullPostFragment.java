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

        import java.util.ArrayList;

public class FullPostFragment extends Fragment implements View.OnClickListener {
    private ImageView mPhoto,mHeart,mComment;
    private TextView mUsername, mDescription, mNbLike, mNbComment;
    private View view;
    int postisLike,imagePost,nbComment;
    String nbLike,username,description;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_page,container,false);

        username=getArguments().getString("USERNAME");
        description=getArguments().getString("DESCRIPTION");
        nbLike=getArguments().getString("NBLIKE");
        imagePost=getArguments().getInt("IMAGE");
        postisLike=getArguments().getInt("ISLIKE",0);
        nbComment=getArguments().getInt("NBCOMMENT",0);
        mPhoto=view.findViewById(R.id.post_page_image);
        mHeart=view.findViewById(R.id.post_page_heart);
        mComment=view.findViewById(R.id.post_page_comment);
        
        mUsername=view.findViewById(R.id.post_page_pseudo);
        mDescription=view.findViewById(R.id.post_page_description);
        mNbLike=view.findViewById(R.id.post_page_nbLike);
        mNbComment=view.findViewById(R.id.post_page_nbComment);

        mPhoto.setImageResource(imagePost);
        mHeart.setImageResource(postisLike);
        mUsername.setText(username);
        mDescription.setText(description);
        mNbLike.setText(nbLike);
        mComment.setOnClickListener(this);
        mHeart.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_page_heart:
                int currentLike=Integer.parseInt(nbLike);

                if (postisLike==R.drawable.redheart){
                    mHeart.setImageResource(R.drawable.heart);
                    currentLike--;
                    nbLike=String.valueOf(currentLike);
                    postisLike=R.drawable.heart;
                }
                else{
                    mHeart.setImageResource(R.drawable.redheart);
                    currentLike++;
                    nbLike=String.valueOf(currentLike);
                    postisLike=R.drawable.redheart;
                }
                mNbLike.setText(String.valueOf(currentLike));
                break;
            case R.id.post_page_comment:
                changeActivityToComment();
                break;
        }

    }
    public void changeActivityToComment(){
        Fragment selectedFragment= null;
        selectedFragment=new CommentFragment();

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }
}