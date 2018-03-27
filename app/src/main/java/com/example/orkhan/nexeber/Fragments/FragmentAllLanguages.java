package com.example.orkhan.nexeber.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.orkhan.nexeber.News;
import com.example.orkhan.nexeber.Adapters.NewsAdapter;
import com.example.orkhan.nexeber.R;
import com.example.orkhan.nexeber.Utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Orkhan on 3/23/2018.
 */

public class FragmentAllLanguages extends Fragment {

    private static final String LOG_TAG = FragmentAllLanguages.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private List<News> mNewsList;
    private RequestQueue mRequest;
    private ProgressBar mProgressBar;
    private ProgressBar mBottomProgressBar;
    private NetworkUtils mNetworkUtils;
    private TextView mErrorTextView;
    private LinearLayoutManager mLinearLayoutManager;

    public FragmentAllLanguages() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_languages_fragment, container, false);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if (!recyclerView.canScrollVertically(1)){
//                    Log.e(TAG, "onScrollStateChanged: reached end" );
//                    fetchData(NetworkUtils.URL_ALL_NEWS + "&page=" + pageNumber++);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
        mNetworkUtils = new NetworkUtils(getContext());
        mNewsList = new ArrayList<>();
        mRequest = Volley.newRequestQueue(getContext());

        initViews(view);
        mProgressBar.setVisibility(View.VISIBLE);
        fetchData(NetworkUtils.URL_ALL_NEWS);
        implementScrollListener();

        return view;
    }


    private void initViews(View view) {
        mProgressBar = view.findViewById(R.id.progressbar);
        mBottomProgressBar = view.findViewById(R.id.bottom_progressbar);
        mErrorTextView = view.findViewById(R.id.error_textview);
        initAndSetUpRecyclerView(view);

    }

    private void initAndSetUpRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_all_languages);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void fetchData(String URL) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            mNetworkUtils.parseJSON(mNewsList, response);

                            if (mNewsList != null && mNewsList.size() != 0) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                mNewsAdapter = new NewsAdapter(getContext(), mNewsList);
                                mRecyclerView.setAdapter(mNewsAdapter);
                            } else {
                                Log.e(LOG_TAG, "mNewsList is null or size is 0");
                                showErrorMessage();
                            }
                        } catch (JSONException e) {
                            Log.e(LOG_TAG, "Error occurred while parsing");
                            showErrorMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(LOG_TAG, "Error occurred");
                        showErrorMessage();
                    }
                }
        );

        mRequest.add(request);
    }

    private void showErrorMessage() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }


    private boolean isUserScrolled = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    int pageNumber = 2;

    private void implementScrollListener() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isUserScrolled = true;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLinearLayoutManager.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                pastVisibleItems = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (isUserScrolled && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    isUserScrolled = false;
                    mBottomProgressBar.setVisibility(View.VISIBLE);
                    fetchData(NetworkUtils.URL_ALL_NEWS + "&page=" + pageNumber++);
                    mBottomProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

}
