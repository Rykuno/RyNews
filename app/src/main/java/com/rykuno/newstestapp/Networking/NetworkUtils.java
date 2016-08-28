package com.rykuno.newstestapp.Networking;

import android.content.Context;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.rykuno.newstestapp.Objects.NewsArticle;
import com.rykuno.newstestapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rykuno on 8/7/16.
 */

public class NetworkUtils {

    private static String LOG_CAT = NetworkUtils.class.getSimpleName();

    public static List<NewsArticle> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NewsArticle> newsArticleList = new ArrayList<>(extractFeatureFromJson(jsonResponse));
        return  newsArticleList;
    }

    private static List<NewsArticle> extractFeatureFromJson(String jsonResponse) {
        List<NewsArticle> newsArticleList = new ArrayList<>();
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try{
            String webTitle;
            String webUrl;
            String thumbnail;
            String author=null;

            JSONObject rootJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = rootJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i =0; i<results.length(); i++){
                JSONObject currentResult = results.getJSONObject(i);

                if ((currentResult.has("webTitle") && currentResult.has("webUrl"))) {
                    webTitle = currentResult.getString("webTitle");
                    webUrl = currentResult.getString("webUrl");
                }else{
                    webTitle = "Not Available";
                    webUrl = null;
                }

                if (currentResult.has("fields")) {
                    JSONObject fields = currentResult.getJSONObject("fields");
                    thumbnail = fields.getString("thumbnail");
                }else{
                    thumbnail = null;
                }

                if(currentResult.has("tags")) {
                    JSONArray tags = currentResult.getJSONArray("tags");
                    if (tags.length() == 0) {
                        author = null;
                    } else {
                        for (int j = 0; j < tags.length(); j++) {
                            JSONObject currentTag = tags.getJSONObject(j);
                            author = currentTag.getString("webTitle");
                        }
                    }
                }

                newsArticleList.add(new NewsArticle(webTitle, author, thumbnail, webUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsArticleList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
