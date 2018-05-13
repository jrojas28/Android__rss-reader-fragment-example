package com.altice_crt_b.rssreader;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    /**
     * ----------------------------
     * ------ Class Variables------
     * ----------------------------
     * Webview ArticleView   The identified view on which articles will be displayed.
     */
    private WebView articleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //Since this activity is called from an intent and should always use an URL, we'll
        //always consider we'll be receiving it as the only data on the Intent.
        Uri data = getIntent().getData();
        //Variable Initialization
        articleView = (WebView) findViewById(R.id.article_view);
        //We need a webClient to manage and decode the HTTP response, so we set a generic
        //web client to the WebView.
        articleView.setWebViewClient(new WebViewClient());
        //Finally, we load the URL into the view.
        articleView.loadUrl(data.toString());
    }
}
