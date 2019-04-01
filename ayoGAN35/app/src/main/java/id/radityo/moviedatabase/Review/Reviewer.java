package id.radityo.moviedatabase.Review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Reviewer extends RealmObject {

    @PrimaryKey
    @SerializedName("id") //author's id type is String
    @Expose
    public String id;
    private int movieId;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("url")
    @Expose
    public String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    //    @SerializedName("id")
//    @Expose
//    public Integer pageId;
//    @SerializedName("page")
//    @Expose
//    public Integer page;
//    @SerializedName("results")
//    @Expose
//    public List<Reviewer> results = null;
//    @SerializedName("total_pages")
//    @Expose
//    public Integer totalPages;
//    @SerializedName("total_results")
//    @Expose
//    public Integer totalResults;


//    public class Result {
//
//        @SerializedName("author")
//        @Expose
//        public String author;
//        @SerializedName("content")
//        @Expose
//        public String content;
//        @SerializedName("id")
//        @Expose
//        public String id;
//        @SerializedName("url")
//        @Expose
//        public String url;
//
//    }

//    public Integer getPageId() {
//        return pageId;
//    }
//
//    public void setPageId(Integer pageId) {
//        this.pageId = pageId;
//    }
//
//    public Integer getPage() {
//        return page;
//    }
//
//    public void setPage(Integer page) {
//        this.page = page;
//    }
//
//    public List<Reviewer> getResults() {
//        return results;
//    }
//
//    public void setResults(List<Reviewer> results) {
//        this.results = results;
//    }
//
//    public Integer getTotalPages() {
//        return totalPages;
//    }
//
//    public void setTotalPages(Integer totalPages) {
//        this.totalPages = totalPages;
//    }
//
//    public Integer getTotalResults() {
//        return totalResults;
//    }
//
//    public void setTotalResults(Integer totalResults) {
//        this.totalResults = totalResults;
//    }

}