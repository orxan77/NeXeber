package com.example.orkhan.nexeber;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.orkhan.nexeber.Adapters.DrawerListItemAdapter;
import com.example.orkhan.nexeber.Adapters.ViewPagerAdapter;
import com.example.orkhan.nexeber.Models.DrawerListItem;
import com.example.orkhan.nexeber.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<DrawerListItem> websitesArrayList;
    private ArrayList<String> selectedWebsitesIDs;
    private CustomViewPager viewPager;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbarAndComponents();
        loadDefaultWebsitesList();
    }

    private void setUpToolbarAndComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.left_drawer);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager = findViewById(R.id.viewPager_id);
        viewPager.setPagingEnabled(true);
        viewPager.setAdapter(pagerAdapter);
        implementViewPagerChangeListener(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout_id);
        tabLayout.setupWithViewPager(viewPager);
        setTitle("");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                selectedWebsitesIDs = new ArrayList<>();
                DrawerListItemAdapter adapter = new DrawerListItemAdapter(getBaseContext(), websitesArrayList);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String id = websitesArrayList.get(i).getServiceIdOfWebsite() + "";
                        if (selectedWebsitesIDs.contains(id))
                            selectedWebsitesIDs.remove(id);
                        else
                            selectedWebsitesIDs.add(id);
                    }
                });

            }

            @Override
            public void onDrawerClosed(View drawerView) {

//                getSupportFragmentManager().beginTransaction().replace(
//                        R.id.viewPager_id,
//                        FragmentAzerbaijani.instantiate(getApplicationContext(), "com.example.orkhan.nexeber.Fragments.FragmentAzerbaijani"))
//                        .commit();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    private void loadDefaultWebsitesList() {
        if (websitesArrayList != null)
            websitesArrayList.clear();
        getWebsiteList(NetworkUtils.URL_ALL_SERVICES);
    }

    private void implementViewPagerChangeListener(CustomViewPager viewPager) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        implementWebsiteDrawer(NetworkUtils.URL_ALL_SERVICES);
                        break;
                    case 1:
                        implementWebsiteDrawer(NetworkUtils.URL_AZERBAIJANI_SERVICES);
                        break;
                    case 2:
                        implementWebsiteDrawer(NetworkUtils.URL_ENGLISH_SERVICES);
                        break;
                    case 3:
                        implementWebsiteDrawer(NetworkUtils.URL_RUSSIAN_SERVICES);
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Default is called", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void implementWebsiteDrawer(String URL) {
        if (websitesArrayList != null)
            websitesArrayList.clear();
        getWebsiteList(URL);
    }

    private void getWebsiteList(String URL) {
        websitesArrayList = new ArrayList<>();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getBaseContext());
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject currentWebsite = response.getJSONObject(i);
                                websitesArrayList.add(new DrawerListItem(
                                        currentWebsite.getString("name"),
                                        currentWebsite.getInt("id")
                                ));
                            }

                        } catch (JSONException e) {
                            Log.e(TAG, "Error occurred while parsing Websites JSON ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Website Volley Error");
                    }
                }
        );

        mRequestQueue.add(arrayRequest);
    }

}

