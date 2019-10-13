package com.example.instalike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instalike.db.CommentActions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


public class CommentFragment extends Fragment{
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private CommentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<com.example.instalike.db.Comment> mListCommentBdd;
    private CommentActions actionComment;
    private ArrayList<Comment> mListComment;
    private int mPostID, mCurrentUserID;
    private View view;
    private Button mPublish;
    private EditText mComment;

    private CommentActions commentActions;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment,container,false);

        mPostID=getArguments().getInt("POST_ID");
        mCurrentUserID=getArguments().getInt("Current_User");
        mPublish=view.findViewById(R.id.fragment_comment_publish_btn);
        mComment=view.findViewById(R.id.fragment_comment_edit_texte);


        createList();
        buildRecycleView();

        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date(Calendar.getInstance().getTime().getTime());
                com.example.instalike.db.Comment newComment= new com.example.instalike.db.Comment(mCurrentUserID, mPostID,mComment.getText().toString(),now);
                commentActions.insertComment(newComment);
                mListComment.add(new Comment(commentActions.getPseudoComment(newComment.getUser_id()),R.drawable.paysage5,newComment.getContent()));
                mAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }



    public void changeItem(int position, String text){
        mAdapter.notifyItemChanged(position);
    }

    public void createList(){
        mListCommentBdd= new ArrayList<com.example.instalike.db.Comment>();
        actionComment=new CommentActions(getContext());
        mListCommentBdd=actionComment.getAllComments(mPostID);
        commentActions= new CommentActions(getContext());

        mListComment=new ArrayList<Comment>();
        for (int i =0; i<mListCommentBdd.size();i++){
            mListComment.add(new Comment(commentActions.getPseudoComment(mListCommentBdd.get(i).getUser_id()),R.drawable.paysage5,mListCommentBdd.get(i).getContent()));
        }



    }

    public void createComment(int position){

    }
    public void buildRecycleView(){

        mRecyclerView=view.findViewById(R.id.ListCommentView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());

        mAdapter = new CommentAdapter(mListComment);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"clicked");

            }

        });


    }
}