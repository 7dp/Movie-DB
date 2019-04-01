package id.radityo.moviedatabase.Database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import id.radityo.moviedatabase.Review.Reviewer;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class MovieModel extends RealmObject {

    @PrimaryKey
    private int movieId;
    private int duration;
    private float rating;
    private String title;
    private String year, sinopsis, posterPath;
    private boolean existing;
    private int timestamp ;
//    private RealmList<Reviewer> reviewsList;
//    @Index

    public MovieModel() {
    }

//    public RealmList<Reviewer> getReviewsList() {
//        return reviewsList;
//    }
//
//    public void setReviewsList(RealmList<Reviewer> reviewsList) {
//        this.reviewsList = reviewsList;
//    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
