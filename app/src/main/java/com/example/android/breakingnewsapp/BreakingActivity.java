package com.example.android.breakingnewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

/**
 * Created by Phinatu on 05/09/2017.
 */

public class BreakingActivity extends AppCompatActivity{

   ImageView mNewsImage;
    TextView mNewsTitle;
    TextView mNewsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breaking_activity);

        mNewsImage= (ImageView) findViewById(R.id.news_image);
        mNewsTitle = (TextView) findViewById(R.id.news_title);
       mNewsDate = (TextView) findViewById(R.id.news_date);

        Intent intent = getIntent();
        final String newsTitle = intent.getStringExtra("newsTitle");
       final  String newsImage = intent.getStringExtra("newsImage");
        final String newsDate = intent.getStringExtra("newsDate");

        mNewsTitle.setText(newsTitle);
       mNewsDate.setText(newsDate);
        Picasso.with(this).load(newsImage).into(mNewsImage);

        mNewsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = ShareCompat.IntentBuilder.from(BreakingActivity.this)
                        .setType("text/plain")
                        .setText("Check out this awesome developer @\"" + newsTitle + "," + newsImage)
                        .getIntent();
                startActivity(shareIntent);
            }
        });

    }
}
