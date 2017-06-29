package com.example.sadanandk.moviereviews;

/**
 * Created by maheshs on 6/28/2017.
 */

public class PojoReview
{
    String author;
    String review;
    String totalresults;


    public String getTotalresults() {
        return totalresults;
    }

    public void setTotalresults(String totalresults) {
        this.totalresults = totalresults;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
