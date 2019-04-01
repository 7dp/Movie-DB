package id.radityo.moviedatabase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.radityo.moviedatabase.Favorite.TabFavorite;
import id.radityo.moviedatabase.Popular.TabPopular;
import id.radityo.moviedatabase.TopRated.TabRated;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabCount;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                TabPopular tabPopular = new TabPopular();
                return tabPopular;
            case 1:
                TabRated tabRated = new TabRated();
                return tabRated;
            case 2:
                TabFavorite tabFavorite = new TabFavorite();
                return tabFavorite;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
