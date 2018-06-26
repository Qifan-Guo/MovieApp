package com.qifan.movieapp.Beans;

public class Movie_Obj {

   private String posterURL;
   private String popularity;
    private String topRate;
    private String title;

public Movie_Obj(String poster,String popularity,String topRate,String title){
    this.posterURL=poster;
    this.popularity=popularity;
    this.topRate=topRate;
    this.title=title;
}


    public String getPosterURL() {
        return posterURL;
    }


    public String getTopRate() {
        return
                "Viewer Rate: "+topRate;
    }
    public String getPopularity() {
        return
                "Popularity: "+popularity;
    }
    public String getTitle() { return title; }



}