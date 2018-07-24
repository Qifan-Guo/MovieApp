package com.qifan.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qifan.movieapp.Utility.HttpUtil;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewHolder> {
    private Context mContext;
    private  static List<MovieReviewsPOJO> reviewsPOJOList;

    public ReviewListAdapter(Context context) {
        mContext = context;

    }

    public void setData(){
        reviewsPOJOList=HttpUtil.ReviewerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);

        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        MovieReviewsPOJO movieReviewsPOJO = reviewsPOJOList.get(holder.getAdapterPosition());
        holder.author.setText(movieReviewsPOJO.getAuthor()+":");
        holder.review.setText(movieReviewsPOJO.getReview()+"\n");

    }

    @Override
    public int getItemCount() {
        if (reviewsPOJOList == null) {
            return 0;
        } else {
            return reviewsPOJOList.size();
        }

    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView review;

        public ReviewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            review = itemView.findViewById(R.id.review);

        }


    }
}
