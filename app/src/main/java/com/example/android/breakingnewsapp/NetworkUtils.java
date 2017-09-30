package com.example.android.breakingnewsapp;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Phinatu on 05/09/2017.
 */

public class NetworkUtils {

        private static final String GITHUB_URL = "https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=5e89c23f36cc48e080cdec1fbe97aad5";

        public static URL buildUrl() {
            Uri buildUri = Uri.parse(GITHUB_URL).buildUpon().build();
            URL url = null;
            try {
                url = new URL(buildUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        public static String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }

        public static List<BreakingNews> extractFeaturesFromJson(String NewsJSON) throws JSONException {
            if (TextUtils.isEmpty(NewsJSON)) {
                return null;
            }

            List<BreakingNews> breakingNews = new ArrayList<>();
            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                // Create a JSONObject from the JSON response string
                JSONObject baseJsonResponse = new JSONObject(NewsJSON);

                // Extract the JSONArray associated with the key called "item",
                // which represents a list of items (or breaking news).
                JSONArray breakingNewsArray = baseJsonResponse.getJSONArray("items");

                // If there are results in the items array
                for (int i = 0; i <breakingNewsArray.length(); i++) {
                    JSONObject currentBreakingNews = breakingNewsArray.getJSONObject(i);
                    String newsTitle = currentBreakingNews.getString("news_title");
                    String newsPic = currentBreakingNews.getString("news_pic");
                    String newsDate = currentBreakingNews.getString("news_date");

                   BreakingNews news = new BreakingNews(newsTitle, newsPic, newsDate);
                    breakingNews.add(news);
                }
                // build up a list of breaking news objects with the corresponding data.

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                e.printStackTrace();
            }

            // Return the list of breaking news
            return breakingNews;
        }
    }