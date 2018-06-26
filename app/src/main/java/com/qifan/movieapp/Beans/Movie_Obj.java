package com.qifan.movieapp.Beans;

import java.io.Serializable;

public class Movie_Obj implements Serializable{



    private final String language;
    private final String overview;
    private final String release_date;
    private String posterURL;
   private String popularity;
    private String topRate;
    private String title;

public Movie_Obj(String poster,String popularity,String topRate,
                 String title,String language,String overview,String release_date){
    this.language=language;
    this.overview=overview;
    this.release_date =release_date;
    this.posterURL=poster;
    this.popularity=popularity;
    this.topRate=topRate;
    this.title=title;
}

    public String getLanguage() {
        return language;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
    public String getPosterURL() {
        return posterURL;
    }


    public String getTopRate() {
    Double double_topRate = Double.parseDouble(topRate);
        return
               "Top Rate: "+ String.format("%.2f",double_topRate);
    }
    public String getPopularity() {
    Double double_popularity=Double.parseDouble(popularity);
        return
               "Popularity: "+ String.format("%.0f",double_popularity);
    }
    public String getTitle() { return title; }



}