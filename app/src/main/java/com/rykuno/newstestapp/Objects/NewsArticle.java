package com.rykuno.newstestapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rykuno on 8/8/16.
 */
public class NewsArticle implements Parcelable{

    private String mTitle;
    private String mAuthor;
    private String mWebUrl;
    private String mThumbnail;

    public NewsArticle(String title, String author, String thumbnail, String webUrl) {
        mTitle = title;
        mAuthor = author;
        mThumbnail = thumbnail;
        mWebUrl = webUrl;
    }

    protected NewsArticle(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
        mWebUrl = in.readString();
        mThumbnail = in.readString();
    }

    public static final Creator<NewsArticle> CREATOR = new Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel in) {
            return new NewsArticle(in);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        if (mAuthor != null) {
            return mAuthor;
        }else{
            return null;
        }
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeString(mWebUrl);
        dest.writeString(mThumbnail);
    }
}
