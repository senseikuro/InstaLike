package com.example.instalike;

import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.media.Image;
import android.widget.ImageView;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;

public class Post {

    private String mUserName;
    private String mDescription;
    private byte[] mImagePosts;
    private String mLike;
    private int mColorLike;
    private boolean islike;
    private ArrayList<Comment> mListComment;
    private byte[] mpp;
    private String date;

    public Post(String username, String description, byte[] imagePosts, byte[] mpp, String like, int color, boolean islike, String date){
        mUserName=username;
        mDescription=description;
        mImagePosts=imagePosts;
        mLike=like;
        mColorLike=color;
        this.islike=islike;
        this.mpp=mpp;
        this.date=date;
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

    public byte[] getmImagePosts() {
        return mImagePosts;
    }
    public void setImagePosts(byte[] image){mImagePosts=image;}
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

    public byte[] getMpp() {
        return mpp;
    }

    public void setMpp(byte[] mpp) {
        this.mpp = mpp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
