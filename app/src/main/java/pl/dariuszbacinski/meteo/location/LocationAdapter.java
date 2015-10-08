package pl.dariuszbacinski.meteo.location;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter {
    private MultiSelector multiSelector;
    private List<Location> locationList;

    public LocationAdapter(LocationRepository locationRepository, MultiSelector multiSelector, FavoriteLocationRepository favoriteLocationRepository) {
        locationList = locationRepository.findAll();
        this.multiSelector = multiSelector;
        multiSelector.setSelectable(true);
        restoreSelectedItems(multiSelector, favoriteLocationRepository.findAll());
    }

    //TODO can be extracted
    private void restoreSelectedItems(MultiSelector multiSelector, List<FavoriteLocation> favoriteLocationList) {
        List<Location> selectedLocations = new LocationTransformation(favoriteLocationList).extractLocations();

        for (Location selectedLocation : selectedLocations) {
            int position = locationList.indexOf(selectedLocation);
            multiSelector.setSelected(position, -1, true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        return new LocationViewHolder((CheckedTextView) view, multiSelector);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Location location = locationList.get(position);
        LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
        locationViewHolder.bindName(location.getName());
        locationViewHolder.bindSelected(multiSelector.isSelected(position, -1));
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public List<FavoriteLocation> getFavoritePositions() {
        return new FavoriteLocationTransformation(locationList).filter(multiSelector.getSelectedPositions());
    }
}
