package com.qifan.movieapp;

public class MovieReviewsPOJO {
    String author;
    String review;

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }



    public MovieReviewsPOJO(String author, String review){
        this.author=author;
        this.review=review;
    }
}
