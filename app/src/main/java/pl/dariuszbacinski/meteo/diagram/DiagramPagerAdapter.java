package pl.dariuszbacinski.meteo.diagram;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

import pl.dariuszbacinski.meteo.location.Location;

public class DiagramPagerAdapter extends FragmentStatePagerAdapter {

    private List<Location> favoriteLocations;

    public DiagramPagerAdapter(FragmentManager fm, List<Location> favoriteLocations) {
        super(fm);
        this.favoriteLocations = favoriteLocations;
    }

    @Override
    public Fragment getItem(int position) {
        Location location = favoriteLocations.get(position);
        DiagramCoordinates diagramCoordinates = new DiagramCoordinates(location.getCol(), location.getRow());
        return DiagramFragment.newInstance(diagramCoordinates);
    }

    @Override
    public int getCount() {
        return favoriteLocations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return favoriteLocations.get(position).getName();
    }

    public void setFavoriteLocations(List<Location> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
        notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        //XXX Hack to reload all fragments on notifyDataSetChanged()
        return POSITION_NONE;
    }
}
