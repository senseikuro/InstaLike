package com.example.instalike;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.instalike.db.Comment;
import com.example.instalike.db.CommentActions;
import com.example.instalike.db.Like;
import com.example.instalike.db.LikeActions;
import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;
import com.example.instalike.db.UserActions;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class FullPostFragment extends Fragment implements View.OnClickListener {
    private ImageView mPhoto,mHeart,mComment,mPP;
    private TextView mUsername, mDescription, mNbLike, mNbComment;
    private View view;
    private int mPostID, nbLike,nbComment, mCurrent_user, mUserId_post;
    private LikeActions mLikeAction;
    private Post post;
    private PostActions postAction;
    private UserActions mUserActions;
    private ImageView mModify;

    private static final int   MENU_SUPPRIMER= Menu.FIRST;
    private static final int   MENU_EDIT= Menu.FIRST+1;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_page,container,false);

        mPostID=getArguments().getInt("POST_ID");
        mCurrent_user=getArguments().getInt("CURRENT_USER");
        mUserId_post=getArguments().getInt("USER_PROFIL");
        System.out.println(mUserId_post);
        mPhoto=view.findViewById(R.id.post_page_image);
        mHeart=view.findViewById(R.id.post_page_heart);
        mComment=view.findViewById(R.id.post_page_comment);
        mPP=view.findViewById(R.id.post_page_Image_pp);
        mModify=view.findViewById(R.id.post_page_menu);

        mUsername=view.findViewById(R.id.post_page_pseudo);
        mDescription=view.findViewById(R.id.post_page_description);
        mNbLike=view.findViewById(R.id.post_page_nbLike);
        mNbComment=view.findViewById(R.id.post_page_nbComment);
        postAction=new PostActions(getContext());
        mModify.setVisibility(view.INVISIBLE);
        setRecources();
        if(mCurrent_user==mUserId_post){
            mModify.setVisibility(view.VISIBLE);
            mModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().openContextMenu(v);
                }
            });
            registerForContextMenu(mModify);
        }


        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo){

            super.onCreateContextMenu(contextMenu,view,menuInfo);
            MenuItem logout=contextMenu.findItem(R.id.menu_parameter_log_out);// vient prendre le menu de l'activity qui l'entoure donc en se retrouvait avec modify supprimer et log out
            logout.setVisible(false);
            getActivity().getMenuInflater().inflate(R.menu.menu_post,contextMenu);

    }

    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_post_supprimer:
                supprimerPost();
                break;
            case R.id.menu_post_modify:
                modifyDescription();
                break;

        }
        return super.onOptionsItemSelected(item);

    }


    private void supprimerPost(){
        postAction.suppPost(mPostID);
        LikeActions likeActions= new LikeActions(getContext());
        ArrayList<Integer> like= new ArrayList<>();
        like=likeActions.getAllUserLikePost(mPostID);

        for(int i=0;i<like.size();i++){
            likeActions.removeLikeWithID(like.get(i));
        }

        CommentActions commentActions= new CommentActions(getContext());
        ArrayList<Comment> comments= new ArrayList<>();
        comments=commentActions.getAllComments(mPostID);
        for (int i=0;i<comments.size();i++){
            commentActions.removeCommentWithID(comments.get(i).getId());
        }
        Fragment selectedFragment= null;
        selectedFragment=new ProfilFragement();
        Bundle bundle= new Bundle();
        bundle.putInt("CURRENT_USER",mCurrent_user);
        bundle.putInt("USER_PROFIL",mCurrent_user);
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
    }
    public void setRecources(){

        nbComment=postAction.getNbComment(mPostID);
        nbLike=postAction.getNbLike(mPostID);
        post=postAction.getPostFromID(mPostID);

        byte[] outImage=post.getPhoto_path();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        mPhoto.setImageBitmap(theImage);
        postAction.close();
        System.out.println(mPhoto);

        mUsername.setText(postAction.getUserName(post.getUser_id()));
        mDescription.setText(post.getDescription());
        mNbLike.setText(String.valueOf(nbLike));
        mNbComment.setText(String.valueOf(nbComment));
        mComment.setOnClickListener(this);
        mHeart.setOnClickListener(this);
        mPP.setOnClickListener(this);
        mUsername.setOnClickListener(this);
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
        mUserActions= new UserActions(getContext());


        if (mUserActions.getUserPP(mUserId_post)!=null){
            outImage=mUserActions.getUserPP(mUserId_post);
            imageStream = new ByteArrayInputStream(outImage);
            theImage = BitmapFactory.decodeStream(imageStream);
            mPP.setImageBitmap(theImage);
        }


    }
    public void modifyDescription(){
        Context context=getContext();
        EditText editDescription= new EditText(context);
        Post post=postAction.getPostFromID(mPostID);
        editDescription.setText(post.getDescription());
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Modify your description")
                .setView(editDescription)
                .setPositiveButton("modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newEdit= String.valueOf(editDescription.getText());
                        Post post= new Post();
                        post= postAction.getPostFromID(mPostID);
                        post.setDescription(newEdit);
                        postAction.updatePost(mPostID,post);
                        setRecources();


                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
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
            case R.id.post_page_Image_pp:
                changeActivityToProfil();
                break;
            case R.id.post_page_pseudo:
                changeActivityToProfil();
                break;
        }

    }
    public void changeActivityToProfil(){
        Fragment selectedFragment= null;
        selectedFragment=new ProfilFragement();
        Bundle bundle= new Bundle();
        bundle.putInt("CURRENT_USER",mCurrent_user);
        bundle.putInt("USER_PROFIL",mUserId_post);
        selectedFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rvPosts,
                selectedFragment).addToBackStack(null).commit();
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