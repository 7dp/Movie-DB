package id.radityo.moviedatabase.Database;

import android.util.Log;

import java.util.List;

import id.radityo.moviedatabase.Review.Review;
import id.radityo.moviedatabase.Review.Reviewer;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {
    private static final String TAG = "movie";
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveObject(List<? extends RealmObject> object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public <T extends RealmObject> List<T> findAll(Class<T> clazz, String field, Integer id) {
        return realm.copyFromRealm(realm.where(clazz).equalTo(field, id).findAll());
    }

    public <T extends RealmObject> List<T> findAllTrailers(Class<T> clazz, String field, String name) {
        return realm.copyFromRealm(realm.where(clazz).equalTo(field, name).findAll());
    }

    public void save(final MovieModel movieModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e(TAG, "#REALM: database was created");
                    Number nowId = realm.where(MovieModel.class).max("timestamp");
                    int nextId;
                    if (nowId == null) {
                        nextId = 1;
                    } else {
                        nextId = nowId.intValue() + 1;
                    }
                    movieModel.setTimestamp(nextId);
                    realm.insertOrUpdate(movieModel);
                } else {
                    Log.e("movie", "#REALM: database not exist");
                }
            }
        });
    }

    //calling all data
    public RealmResults<MovieModel> getStoredMovie() {
        return realm.where(MovieModel.class).sort("timestamp", Sort.DESCENDING).findAll();
    }

    public List<Reviewer> getAllReviews() {
        RealmResults<Reviewer> results = realm.where(Reviewer.class).findAll();
        return results;
    }

    //updating data
    public void update(final int movieId,
                       final int duration,
                       final float rating,
                       final String title,
                       final String year,
                       final String posterPath,
                       final String sinopsis) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MovieModel model = realm.where(MovieModel.class)
                        .equalTo("movieId", movieId)
                        .findFirst();
                model.setTitle(title);
                model.setDuration(duration);
                model.setRating(rating);
                model.setYear(year);
                model.setPosterPath(posterPath);
                model.setSinopsis(sinopsis);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "#REALM : update success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    //deleting data
    public void delete(final int movieId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<MovieModel> results = realm.where(MovieModel.class).equalTo("movieId", movieId).findAll();
                for (int i = 0; i < results.size(); i++) {
                    MovieModel model = results.get(i);
                    model.deleteFromRealm();
                }
            }
        });
    }
}
