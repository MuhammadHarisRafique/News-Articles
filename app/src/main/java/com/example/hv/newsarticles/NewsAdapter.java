package com.example.hv.newsarticles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hv on 11/25/16.
 */
public class NewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<News> items;

    public NewsAdapter(Context context, ArrayList<News> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // inflate the layout for each list row
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_news, parent, false);

        //get current item to be displayed
        News currentItem = (News) getItem(position);

        //get the TextView for book name, book author and book publisher

        TextView newsTitle = (TextView) convertView.findViewById(R.id.newstitle);

        TextView newsContentSnippet = (TextView) convertView.findViewById(R.id.newscontentsnippet);

        TextView newsLink = (TextView) convertView.findViewById(R.id.newslink);

        //sets the text for book name, book author and book publisher from the current item object
        newsTitle.setText(currentItem.getTitle());
        newsContentSnippet.setText(currentItem.getContentSnippet());
        newsLink.setText(currentItem.getLink());

        //returns the view for the current row
        return convertView;
    }
}
