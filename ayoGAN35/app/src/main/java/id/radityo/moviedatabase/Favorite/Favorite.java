package id.radityo.moviedatabase.Favorite;

public class Favorite {
    private String posterPath;

    public Favorite(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
