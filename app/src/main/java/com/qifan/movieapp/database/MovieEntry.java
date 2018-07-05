package com.qifan.movieapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {

    private String title;
    private String posterURL;
    private String topRate;
    private String popularity;
    private String language;
    private Boolean favorite;
    private String releaseDate;
    private String overview;
    @PrimaryKey(autoGenerate = true)
    private int id;
//This constructor writes to the database, so it does not know what the id would be
    @Ignore
    public MovieEntry(String title, String posterURL, String topRate, String popularity, String language, Boolean favorite, String releaseDate, String overview) {
        this.title = title;
        this.posterURL = posterURL;
        this.topRate = topRate;
        this.popularity = popularity;
        this.language = language;
        this.favorite = favorite;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }
// when read from the database then id would be required
    public MovieEntry(String title, String posterURL, String topRate, String popularity, String language, Boolean favorite, String releaseDate, String overview, int id) {
        this.title = title;
        this.posterURL = posterURL;
        this.topRate = topRate;
        this.popularity = popularity;
        this.language = language;
        this.favorite = favorite;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public void setTopRate(String topRate) {
        this.topRate = topRate;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getTopRate() {
        return topRate;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }
}
