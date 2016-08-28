package com.rykuno.newstestapp.Activities.Fragments;




import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rykuno.newstestapp.Adapters.ArticleAdapter;
import com.rykuno.newstestapp.Loaders.FragmentLoader;
import com.rykuno.newstestapp.Objects.NewsArticle;
import com.rykuno.newstestapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rykuno on 8/7/16.
 */
public class TechFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsArticle>>{
    private static final int FRAGMENT_LOADER_ID = 0;
    private static final String mUrlSection = "technology";
    private String mUrl;
    private List<NewsArticle> mNewsArticleList = new ArrayList<>();

    private Button mLoadMoreArticles;
    private ListView mListView;
    private ArticleAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private LoaderManager loaderManager;

    private int mCurrentPageNumber=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_layout, container, false);
        mLoadMoreArticles = (Button) rootView.findViewById(R.id.loadMore);
        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        mListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new ArticleAdapter(getActivity(), mNewsArticleList);
        mListView.setAdapter(mAdapter);

        if (isNetworkAvailable()) {
            updateUrl();
            loaderManager = getLoaderManager();
            loaderManager.initLoader(FRAGMENT_LOADER_ID, null, this);
        }else{
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.check_network);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsArticle currentItem = mNewsArticleList.get(position);
                if (currentItem.getWebUrl() != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.getWebUrl()));
                    startActivity(browserIntent);
                }else{
                    Toast.makeText(getActivity(), R.string.url_unavailable, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLoadMoreArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);
                v.startAnimation(animation);
                mCurrentPageNumber++;
                updateUrl();
                restartLoader();
            }
        });
        return rootView;
    }

    private void updateUrl(){
        mUrl = "https://content.guardianapis.com/search?&show-fields=thumbnail&show-tags=contributor&section="+mUrlSection+"&page="+mCurrentPageNumber+"&" +
                "api-key=test";
    }

    private void restartLoader(){
        if (isNetworkAvailable()) {
            loaderManager.restartLoader(FRAGMENT_LOADER_ID, null, this);
            Toast.makeText(getActivity(), "Page " + mCurrentPageNumber, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), R.string.check_network, Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle args) {
        return new FragmentLoader(getActivity(), mUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> data) {
        mLoadingIndicator.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }else{
            mEmptyStateTextView.setText(R.string.articles_not_available);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        mAdapter.clear();
    }
}
