package com.example.instalike;

public class Comment {

    private String mUserName;
    private int mImagePosts;
    private String mComment;
    public Comment(String username,int imagePosts, String comment){
        mUserName=username;
        mImagePosts=imagePosts;
        mComment=comment;

    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public int getmImagePosts() {
        return mImagePosts;
    }

    public void setmImagePosts(int mImagePosts) {
        this.mImagePosts = mImagePosts;
    }


    public String getmComment() {
        return mComment;
    }

}
