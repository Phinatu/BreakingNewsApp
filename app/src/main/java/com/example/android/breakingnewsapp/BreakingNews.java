package com.example.android.breakingnewsapp;


/**
 * Created by Phinatu on 05/09/2017.
 */

public class BreakingNews {
    private String mNewsImage;
    private String mNewsTitle;
    private String mNewsDate;

public BreakingNews(String mNewsImage, String mNewsTitle, String mNewsDate) {
     this.mNewsImage = mNewsImage;
     this.mNewsTitle = mNewsTitle;
     this.mNewsDate = mNewsDate;
}

    public void setmNewsImage(String mNewsImage) {

        this.mNewsImage = mNewsImage;
    }

    public  String getmNewsImage() {

        return mNewsImage;
        }


    public void setmNewsTitle(String mNewsTitle) {

        this.mNewsTitle = mNewsTitle;
    }

    public  String getmNewsTitle()
    {
        return mNewsTitle;
    }


    public void setmNewsDate(String mNewsDate)
    {
        this.mNewsDate = mNewsDate;
    }

    public  String getmNewsDate()
    {
        return mNewsDate;
    }

}