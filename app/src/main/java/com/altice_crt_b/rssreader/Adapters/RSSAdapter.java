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

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();

        Log.wtf("RSS_ADAPTER", "Data Set Changed");
    }

    @Override
    public int getCount() {
        return this.articles.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = (View) LayoutInflater.from(this.getContext()).inflate(R.layout.article_container, parent, false);
            ((TextView) convertView.findViewById(R.id.title)).setText(this.articles.get(position).getTitle());
            ((TextView) convertView.findViewById(R.id.author)).setText(this.articles.get(position).getAuthor());
            ((TextView) convertView.findViewById(R.id.description)).setText(this.articles.get(position).getDescription());
            ((TextView) convertView.findViewById(R.id.pubDate)).setText(this.articles.get(position).getPubDate().toString());
        }
        Log.wtf("RSS_ADAPTER", "Creating Object " + position);
        return convertView;
    }


}
