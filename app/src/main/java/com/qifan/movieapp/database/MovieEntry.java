package com.qifan.movieapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.lang.reflect.Array;

@Entity(tableName = "movie")
public class MovieEntry{


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String posterURL;
    private String topRate;
    private String popularity;
    private String language;
    private Boolean favorite;
    private String releaseDate;
    private String overview;
    private String sortBy;
    private int movieID;

    //This constructor writes to the database, so it does not know what the id would be
    @Ignore
    public MovieEntry(String title, String posterURL, String topRate, String popularity, String language, Boolean favorite, String releaseDate, String overview, String sortBy,int movieID) {
        this.title = title;
        this.posterURL = posterURL;
        this.topRate = topRate;
        this.popularity = popularity;
        this.language = language;
        this.favorite = favorite;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.sortBy = sortBy;
        this.movieID =movieID;
    }

    // when read from the database then id would be required
    public MovieEntry(int id, String title, String posterURL, String topRate, String popularity, String language, Boolean favorite, String releaseDate, String overview, String sortBy,int movieID) {
        this.id = id;
        this.title = title;
        this.posterURL = posterURL;
        this.topRate = topRate;
        this.popularity = popularity;
        this.language = language;
        this.favorite = favorite;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.sortBy = sortBy;
        this.movieID =movieID;

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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    @NonNull
    public String getTopRate1() {
        Double double_topRate = Double.parseDouble(topRate);
        return
                "Top Rate: " + String.format("%.2f", double_topRate);
    }

    @NonNull
    public String getPopularity1() {
        Double double_popularity = Double.parseDouble(popularity);
        return
                "Popularity: " + String.format("%.0f", double_popularity);
    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }
////    private int id;
////    private String title;
////    private String posterURL;
////    private String topRate;
////    private String popularity;
////    private String language;
////    private Boolean favorite;
////    private String releaseDate;
////    private String overview;
////    private String sortBy;
////    private int movieID;
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//            dest.writeInt(id);
//            dest.writeString(title);
//            dest.writeString(posterURL);
//            dest.writeString(topRate);
//            dest.writeString(popularity);
//            dest.writeString(language);
//            dest.writeByte((byte) (favorite ? 1 : 0));
//            dest.writeString(releaseDate);
//            dest.writeString(overview);
//            dest.writeString(sortBy);
//            dest.writeInt(movieID);
//    }
//
//    public static final Parcelable.Creator<MovieEntry> CREATOR
//            = new Parcelable.Creator<MovieEntry>() {
//        public MovieEntry createFromParcel(Parcel in) {
//            return new MovieEntry(in);
//        }
//
//        public MovieEntry[] newArray(int size) {
//            return new MovieEntry[size];
//        }
//    };
//    private MovieEntry(Parcel in) {
//        id = in.readInt();
//        title=in.readString();
//        posterURL=in.readString();
//        topRate=in.readString();
//        popularity=in.readString();
//        language=in.readString();
//        releaseDate=in.readString();
//        overview=in.readString();
//        sortBy=in.readString();
//        movieID=in.readInt();
//        favorite = in.readByte() != 0;
//
//    }

}
