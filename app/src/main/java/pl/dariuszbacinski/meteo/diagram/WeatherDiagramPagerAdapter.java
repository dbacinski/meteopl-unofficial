package pl.dariuszbacinski.meteo.diagram;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import pl.dariuszbacinski.meteo.location.LocationTransformation;

public class WeatherDiagramPagerAdapter extends FragmentPagerAdapter {

    private LocationTransformation locationTransformation;
    private CurrentDateProvider currentDateProvider;

    public WeatherDiagramPagerAdapter(FragmentManager fm, LocationTransformation locationTransformation, CurrentDateProvider currentDateProvider) {
        super(fm);
        this.locationTransformation = locationTransformation;
        this.currentDateProvider = currentDateProvider;
    }

    @Override
    public Fragment getItem(int position) {
        DiagramCoordinates diagramCoordinates = new DiagramCoordinates(currentDateProvider.getCurrentDate(), locationTransformation.extractLocationAtPosition(position).getCol(), locationTransformation.extractLocationAtPosition(position).getRow());
        return WeatherDiagramFragment.newInstance(diagramCoordinates);
    }

    @Override
    public int getCount() {
        return locationTransformation.getFavoriteLocationCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return locationTransformation.extractLocationAtPosition(position).getName();
    }
}
