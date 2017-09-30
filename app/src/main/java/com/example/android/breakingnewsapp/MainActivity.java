package com.example.android.breakingnewsapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<String>, BreakingNewsAdapter.ListItemClickListener{
    private RecyclerView mNewsRecyclerView;
    private BreakingNewsAdapter mBreakingNewsAdapter;
    private List<BreakingNews> mBreakingNewsList = new ArrayList<>();
    private ProgressBar mNewsProgressBar;
    private TextView mNewsErrorMessage;

    private static final int NEWS_LOADER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        mNewsProgressBar = (ProgressBar) findViewById(R.id.news_progress_bar);
        mNewsErrorMessage = (TextView) findViewById(R.id.news_error_message);
        mNewsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        LoaderManager loaderManager = getLoaderManager();
        mNewsRecyclerView.setHasFixedSize(true);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

// Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

// Get a reference to the LoaderManager, in order to interact with loaders.


            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER, null, MainActivity.this).forceLoad();
        } else {

            // Otherwise, display error
            showErrorMessage();
        }
    }

    private void showErrorMessage() {
        mNewsProgressBar.setVisibility(View.INVISIBLE);

        mNewsErrorMessage.setVisibility(View.VISIBLE);
    }

    private void showDeveloperData() {
        mNewsProgressBar.setVisibility(View.INVISIBLE);
        mNewsErrorMessage.setVisibility(View.INVISIBLE);
        mNewsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public android.content.Loader<String> onCreateLoader(int id, Bundle args) {
        mNewsProgressBar.setVisibility(View.VISIBLE);
        mNewsRecyclerView.setVisibility(View.INVISIBLE);
        return new android.content.AsyncTaskLoader<String>(this) {

            @Override
            public String loadInBackground() {
                URL url = NetworkUtils.buildUrl();
                try {
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                    return jsonResponse;
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        if (data == null) {
            showErrorMessage();
        } else {

            showDeveloperData();
            responseFromJSon(data);
            mBreakingNewsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {
        loader.forceLoad();
    }

    public void responseFromJSon(String jsonResponse) {
        mNewsProgressBar.setVisibility(View.INVISIBLE);
        mNewsRecyclerView.setVisibility(View.VISIBLE);
        try {
            mBreakingNewsAdapter = new BreakingNewsAdapter(NetworkUtils.extractFeaturesFromJson(jsonResponse), MainActivity.this);
            mNewsRecyclerView.setAdapter(mBreakingNewsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNewsRecyclerView.setAdapter(null);
    }

    @Override
    public void onListItemClick(BreakingNews clickedItem) {
        Intent intent = new Intent(MainActivity.this, BreakingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("newsTitle", clickedItem.getmNewsTitle());
        bundle.putString("newsImage", clickedItem.getmNewsImage());
        bundle.putString("newsDate", clickedItem.getmNewsDate());
        intent.putExtras(bundle);
        startActivity(intent);

    }
    }
