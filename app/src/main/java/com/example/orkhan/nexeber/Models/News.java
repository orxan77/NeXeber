package com.example.orkhan.nexeber.Models;

/**
 * Created by Orkhan on 3/23/2018.
 */

public class News {

    private int mId;
    private int mServiceId;
    private String mTitle;
    private String mDescription;
    private String mNewsUrl;
    private String mImageUrl;
    private String mCreateDate;
    private int mViewCount;

    public News(int id, String title, String description, String newsUrl, String imageUrl, String createDate, int serviceId) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mNewsUrl = newsUrl;
        this.mImageUrl = imageUrl;
        this.mCreateDate = createDate;
        this.mServiceId = serviceId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public int getWebsiteId() {
        return mServiceId;
    }

}
