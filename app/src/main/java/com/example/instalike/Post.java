package com.example.instalike;

import android.graphics.ColorFilter;
import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

public class Post {

    private String mUserName;
    private String mDescription;
    private int mImagePosts;
    private String mLike;
    private int mColorLike;
    private boolean islike;
    private ArrayList<Comment> mListComment;
    public Post(String username, String description, int imagePosts, String like){
        mUserName=username;
        mDescription=description;
        mImagePosts=imagePosts;
        mLike=like;
        mColorLike=R.drawable.heart;
        islike=false;
    }
    public Post(){

    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmDescription() {
        return mDescription;
    }

    private static int lastPostID = 0;

    public int getmImagePosts() {
        return mImagePosts;
    }
    public void setImagePosts(int image){mImagePosts=image;}
    public void changeDescription(String string){
        mDescription=string;
    }

    public String getmLike() {
        return mLike;
    }

    public void setUserName(String user){mUserName=user;}
    public void setDescription(String des){mDescription=des;}

    public void setmLike(String mLike) {
        this.mLike = mLike;
    }

    public int getmColorLike() {
        return mColorLike;
    }

    public void setmColorLike(int mColorLike) {
        this.mColorLike = mColorLike;
    }

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public void addComment(Comment comment){
        mListComment.add(comment);
    }
    public ArrayList<Comment> getmListComment() {
        ArrayList<Comment> comment=new ArrayList<Comment>();
        for (int i=0; i<mListComment.size();i++){
            comment.add(mListComment.get(i));
        }

            return comment;
    }

    public void setmListComment(ArrayList<Comment> mListComment) {
        this.mListComment = mListComment;
    }
    public String getOneComment(int position){
        return mListComment.get(position).getmComment();
    }
}
