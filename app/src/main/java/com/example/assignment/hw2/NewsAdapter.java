package com.example.assignment.hw2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {

    Context context;
    List<NewsItem> newsItems;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlenewsstory, parent, false);
        return new NewsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsItemViewHolder holder, int position) {
        final NewsItem newsItem = newsItems.get(position);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "" + newsItem.getWebUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        holder.title.setText("Title : " + newsItem.getTitle());
        holder.description.setText("Description : " + newsItem.getDescription());
        holder.publishedat.setText("Date  : " + newsItem.getPublishedAt());


    }

    @Override
    public int getItemCount() {
        if (newsItems != null) return newsItems.size();
        else return 0;
    }


    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView title, description, publishedat;
        public NewsItemViewHolder(View v) {
            super(v);
            layout = v.findViewById(R.id.linear);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.desc);
            publishedat = v.findViewById(R.id.date);

        }
    }
}
