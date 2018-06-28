package com.qifan.movieapp.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MovieObj implements Parcelable {



    private final String language;
    private final String overview;
    private final String release_date;
    private String posterURL;
   private String popularity;
    private String topRate;
    private String title;

public MovieObj(String poster, String popularity, String topRate,
                String title, String language, String overview, String release_date){
    this.language=language;
    this.overview=overview;
    this.release_date =release_date;
    this.posterURL=poster;
    this.popularity=popularity;
    this.topRate=topRate;
    this.title=title;
}

    protected MovieObj(Parcel in) {
        language = in.readString();
        overview = in.readString();
        release_date = in.readString();
        posterURL = in.readString();
        popularity = in.readString();
        topRate = in.readString();
        title = in.readString();
    }

    public static final Creator<MovieObj> CREATOR = new Creator<MovieObj>() {
        @Override
        public MovieObj createFromParcel(Parcel in) {
            return new MovieObj(in);
        }

        @Override
        public MovieObj[] newArray(int size) {
            return new MovieObj[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(language);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(posterURL);
        dest.writeString(popularity);
        dest.writeString(topRate);
        dest.writeString(title);
    }
}