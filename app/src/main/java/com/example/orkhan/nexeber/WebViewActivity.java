package com.example.orkhan.nexeber;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by Orkhan on 3/27/2018.
 */

public class WebViewActivity extends AppCompatActivity {

    private static final String LOG_TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);
        Log.e(LOG_TAG, "onCreate: WebViewActivity called");

        String newsUrl = getIntent().getStringExtra("newsUrl");
        webView.loadUrl(newsUrl);
        webView.setNetworkAvailable(true);
        webView.canGoBack();
    }
}
