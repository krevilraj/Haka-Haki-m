package com.mayurit.hakahaki.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectModel {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("like_count")
    @Expose
    private Object likeCount;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Object getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Object likeCount) {
        this.likeCount = likeCount;
    }

}