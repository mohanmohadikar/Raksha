package com.example.mohanmohadikar.raksha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Safety extends AppCompatActivity {
    private WebView brow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);


        brow = (WebView) findViewById(R.id.webviewSafety);
        brow.setWebViewClient(new MyBrowser());
        brow.getSettings().setJavaScriptEnabled(true);
        brow.loadUrl("https://issuesiface.com/magazine/top-10-safety-tips-for-women");
    }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
