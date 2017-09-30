package com.example.android.breakingnewsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static java.security.AccessController.getContext;

/**
 * Created by Phinatu on 05/09/2017.
 */

public class BreakingNewsAdapter extends RecyclerView.Adapter<BreakingNewsAdapter.BreakingNewsViewHolder> {
    List<BreakingNews> mList;

    private ListItemClickListener mOnclickListener;

    public BreakingNewsAdapter(List<BreakingNews> breakingNews, ListItemClickListener itemClickListener) {
        mOnclickListener = itemClickListener;
        mList = breakingNews;

    }

    @Override
    public BreakingNewsAdapter.BreakingNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.breaking_list_item, parent, false);

        return new BreakingNewsViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(BreakingNewsAdapter.BreakingNewsViewHolder holder, int position) {
        final BreakingNews breakingNews = mList.get(position);
        holder.mNews.setText(breakingNews.getmNewsTitle());
        Picasso.with(holder.itemView.getContext()).load(breakingNews.getmNewsDate()).into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(BreakingNews clickedItem);
    }

    public class BreakingNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImage;
        private TextView mNews;

        public BreakingNewsViewHolder(View v) {
            super(v);

            mImage = (ImageView) v.findViewById(R.id.image);
            mNews = (TextView) v.findViewById(R.id.news);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnclickListener.onListItemClick(mList.get(clickedPosition));
        }
    }

}