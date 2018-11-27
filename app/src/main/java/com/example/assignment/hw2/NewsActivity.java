package com.example.assignment.hw2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    NewsAdapter newsad;
    NewsItemViewModel newsStoriesViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_stories);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        newsad = new NewsAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NewsActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsad);

        newsStoriesViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

       // newsStoriesViewModel.getDataFromServer();

        newsStoriesViewModel.getNewsSories().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                newsad.setNewsItems(newsItems);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.refresh) {
            if (NetworkUtils.isOnline(NewsActivity.this)) {

                newsStoriesViewModel.getDataFromServer();
                newsStoriesViewModel.getNewsSories().observe(this, new Observer<List<NewsItem>>() {
                    @Override
                    public void onChanged(@Nullable List<NewsItem> newsItems) {
                        newsad.setNewsItems(newsItems);
                    }
                });
            }
            else {
                Toast.makeText(this, "Not connected to internet.", Toast.LENGTH_LONG).show();
            }
        }

        return true;
    }

}
