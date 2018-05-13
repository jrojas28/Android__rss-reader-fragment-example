package com.altice_crt_b.rssreader.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altice_crt_b.rssreader.R;
import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by jaime on 5/12/2018.
 */

public class RSSAdapter extends ArrayAdapter {
    /**
     * ----------------------------
     * ------ Class Variables------
     * ----------------------------
     * ArrayList<article> articles      The article objects held by the adapter.
     */
    public ArrayList<Article> articles;

    public RSSAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.articles = new ArrayList<Article>();
    }

    public RSSAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.articles = new ArrayList<Article>(objects);
    }

    @Override
    public Object getItem(int position) {
        return this.articles.get(position);
    }

    @Override
    public int getPosition(Object object) {
        return this.articles.indexOf(object);
    }

    /**
     * setArticles      a setter intended to change the article list as a whole.
     * @param articles  The article list that'll be used by the Adapter.
     */
    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
        //Because we need to update the view too, we notify changes
        //This allows further events to know of the new article array and
        //proceed updating the view as expected.
        notifyDataSetChanged();
    }

    /**
     * getCount         a function intended to obtain the amount of elements in the objects list.
     *                  Due to how we send initially an empty object array to the SUPER on the
     *                  constructor, we need to override this function to use our own articles list
     *                  which can be overriden by the setArticles function. Otherwise, the count will
     *                  only return 0 because no list is passed to SUPER in the first constructor.
     * @return count    The amount of elements within the articles list.
     */
    @Override
    public int getCount() {
        return this.articles.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            //If there's no view, that means this article is a new one not yet processed.
            Article article = this.articles.get(position);
            //Now, we'll need to inflate an Article Container layout, providing us with a full View
            //with the fields necessary to fill in the data for the new article.
            convertView = (View) LayoutInflater.from(this.getContext()).inflate(R.layout.article_container, parent, false);
            ((TextView) convertView.findViewById(R.id.title)).setText(article.getTitle());
            ((TextView) convertView.findViewById(R.id.author)).setText(article.getAuthor());
            ((TextView) convertView.findViewById(R.id.description)).setText(article.getDescription());
            ((TextView) convertView.findViewById(R.id.pubDate)).setText(article.getPubDate().toString());
        }
        //Finally, return the view.
        return convertView;
    }


}
