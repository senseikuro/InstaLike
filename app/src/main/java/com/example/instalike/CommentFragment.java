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
import java.util.ArrayList;


public class CommentFragment extends Fragment{
    @Nullable
    private ImageView photo;
    private ImageView heart;
    private CommentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> Posts;
    private ArrayList<Comment> mListComment;

    private View view;
    private Button mPublish;
    private EditText mComment;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment,container,false);
        createList();
        createComment(0);
        buildRecycleView();
        mPublish=view.findViewById(R.id.fragment_comment_publish_btn);







        mComment=view.findViewById(R.id.fragment_comment_edit_texte);
        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListComment.add(new Comment("Florian",R.drawable.paysage4, mComment.getText().toString()));
                Posts.get(0).setmListComment(mListComment);
                mAdapter.notifyDataSetChanged();
                buildRecycleView();
            }
        });
        return view;
    }

    public void changeItem(int position, String text){
        Posts.get(position).changeDescription("clicked");
        mAdapter.notifyItemChanged(position);
    }

    public void createList(){
        Posts =new ArrayList<Post>();
        Posts.add(new Post("ichiban japan", "super voyage à tokyo",R.drawable.paysage2,"120"));
        Posts.add(new Post("VincentJouanne", "i love BJJ",R.drawable.paysage3,"110"));
        Posts.add(new Post("Florent Brassac", "t'as dead ça chacal",R.drawable.paysage4,"105"));
        Posts.add(new Post("PaullBoveyron", "Je suis une locomotive",R.drawable.paysage5,"23"));
    }

    public void createComment(int position){
        mListComment=new ArrayList<Comment>();
        mListComment.add(new Comment("vincent",R.drawable.paysage2, "superbePhoto"));
        mListComment.add(new Comment("paul",R.drawable.paysage3, "nice"));
        mListComment.add(new Comment("Thomas",R.drawable.paysage4, "super à visiter"));
        Posts.get(position).setmListComment(mListComment);
        System.out.println(Posts.get(position).getmListComment());
    }
    public void buildRecycleView(){

        mRecyclerView=view.findViewById(R.id.ListCommentView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(getActivity());

        mAdapter = new CommentAdapter(Posts.get(0).getmListComment());

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