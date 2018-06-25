package com.qifan.movieapp.Beans;

public class Movie_Obj {

   private String posterURL;
   private String popularity;
    private String topRate;

public Movie_Obj(String poster,String popularity,String topRate){
    this.posterURL=poster;
    this.popularity=popularity;
    this.topRate=topRate;
}

    public String getTopRate() {
        return topRate;
    }
    public String getPosterURL() {
        return posterURL;
    }



    public String getPopularity() {
        return popularity;
    }



}