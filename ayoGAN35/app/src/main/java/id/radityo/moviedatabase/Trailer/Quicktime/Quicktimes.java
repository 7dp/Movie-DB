package id.radityo.moviedatabase.Trailer.Quicktime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Quicktimes extends RealmObject {
    @PrimaryKey
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("type")
    @Expose
    public String type;
    private int movieId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
