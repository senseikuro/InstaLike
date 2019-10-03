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

public class FullPostFragment extends Fragment {
    private ImageView mPhoto,mHeart,mComment;
    private TextView mUsername, mDescription, mNbLike, mNbComment;
    private View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_page,container,false);

        String username=getArguments().getString("USERNAME");
        String description=getArguments().getString("DESCRIPTION");
        String nbLike=getArguments().getString("NBLIKE");
        int imagePost=getArguments().getInt("IMAGE");
        int postisLike=getArguments().getInt("ISLIKE",0);
        int nbComment=getArguments().getInt("NBCOMMENT",0);
        System.out.println(nbLike);
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



        return view;
    }
}