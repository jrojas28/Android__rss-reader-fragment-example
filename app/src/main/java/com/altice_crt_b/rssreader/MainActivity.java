package com.altice_crt_b.rssreader;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.altice_crt_b.rssreader.Adapters.RSSAdapter;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WebFragment.OnFragmentInteractionListener {
    /**
     * ----------------------------
     * ----- Static Variables -----
     * ----------------------------
     * TAG          Serves as the Main Activity's TAG when Logging.
     * RSS_FEED_URL The URL from where we'll be fetching.
     */
    final public static String TAG = "MAIN";
    final public static String RSS_FEED_URL = "https://www.wired.com/feed/rss";

    /**
     * ----------------------------
     * ------ Class Variables------
     * ----------------------------
     * RSSAdapter rssAdapter            The Adapter intended to organize and adapt each article to a view.
     * ListView articleList             The listView item on which all views created by the rssAdapter will be attached
     * WebFragment articleFragment      The fragment to display on the second half of the page.
     * FrameLayout articleLoadingFrame  The frameLayout that holds the round progress bar shown before loading all articles.
     */

    private RSSAdapter rssAdapter;
    private ListView articleList;
    private WebFragment articleFragment;
    private FrameLayout articleLoadingFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Variable Initialization
        rssAdapter = new RSSAdapter(this, 0);
        articleList = (ListView) findViewById(R.id.article_list);
        articleList.setAdapter(rssAdapter);
        articleLoadingFrame = (FrameLayout) findViewById(R.id.article_loading);
        articleFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);
        //Create the listener for each item within the articleList.
        //This will, in turn allow to show every article on the fragment below when clicked.
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Find the article position.
                Article article = (Article) rssAdapter.getItem(i);
                //Obtain the URL linking to it.
                String url = article.getLink();
                //Load the URL on the fragment based on this custom function.
                articleFragment.loadUrl(url);

            }
        });
        //Create a parser for the RSS Feed
        Parser parser = new Parser();
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                //When the parser completes, set the article list to the adapter
                //After doing so, hide the loading icon for all articles.
                rssAdapter.setArticles(list);
                articleLoadingFrame.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.wtf(TAG, "There was a problem fetching the RSS feed.");
            }
        });
        //Excecute parsing.
        parser.execute(RSS_FEED_URL);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        /**
         * This is REQUIRED for Fragments to work.
         * This basically helps with Fragment interaction, allowing the master view
         * (in this case, the main view) to know when certain actions happen within the view.
         * Since our example just shows articles, and we don't need to know if the article has
         * been swiped or clicked, no code is necessary to handle this.
         */
    }
}
