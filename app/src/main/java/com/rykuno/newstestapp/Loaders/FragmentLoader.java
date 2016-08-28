package com.rykuno.newstestapp.Loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.rykuno.newstestapp.Networking.NetworkUtils;
import com.rykuno.newstestapp.Objects.NewsArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rykuno on 8/8/16.
 */
public class FragmentLoader extends AsyncTaskLoader<List<NewsArticle>> {
    private String mUrl;
    private List<NewsArticle> mData;


    public FragmentLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<NewsArticle> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<NewsArticle> newsArticleList= new ArrayList<>(NetworkUtils.fetchNewsData(mUrl));
        return newsArticleList;
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            // Use cached data
            deliverResult(mData);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }
    }

    @Override
    public void deliverResult(List<NewsArticle> data) {
        mData = data;
        super.deliverResult(data);

    }
}


