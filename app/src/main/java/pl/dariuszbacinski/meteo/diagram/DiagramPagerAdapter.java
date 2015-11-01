package pl.dariuszbacinski.meteo.diagram;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import pl.dariuszbacinski.meteo.location.LocationTransformation;

public class DiagramPagerAdapter extends FragmentStatePagerAdapter {

    private LocationTransformation locationTransformation;

    public DiagramPagerAdapter(FragmentManager fm, LocationTransformation locationTransformation) {
        super(fm);
        this.locationTransformation = locationTransformation;
    }

    @Override
    public Fragment getItem(int position) {
        DiagramCoordinates diagramCoordinates = new DiagramCoordinates(locationTransformation.extractLocationAtPosition(position).getCol(), locationTransformation.extractLocationAtPosition(position).getRow());
        return DiagramFragment.newInstance(diagramCoordinates);
    }

    @Override
    public int getCount() {
        return locationTransformation.getFavoriteLocationCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return locationTransformation.extractLocationAtPosition(position).getName();
    }

    public void setLocations(LocationTransformation locationTransformation) {
        this.locationTransformation = locationTransformation;
        notifyDataSetChanged();
    }

    public int getItemPosition(Object object) {
        //XXX Hack to reload all fragments on notifyDataSetChanged()
        return POSITION_NONE;
    }
}
