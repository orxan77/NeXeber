package com.example.orkhan.nexeber.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.orkhan.nexeber.Models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Orkhan on 3/23/2018.
 */

public class NetworkUtils {

    private static final String BASE_URL = "http://www.nexeber.com/api";
    public static final String URL_NEWS = BASE_URL + "/news";
    public static final String URL_ENGLISH_NEWS = URL_NEWS + "?lang=en";
    public static final String URL_AZERBAIJANI_NEWS = URL_NEWS + "?lang=az";
    public static final String URL_RUSSIAN_NEWS = URL_NEWS + "?lang=ru";
    public static final String URL_ALL_SERVICES = BASE_URL + "/services";
    public static final String URL_AZERBAIJANI_SERVICES = URL_ALL_SERVICES + "?lang=az";
    public static final String URL_ENGLISH_SERVICES = URL_ALL_SERVICES + "?lang=en";
    public static final String URL_RUSSIAN_SERVICES = URL_ALL_SERVICES + "?lang=ru";
    public static final String URL_ALL_NEWS = BASE_URL + "/news";

    private Context mContext;

    public NetworkUtils(Context context) {
        this.mContext = context;
    }

    public void parseNewsJSON(List<News> newsList, JSONObject jsonObject) throws JSONException {
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
