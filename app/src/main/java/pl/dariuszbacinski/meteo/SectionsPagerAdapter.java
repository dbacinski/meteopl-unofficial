package pl.dariuszbacinski.meteo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private FavoriteWeatherDataProvider favoriteWeatherDataProvider;
    private CurrentDateProvider currentDateProvider;

    public SectionsPagerAdapter(FragmentManager fm, FavoriteWeatherDataProvider favoriteWeatherDataProvider, CurrentDateProvider currentDateProvider) {
        super(fm);
        this.favoriteWeatherDataProvider = favoriteWeatherDataProvider;
        this.currentDateProvider = currentDateProvider;
    }

    @Override
    public Fragment getItem(int position) {
        DiagramCoordinates diagramCoordinates = new DiagramCoordinates(currentDateProvider.getCurrentDate(),favoriteWeatherDataProvider.getFavoriteLocation(position).getCol(), favoriteWeatherDataProvider.getFavoriteLocation(position).getRow());
        return WeatherDiagramFragment.newInstance(diagramCoordinates);
    }

    @Override
    public int getCount() {
        return favoriteWeatherDataProvider.getFavoriteLocationCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return favoriteWeatherDataProvider.getFavoriteLocation(position).getName();
    }
}
