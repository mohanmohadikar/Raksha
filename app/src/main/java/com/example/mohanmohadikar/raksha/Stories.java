package com.example.mohanmohadikar.raksha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Stories extends AppCompatActivity {


    private WebView brow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        brow = (WebView) findViewById(R.id.webview);
        brow.setWebViewClient(new MyBrowser());
        brow.getSettings().setJavaScriptEnabled(true);
        brow.loadUrl("https://thewellarmedwoman.com/womens-survival-stories/");

    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
