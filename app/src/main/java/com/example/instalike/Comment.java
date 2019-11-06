package com.example.instalike;

public class Comment {

    private String mUserName;
    private byte[] mImagepp;
    private String mComment;
    public Comment(String username,byte [] mImagepp, String comment){
        this.mUserName=username;
        this.mImagepp=mImagepp;
        this.mComment=comment;

    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public byte[] getimagepp() {
        return mImagepp;
    }

    public void setimagepp(byte[] mImagepp) {
        this.mImagepp = mImagepp;
    }


    public String getmComment() {
        return mComment;
    }

}
