package com.example.orkhan.nexeber.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.orkhan.nexeber.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Orkhan on 3/23/2018.
 */

public class NetworkUtils {

    public static final String BASE_URL = "http://www.nexeber.com/api";
    public static final String URL_ALL_NEWS = BASE_URL + "/news?lang=az";
    private Context mContext;

    public NetworkUtils(Context context) {
        this.mContext = context;
    }

    public void parseJSON(List<News> newsList, JSONObject jsonObject) throws JSONException {
        JSONArray newses = jsonObject.getJSONArray("data");

        for (int i = 0; i < newses.length(); i++) {
            JSONObject currentNews = newses.getJSONObject(i);
            newsList.add(new News(
                    currentNews.getInt("id"),
                    currentNews.getString("title"),
                    currentNews.getString("description"),
                    currentNews.getString("link"),
                    currentNews.getString("image"),
                    currentNews.getString("created_at"),
                    currentNews.getInt("service_id")
            ));
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
