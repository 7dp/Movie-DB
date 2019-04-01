package id.radityo.moviedatabase.Trailer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.radityo.moviedatabase.Trailer.Quicktime.Quicktimes;
import id.radityo.moviedatabase.Trailer.Youtube.Youtubes;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Trailers extends RealmObject {

    @PrimaryKey
    @SerializedName("id")   //movieId => example = 299537
    @Expose
    public Integer id;

    @SerializedName("quicktime")
    @Expose
    public RealmList<Quicktimes> quicktime = null;

    @SerializedName("youtube")
    @Expose
    public RealmList<Youtubes> youtube = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RealmList<Quicktimes> getQuicktime() {
        return quicktime;
    }

    public void setQuicktime(RealmList<Quicktimes> quicktime) {
        this.quicktime = quicktime;
    }

    public RealmList<Youtubes> getYoutube() {
        return youtube;
    }

    public void setYoutube(RealmList<Youtubes> youtube) {
        this.youtube = youtube;
    }

    //    @PrimaryKey
//    @SerializedName("id")   //movieId => example = 299537
//    @Expose
//    public Integer id;
//    @SerializedName("quicktime")
//    @Expose
//    public List<Trailers> quicktime = null;
//    @SerializedName("youtube")
//    @Expose
//    public List<Trailers> youtube = null;
//
//    @SerializedName("name")
//    @Expose
//    public String name;
//    @SerializedName("size")
//    @Expose
//    public String size;
//    @SerializedName("source")
//    @Expose
//    public String source;
//    @SerializedName("type")
//    @Expose
//    public String type;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public List<Trailers> getQuicktime() {
//        return quicktime;
//    }
//
//    public void setQuicktime(List<Trailers> quicktime) {
//        this.quicktime = quicktime;
//    }
//
//    public List<Trailers> getYoutube() {
//        return youtube;
//    }
//
//    public void setYoutube(List<Trailers> youtube) {
//        this.youtube = youtube;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
}
